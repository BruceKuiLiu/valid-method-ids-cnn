package edu.lu.uni;

public class Configuration {
	/**
	 * Configuration of the first step: tokenize and vectorize the Java source code.
	 */
//	public static final List<String> PROJECTS = new ArrayList<>();  // input
//	public static final List<TokenType> TOKEN_TYPES = new ArrayList<>();
//	private static final String DATA_PATH = "../training-data/";
//	static {
//		File trainingDataFile = new File(DATA_PATH);
//		File[] files = trainingDataFile.listFiles();
//		for (File file : files) {
//			if (!file.getName().contains(".")) {
//				PROJECTS.add(DATA_PATH + file.getName() + "/.git");
//			}
//		}
//		
////		TOKENTYPES.add(TokenType.COMBINED_AST_NODE_NAME_AND_RAW_TOKEN);
////		TOKENTYPES.add(TokenType.ONLY_AST_NODE_NAME);
////		TOKENTYPES.add(TokenType.ONLY_RAW_TOKEN);
//		TOKEN_TYPES.add(TokenType.SEPRATED_AST_NODE_NAME_AND_RAW_TOKEN);
////		TOKENTYPES.add(TokenType.STATEMENT_NODE_NAME_WITH_RAW_TOKEN);
//	}
	public static final String TOKENIZATION_OUTPUT_PATH = "../OUTPUT/tokenization/"; // the file path of output
	public static final String STRING_DATA_FILE_EXTENSION = ".list";  // the file extension of string data.
	public static final String DIGITAL_DATA_FILE_EXTENSION = ".csv";  // the file extension of digital data.
	
	
	/**
	 * Configuration of the second step: select methods, and encode/embed tokens of method bodies.
	 */
	public static final String TOKEN_FILE_PATH = TOKENIZATION_OUTPUT_PATH + "tokens/";    // the file path of input
	public static final String SIZE_FILE_PATH = TOKENIZATION_OUTPUT_PATH + "sizes/";      // input
	/*
	 * the max size will be used to select methods.
	 * The methods, of which the size of token vectors is less than or equals to the max size, will be selected as latter data.
	 */
//	public static final MaxSizeType maxSizeType = MaxSizeType.ThirdQuarter;  
	public static final String ENCODED_METHOD_BODY_FILE_PATH = "../OUTPUT/encoding/encoded_method_bodies/";  // output
	public static final String SELECTED_METHOD_BODY_PATH = "../OUTPUT/encoding/selected_method_body/";       // output
	public static final String SELECTED_SOURCE_CODE_PATH = "../OUTPUT/encoding/selected_source_code/";       // output
	
	// token embedding with word2vec
	public static final int SIZE_OF_EMBEDDED_VECTOR = 300;
	public static final int MIN_WORD_FREQUENCY = 2;
	public static final String PREPROCESSED_TOKEN_FILE_PATH = "../OUTPUT/embedding/data_preprocess/";
	public static final String EMBEDDED_TOKEN_FILE_PATH = "../OUTPUT/embedding/token_map/";
	public static final String EMBEDDED_DATA_FILE_PATH = "../OUTPUT/data_for_CNN/embedded_data/";
		
	
	/**
	 * Configuration of the third step: extract features of method bodies by deep learning with the CNN algorithm.
	 */
	public static final int BATCH_SIZE = 200;
	public static final int SIZE_OF_FEATURE_VECTOR = 300; // size of extracted feature vectors
	public static final String DATA_APPZENDED_ZERO = "../OUTPUT/data_for_CNN/append_zero/";        // file path of output
	public static final String DATA_STANDARDIZED = "../OUTPUT/data_for_CNN/standardized_data/";    // file path of output
	public static final String DATA_EXTRACTED_FEATURE = "../OUTPUT/CNN_extracted_feature/";        // file path of output
	public static final String DATA_EXTRACTED_FEATURE_BY_PROJECTS = "../OUTPUT/extracted_features_by_project/";
	public static final int NEPOCHS = 10;
	
}
