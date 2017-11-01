package edu.lu.uni;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lu.uni.data.preparing.Standardization;
import edu.lu.uni.data.preparing.ZeroAppender;
import edu.lu.uni.deeplearning.extractor.FeatureExtractor;
import edu.lu.uni.deeplearning.extractor.FeatureExtractor2;
import edu.lu.uni.feature.exporter.FeatureExporter;
import edu.lu.uni.serval.utils.FileHelper;

public class App {

	private static Logger logger = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		App example = new App();
		try {
			logger.info("****************Start to extract features by CNN****************\n");
//			// data preprocessing
//			example.appendZero();
//			example.standardizeData();
			// feature extracting: deep learning with the CNN algorithm
//			example.extractFeatureWithCNN();
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
	 * Append zero to the integer vectors of which sizes are less than the max size,
	 * to make all the lengths of vectors consistent and equal to the max size.
	 * @throws IOException
	 */
	@Deprecated
	public void appendZero() throws IOException {
		String inputFilePath = Configuration.ENCODED_METHOD_BODY_FILE_PATH;
		String inputFileExtension = Configuration.STRING_DATA_FILE_EXTENSION;
		List<File> integerVectorsFiles = FileHelper.getAllFiles(inputFilePath, inputFileExtension);

		String outputFileExtension = Configuration.DIGITAL_DATA_FILE_EXTENSION;
		String outputFilePath = Configuration.DATA_APPZENDED_ZERO;
		// Clear existing output data generated at the last time.
		FileHelper.deleteDirectory(outputFilePath);
		
		ZeroAppender appender = new ZeroAppender();
		for (File file : integerVectorsFiles) {
			appender.appendZeroForVectors(file, inputFilePath, outputFilePath, inputFileExtension, outputFileExtension);
		}
	}

	/**
	 * Standardize the data of integer vectors.
	 */
	@Deprecated
	public void standardizeData() {
		String inputFilePath = Configuration.ENCODED_METHOD_BODY_FILE_PATH;
		String fileExtension = Configuration.STRING_DATA_FILE_EXTENSION;
		List<File> integerVectorsFiles = FileHelper.getAllFiles(inputFilePath, fileExtension);

		String outputFileExtension = Configuration.DIGITAL_DATA_FILE_EXTENSION;
		String outputFilePath = Configuration.DATA_STANDARDIZED;
		// Clear existing output data generated at the last time.
		FileHelper.deleteDirectory(outputFilePath);
		
		for (File file : integerVectorsFiles) {
			Standardization s = new Standardization(file, outputFilePath, fileExtension, outputFileExtension);
			s.standardize();
		}
	}

	/**
	 * Extract features of method bodies by deep learning with the CNN algorithm.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Deprecated
	public void extractFeatureWithCNN() throws IOException, InterruptedException {
		String fileExtension = Configuration.DIGITAL_DATA_FILE_EXTENSION;
		List<File> inputFiles = FileHelper.getAllFiles(Configuration.DATA_APPZENDED_ZERO, fileExtension); // normal data.
		inputFiles.addAll(FileHelper.getAllFiles(Configuration.DATA_STANDARDIZED, fileExtension));        // standardized data.
		
		String outputPath = Configuration.DATA_EXTRACTED_FEATURE;
		// Clear existing output data generated at the last time.
		FileHelper.deleteDirectory(outputPath);
		
		for (File inputFile : inputFiles) {
			String fileName = inputFile.getName();
			int sizeOfVector = Integer.parseInt(fileName.substring(fileName.lastIndexOf("=") + 1, fileName.lastIndexOf(fileExtension)));
			int batchSize = 1000;
			int sizeOfFeatureVector = 100;
			
			FeatureExtractor extractor = new FeatureExtractor(inputFile, sizeOfVector, batchSize, sizeOfFeatureVector);
			extractor.setOutputPath(outputPath);
			
//			extractor.setNumberOfEpochs(1);
//			extractor.setSeed(123);
//			extractor.setNumOfOutOfLayer1(20);
//			extractor.setNumOfOutOfLayer2(50);
			
			extractor.extracteFeaturesWithCNN(); 
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
//		List<File> inputFiles = FileHelper.getAllFiles(Configuration.EMBEDDED_DATA_FILE_PATH, fileExtension);
		
		String outputPath = Configuration.DATA_EXTRACTED_FEATURE;
		// Clear existing output data generated at the last time.
		FileHelper.deleteDirectory(outputPath);
		File inputFile = new File(Configuration.EMBEDDED_DATA_FILE_PATH);
		String fileName = inputFile .getName();
		int sizeOfEmbeddedVector = Configuration.SIZE_OF_EMBEDDED_VECTOR;
		int sizeOfTokensVector = Integer.parseInt(fileName.substring(fileName.lastIndexOf("=") + 1, fileName.lastIndexOf(fileExtension)));
		int batchSize = Configuration.BATCH_SIZE;
		int sizeOfFeatureVector = Configuration.SIZE_OF_FEATURE_VECTOR;  // size of vectors of extracted features.
		
		FeatureExtractor2 extractor = new FeatureExtractor2(inputFile, sizeOfTokensVector, sizeOfEmbeddedVector, batchSize, sizeOfFeatureVector);
		// TODO tune the parameters below.
		extractor.setNumberOfEpochs(Configuration.N_EPOCHS);
		extractor.setSeed(123);
		extractor.setNumOfOutOfLayer1(20);
		extractor.setNumOfOutOfLayer2(50);
		extractor.setOutputPath(outputPath);
		
		extractor.extracteFeaturesWithCNN();
	}
	
	public void exportFeaturesByProjects() {
		String fileExtension = Configuration.DIGITAL_DATA_FILE_EXTENSION;
		List<File> inputFiles = FileHelper.getAllFiles(Configuration.DATA_EXTRACTED_FEATURE, fileExtension);
		String methodInfoFiles = Configuration.ENCODED_METHOD_BODY_FILE_PATH;
		String outputPath = Configuration.DATA_EXTRACTED_FEATURE_BY_PROJECTS;
		// Clear existing output data generated at the last time.
		FileHelper.deleteDirectory(outputPath);
		
		for (File inputFile : inputFiles) {
			FeatureExporter exporter = new FeatureExporter(inputFile, methodInfoFiles, outputPath, Configuration.STRING_DATA_FILE_EXTENSION);
			exporter.exportFeatureByProjects();
			System.out.println(inputFile.getName());
		}
	}
}
