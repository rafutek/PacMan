package threads;

import sprites.Sprite;
import sprites.Sprites;

public class AnimationThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 20;
	
	private Sprites energizers;
	private Sprite pacMan;
	
	/**
	 * Thread that will update the sprites' images order, 
	 * thus the render thread will display another image so it will create the animation.
	 * @param energizers
	 */
	public AnimationThread(Sprites energizers, Sprite pacMan) {
		super(WAIT_TIME, NB_WAITS);
		setName("Animation");
		
		this.energizers = energizers;
		this.pacMan = pacMan;
	}

	@Override
	public void finallyDoThat() {
		
		// change the image of the animated sprites to display
		energizers.update();
		pacMan.updateImg();
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
