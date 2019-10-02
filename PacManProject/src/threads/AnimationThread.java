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
	
	private MovingSprite pinky;
	private MovingSpriteState pinkyLastState, pinkyCurrentState;
	
	private MovingSprite clyde;
	private MovingSpriteState clydeLastState, clydeCurrentState;
	
	private MovingSprite inky;
	private MovingSpriteState inkyLastState, inkyCurrentState;
	
	/**
	 * Thread that will update the sprites' images order, 
	 * thus the render thread will display another image so it will create the animation.
	 * @param energizers
	 */
	public AnimationThread(Sprites energizers, MovingSprite pacMan, MovingSprite blinky, MovingSprite pinky, MovingSprite clyde, MovingSprite inky) {
		super(WAIT_TIME, NB_WAITS);
		setName("Sprites Animation");
		
		this.energizers = energizers;
		
		this.pacMan = pacMan;
		synchronized(pacMan) {
			pacManLastState = pacMan.getState();
		}
		
		this.blinky = blinky;
		synchronized(blinky) {
			blinkyLastState = blinky.getState();
		}
		
		this.pinky = pinky;
		synchronized(pinky) {
			pinkyLastState = pinky.getState();
		}
		
		this.clyde = clyde;
		synchronized(clyde) {
			clydeLastState = clyde.getState();
		}
		
		this.inky = inky;
		synchronized(inky) {
			inkyLastState = inky.getState();
		}
	}

	@Override
	protected void doThatAtStart() {}
	
	@Override
	protected void doThatWhileWaiting() {
		
		//pac-man
		synchronized(pacMan) {
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
		}
		
		//blinky
		synchronized(blinky) {
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
		}
		
		//pinky
		synchronized(pinky) {
			pinkyCurrentState = pinky.getState();
			if(pinkyCurrentState != pinkyLastState) { // have to change the animation list to the new state
				if(pinkyCurrentState == MovingSpriteState.STOP) {
					pinky.setNoMovementAnimation();
				}
				if(pinkyCurrentState == MovingSpriteState.LEFT) {
					pinky.setGoLeftAnimation();
				}
				else if(pinkyCurrentState == MovingSpriteState.RIGHT) {
					pinky.setGoRightAnimation();
				}
				else if(pinkyCurrentState == MovingSpriteState.UP) {
					pinky.setGoUpAnimation();
				}
				else if(pinkyCurrentState == MovingSpriteState.DOWN) {
					pinky.setGoDownAnimation();
				}
				else if(pinkyCurrentState == MovingSpriteState.DEATH) {
					pinky.setDeathAnimation();
				}
				pinkyLastState = pinkyCurrentState;
			}				
		}
		
		//clyde
		synchronized(clyde) {
			clydeCurrentState = clyde.getState();
			if(clydeCurrentState != clydeLastState) { // have to change the animation list to the new state
				if(clydeCurrentState == MovingSpriteState.STOP) {
					clyde.setNoMovementAnimation();
				}
				if(clydeCurrentState == MovingSpriteState.LEFT) {
					clyde.setGoLeftAnimation();
				}
				else if(clydeCurrentState == MovingSpriteState.RIGHT) {
					clyde.setGoRightAnimation();
				}
				else if(clydeCurrentState == MovingSpriteState.UP) {
					clyde.setGoUpAnimation();
				}
				else if(clydeCurrentState == MovingSpriteState.DOWN) {
					clyde.setGoDownAnimation();
				}
				else if(clydeCurrentState == MovingSpriteState.DEATH) {
					clyde.setDeathAnimation();
				}
				clydeLastState = clydeCurrentState;
			}				
		}
		
		//inky
		synchronized(clyde) {
			inkyCurrentState = inky.getState();
			if(inkyCurrentState != inkyLastState) { // have to change the animation list to the new state
				if(inkyCurrentState == MovingSpriteState.STOP) {
					inky.setNoMovementAnimation();
				}
				if(inkyCurrentState == MovingSpriteState.LEFT) {
					inky.setGoLeftAnimation();
				}
				else if(inkyCurrentState == MovingSpriteState.RIGHT) {
					inky.setGoRightAnimation();
				}
				else if(inkyCurrentState == MovingSpriteState.UP) {
					inky.setGoUpAnimation();
				}
				else if(inkyCurrentState == MovingSpriteState.DOWN) {
					inky.setGoDownAnimation();
				}
				else if(inkyCurrentState == MovingSpriteState.DEATH) {
					inky.setDeathAnimation();
				}
				inkyLastState = inkyCurrentState;
			}				
		}
	}
	
	@Override
	public void finallyDoThat() {
		
		// change the image of the animated sprites to display
		energizers.updateImg();
		synchronized(pacMan) {
			pacMan.updateImg();
		}
		synchronized(blinky) {
			blinky.updateImg();
		}
		synchronized(pinky) {
			pinky.updateImg();
		}
		synchronized(clyde) {
			clyde.updateImg();
		}
		synchronized(inky) {
			inky.updateImg();
		}
	}

}
