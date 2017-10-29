package NIO.SocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 用于理解SocketChannel的案例：服务端
 *Java NIO中的SocketChannel是一个连接到TCP网络套接字的通道。可以通过以下2种方式创建SocketChannel：
 * 	1. 打开一个SocketChannel并连接到互联网上的某台服务器(ip + port)。
 * 	2. 一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。
 * @author 李嘉明
 * @2017-3-24
 */
public class Server {
	private int size = 1024;
	private ServerSocketChannel socketChannel;
	private ByteBuffer byteBuffer;
	private Selector selector;
	/** 端口号 */
	private final int port = 8998;
	private int remoteClientNum = 0;

	public Server() {
		try {
			initChannel();
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	public void initChannel() throws Exception {
		socketChannel = ServerSocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.bind(new InetSocketAddress(port));
		System.out.println("listener on port:" + port);
		selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		byteBuffer = ByteBuffer.allocate(size);
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
	}

	private void listener() throws Exception {
		while (true) {
			int n = selector.select();
			if (n == 0) {
				continue;
			}
			Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = ite.next();
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					remoteClientNum++;
					System.out.println("online client num = " + remoteClientNum);
					replyClient(channel);
				}
				if (key.isReadable()) {
					readDataFromSocket(key);
				}
				ite.remove();
			}
		}
	}

	protected void readDataFromSocket(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		int count;
		byteBuffer.clear();
		while ((count = socketChannel.read(byteBuffer)) > 0) {
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				socketChannel.write(byteBuffer);
			}
			byteBuffer.clear();
		}
		if (count < 0) {
			socketChannel.close();
		}
	}

	private void replyClient(SocketChannel channel) throws IOException {
		byteBuffer.clear();
		byteBuffer.put("hello client!\r\n".getBytes());
		byteBuffer.flip();
		channel.write(byteBuffer);
	}

	public static void main(String[] args) {
		try {
			new Server().listener();
		} catch (Exception e) {
		}
	}
}
