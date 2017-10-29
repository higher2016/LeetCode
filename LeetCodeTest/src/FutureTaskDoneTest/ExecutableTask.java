package FutureTaskDoneTest;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ExecutableTask implements Callable<String> {
	private String name;
	public String getName() {
		return name;
	}
	public ExecutableTask(String name) {
		super();
		this.name = name;
	}
	@Override
	public String call() throws Exception {
		System.out.printf("%s: Waiting %d seconds for results.\n",name,1);
		TimeUnit.SECONDS.sleep(1);
		return "I'm "+name;
	}
}
