package forWork1;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalTest {
	public static void main(String[] args) {
		ThreadLocalss x = new ThreadLocalss();
		x.setTime(100L);
		System.out.println(x.getTime());
		ThreadLocalss x1 = new ThreadLocalss();
		System.out.println(x1.getTime());
		ThreadLocalss x2 = new ThreadLocalss();
		x2.setTime(100L);
		System.out.println(x2.getTime());
	}

	static class ThreadLocalss extends Thread{
		@SuppressWarnings("rawtypes")
		private final Map<Map,Long> time1 = new HashMap<Map,Long>();
		public Long getTime(){
			return time1.get(time1);
		}
		public void setTime(Long time){
			time1.put(time1,time);
		}
	}
}
