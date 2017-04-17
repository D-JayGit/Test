import sun.security.pkcs11.wrapper.CK_SSL3_KEY_MAT_OUT;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by DJ on 14/10/16.
 *
 */
/*public class Student {
	static String project;*/
	/*public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = br.readLine();
			String[] nos=line.split(" ");
			int n = Integer.parseInt(nos[0]);
			int q=Integer.parseInt(nos[1]);
			 project=br.readLine();
			String[] query=new String[1000000];
			*//*for(int i=0;i<q;i++) {
				query[i] = br.readLine();
			}*//*
			List<String> queryList=Arrays.asList(query);
			queryList.forEach(qry->{
					String[] queryParam=qry.split(" ");
					if(queryParam.length==3){
					//	int type=Integer.parseInt(queryParam[0])
						int charIndex=Integer.parseInt(queryParam[1]);
						char ch=queryParam[2].charAt(0);
						char[] arr=project.toCharArray();
						arr[charIndex-1]=ch;
						project=String.copyValueOf(arr);
						*//*char replace=project.charAt(charIndex-1);
						project=project.replace(replace,ch);*//*

					}else {
						int charIndex=Integer.parseInt(queryParam[1]);
						char[] sorted=project.toCharArray();

						Arrays.parallelSort(sorted);
						System.out.println(sorted[charIndex-1]);
					}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		*//*System.out.println(new Date(System.currentTimeMillis()));
						for (int i=0;i<100000;i++)
							System.out.println("No:"+i);
		System.out.println(new Date(System.currentTimeMillis()));*//*
	}*/
//}


public class Student{
	String name;

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		System.out.println("GetName");
		return name;
	}

	public void setName(String name) {
		System.out.println("SssetName");
		this.name = name;
	}
}

