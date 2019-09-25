package threads;

public class AnimationThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 50;
	
	public AnimationThread() {
		super(WAIT_TIME, NB_WAITS);
		setName("Animation");
	}

	@Override
	public void finallyDoThat() {
		System.out.println("change images order !");
		
	}
	
	
	
	//-------------------------------------------------------
	
	
	public static void main(String args[]) throws InterruptedException {
		
		int WAIT_DELAY = 5000;
		int JOIN_DELAY = 100;
		
		AnimationThread anim = new AnimationThread();
		
		anim.start();
		
		Thread.sleep(WAIT_DELAY);
		
		synchronized(anim) {
			anim.pauseThread();
			anim.wait(2000);
			anim.resumeThread();
		}
		Thread.sleep(WAIT_DELAY);
		
		anim.stopThread();
		anim.join(JOIN_DELAY);
		if(anim.isAlive()) {
			anim.interrupt();
		}
	}
	
}
