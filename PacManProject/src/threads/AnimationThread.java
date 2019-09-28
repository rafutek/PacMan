package threads;

import sprites.MovingSprite;
import sprites.MovingSpriteState;
import sprites.Sprites;

public class AnimationThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 20;
	
	private Sprites energizers;
	
	private MovingSprite pacMan;
	private MovingSpriteState pacManLastState, pacManCurrentState;

	private MovingSprite blinky;
	private MovingSpriteState blinkyLastState, blinkyCurrentState;
	
	/**
	 * Thread that will update the sprites' images order, 
	 * thus the render thread will display another image so it will create the animation.
	 * @param energizers
	 */
	public AnimationThread(Sprites energizers, MovingSprite pacMan, MovingSprite blinky) {
		super(WAIT_TIME, NB_WAITS);
		setName("Animation");
		
		this.energizers = energizers;
		
		this.pacMan = pacMan;
		pacManLastState = pacMan.getState();
		
		this.blinky = blinky;
		blinkyLastState = blinky.getState();
	}

	@Override
	protected void doThatWhileWaiting() {
		
		//pac-man
		pacManCurrentState = pacMan.getState();
		if(pacManCurrentState != pacManLastState) { // have to change the animation list to the new state
			if(pacManCurrentState == MovingSpriteState.STOP) {
				pacMan.setNoMovementAnimation();
			}
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
			pacManLastState = pacManCurrentState;
		}
		
		//blinky
		blinkyCurrentState = blinky.getState();
		if(blinkyCurrentState != blinkyLastState) { // have to change the animation list to the new state
			if(blinkyCurrentState == MovingSpriteState.STOP) {
				blinky.setNoMovementAnimation();
			}
			if(blinkyCurrentState == MovingSpriteState.LEFT) {
				blinky.setGoLeftAnimation();
			}
			else if(blinkyCurrentState == MovingSpriteState.RIGHT) {
				blinky.setGoRightAnimation();
			}
			else if(blinkyCurrentState == MovingSpriteState.UP) {
				blinky.setGoUpAnimation();
			}
			else if(blinkyCurrentState == MovingSpriteState.DOWN) {
				blinky.setGoDownAnimation();
			}
			else if(blinkyCurrentState == MovingSpriteState.DEATH) {
				blinky.setDeathAnimation();
			}
			blinkyLastState = blinkyCurrentState;
		}		
		
		//...
		
	}
	
	@Override
	public void finallyDoThat() {
		
		// change the image of the animated sprites to display
		energizers.update();
		pacMan.updateImg();
		blinky.updateImg();
	}

}
