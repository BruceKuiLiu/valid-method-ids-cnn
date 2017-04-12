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
import org.nd4j.jita.conf.CudaEnvironment;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.util.DataTypeUtil;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lu.uni.util.FileHelper;

/**
 * Extract features of method body by deep learning with the CNN algorithm.
 * @author kui.liu
 *
 */
public class FeatureExtractorGPU {
	
	private static Logger log = LoggerFactory.getLogger(FeatureExtractorGPU.class);
	
	private File inputFile;
    int sizeOfVector = 0;    // The vector size of each instance.
    int sizeOfCodeVec = 0;   // The vector size of each instance.
	private int batchSize;
	private int sizeOfFeatureVector; // The size of feature vector, which is the extracted features of each instance.
	
	private final int nChannels = 1; // Number of input channels.
	private final int iterations = 1;// Number of training iterations. 
	                                 // Multiple iterations are generally only used when doing full-batch training on very small data sets.
	private int nEpochs = 1;         // Number of training epochs
	private int seed = 123;
	
	private int numOfOutOfLayer1 = 20;
	private int numOfOutOfLayer2 = 50;
	
	private int outputNum; // The number of possible outcomes
	
	private String inputPath;
	private String outputPath;
	
	public FeatureExtractorGPU(File inputFile, int sizeOfVector, int sizeOfCodeVec, int batchSize, int sizeOfFeatureVector) {
		this.inputFile = inputFile;
		this.sizeOfVector = sizeOfVector;
		this.sizeOfCodeVec = sizeOfCodeVec;
		this.batchSize = batchSize;
		this.sizeOfFeatureVector = sizeOfFeatureVector;
		/*
		 * If the deep learning is unsupervised learning, the number of outcomes is the size of input vector.
		 * If the deep learning is supervised learning, the number of outcomes is the number of classes.
		 */
		outputNum = sizeOfVector * sizeOfCodeVec;
		inputPath = inputFile.getParent();
		inputPath = inputPath.substring(0, inputPath.lastIndexOf("/") + 1);
	}
	
	public void setNumberOfEpochs(int nEpochs) {
		this.nEpochs = nEpochs;
	}
	
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public void setNumOfOutOfLayer1(int numOfOutOfLayer1) {
		this.numOfOutOfLayer1 = numOfOutOfLayer1;
	}

	public void setNumOfOutOfLayer2(int numOfOutOfLayer2) {
		this.numOfOutOfLayer2 = numOfOutOfLayer2;
	}
	
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	public void extracteFeaturesWithCNN() throws FileNotFoundException, IOException, InterruptedException {
		// PLEASE NOTE: For CUDA FP16 precision support is available
        DataTypeUtil.setDTypeForContext(DataBuffer.Type.HALF);

        // temp workaround for backend initialization

        CudaEnvironment.getInstance().getConfiguration()
            // key option enabled
            .allowMultiGPU(true)

            // we're allowing larger memory caches
            .setMaximumDeviceCache(2L * 1024L * 1024L * 1024L)

            // cross-device access is used for faster model averaging over pcie
            .allowCrossDeviceAccess(true);
        
        log.info("Load data....");
        RecordReader trainingDataReader = new CSVRecordReader();
        trainingDataReader.initialize(new FileSplit(inputFile));
        DataSetIterator trainingDataIter = new RecordReaderDataSetIterator(trainingDataReader, batchSize);
        
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
                .layer(0, new ConvolutionLayer.Builder(1, sizeOfCodeVec)
                        //nIn and nOut specify depth. nIn here is the nChannels and nOut is the number of filters to be applied
                        .nIn(nChannels)
                        .stride(1, 1)
                        .nOut(numOfOutOfLayer1)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2,1)
                        .stride(2,1)
                        .build())
                .layer(2, new ConvolutionLayer.Builder(3, 1)
                        .stride(1, 1)
                        .nOut(numOfOutOfLayer2)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2,1)
                        .stride(2,1)
                        .build())
                .layer(4, new DenseLayer.Builder().activation(Activation.RELU)
                        .nOut(sizeOfFeatureVector).build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutionalFlat(sizeOfVector,sizeOfCodeVec,1))
                .backprop(true).pretrain(false);

        MultiLayerConfiguration conf = builder.build();
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        // ParallelWrapper will take care of load balancing between GPUs.
        @SuppressWarnings({ "unchecked", "rawtypes" })
		ParallelWrapper wrapper = new ParallelWrapper.Builder(model, nEpochs)
            // DataSets prefetching options. Set this value with respect to number of actual devices
            .prefetchBuffer(4)

            // set number of workers equal or higher then number of available devices. x1-x2 are good values to start with
            .workers(4)

            // rare averaging improves performance, but might reduce model accuracy
            .averagingFrequency(3)

            // if set to TRUE, on every averaging model score will be reported
            .reportScoreAfterAveraging(true)

            // optinal parameter, set to false ONLY if your system has support P2P memory access across PCIe (hint: AWS do not support P2P)
            .useLegacyAveraging(true)

            .build();
        
        StringBuilder features = new StringBuilder();
        
        log.info("Train model....");
        model.setListeners(new ScoreIterationListener(1));
        
        String fileName = inputFile.getPath().replace(inputPath, outputPath);
        int batchers = 0;
        for( int i=0; i<nEpochs; i++ ) {
        	// Please note: we're feeding ParallelWrapper with iterator, not model directly
    		wrapper.fit(trainingDataIter, i, batchSize, fileName);
    		
//    		if (i == nEpochs - 1) {
//    			MultiLayerNetwork mo = (MultiLayerNetwork) wrapper.model;
//            	INDArray input = mo.getOutputLayer().input();
//            	features.append(input.toString().replace("[[", "").replaceAll("\\],", "")
//            			.replaceAll(" \\[", "").replace("]]", "") + "\n");
//            	
//            	batchers ++;
//            	if ((batchers * batchSize) >= 100000) {
//            		FileHelper.outputToFile(fileName, features, true);
//            		features.setLength(0);
//            	}
//            }
            log.info("*** Completed epoch {} ***", i);
        }
        wrapper.shutdown();
        
        log.info("****************Extracting features finished****************");
        
    	FileHelper.outputToFile(fileName, features, true);
	}

	public void addMethodNameToFeatures(String file, String filePath) throws IOException {
		// filePath = "OUTPUT/encoding/encoded_method_bodies/";
		List<File> integerFeatureFiles = FileHelper.getAllFiles(filePath, ".list");
		
		for (File integerFeatureFile : integerFeatureFiles) {
			String fileName = integerFeatureFile.getName();
			if (file.contains(fileName.substring(0, fileName.lastIndexOf(".list")))) {
				addMethodName(file, integerFeatureFile);
				break;
			}
		}
	}

	private void addMethodName(String file, File integerFeatureFile) throws IOException {
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
		
		FileHelper.outputToFile(file.replace(".csv", ".list"), content, false);
	}

}
