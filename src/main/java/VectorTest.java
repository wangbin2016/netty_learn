import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class VectorTest {
	
	public static Vector<String> v = new Vector<String>();
	
	public static void main(String[] args) {
		while(true){
			for(int i=0;i<10;i++){
				v.add("f");
			}
			
			Thread removeThread = new Thread(new Runnable(){
				public void run(){
					for(int i=0;i<v.size();i++){
						v.remove(i);
					}
				}
			});
			
			Thread printThread = new Thread(new Runnable(){
				public void run(){
					for(int i=0;i<v.size();i++){
						System.out.println(v.get(i));
					}
				}
			});
			removeThread.start();
			printThread.start();
			while(Thread.activeCount()>10);
		}
	}
}
