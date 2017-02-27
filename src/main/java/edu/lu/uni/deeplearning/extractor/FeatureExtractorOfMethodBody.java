package edu.lu.uni.deeplearning.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lu.uni.util.FileHelper;

public class FeatureExtractorOfMethodBody {
	
	private static Logger log = LoggerFactory.getLogger(FeatureExtractorOfMethodBody.class);
//	private static final String DATA_FILE_PATH = "outputData/Standardization/method-body/";
	private static final String DATA_FILE_PATH = "outputData/WithoutNormalization/method-body/";
//	private static final String DATA_FILE_PATH = "outputData/Normalization/method-body/";
	private static final String INTEGER_FEATURE_FILE_PATH = "inputData/unsupervised-learning/method-body/";
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		List<File> files = FileHelper.getAllFiles(DATA_FILE_PATH, ".csv");
		for (File file : files) {
			String fileName = file.getName();
			int sizeOfVector = Integer.parseInt(fileName.substring(fileName.lastIndexOf("=") + 1, fileName.lastIndexOf(".csv")));
			int batchSize = 1000;
			
//			if (fileName.contains("feature-ast-node-name-with-node-label")) {
//				batchSize = 4713;
//			} else if (fileName.contains("feature-only-ast-node-name")) {
//				batchSize = 4713;
//			} else if (fileName.contains("feature-raw-tokens-with-operators")) {
//				batchSize = 4702;
//			} else if (fileName.contains("feature-raw-tokens-without-operators")) {
//				batchSize = 500;
//			} else if (fileName.contains("feature-statement-node-name-with-all-node-label")) {
//				batchSize = 500;
//			}
			
			extracteFeaturesWithCNN(file, sizeOfVector, batchSize); 
		}
		
		
	}
	
	private static void extracteFeaturesWithCNN(File file, int sizeOfVector, int batchSize) throws FileNotFoundException, IOException, InterruptedException {
		
		int nChannels = 1;   // Number of input channels
        int outputNum = sizeOfVector; // The number of possible outcomes
        
        int nEpochs = 1;     // Number of training epochs
        int iterations = 1;  // Number of training iterations. 
        					 // Multiple iterations are generally only used when doing full-batch training on very small data sets.
        int seed = 123;      //
        int sizeOfFeatureVector = 300;

        log.info("Load data....");
        RecordReader trainingDataReader = new CSVRecordReader();
        trainingDataReader.initialize(new FileSplit(file));
        DataSetIterator trainingDataIter = new RecordReaderDataSetIterator(trainingDataReader,batchSize);
        
        /*
         *  Construct the neural network
         */
        log.info("Build model....");
        MultiLayerConfiguration.Builder builder = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(iterations) // Training iterations as above
                .regularization(true).l2(0.0005)
                /**
                 * Some simple advice is to start by trying three different learning rates – 1e-1, 1e-3, and 1e-6 
                 */
                .learningRate(.01)//.biasLearningRate(0.02)
                //.learningRateDecayPolicy(LearningRatePolicy.Inverse).lrPolicyDecayRate(0.001).lrPolicyPower(0.75)
                /**
                 * XAVIER weight initialization is usually a good choice for this. 
                 * For networks with rectified linear (relu) or leaky relu activations, 
                 * RELU weight initialization is a sensible choice.
                 */
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .list()
                .layer(0, new ConvolutionLayer.Builder(1, 3)
                        //nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied
                        .nIn(nChannels)
                        .stride(1, 1)
                        .nOut(20)
                        .activation("identity")
                        .build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(1,2)
                        .stride(1,2)
                        .build())
                .layer(2, new ConvolutionLayer.Builder(1, 3)
                        .stride(1, 1)
                        .nOut(50)
                        .activation("identity")
                        .build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(1,2)
                        .stride(1,2)
                        .build())
                .layer(4, new DenseLayer.Builder().activation("relu")
                        .nOut(sizeOfFeatureVector).build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR)
                        .nOut(outputNum)
                        .activation("softmax")
                        .build())
                .setInputType(InputType.convolutionalFlat(1,sizeOfVector,1))
                .backprop(true).pretrain(false);

        MultiLayerConfiguration conf = builder.build();
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();


        StringBuilder features = new StringBuilder();
        
        log.info("Train model....");
        model.setListeners(new ScoreIterationListener(1));
        for( int i=0; i<nEpochs; i++ ) {
        	while (trainingDataIter.hasNext()) {
        		DataSet next = trainingDataIter.next();
                model.fit(new DataSet(next.getFeatureMatrix(),next.getFeatureMatrix()));
                INDArray input = model.getOutputLayer().input();
            	features.append(input + "\n");
        	}
//            model.fit(trainingData);
            // During the process of fitting, each training instance is used to calibrate the parameters of neural network.
            log.info("*** Completed epoch {} ***", i);
        }
        log.info("****************Example finished********************");
        
//        int layerIndex = 0;
        String fileName = file.getPath().replace("outputData/", "outputData/CNN/");
    	FileHelper.createFile(new File(fileName), 
    			features.toString().replace("[[", "").replaceAll("\\],", "")
//    			.replaceAll(" \\[", "").replace("]]", ""));
    			.replaceAll(" \\[", "").replace("]]", "").replace(",", "").replace(" ", ", "));
        
//        addMethodNameToFeatures(fileName);
	}

	public static void addMethodNameToFeatures(String file) throws IOException {
		List<File> integerFeatureFiles = FileHelper.getAllFiles(INTEGER_FEATURE_FILE_PATH, ".list");
		
		for (File integerFeatureFile : integerFeatureFiles) {
			String fileName = integerFeatureFile.getName();
			if (file.contains(fileName.substring(0, fileName.lastIndexOf(".list")))) {
				addMethodName(file, integerFeatureFile);
				break;
			}
		}
	}

	private static void addMethodName(String file, File integerFeatureFile) throws IOException {
		String features = FileHelper.readFile(new File(file));
		String methodNames = FileHelper.readFile(integerFeatureFile);
		BufferedReader br1 = new BufferedReader(new StringReader(features));
		BufferedReader br2 = new BufferedReader(new StringReader(methodNames));
		String featureLine = null;
		String methodNameLine = null;
		StringBuilder content = new StringBuilder();
		
		while ((featureLine = br1.readLine()) != null && (methodNameLine = br2.readLine()) != null) {
			int indexOfHarshKey = methodNameLine.indexOf("#");
			
			if (indexOfHarshKey < 0) {
				log.error("The below raw feature is invalid!\n" + methodNameLine);
				continue;
			}
			
			String methodName = methodNameLine.substring(0, indexOfHarshKey + 1);
			content.append(methodName + "[" + featureLine + "]\n");
		}
		
		FileHelper.createFile(new File(file.replace(".csv", ".list")), content.toString());
	}
	
}
