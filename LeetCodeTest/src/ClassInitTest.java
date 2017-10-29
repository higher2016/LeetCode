/**
 * ClassInitTest:类初始化
 * 
 * @author JM
 * @date 2017-6-6 上午12:29:46
 * @since JDK 1.7
 */
public class ClassInitTest {
	public static void main(String[] args) {
//		System.out.println("ClassInitTest");
//		System.out.println(Ji.i);
//		Ji ji = new Ji("s");
	}
}

class Ji {
	public static int i = 90;

	public Ji(String s) {
		System.out.println("Ji String con");
	}
	public Ji() {
		System.out.println("Ji defalut con");
	}

	{
		System.out.println("Ji---");// 凡是创建新的Ji对象就会执行一次
	}
	static {
		System.out.println("Ji"); // 只有在类初始化的时候执行
	}
}

class Ji1 extends Ji {

	public Ji1(String s) {
		super(s);// 子类的构造函数，肯定会调用父类的构造函数。（注意调用父类的无参构造函数不会创建一个新的父类对象）
		// 如果没有调用，JVM虚拟机会自动帮你调用父类的无参构造函数，当父类没有无参构造函数且没有显式调用父类其他构造函数，则编译错误
		// TODO Auto-generated constructor stub
		System.out.println("Ji1--");
	}

}