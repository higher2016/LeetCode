import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorTest {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newScheduledThreadPool(3);
		@SuppressWarnings("unused")
		Map<Integer, Future<String>> futureList = new HashMap<Integer, Future<String>>();
		for (int i = 0; i < 20; i++) {
			Person person = new Person("Li" + i, i + 1);
			TaskTesst taskTest = new TaskTesst(person);
//			futureList.put(i, executorService.submit(taskTest));
			executorService.submit(taskTest);
		}
//		for (int i = 0; i < futureList.size(); i++) {
//			try {
//				System.out.println(futureList.get(i).get());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//		}
	}
}

class TaskTest implements Callable<String> {
	private Person person;

	public TaskTest(Person person) {
		super();
		this.person = person;
	}

	@Override
	public String call() throws Exception {
		System.out.println("-------" + person.getName());
		return "My name is " + person.getName() + ".I'm " + person.getAge() + " years old!";
	}

}
class TaskTesst implements Runnable {
	private Person person;
	
	public TaskTesst(Person person) {
		super();
		this.person = person;
	}
	
	@Override
	public void run(){
		System.out.println("-------" + person.getName());
	}
	
}

class Person {
	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Person() {
		super();
	}

	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
}
