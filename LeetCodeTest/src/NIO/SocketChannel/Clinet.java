package NIO.SocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

/**
 * 用于理解SocketChannel的案例：客户端<br/>
 * Java NIO中的SocketChannel是一个连接到TCP网络套接字的通道。可以通过以下2种方式创建SocketChannel：
 * 	1. 打开一个SocketChannel并连接到互联网上的某台服务器(ip + port)。
 * 	2. 一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。
 * @author 李嘉明
 * @2017-3-24
 */
public class Clinet {
	private int size = 1024;
	private ByteBuffer byteBuffer;
	private SocketChannel socketChannel;

	public void connectServer() throws IOException {
		socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8998));
		byteBuffer = ByteBuffer.allocate(size);
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
		receive();
	}

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
		new Clinet().connectServer();
	}
}
