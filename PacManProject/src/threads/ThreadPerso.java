package threads;

public abstract class ThreadPerso extends Thread {
	
	private int wait_time = 8; //ms
	protected volatile boolean running = false;
	protected boolean paused = false;
	
	public ThreadPerso(String threadName) {
		setName(threadName);
	}
	
	public void run() {
		System.out.println("Start "+getName()+" thread");
		
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
		System.out.println("Stop "+getName()+" thread");
		verifyThreadIsStopped();
	}
	
	/**
	 * Change the duration of the sleep in the thread.
	 * @param t is the new delay.
	 */
	public synchronized void setWaitTime(int t) {
		wait_time = t;
	}
	

	/**
	 * Actions done when when the thread starts.
	 * They have to be defined in the inheriting classes.
	 */
	protected abstract void doThatAtStart();  
	
	
	/**
	 * Actions done while the thread is running and not paused.
	 * They have to be defined in the inheriting classes.
	 */
	protected abstract void doThat();  
	
	/**
	 * Actions done when the thread is stopped.
	 * They have to be defined in the inheriting classes.
	 */
	protected abstract void doThatAtStop();  
	
	
	private void verifyThreadIsStopped() {
		stopThread();
		try {
			join(100);
			if(isRunning()) {
				interrupt();
			}
		} catch (InterruptedException e) {}
	}
	
	/**
	 * Method that starts the thread.
	 */
	public void startThread() {
		if(!running) {
			start();
		}
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
	
	/**
	 * Stop the thread.
	 */
	public void stopThread() {
		running = false;
	}
	
	/**
	 * Return true if the thread is running, else return false.
	 * @return running boolean value.
	 */
	public boolean isRunning() {
		return running;
	}

}
