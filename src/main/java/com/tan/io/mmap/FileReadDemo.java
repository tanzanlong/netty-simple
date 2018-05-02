package com.tan.io.mmap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileReadDemo {

	
	/** 
	    * 传统IO读取数据,不指定缓冲区大小
	    * @return 
	    */ 
	   public static void readFile1(String path) { 
	       long start = System.currentTimeMillis();//开始时间
	       File file = new File(path);  
	       if (file.isFile()) {  
	           BufferedReader bufferedReader = null;  
	           FileReader fileReader = null;  
	           try {  
	               fileReader = new FileReader(file);  
	               bufferedReader = new BufferedReader(fileReader);  
	               String line = bufferedReader.readLine();  
	               System.out.println("========================== 传统IO读取数据，使用虚拟机堆内存 ==========================");  
	               while (line != null) { //按行读数据
	                   System.out.println(line);  
	                   line = bufferedReader.readLine();  
	               }  
	           } catch (FileNotFoundException e) {  
	               e.printStackTrace();  
	           } catch (IOException e) {  
	               e.printStackTrace();  
	           } finally {  
	               //最后一定要关闭
	               try {  
	                   fileReader.close();  
	                   bufferedReader.close();  
	               } catch (IOException e) {  
	                   e.printStackTrace();  
	               }  
	               long end = System.currentTimeMillis();//结束时间
	               System.out.println("传统IO读取数据，不指定缓冲区大小，总共耗时："+(end - start)+"ms");
	           }  
	  
	       }  
	   }
	
	   
	   
	   
	   
	   /** 
	     * 传统IO读取数据,指定缓冲区大小
	     * @param path 
	     * @return 
	     * @throws FileNotFoundException 
	     */ 
	    public static void readFile2(String path) throws FileNotFoundException {
	        long start = System.currentTimeMillis();//开始时间
	        int bufSize = 1024 * 1024 * 5;//5M缓冲区
	        File fin = new File(path); // 文件大小200M
	        FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
	        ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);                      
	        String enterStr ="";
	;
	        long len = 0L;
	        try {
	            byte[] bs = new byte[bufSize];
	            String tempString = null;
	            while (fcin.read(rBuffer) != -1) {//每次读5M到缓冲区
	                int rSize = rBuffer.position();
	                rBuffer.rewind();
	                rBuffer.get(bs);//将缓冲区数据读到数组中
	                rBuffer.clear();//清除缓冲
	                tempString = new String(bs, 0, rSize);
	                int fromIndex = 0;//缓冲区起始
	                int endIndex = 0;//缓冲区结束
	                //按行读缓冲区数据
	                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
	                    String line = tempString.substring(fromIndex, endIndex);//转换一行          
	                    System.out.print(line);                  
	                    fromIndex = endIndex + 1;
	                }
	            }
	            long end = System.currentTimeMillis();//结束时间
	            System.out.println("传统IO读取数据,指定缓冲区大小，总共耗时："+(end - start)+"ms");
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * NIO 内存映射读大文件
	     * @param path
	     */
	    public static void readFile3(String path) {
	        long start = System.currentTimeMillis();//开始时间
	        long fileLength = 0;  
	        final int BUFFER_SIZE = 0x300000;// 3M的缓冲  
	            File file = new File(path);  
	            fileLength = file.length();  
	            try {  
	                MappedByteBuffer inputBuffer = new RandomAccessFile(file, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileLength);// 读取大文件  
	   
	                byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容  
	   
	                for (int offset = 0; offset < fileLength; offset += BUFFER_SIZE) {  
	                    if (fileLength - offset >= BUFFER_SIZE) {  
	                        for (int i = 0; i < BUFFER_SIZE; i++)  
	                            dst[i] = inputBuffer.get(offset + i);  
	                    } else {  
	                        for (int i = 0; i < fileLength - offset; i++)  
	                            dst[i] = inputBuffer.get(offset + i);  
	                    }  
	                    // 将得到的3M内容给Scanner，这里的XXX是指Scanner解析的分隔符  
	                    Scanner scan = new Scanner(new ByteArrayInputStream(dst)).useDelimiter(Pattern.compile(""));  
	                    while (scan.hasNext()) {  
	                        // 这里为对读取文本解析的方法  
	                        System.out.print(scan.next() +"");  
	                    }  
	                    scan.close();  
	                }  
	                System.out.println();
	                long end = System.currentTimeMillis();//结束时间
	                System.out.println("NIO 内存映射读大文件，总共耗时："+(end - start)+"ms");
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	    } 
}
