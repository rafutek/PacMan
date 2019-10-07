package threads;

import sprites.Blinky;
import sprites.Clyde;
import sprites.Inky;
import sprites.MovingSpriteState;
import sprites.PacMan;
import sprites.Pinky;
import sprites.Sprites;

public class AnimationThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 20;
	
	private Sprites energizers;
	private boolean wasEscaping=false;
	private PacMan pacMan;
	private MovingSpriteState pacManLastState, pacManCurrentState;

	private Blinky blinky;
	private MovingSpriteState blinkyLastState, blinkyCurrentState;
	
	private Pinky pinky;
	private MovingSpriteState pinkyLastState, pinkyCurrentState;
	
	private Clyde clyde;
	private MovingSpriteState clydeLastState, clydeCurrentState;
	
	private Inky inky;
	private MovingSpriteState inkyLastState, inkyCurrentState;
	
	/**
	 * Thread that will update the sprites' images order, 
	 * thus the render thread will display another image so it will create the animation.
	 * @param energizers
	 */
	public AnimationThread(Sprites energizers, PacMan pacMan, Blinky blinky, Pinky pinky, Clyde clyde, Inky inky) {
		super(WAIT_TIME, NB_WAITS);
		setName("Sprites Animation");
		
		if(energizers.getSprites() != null && !energizers.getSprites().isEmpty()) {
			this.energizers = energizers;
		}
		
		if(pacMan != null) {
			this.pacMan = pacMan;
			synchronized(pacMan) {
				pacManLastState = pacMan.getState();
			}			
		}
		
		if(blinky != null) {
			this.blinky = blinky;
			synchronized(blinky) {
				blinkyLastState = blinky.getState();
			}			
		}
		
		if(pinky != null) {
			this.pinky = pinky;
			synchronized(pinky) {
				pinkyLastState = pinky.getState();
			}			
		}

		if(clyde != null) {
			this.clyde = clyde;
			synchronized(clyde) {
				clydeLastState = clyde.getState();
			}			
		}

		if(inky != null) {
			this.inky = inky;
			synchronized(inky) {
				inkyLastState = inky.getState();
			}			
		}

	}

	@Override
	protected void doThatAtStart() {}
	
	@Override
	protected void doThatWhileWaiting() {
		
		//pac-man
		if(pacMan != null) {
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
						
		}

		//blinky
		if(blinky != null) {
			synchronized(blinky) {
				if (blinky.escaping()) {
					blinky.setEscapingAnimation();
					wasEscaping =true;
				}
				
				else {
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
				
			}
			
		}

		//pinky
		if(pinky != null) {
			synchronized(pinky) {
				if (pinky.escaping()) {
					pinky.setEscapingAnimation();
					wasEscaping =true;
				}
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
		}
		
		//clyde
		if(clyde != null) {
			synchronized(clyde) {
				if (clyde.escaping()) {
					clyde.setEscapingAnimation();
					wasEscaping =true;
				}
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
		}

		
		//inky
		if(inky != null) {
			synchronized(inky) {
				if (blinky.escaping()) {
					inky.setEscapingAnimation();
					wasEscaping =true;
				}
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
		}

	
	@Override
	public void finallyDoThat() {
		
		// change the image of the animated sprites to display
		if(energizers != null && energizers.getSprites() != null && !energizers.getSprites().isEmpty()) {
			energizers.updateImg();
		}
		if(pacMan != null) {
			synchronized(pacMan) {
				pacMan.updateImg();
			}			
		}
		if(blinky != null) {
			synchronized(blinky) {
				blinky.updateImg();
			}			
		}
		if(pinky != null) {
			synchronized(pinky) {
				pinky.updateImg();
			}			
		}
		if(clyde != null) {
			synchronized(clyde) {
				clyde.updateImg();
			}			
		}
		if(inky != null) {
			synchronized(inky) {
				inky.updateImg();
			}			
		}

	}

}
