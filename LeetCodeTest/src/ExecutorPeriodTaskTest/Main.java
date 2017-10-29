package ExecutorPeriodTaskTest;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		
		Task task = new Task("task-1");
		System.out.printf("Main start at :%s\n",new Date());
		executorService.scheduleWithFixedDelay(task, 4, 2, TimeUnit.SECONDS);
	}
}

class Task implements Runnable {
	private String name;
	public Task() {
		super();
	}
	public Task(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		System.out.printf("%s: have started,time is %s\n", this.name, new Date());
	}
}
