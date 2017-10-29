package ExecutorDelayTaskTest;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * 任务类
 * @author JM
 * @2017-3-9
 */
public class Task implements Callable<String> {
	private String name;
	public Task() {
	}
	public Task(String name) {
		this.name = name;
	}
	@Override
	public String call() throws Exception {
		System.out.printf("%s:have started-----------.Time: %s\n", name, new Date());
		return name + ":is end";
	}
}
