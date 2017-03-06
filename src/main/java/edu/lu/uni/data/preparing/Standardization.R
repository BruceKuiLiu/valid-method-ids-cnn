a <- read.csv("~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_combined_ast_node_name_and_raw_tokenSIZE=35.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_combined_ast_node_name_and_raw_tokenSIZE=35.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_only_ast_node_nameSIZE=35.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_only_ast_node_nameSIZE=35.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_only_raw_tokenSIZE=31.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_only_raw_tokenSIZE=31.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_seprated_ast_node_name_and_raw_tokenSIZE=62.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_seprated_ast_node_name_and_raw_tokenSIZE=62.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_statement_node_name_with_raw_tokenSIZE=35.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/OUTPUT/encoding/encoded_method_bodies/feature_statement_node_name_with_raw_tokenSIZE=35.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-ast-node-name-with-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-ast-node-name-with-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-only-ast-node-nameSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-only-ast-node-nameSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-raw-tokens-with-operatorsSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-raw-tokens-with-operatorsSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-ast-node-name-with-raw-tokenSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-ast-node-name-with-raw-tokenSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-statement-node-name-with-all-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/RAW_CAMEL_TOKENIATION/projects$feature-statement-node-name-with-all-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-ast-node-name-with-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-ast-node-name-with-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-only-ast-node-nameSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-only-ast-node-nameSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-raw-tokens-with-operatorsSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-raw-tokens-with-operatorsSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-ast-node-name-with-raw-tokenSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-ast-node-name-with-raw-tokenSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-statement-node-name-with-all-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP/projects$feature-statement-node-name-with-all-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-ast-node-name-with-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-ast-node-name-with-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-only-ast-node-nameSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-only-ast-node-nameSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-raw-tokens-with-operatorsSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-raw-tokens-with-operatorsSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-ast-node-name-with-raw-tokenSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-ast-node-name-with-raw-tokenSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-statement-node-name-with-all-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/SIMPLIFIED_NLP(2)/projects$feature-statement-node-name-with-all-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-ast-node-name-with-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-ast-node-name-with-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-only-ast-node-nameSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-only-ast-node-nameSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-raw-tokens-with-operatorsSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-raw-tokens-with-operatorsSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-ast-node-name-with-raw-tokenSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-ast-node-name-with-raw-tokenSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-statement-node-name-with-all-node-labelSIZE=12.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP/projects$feature-statement-node-name-with-all-node-labelSIZE=12.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-ast-node-name-with-node-labelSIZE=24.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-ast-node-name-with-node-labelSIZE=24.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-only-ast-node-nameSIZE=24.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-only-ast-node-nameSIZE=24.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-raw-tokens-with-operatorsSIZE=24.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-raw-tokens-with-operatorsSIZE=24.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-ast-node-name-with-raw-tokenSIZE=24.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-ast-node-name-with-raw-tokenSIZE=24.txt",col.names=F,row.names=F)

a <- read.csv("~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-statement-node-name-with-all-node-labelSIZE=24.csv")
write.table(scale(a$TOKEN, center=T,scale=T),"~/Public/git/valid-method-ids-cnn/inputData/integer-vectors/method-name/TOKENAZATION_WITH_NLP(2)/projects$feature-statement-node-name-with-all-node-labelSIZE=24.txt",col.names=F,row.names=F)
