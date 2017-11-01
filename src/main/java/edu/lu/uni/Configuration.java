package edu.lu.uni;

public class Configuration {
	public static final String STRING_DATA_FILE_EXTENSION = ".list";  // the file extension of string data.
	public static final String DIGITAL_DATA_FILE_EXTENSION = ".csv";  // the file extension of digital data.
	
	public static final String ENCODED_METHOD_BODY_FILE_PATH = "../OUTPUT/encoding/encoded_method_bodies" + STRING_DATA_FILE_EXTENSION;  // output
	
	// token embedding with word2vec
	public static final int SIZE_OF_EMBEDDED_VECTOR = 300;
	public static final String EMBEDDED_DATA_FILE_PATH = "../OUTPUT/data_for_CNN/vectorized_tokens" + DIGITAL_DATA_FILE_EXTENSION;
		
	
	/**
	 * Configuration of the third step: extract features of method bodies by deep learning with the CNN algorithm.
	 */
	public static final int BATCH_SIZE = 200;
	public static final int SIZE_OF_FEATURE_VECTOR = 300; // size of extracted feature vectors
	public static final String DATA_APPZENDED_ZERO = "../OUTPUT/data_for_CNN/append_zero/";        // file path of output
	public static final String DATA_STANDARDIZED = "../OUTPUT/data_for_CNN/standardized_data/";    // file path of output
	public static final String DATA_EXTRACTED_FEATURE = "../OUTPUT/CNN_extracted_feature/";        // file path of output
	public static final String DATA_EXTRACTED_FEATURE_BY_PROJECTS = "../OUTPUT/extracted_features_by_project/";
	public static final int N_EPOCHS = 10;
	
}
