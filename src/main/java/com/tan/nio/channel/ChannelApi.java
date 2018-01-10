package com.tan.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ChannelApi {
	public static void main(String[] args) throws IOException {

		String filepath = "F://2017/netty/file/索引.txt";
		ChannelApi.readFileChannelFile(filepath);
		ChannelApi.readSocketChannel(filepath);
	}

	/**
	 * Java NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。
	 * FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public static void readFileChannelFile(String filepath) throws IOException {
		RandomAccessFile aFile = new RandomAccessFile(
				filepath, "rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			System.out.println("Read " + bytesRead);
			buf.flip();
			while (buf.hasRemaining())
				System.out.print((char) buf.get());

			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}
	
	/**
	 * Java NIO中的SocketChannel是一个连接到TCP网络套接字的通道。可以通过以下2种方式创建SocketChannel：
      1 .打开一个SocketChannel并连接到互联网上的某台服务器。
      2.一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。件。
	 * 
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public static void readSocketChannel(String filepath) throws IOException {
		
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("jenkov.com", 80)); //http://
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = socketChannel.read(buf);
		while (bytesRead != -1) {
			System.out.println("Read " + bytesRead);
			buf.flip();
			while (buf.hasRemaining())
				System.out.print((char) buf.get());

			buf.clear();
			bytesRead = socketChannel.read(buf);
		}
		socketChannel.close();
	}
}
