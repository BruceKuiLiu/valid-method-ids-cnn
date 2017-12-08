package edu.lu.uni.serval.similarity;

public class Similarity {
	private int index1;
	private int index2;
	private Double similarity;
	
	public Similarity(int index1, int index2, Double similarity) {
		super();
		this.index1 = index1;
		this.index2 = index2;
		this.similarity = similarity;
	}
	
	public int getIndex1() {
		return index1;
	}
	
	public int getIndex2() {
		return index2;
	}
	
	public Double getSimilarity() {
		return similarity;
	}
	
}
