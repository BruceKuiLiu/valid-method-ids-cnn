package edu.lu.uni.data.preparing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.lu.uni.util.FileHelper;

/**
 * Normalize data.
 * Normalize the values in the data vectors into values ranging from 0 to 1 by using min-max normalization.
 * 
 * @author kui.liu
 *
 */
@Deprecated
public class DataForNormalization {
	
	private static final String INPUT_FILE_PATH = "OUTPUT/data_preprocess/append_zero/";
	
	public static void main(String[] args) throws IOException {
		DataForNormalization dp = new DataForNormalization();
		
		dp.normalizeVectorsInFiles(INPUT_FILE_PATH);
	}
	
	public void normalizeVectorsInFiles(String inputFolderPath) throws IOException {
		List<File> integerVectorsFiles = FileHelper.getAllFiles(inputFolderPath, ".csv");
		
		for (File vectorFile : integerVectorsFiles) {
			normalizeVectors(vectorFile);
		}
	}

	public void normalizeVectors(File vectorFile) throws IOException {
		String outputFileName = vectorFile.toString().replace("/append_zero/", "/Normalization/");
		
		File outputFile = new File(outputFileName);
		if (outputFile.exists()) {
			outputFile.delete();
		}
		
		double min = 0; 
		double max = getMax(vectorFile);
		
		FileInputStream fis = new FileInputStream(vectorFile);
		Scanner scanner = new Scanner(fis);
		
		int lines = 0; 
		StringBuilder content = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#.##########");
		
		while (scanner.hasNextLine()) {
			String vectorLine = scanner.nextLine();
			List<String> vector = new ArrayList<>();
			vector.addAll(Arrays.asList(vectorLine.split(", ")));
			
			for (int i = 0, size = vector.size(); i < size; i ++) {
				double normalizedValue = (Double.parseDouble(vector.get(i)) - min) / (max - min);
				vector.set(i, df.format(normalizedValue));
			}
			
			lines ++;
			content.append(vector.toString().replace("[", "").replace("]", "") + "\n");
			if (lines % 1000 == 0) {
				FileHelper.outputToFile(outputFileName, content, true);
				content.setLength(0);
			}
		}
		
		scanner.close();
		fis.close();
		
		if (content.length() > 0) {
			FileHelper.outputToFile(outputFileName, content, true);
		}
	}

	private double getMax(File vectorFile) throws IOException {
		int max = 0;
		
		FileInputStream fis = new FileInputStream(vectorFile);
		Scanner scanner = new Scanner(fis);
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			List<String> vector = new ArrayList<>();
			vector.addAll(Arrays.asList(line.split(", ")));
			
			for (int i = 0, size = vector.size(); i < size; i ++) {
				if ("0".equals(vector.get(i))) {
					break;
				}
				int element = Integer.parseInt(vector.get(i));
				if (max < element) {
					max = element;
				}
			}
		}
		
		scanner.close();
		fis.close();
		
		return max;
	}

}
