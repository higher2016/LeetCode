package ExecutorCancelTaskTest;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<String>{
	private String name;

	public Task(String name) {
		super();
		this.name = name;
	}

	@Override
	public String call() throws Exception {
		while(true){
			System.out.printf("Task:%s is doing.Time:%s\n",name,new Date());
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
}
