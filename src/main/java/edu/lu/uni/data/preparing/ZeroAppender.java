package edu.lu.uni.data.preparing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lu.uni.serval.utils.FileHelper;

/**
 * Preprocess data:
 * Append 0 to all vectors, make all vectors hold the same size
 * 
 * @author kui.liu
 *
 */
public class ZeroAppender {
	
	private static Logger logger = LoggerFactory.getLogger(ZeroAppender.class);

	private static final String INPUT_FILE_PATH = "OUTPUT/encoding/encoded_method_bodies/";
	private static final String OUTPUT_FILE_PATH = "OUTPUT/data_preprocess/append_zero/";
	
	public static void main(String[] args) throws IOException {
		ZeroAppender appender = new ZeroAppender();
		
		appender.appendZeroForVectorsInFiles(INPUT_FILE_PATH, ".list", OUTPUT_FILE_PATH, ".csv");
	}
	
	public void appendZeroForVectorsInFiles(String inputFolderPath, String fileExtension, String outputFilePath, String numericFileExtension) throws IOException {
		List<File> integerVectorsFiles = FileHelper.getAllFiles(inputFolderPath, fileExtension);
		
		for (File vectorFile : integerVectorsFiles) {
			appendZeroForVectors(vectorFile, inputFolderPath, outputFilePath, fileExtension, numericFileExtension);
		}
	}

	public void appendZeroForVectors(File vectorFile, String inputFilePath, String outputFilePath, String tokenFileExtension, String numericFileExtension) throws IOException {
		String outputFileName = vectorFile.toString().replace(inputFilePath, outputFilePath);
		int maxSizeOfVector = 0;
		maxSizeOfVector = Integer.parseInt(outputFileName.substring(outputFileName.lastIndexOf("SIZE=") + "SIZE=".length(),
				outputFileName.lastIndexOf(tokenFileExtension)));
		outputFileName = outputFileName.replace(tokenFileExtension, numericFileExtension);
		
		String vectors = FileHelper.readFile(vectorFile);
		BufferedReader br = new BufferedReader(new StringReader(vectors));
		String vectorLine = null;
		int lines = 0; 
		StringBuilder content = new StringBuilder();
		
		while ((vectorLine = br.readLine()) != null) {
			int indexOfHarshKey = vectorLine.indexOf("#");
			
			if (indexOfHarshKey < 0) {
				logger.error("The below raw feature is invalid!\n" + vectorLine);
				continue;
			}
			
			String dataVector = vectorLine.substring(indexOfHarshKey + 2, vectorLine.length() - 1);
			List<String> vector = new ArrayList<>();
			vector.addAll(Arrays.asList(dataVector.split(", ")));
			int sizeOfVector = vector.size();
			
			for (int i = sizeOfVector; i < maxSizeOfVector; i ++) {
				vector.add("0");
			}
			
			lines ++;
			content.append(vector.toString().replace("[", "").replace("]", "") + "\n");
			if (lines % 1000 == 0) {
				FileHelper.outputToFile(outputFileName, content, true);
				content = new StringBuilder();
			}
		}
		if (content.length() > 0) {
			FileHelper.outputToFile(outputFileName, content, true);
		}
	}

}
