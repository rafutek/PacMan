package threads;

import sprites.MovingSprite;
import sprites.MovingSpriteState;
import sprites.Sprite;
import sprites.Sprites;

public class AnimationThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 20;
	
	private Sprites energizers;
	private MovingSprite pacMan;
	
	private MovingSpriteState pacManLastState, pacManCurrentState;
	
	/**
	 * Thread that will update the sprites' images order, 
	 * thus the render thread will display another image so it will create the animation.
	 * @param energizers
	 */
	public AnimationThread(Sprites energizers, MovingSprite pacMan) {
		super(WAIT_TIME, NB_WAITS);
		setName("Animation");
		
		this.energizers = energizers;
		this.pacMan = pacMan;
		pacManLastState = pacMan.getState();
	}

	@Override
	protected void doThatWhileWaiting() {
		pacManCurrentState = pacMan.getState();
		if(pacManCurrentState != pacManLastState) { // have to change the animation list to the new state
			if(pacManCurrentState == MovingSpriteState.LEFT) {
				pacMan.setGoLeftAnimation();
			}
			else if(pacManCurrentState == MovingSpriteState.RIGHT) {
				pacMan.setGoRightAnimation();
			}
			else if(pacManCurrentState == MovingSpriteState.UP) {
				pacMan.setGoUpAnimation();
			}
			else if(pacManCurrentState == MovingSpriteState.DOWN) {
				pacMan.setGoDownAnimation();
			}
			else if(pacManCurrentState == MovingSpriteState.DEATH) {
				pacMan.setDeathAnimation();
			}
			
		}
		
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
