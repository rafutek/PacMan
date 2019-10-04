package threads;


import java.util.concurrent.ThreadLocalRandom;

import sprites.Ghost;

public class GhostBehaviorThread extends TimerThread {
	
	private static final int WAIT_TIME = 10;
	
	private int random_min = 100;
	private int random_max = 500;
	
	private Ghost ghost;

	public GhostBehaviorThread(Ghost ghost) {
		super(WAIT_TIME, 0);
		setName("Ghost behavior");
		this.ghost = ghost;
		setRandomNbWaits();
	}
	
	@Override
	protected void doThatAtStart() {}
	
	@Override
	protected void doThatWhileWaiting() {
		
		if(ghost.goingToLastSeenPosition()) {
			System.out.println("going to last seen position");
			counterWaits=0;
			ghost.checkAtLastSeenPosition();
		}		
		
		else if(ghost.escaping()) {
			System.out.println("escaping");
			counterWaits=0;
		}
		
		else if (ghost.specificAvailable()) {
			counterWaits=0; //reset the timer so the direction will not be randomized
  			ghost.launchSpecific();
		}

	}

	@Override
	protected void finallyDoThat() {
		ghost.setRandomDirection(); // change direction at a random time
		setRandomNbWaits();
	}


	
	private void setRandomNbWaits() {
		int random = ThreadLocalRandom.current().nextInt(random_min, random_max + 1);	
		nb_waits_max = random;
	}

	
	public void changeDirection() {
		ghost.setRandomDirection(); // change direction at a random time
		setRandomNbWaits();
		if(ghost.goingToLastSeenPosition()) {
			ghost.notGoingToLastSeenPosition();
		}
		if(ghost.escaping()) {
			ghost.notEscape();
		}
	}
	
}