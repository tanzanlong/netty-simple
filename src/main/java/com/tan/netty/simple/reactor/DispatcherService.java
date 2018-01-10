package com.tan.netty.simple.reactor;

import java.nio.channels.SelectionKey;

/**代表事件分发策略
 * @author Administrator
 *
 */
public interface DispatcherService {

	 void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key);
	 
	 void stop() throws InterruptedException;
}
