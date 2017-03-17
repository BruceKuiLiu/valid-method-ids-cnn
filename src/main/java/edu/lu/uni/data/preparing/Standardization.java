package edu.lu.uni.data.preparing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lu.uni.util.FileHelper;

/**
 * Standardize data.
 * 
 * @author kui.liu
 *
 */
public class Standardization {
	
	private static Logger logger = LoggerFactory.getLogger(Standardization.class);
	
	private File input;
	private String outputPath;
	private List<Integer> integers;
	private double sum = 0;
	private double mean;
	private double s; // standard deviation
	private Map<Integer, Double> standardizedMap;
	private String tokenFileExtension;
	private String numericFileExtension;

	public Standardization(File input, String outputPath, String tokenFileExtension, String numericFileExtension) {
		this.input = input;
		this.outputPath = outputPath;
		this.tokenFileExtension = tokenFileExtension;
		this.numericFileExtension = numericFileExtension;
	}
	
	public void standardize() {
		try {
			readData();
			mean = sum / integers.size();
			computeStandardDeviation();
			computeStandardizedValue();
			outputData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void outputData() throws IOException {
		String outputFileName = input.getName();
		int maxSizeOfVector = 0;
		maxSizeOfVector = Integer.parseInt(outputFileName.substring(outputFileName.lastIndexOf("SIZE=") + "SIZE=".length(),
				outputFileName.lastIndexOf(tokenFileExtension)));
		outputFileName = outputPath + outputFileName.replace(tokenFileExtension, numericFileExtension);
		
		FileInputStream fis = new FileInputStream(input);
		Scanner scanner = new Scanner(fis);
		StringBuilder content = new StringBuilder();
		int counter = 0;
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int indexOfHarshKey = line.indexOf("#");
			if (indexOfHarshKey < 0) {
				logger.error("The below raw feature is invalid!\n" + line);
				continue;
			}
			String dataVector = line.substring(indexOfHarshKey + 2, line.length() - 1);
			String[] vector = dataVector.split(", ");
			List<Double> standarizedValues = new ArrayList<>();
			
			int length = vector.length;
			for (int i = 0; i < length; i ++) {
				int integerToken = Integer.parseInt(vector[i]);
				standarizedValues.add(standardizedMap.get(integerToken));
			}
			for (int i = length; i < maxSizeOfVector; i ++) {
				standarizedValues.add(0.0);
			}
			
			content.append(standarizedValues.toString().replace("[", "").replace("]", "") + "\n");
			
			counter ++;
			if (counter % 1000 == 0) {
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

	private void computeStandardizedValue() {
		standardizedMap = new HashMap<>();
		
		for (int integer : integers) {
			if (standardizedMap.containsKey(integer)) {
				continue;
			}
			double value = (integer - mean) / s;
			standardizedMap.put(integer, value);
		}
		
		integers.clear();
	}

	private void computeStandardDeviation() {
		double deviationSum = 0;
		for (int integer : integers) {
			double deviation = integer - mean;
			deviationSum += deviation * deviation;
		}
		s = Math.sqrt(deviationSum / (integers.size() - 1));
	}

	private void readData() throws IOException {
		integers = new ArrayList<>();
		
		FileInputStream fis = new FileInputStream(input);
		Scanner scanner = new Scanner(fis);
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int indexOfHarshKey = line.indexOf("#");
			if (indexOfHarshKey < 0) {
				logger.error("The below raw feature is invalid!\n" + line);
				continue;
			}
			String dataVector = line.substring(indexOfHarshKey + 2, line.length() - 1);
			String[] vector = dataVector.split(", ");
			for (int i = 0, length = vector.length; i < length; i ++) {
				int integerToken = Integer.parseInt(vector[i]);
				integers.add(integerToken);
				sum += integerToken;
			}
		}
		
		scanner.close();
		fis.close();
	}
}
