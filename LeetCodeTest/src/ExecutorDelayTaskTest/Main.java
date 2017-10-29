package ExecutorDelayTaskTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试类
 * @author JM
 * @2017-3-9
 */
public class Main {
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		for (int i = 1; i <= 10; i++) {
			Task task = new Task("task-" + i);
			//三个参数分别代表：需要被执行的任务、执行该任务的延迟时间量（无单位量）、延迟时间的单位（毫秒、秒、天）
			Future<String> result = executor.schedule(task, i, TimeUnit.SECONDS);
			resultList.add(result);
		}

		for (int i = 0; i < resultList.size(); i++) {
			try {
				System.out.println(resultList.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
	}
}
