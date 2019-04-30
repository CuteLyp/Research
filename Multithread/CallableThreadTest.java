package Multithreads;

import java.util.concurrent.*;

public class CallableThreadTest implements Callable<String> {
	public static void main(String[] args) {
		CallableThreadTest test = new CallableThreadTest();
		FutureTask<String> future = new FutureTask<>(test);
		for(int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if(i == 2) {  
                new Thread(future, "sub-thread").start();  
            }
        }
        try {
            System.out.println(future.get());  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (ExecutionException e) {  
            e.printStackTrace();  
        }  
	}
	
	@Override
	public String call() throws Exception {
		int i = 0;
		for(; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
		return "Child thread finished";
	}
}
/*
	Runnable r = () -> System.out.println("Hello World!");
	Thread th = new Thread(r);
	th.start();
	
	is equivalent to
	
	Runnable r = new Runnable() {
   		@Override
   		public void run() {
			System.out.println("Hello World!");
   		}
	};
	Thread th = new Thread(r);
	th.start();  
*/