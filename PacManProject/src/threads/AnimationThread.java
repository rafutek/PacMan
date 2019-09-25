package threads;

import sprites.Sprites;

public class AnimationThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 50;
	
	private Sprites energizers;
	
	/**
	 * Thread that will update the sprites' images order, 
	 * thus the render thread will display another image so it will create the animation.
	 * @param energizers
	 */
	public AnimationThread(Sprites energizers) {
		super(WAIT_TIME, NB_WAITS);
		setName("Animation");
		
		this.energizers = energizers;
	}

	@Override
	public void finallyDoThat() {
		energizers.update();
	}
	
	
	
//	//-------------------------------------------------------
//	
//	
//	public static void main(String args[]) throws InterruptedException {
//		
//		int WAIT_DELAY = 5000;
//		int JOIN_DELAY = 100;
//		
//		AnimationThread anim = new AnimationThread(null);
//		
//		anim.start();
//		
//		Thread.sleep(WAIT_DELAY);
//		
//		synchronized(anim) {
//			anim.pauseThread();
//			anim.wait(2000);
//			anim.resumeThread();
//		}
//		Thread.sleep(WAIT_DELAY);
//		
//		anim.stopThread();
//		anim.join(JOIN_DELAY);
//		if(anim.isAlive()) {
//			anim.interrupt();
//		}
//	}
	
}
