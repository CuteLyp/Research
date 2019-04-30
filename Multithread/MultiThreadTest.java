package Multithreads;

public class MultiThreadTest {
	public static int i = 0;
	public static void main(String[] args) throws InterruptedException {
		
		ThreadPool threadPool = new ThreadPool(2);
		
		MyThread myThread1 = new MyThread("1");
		MyThread myThread2 = new MyThread("2");
		long startTime = System.nanoTime();
		
		threadPool.addTask(myThread1);
		threadPool.addTask(myThread2);
		
		long endTime = System.nanoTime();
		System.out.println("Running time: " + ((endTime-startTime)/1000) + "ns");
	}
	
	public static class MyThread implements Runnable {
		private String name;
		public MyThread(String name) {
			this.name = name;
		}
		public void run() {
			for(int j = 0; j < 5; j++) {
				i++;
				System.out.println("Thread-" + name + " is running... " + i);
			}
		}
	}
}
