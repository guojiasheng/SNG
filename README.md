# SNG
提取氨基酸文件

2个csv文件，头2列没用。第三列是位置，第四列是初始氨基酸，第5列是变异后，第六列是是否有害，第7列是序列
 
比如：第二行pos是10，aa1是A，后面序列的第10位一定是A。
 
分类什么样的变异是有害，什么样是无害。
需要提取pos前后几位的氨基酸信息。
要求能提取前后n位的氨基酸，并生成arff
 
比如n=1，第一行就变成：（E是aa2）
G,A,G,E,1

SNG.java

Usage:
java -jar -i inputFile -o outArff

######readCsv("D://工作//nsSNP_rawData//humvar_dataset.csv","D://工作//nsSNP_rawData//out.arff");

