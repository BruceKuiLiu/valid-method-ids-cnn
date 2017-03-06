package edu.lu.uni;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.lu.uni.data.preparing.Standardization;
import edu.lu.uni.data.preparing.ZeroAppender;
import edu.lu.uni.deeplearning.extractor.FeatureExtractor;
import edu.lu.uni.util.FileHelper;

public class App {

	public static void main(String[] args) {
		App example = new App();
		try {
			// data preprocessing
			example.appendZero();
			example.standardizeData();
			// feature extracting: deep learning with the CNN algorithm
			example.extractFeatureWithCNN();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void appendZero() throws IOException {
		String inputFolderPath = Configuration.ENCODED_METHOD_BODY_FILE_PATH;
		String fileExtension = Configuration.TOKEN_FILE_EXTENSION;
		String outputFilePath = Configuration.DATA_APPZENDED_ZERO;
		List<File> integerVectorsFiles = FileHelper.getAllFiles(inputFolderPath, fileExtension);
		
		// Clear existing output data generated at last time.
		FileHelper.deleteDirectory(outputFilePath);
		
		ZeroAppender appender = new ZeroAppender();
		for (File file : integerVectorsFiles) {
			appender.appendZeroForVectors(file, inputFolderPath, outputFilePath, fileExtension, Configuration.SIZE_FILE_EXTENSION);
		}
	}

	public void standardizeData() {
		String inputFolderPath = Configuration.ENCODED_METHOD_BODY_FILE_PATH;
		String fileExtension = Configuration.TOKEN_FILE_EXTENSION;
		String outputFilePath = Configuration.DATA_STANDARDIZED;
		List<File> integerVectorsFiles = FileHelper.getAllFiles(inputFolderPath, fileExtension);
		
		// Clear existing output data generated at last time.
		FileHelper.deleteDirectory(outputFilePath);
		
		for (File file : integerVectorsFiles) {
			Standardization s = new Standardization(file, outputFilePath, Configuration.TOKEN_FILE_EXTENSION, Configuration.SIZE_FILE_EXTENSION);
			s.standardize();
		}
	}

	public void extractFeatureWithCNN() throws IOException, InterruptedException {
		String fileExtension = Configuration.SIZE_FILE_EXTENSION;
		List<File> files = FileHelper.getAllFiles(Configuration.DATA_APPZENDED_ZERO, fileExtension);
		files.addAll(FileHelper.getAllFiles(Configuration.DATA_STANDARDIZED, fileExtension));
		
		String outputPath = Configuration.DATA_EXTRACTED_FEATURE;
		
		// Clear existing output data generated at last time.
		FileHelper.deleteDirectory(outputPath);
		
		for (File file : files) {
			String fileName = file.getName();
			int sizeOfVector = Integer.parseInt(fileName.substring(fileName.lastIndexOf("=") + 1, fileName.lastIndexOf(fileExtension)));
			int batchSize = 1000;
			int sizeOfFeatureVector = 100;
			
			FeatureExtractor extractor = new FeatureExtractor(file, sizeOfVector, batchSize, sizeOfFeatureVector);
			extractor.setOutputPath(outputPath);
			
//			extractor.setNumberOfEpochs(1);
//			extractor.setSeed(123);
//			extractor.setNumOfOutOfLayer1(20);
//			extractor.setNumOfOutOfLayer2(50);
			
			extractor.extracteFeaturesWithCNN(); 
		}
	}
}
