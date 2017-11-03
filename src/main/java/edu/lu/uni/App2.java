package edu.lu.uni;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lu.uni.deeplearning.extractor.FeatureExtractor2;
import edu.lu.uni.feature.exporter.FeatureExporter;
import edu.lu.uni.serval.utils.FileHelper;

public class App2 {

	private static Logger logger = LoggerFactory.getLogger(App2.class);
	
	public static void main(String[] args) {
		App2 example = new App2();
		try {
			logger.info("****************Start to extract features by CNN****************\n");
			example.extractFeatures2();
			example.exportFeaturesByProjects();
			logger.info("****************Finish off extracting features by CNN****************\n");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Extract features of method bodies by deep learning with the CNN algorithm.
	 * The input data: the results of token embedding.
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void extractFeatures2() throws FileNotFoundException, IOException, InterruptedException {
		String fileExtension = Configuration.DIGITAL_DATA_FILE_EXTENSION;
		List<File> inputFiles = FileHelper.getAllFilesInCurrentDiectory("../OUTPUT/commons/", fileExtension);
		
		File inputFile = null;
		for (File file : inputFiles) {
			if (file.getName().startsWith("vectorized_tokensSIZE")) {
				inputFile = file;
			}
		}
		if (inputFile == null) {
			System.err.println("Cannot find the input data.");
			return;
		}
		String fileName = inputFile .getName();
		int sizeOfEmbeddedVector = Configuration.SIZE_OF_EMBEDDED_VECTOR;
		int sizeOfTokensVector = Integer.parseInt(fileName.substring(fileName.lastIndexOf("=") + 1, fileName.lastIndexOf(fileExtension)));
		int batchSize = 64;//Configuration.BATCH_SIZE;
		int sizeOfFeatureVector = Configuration.SIZE_OF_FEATURE_VECTOR;  // size of vectors of extracted features.

		String outputPath = "../OUTPUT/commons/CNN_extracted_feature/";
		// Clear existing output data generated at the last time.
		FileHelper.deleteDirectory(outputPath);
		
		FeatureExtractor2 extractor = new FeatureExtractor2(inputFile, sizeOfTokensVector, sizeOfEmbeddedVector, batchSize, sizeOfFeatureVector);
		extractor.setNumberOfEpochs(Configuration.N_EPOCHS);
		extractor.setSeed(123);
		extractor.setNumOfOutOfLayer1(20);
		extractor.setNumOfOutOfLayer2(50);
		extractor.setOutputPath(outputPath);
		
		extractor.extracteFeaturesWithCNN();
	}
	
	public void exportFeaturesByProjects() {
		String fileExtension = Configuration.DIGITAL_DATA_FILE_EXTENSION;
		List<File> inputFiles = FileHelper.getAllFiles("../OUTPUT/commons/CNN_extracted_feature/", fileExtension);
		String methodInfoFiles = "../OUTPUT/commons/selected_method_tokens.list";
		String outputPath = "../OUTPUT/commons/extracted_features_by_project/";
		// Clear existing output data generated at the last time.
		FileHelper.deleteDirectory(outputPath);
		
		for (File inputFile : inputFiles) {
			FeatureExporter exporter = new FeatureExporter(inputFile, methodInfoFiles, outputPath, Configuration.STRING_DATA_FILE_EXTENSION);
			exporter.exportFeatureByProjects();
			System.out.println(inputFile.getName());
		}
	}
}
