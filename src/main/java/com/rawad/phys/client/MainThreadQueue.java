package com.rawad.phys.client;

import java.util.concurrent.LinkedBlockingQueue;

public final class MainThreadQueue {
	
	private static final LinkedBlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<Runnable>();
	
	public static boolean addRunnable(Runnable e) {
		return QUEUE.add(e);
	}
	
	public static Runnable pollRunnable() throws InterruptedException {
		return QUEUE.take();
	}
	
}
