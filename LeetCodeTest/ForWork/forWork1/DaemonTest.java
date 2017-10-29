package forWork1;

/**
 * DaemonTest:守护线程测试
 * @author	 JM
 * @date 	 2017-5-31 下午9:30:13
 * @since	 JDK 1.7
 */
public class DaemonTest {

	/**
	 * main:当主线程执行完之后，Java虚拟机将不存在非Daemon线程，这时所有未结束的Daemon线程都将强制终止（即使finally块内的任务也不会被执行）
	 * @author JM 
	 * 2017-5-31 下午9:31:23
	 * @param args   
	 * void
	 */
	public static void main(String[] args) {
		Thread thread = new Thread(new Daemon());
		System.out.println("sssaaa");
		// 将线程设置为守护线程（必须要在线程启动之前设置）
		thread.setDaemon(true);
		thread.start();
	}
	
	private static class Daemon implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("sss");
			}
		}
	}
}
