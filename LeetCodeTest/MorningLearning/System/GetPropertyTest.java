package System;

/**
 * 通过System的静态方法getProperty(String key)获取系统相关属性
 * @author JM
 * @2017-3-15
 */
public class GetPropertyTest {
	public static void main(String[] args) {
		//获取当前项目的绝对路径
		System.out.println(System.getProperty("user.dir"));
		//用户的账户名
		System.out.println(System.getProperty("user.home"));
		//用户的主目录
		System.out.println(System.getProperty("user.name"));
		//获取Java的安装目录
		System.out.println(System.getProperty("java.home"));
		//获取运行时环境版本
		System.out.println(System.getProperty("java.version"));
		//获取类路径
		System.out.println(System.getProperty("java.class.path"));
		
		System.out.println(System.getProperty("ssl"));
	}
}
