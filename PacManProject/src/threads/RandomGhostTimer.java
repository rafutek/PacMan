package threads;


import java.util.concurrent.ThreadLocalRandom;

import sprites.Ghost;

public class RandomGhostTimer extends TimerThread {
	
	private static final int WAIT_TIME = 10;
	
	private int random_min = 100;
	private int random_max = 500;
	
	private Ghost ghost;

	public RandomGhostTimer(Ghost ghost) {
		super(WAIT_TIME, 0);
		setName("Random ghost direction");
		this.ghost = ghost;
		setRandomNbWaits();
	}
	
	@Override
	protected void doThatAtStart() {}
	
	@Override
	protected void doThatWhileWaiting() {}

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
	}
	
}
