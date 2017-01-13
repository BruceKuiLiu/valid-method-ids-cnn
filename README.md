# valid-method-ids-cnn

## First, preprocess data for deep learning.
Package: edu.lu.uni.data.preparing

### 1. append 0 to all vectors, make all vectors hold the same size.
JavaFile: DataPreprocessor.java

Input: inputData/unsupervised-learning/

Output: outputData/WithoutNormalization/


### 2. Normalize the values in the data vectors of results of 1.
JavaFile: DataForNormalization.java

Input: outputData/WithoutNormalization/

Output: outputData/Normalization/


### 3. Standardize the values in the data vectors of results of 1.
JavaFile: DataForStandardization.java

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

apache$commons-math$feature-ast-node-name-with-node-labelSIZE=82.list

SIZE=82 means the size of feature's vector is 82. 
The size is selected by computing the value of upper whisker in the boxplot of all sizes' distribution.

apache$commons-math$feature-ast-node-name-with-node-labelSIZE=82.listMAXSize=9.list

MAXSize=9 means the size of label's vector is 9.

### Mapping features with labels
.csv files can be directly used to cluster. We can use the same line number to map features with labels.
or use data in the .list files, features and labels hold the same keys.

e.g. key: org.apache.commons.math4.analysis.differentiation:DerivativeStructure:getFreeParameters:null#
