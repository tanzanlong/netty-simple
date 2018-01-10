package com.tan.netty.simple.reactor;

import java.io.IOException;
import java.nio.channels.Selector;

public class ReactorSimple {

	private final Selector selector;
	
	private final DispatcherService dispatcherService;
	
	public ReactorSimple(DispatcherService dispatcherService){
		this.dispatcherService=dispatcherService;
		try {
			selector=Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
