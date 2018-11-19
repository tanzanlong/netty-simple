package com.tan.netty.api.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufApi {

   public static void main(String[] args) {
      // CompositeByteBuf compBuf = Unpooled.compositeBuffer();           
       ByteBuf heapBuf = Unpooled.buffer(8); 
        /**堆缓冲区
         * 1.检查 ByteBuf 是否有支持数组。 
         * 2.如果有的话，得到引用数组。 
         * 3.计算第一字节的偏移量。 
         * 4.获取可读的字节数。 
         * 5.使用数组，偏移量和长度作为调用方法的参数。
         */
       if (heapBuf.hasArray()) {                //1
           byte[] array = heapBuf.array();        //2
           int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();                //3
           int length = heapBuf.readableBytes();//4
       }
       
        /**
         * 1.检查 ByteBuf 是不是由数组支持。如果不是，这是一个直接缓冲区。 
         * 2.获取可读的字节数 
         * 3.分配一个新的数组来保存字节 
         * 4.字节复制到数组
         * 5.将数组，偏移量和长度作为参数调用某些处理方法
         */
       ByteBuf directBuf = Unpooled.directBuffer(16); 
       if (!directBuf.hasArray()) {            //1
           int length = directBuf.readableBytes();//2
           byte[] array = new byte[length];    //3
           directBuf.getBytes(directBuf.readerIndex(), array);        //4    
           //handleArray(array, 0, length);  //5
       }
}

}
