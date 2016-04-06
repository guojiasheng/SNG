import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
		for (i=0;i<=n+2;i++){
			bufferedWriter.write("@attribute feature"+i+"{I,L,V,F,M,C,A,G,P,T,S,Y,W,Q,N,H,E,D,K,R,B}" + "\n");
		}
		bufferedWriter.write("@attribute aa2"+i+"{I,L,V,F,M,C,A,G,P,T,S,Y,W,Q,N,H,E,D,K,R,B}" + "\n");
		bufferedWriter.write("@attribute class {0,1}" + "\n");
		bufferedWriter.write("@data" + "\n");

		int beginPos,endPos;
		while(InputLine != null)
		{
			String[] lineList = InputLine.split(",");
			length=lineList.length;
		    pos = Integer.parseInt(lineList[length-5]);
		    sequence = lineList[length-1];
	
		     //假设pos的位置在后n位，补B
		    if(sequence.length() <= pos+n){
		    	for(i=0;i<n;i++) sequence+="B";
		    	
		    }
		  //假设pos的位置在前n位，补B，同时pos前移
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
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		readCsv("D://工作//nsSNP_rawData//humvar_dataset.csv","D://工作//nsSNP_rawData//out.arff");
	}

}
