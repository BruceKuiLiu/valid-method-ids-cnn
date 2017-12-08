package edu.lu.uni.serval.similarity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.lu.uni.serval.utils.DistanceCalculator;
import edu.lu.uni.serval.utils.DistanceCalculator.DistanceFunction;

public class SimilarityComputer {
	
	/**
	 * Most similar name list.
	 * Most similar method body ==> names similarities list.
	 * Statistic of relationship between return types and first tokens of method names. ==> common and uncommon methods as testing cases.
	 */
	
	/**
	 * 1. inner project similarity.
	 * 2. cross project similarity. @TOO_BIG_DATA
	 * 3. inner cluster similarity. @NEGATIVE.
	 */
	
	private DistanceFunction distanceFunction = DistanceFunction.COSINESIMILARITY;
	private boolean isABS = false;// The absolute value of a similarity.
	private String featuresFileName = "";//TODO
	
	public void compute() {
		//<index_i, index_j, similarity>
		List<Similarity> similarities = new ArrayList<>();
		List<Double[]> features = readFeatures(featuresFileName);
		int size = features.size();
		int indexSize = size / 2;
		if (indexSize * 2 == size) {
			indexSize --;
		}
		for (int i = 0; i < indexSize; i ++) {
			for (int j = i + 1; j < size; j ++) {
				Double[] features1 = features.get(i);
				Double[] features2 = features.get(j);
				Double similarity = computeSimilarity(features1, features2);
				Similarity s = new Similarity(i, j, similarity);
				similarities.add(s);
			}
		}
	}

	
	private Double computeSimilarity(Double[] features1, Double[] features2) {
		if (distanceFunction == null) {
			return Double.NaN;
		} else {
			Double distance = new DistanceCalculator().calculateDistance(distanceFunction, features1, features2);
			if (isABS) {
				return Math.abs(distance);
			} else {
				return distance;
			}
		}
	}


	private List<Double[]> readFeatures(String fileName) {
		List<Double[]> features = new ArrayList<>();
		FileInputStream fis = null;
		Scanner scanner = null;
		
		try {
			fis = new FileInputStream(fileName);
			scanner = new Scanner(fis);
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if ("".equals(line)) {
					features.add(null);
				} else {
					Double[] feature = doubleParseFeature(line);
					features.add(feature);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				scanner.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return features;
	}
	
	private Double[] doubleParseFeature(String feature) {
		String[] features = feature.split(", ");
		int length = features.length;
		Double[] doubleFeatures = new Double[length];
		for (int i = 0; i < length; i ++) {
			doubleFeatures[i] = Double.parseDouble(features[i]);
		}
		return doubleFeatures;
	}
}
