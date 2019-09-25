package threads;

public abstract class ThreadPerso extends Thread {
	
	private int wait_time = 8; //ms
	private volatile boolean running = false;
	private boolean paused = false;
	
	public ThreadPerso(String threadName) {
		setName(threadName);
	}
	
	public void run() {
		System.out.println("Start "+getName());
		
		doThatAtStart();
		
		running = true;
		while(running) {
			try {
				Thread.sleep(wait_time);
				if(!paused) {
					doThat();
				}
			} catch (InterruptedException e) {}
		}
		
		doThatAtStop();
		
		System.out.println("Stop "+getName());
	}
	
	/**
	 * Change the duration of the sleep in the thread.
	 * @param t is the new delay.
	 */
	public synchronized void setWaitTime(int t) {
		wait_time = t;
	}
	
	public void startThread() {
		if(!running) {
			start();
		}
	}

	/**
	 * Actions done when when the thread starts.
	 * They have to be defined in the inheriting classes.
	 */
	public abstract void doThatAtStart();  
	
	
	/**
	 * Actions done while the thread is running and not paused.
	 * They have to be defined in the inheriting classes.
	 */
	public abstract void doThat();  
	
	/**
	 * Actions done when the thread is stoped.
	 * They have to be defined in the inheriting classes.
	 */
	public abstract void doThatAtStop();  
	
	/**
	 * Stop the thread.
	 */
	public void stopThread() {
		running = false;
	}
	
	/**
	 * Stop doing the actions defined in doThat() method.
	 */
	public synchronized void pauseThread() {
		paused = true;
	}
	
	/**
	 * Start again or continue doing the actions defined in doThat() method.
	 */
	public synchronized void resumeThread() {
		paused = false;
	}

}
