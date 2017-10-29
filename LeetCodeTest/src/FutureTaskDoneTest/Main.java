package FutureTaskDoneTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();
		ResultTask[] resultTasks = new ResultTask[5];
		for (int i = 0; i < 5; i++) {
			ExecutableTask executableTask = new ExecutableTask("task-" + i);
			resultTasks[i] = new ResultTask(executableTask);
			executor.submit(executableTask);
		}

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < resultTasks.length; i++) {
			resultTasks[i].cancel(true);
		}

		for (int i = 0; i < resultTasks.length; i++) {
			try{
				if(!resultTasks[i].isCancelled()){
					System.out.printf("%s\n",resultTasks[i].get());
				}
			}catch (Exception e) {
			}
		}
		executor.shutdown();
	}
}
