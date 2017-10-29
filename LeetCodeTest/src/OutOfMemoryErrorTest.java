import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMemoryErrorTest:堆内存溢出异常<br>
 * 如果虚拟机在扩展栈时无法申请到足够的内存空间，则抛出OutOfMemoryError异常。
 * 
 * @author JM
 * @date 2017-6-5 下午10:31:32
 * @since JDK 1.7
 */
public class OutOfMemoryErrorTest {
	static class OOMObject {
	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		while (true) {
			list.add(new OOMObject());
		}
	}
}
