package NIO.Selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {
	private int size = 1024;
	private ByteBuffer byteBuffer;
	private SocketChannel socketChannel;

	/**
	 * 将通道连接服务器
	 * @throws IOException
	 */
	public void connectServer() throws IOException {
		socketChannel = SocketChannel.open();
		// 将这个channel连接到服务器上（IP+port）
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8998));
		//初始化缓冲区大小
		byteBuffer = ByteBuffer.allocate(size);
		//定义byteBuffer从缓冲区中存储或检索多字节数值时使用哪一字节顺序的常量
		//ByteOrder:作用就像一个类型安全的枚举。它定义了以其本身实例预初始化的两个public区域。
		//只有这两个ByteOrder实例总是存在于JVM中，因此它们可以通过使用--操作符进行比较
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
		receive();
	}

	/**
	 * 接收来自服务器的消息
	 * @throws IOException
	 */
	private void receive() throws IOException {
		while (true) {
			byteBuffer.clear();
			while (socketChannel.read(byteBuffer) > 0) {
				byteBuffer.flip();
				while (byteBuffer.hasRemaining()) {
					System.out.print((char) byteBuffer.get());
				}
				send("send data to server\r\n".getBytes());
				byteBuffer.clear();
			}
		}
	}

	private void send(byte[] data) throws IOException {
		byteBuffer.clear();
		byteBuffer.put(data);
		byteBuffer.flip();
		socketChannel.write(byteBuffer);
	}

	public static void main(String[] args) throws IOException {
		new SocketChannelTest().connectServer();
	}
}
