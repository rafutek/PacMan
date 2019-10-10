package threads;


import java.util.concurrent.ThreadLocalRandom;

import sprites.Ghost;
import sprites.PacMan;

public class GhostBehaviorThread extends TimerThread {
	
	private static final int WAIT_TIME = 10;
	
	private int random_min = 100; // change random direction between delays of 1 to 5 seconds
	private int random_max = 500;
	protected PacMan pacMan;
	private Ghost ghost;

	public GhostBehaviorThread(Ghost ghost, PacMan pacMan) {
		super(WAIT_TIME, 0);
		setName("Ghost behavior");
		this.ghost = ghost;
		setRandomNbWaits();
		this.pacMan=pacMan;
	}
	
	@Override
	protected void doThatAtStart() {}
	
	@Override
	protected void doThatWhileWaiting() {
		if (ghost.escaping()) {
			// change direction only when ghost is in the same corridor and its actual direction go to pac-man
			if (ghost.sameCorridor() && ghost.directionToPacMan() == ghost.getState()) {
				ghost.chooseDirectionToEscapeFrom(ghost.lastSeenPacManMatrixPos());
				counterWaits=0;
			}			
		}
		else if(ghost.goingToLastSeenPosition()) {
			counterWaits=0;
			ghost.checkAtLastSeenPosition();
		}		
		else if (ghost.specificAvailable()) {
			counterWaits=0; //reset the timer so the direction will not be randomized
  			ghost.launchSpecific();
		}			
	}

	@Override
	protected void finallyDoThat() {
		synchronized(ghost) {
			ghost.setRandomDirection(); // change direction at a random time		
		}
		setRandomNbWaits();
	}


	
	private void setRandomNbWaits() {
		int random = ThreadLocalRandom.current().nextInt(random_min, random_max + 1);	
		nb_waits_max = random;
	}

	
	public void changeDirection() {
		synchronized(ghost) {
			ghost.setRandomDirection(); // change direction at a random time			
			setRandomNbWaits();
			if(ghost.goingToLastSeenPosition()) {
				ghost.notGoingToLastSeenPosition();
			}
		}
	}
	
}