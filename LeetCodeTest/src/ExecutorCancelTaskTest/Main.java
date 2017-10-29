package ExecutorCancelTaskTest;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
		Task task = new Task("Task-1");
		Future<String> result = executor.submit(task);
		
		try {
			TimeUnit.SECONDS.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Cancle the %s task.Time:%s","Task-1\n",new Date());
		//将执行器中无限循环的任务取消，原来Future还有这么多用处
		result.cancel(true);
		
		System.out.println("Cancle result:"+result.isCancelled());
		System.out.println("Done result:"+result.isDone());
		executor.shutdown();
		System.out.println("Finish!!!!");
	}
}
