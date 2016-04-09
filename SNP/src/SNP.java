import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class SNP {

	
	static int n=2;
	static int length;
	static void readCsv(String inputFile,String outArffFile) throws IOException{
		BufferedReader InputBR = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "utf-8"));
		String InputLine = InputBR.readLine();
		InputLine = InputBR.readLine();
		int pos;
		String sequence = "";
		String extractSequence = "";
		String lable = "";
		String aa2 = "";
		int i=0;
		
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outArffFile), false), "utf-8"));
		bufferedWriter.write("@relation sng.arff"+"\n");
		for (i=0;i<=2*n;i++){
			bufferedWriter.write("@attribute feature"+i+"{I,L,V,F,M,C,A,G,P,T,S,Y,W,Q,N,H,E,D,K,R,B}" + "\n");
		}
		bufferedWriter.write("@attribute aa2"+i+"{I,L,V,F,M,C,A,G,P,T,S,Y,W,Q,N,H,E,D,K,R,B}" + "\n");
		bufferedWriter.write("@attribute class {0,1}" + "\n");
		bufferedWriter.write("@data" + "\n");

		int beginPos,endPos;
		int index=0;
		while(InputLine != null)
		{
			index++;
			String[] lineList = InputLine.split(",");
			length=lineList.length;
		    pos = Integer.parseInt(lineList[length-5]);
		    sequence = lineList[length-1];
	
		     //����pos��λ���ں�nλ����B
		    if(sequence.length() <= pos+n){
		    	for(i=0;i<n;i++) sequence+="B";
		    	
		    }
		  //����pos��λ����ǰnλ����B��ͬʱposǰ��
		    if(pos<=n){
		    	for(i=0;i<=n;i++)
		    	{
		    		sequence="B"+sequence;
		    		pos++;
		    		 
		    	} 	
		    }
		    beginPos = pos-n-1;
		    endPos = pos+n;
		    extractSequence = sequence.substring(beginPos,endPos);
		    
		    boolean retval = extractSequence.contains("U");
		    if(retval){
		    	 InputLine = InputBR.readLine();
		    	continue;
		    }
		    
		    
		    lable = lineList[length-2];
		    aa2 = lineList[length-3];
		    
		    for (i = 0;i < extractSequence.length() ; i++){
		    	bufferedWriter.write(extractSequence.charAt(i)+",");
		    }
		    bufferedWriter.write(aa2+","+lable+"\n");
		    InputLine = InputBR.readLine();
		}
		bufferedWriter.close();
		InputBR.close();
	}
	
	public static Classifier getClassifier(String name){
		Classifier classify = null;
		switch (name){
			case "rf":
				 classify=new RandomForest();
				 break;
			case "j48":
				 classify=new J48();
				 break;
			
			default:
				System.out.println("Ŀǰֻ֧��rf(randomForst),j488");
		}
		return classify;
	}
	
	public static double featureRate(String file) throws Exception{
		FileReader reader=new FileReader(file);
		Instances  data=new Instances(reader);
		data.setClassIndex(data.numAttributes()-1);//����ѵ�����У�target��index  
		Classifier classify=getClassifier("j48");
		classify.buildClassifier(data);
		Evaluation eval=new Evaluation(data);
		eval.crossValidateModel(classify, data,10, new Random());
		eval.evaluateModel(classify, data);
		System.out.println(1-eval.errorRate());
		return 1-eval.errorRate();
		//System.out.println(String.valueOf(1-eval.errorRate()));
		//System.out.println(eval.toSummaryString());
		//System.out.println(eval.toMatrixString());
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//String command="-i D://����//nsSNP_rawData//humvar_dataset.csv -o D://����//nsSNP_rawData//out.arff -n 3";
		//String com[] = command.split(" ");
		
		/*
		double bestRate=0;
		double temp;
		int bestN=0;
		for(int i =1; i<100 ; i++){
			
			String command="-i D://����//nsSNP_rawData//humvar_dataset.csv -o D://����//nsSNP_rawData//sng -n 3";
			String com[] = command.split(" ");
			n = Integer.valueOf(i);
			String inputFile = com[1];
			String outArff =com[3]+"_"+String.valueOf(n)+".arff";
			readCsv(inputFile,outArff);
			temp = featureRate(outArff);
			if (temp>bestRate){
				bestRate = temp;
				bestN = i;
			}
			
		}
		System.out.println(String.valueOf(bestN)+" "+bestRate);*/

		String inputFile = args[1];
		String outArff = args[3];
		n = Integer.valueOf(args[5]);
		readCsv(inputFile,outArff);
	}
}
