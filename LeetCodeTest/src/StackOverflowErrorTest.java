
/**
 * TEST2:java.lang.StackOverflowError:栈溢出异常<br>
 * 如果当前线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError异常。
 * @author	 JM
 * @date 	 2017-6-5 下午10:29:05
 * @since	 JDK 1.7
 */
public class StackOverflowErrorTest {
//  private int stackLength = 1;
  public void stackLeak() {
//    stackLength++;
    stackLeak();
  }
  public static void main(String[] args) {
    StackOverflowErrorTest oom = new StackOverflowErrorTest();
//    try {
      oom.stackLeak();
//    } catch (Throwable e) {
//      System.out.println("stack length:" + oom.stackLength);
//      System.out.println(e.toString());
//    }
  }
}
