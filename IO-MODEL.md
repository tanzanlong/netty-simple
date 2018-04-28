# 5种IO模型

1. 阻塞式IO模型(blocking IO model)
2. 非阻塞式IO模型(noblocking IO model)
3. IO复用式IO模型(IO multiplexing model)
4. 信号驱动式IO模型(signal-driven IO model)
5. 异步IO式IO模型(asynchronous IO model)

##阻塞式IO模型(blocking IO model)
 在进程空间中调用系统函数recvfrom，在调用到数据包到达到被复制应用进程的缓冲区或者发生错误时才返回。在此期间，线程一直等待。因此称为阻塞式IO。
 
 
## 非阻塞式IO模型(noblocking IO model)