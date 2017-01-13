# valid-method-ids-cnn

## First, preprocess data for deep learning.
Package: edu.lu.uni.data.preparing

### 1. append 0 to all vectors, make all vectors hold the same size.
Input: inputData/unsupervised-learning/

Output: outputData/WithoutNormalization/


### 2. Normalize the values in the data vectors of results of 1.
Input: outputData/WithoutNormalization/

Output: outputData/Normalization/


### 3. Standardize the values in the data vectors of results of 1.
Input: inputData/unsupervised-learning/

Output: outputData/Standardization/


## Second, extract(encode) features of data vectors by deep learning (CNN).
Package: edu.lu.uni.deeplearning.extractor

###1. extract(encode) features of method body.
Input: src/main/resources/WithoutNormalization/, src/main/resources/src/main/resources/WithoutNormalization/, src/main/resources/Standardization/

Output: outputData/CNN/

### 2. extract(encode) features of method name.
Input: src/main/resources/WithoutNormalization/, src/main/resources/src/main/resources/WithoutNormalization/, src/main/resources/Standardization/

Output: outputData/CNN/

The results of CNN will be used in clustering.
