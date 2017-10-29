package test;

/**
 * RecursiveTest:递归测试方法
 * 
 * @author JM
 * @date 2017-5-28 下午11:08:32
 * @since JDK 1.7
 */
public class RecursiveTest {

	public static void main(String[] args) {
		System.out.println(f(0));
		System.out.println(f(1));
		System.out.println(f(2));
		 System.out.println(f(-2));
	}

	public static int f(int x) {
		if (x < 0)
			throw new RuntimeException("输入数据有误");
		if (x == 0)
			return 0;
		return 2 * f(x - 1) + x * x;
	}
}
