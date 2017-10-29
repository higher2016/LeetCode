package forWork1;

/**
 * VolatileTest:关键字volatile对线程同步的作用（volatile无法保证非原子性操作的多线程安全问题得到解决）<br>
 * volatile解决的是多线程共享变量的可见性问题，但对于多线程共享变量修改的问题是无法通过volatile来解决的<br>
 * 一个地方修改其他地方正确读取是可以做到的，多个地方修改并保证最终没有线程同步问题是无法做到的。
 * @author	 JM
 * @date 	 2017-5-31 下午9:52:43
 * @since	 JDK 1.7
 */
public class VolatileTest {

	private static volatile int count = 0;
	private static final int times = 100000;

	public static void main(String[] args) {
		long curTime = System.nanoTime();
		Thread decThread = new DecThread();
		decThread.start();
		// 使用run()来运行结果为0,原因是单线程执行不会有线程安全问题
		// new DecThread().run();
		System.out.println("Start thread: " + Thread.currentThread() + " i++");
		for (int i = 0; i < times; i++) {
			count++;
		}
		System.out.println("End thread: " + Thread.currentThread() + " i--");
		// 等待decThread结束
		while (decThread.isAlive())
			;

		long duration = System.nanoTime() - curTime;
		System.out.println("Result: " + count);
		System.out.format("Duration: %.2fs\n", duration / 1.0e9);
	}

	private static class DecThread extends Thread {
		@Override
		public void run() {
			System.out.println("Start thread: " + Thread.currentThread() + " i--");
			for (int i = 0; i < times; i++) {
				count++;
			}
			System.out.println("End thread: " + Thread.currentThread() + " i--");
		}
	}
}
