/*
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

*/
/**
 *
 * Created by mangospring on 3/10/16.
 *//*

public class Test {
	public static void main(String[] args) {
	*/
/*	ScheduledThreadPoolExecutor executor =new ScheduledThreadPoolExecutor(5);
	final int i=5000;
		boolean b=false;
		ScheduledFuture future= executor.scheduleAtFixedRate(()->{
			System.out.println("thread1");
			for(int k=0;k<=i;k++){

				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(k>=i){
					b=true;
			}}

		},0,3, TimeUnit.SECONDS);

		future.cancel(true);

		executor.scheduleAtFixedRate(()->{
			System.out.println("thread2");
		},0,3, TimeUnit.SECONDS);
*//*



Thread a=new Thread(new Runnable(){
	boolean a=false;
	int i;
	@Override
	public void run() {
		System.out.println("in");
		while(!a){
			i++;
			try {
				Thread.sleep(2);
				System.out.println(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (i==20)
				a=true;

		}
		System.out.println("after done");
	}
});
		a.start();
	}
}
	*/
/*	Result result = JUnitCore.runClasses(FilePartUploadClient.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());*//*


	//}



	*/
/*public static void main(String[] args) {
		*/
/*try {
			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();
			doc.addPage(page);
			PDPageContentStream stream = new PDPageContentStream(doc, page);
			PDSimpleFont font = PDType1Font.TIMES_ROMAN;
			stream.setFont(font, 14);
			stream.beginText();
			stream.newLineAtOffset(150, 750);
			String text = "Hello! 123 abc äöüß € ";
			stream.showText(text);
			stream.endText();
			stream.stroke();
			stream.close();
			doc.save("test.pdf");
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*//*

		*/
/*try{
			Class student = Class.forName("Student");
			Method[] methods = student.getDeclaredMethods();

			ArrayList<String> methodList = new ArrayList<>();
			for(Method method:methods){
				methodList.add(method.getName());
			}
			Collections.sort(methodList);
			for(String name: methodList){
				System.out.println(name);
			}
		}catch(Exception e){
			e.printStackTrace();
		}*//*

	*/
/*	Map<String,String> test=new ConcurrentHashMap<>();
		test.put("1","one");
		test.put("2","two");
		test.put("3","three");
		test.put("4","four");
		test.put("5","five");
		test.put("6","six");

		//ArrayList<String> list=new ArrayList<>(test.entrySet());

		System.out.println(test.entrySet());
	}*//*


//}*/
