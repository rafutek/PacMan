package threads;

public class TimerThread extends ThreadPerso{
	
	private final int NB_LOOP = 100; //for 1 sec timer
	private int countLoop = 0;
	private int nb_sec = 0;

	
	public TimerThread() {
		super("Timer");
		setWaitTime(10); 
	}

	@Override
	public void doThatAtStart() {
		// nothing to do at start
	}


	@Override
	public void doThat() {
		countLoop++;
		if(countLoop == NB_LOOP) {
			countLoop = 0;
			nb_sec++;
			System.out.println(nb_sec);
		}
	}
	
	@Override
	public void doThatAtStop() {
		// nothing to do at stop
	}
	
	//-------------------------------------------------------
	
	
	private static final int WAIT_DELAY = 5000;
	private static final int JOIN_DELAY = 100;
	
	public static void main(String args[]) throws InterruptedException {
		
		TimerThread timer = new TimerThread();
		
		timer.start();
		
		Thread.sleep(WAIT_DELAY);
		
		synchronized(timer) {
			timer.pauseThread();
			timer.wait(2000);
			timer.resumeThread();
		}
		Thread.sleep(WAIT_DELAY);
		
		timer.stopThread();
		timer.join(JOIN_DELAY);
		if(timer.isAlive()) {
			timer.interrupt();
		}
	}

}
