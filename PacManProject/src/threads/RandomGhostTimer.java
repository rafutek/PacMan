package threads;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import resources.Tiles;
import sprites.Blinky;
import sprites.Ghost;
import sprites.Position;

public class RandomGhostTimer extends TimerThread {
	
	private static final int WAIT_TIME = 10;
	private int nb_waits = 100; 
	
	private int random_min = 100;
	private int random_max = 500;
	
	private Ghost ghost;

	public RandomGhostTimer(Ghost ghost) {
		super(WAIT_TIME, 0);
		nb_waits_max = nb_waits;
		this.ghost = ghost;
	}
	
	@Override
	protected void doThatAtStart() {
		// TODO Auto-generated method stub

	}
	
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
		counterWaits = 0; 
	}
	
	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		RandomGhostTimer rTh = new RandomGhostTimer(new Blinky(new Position(0, 0), new Tiles()));
		rTh.startThread();
		
		synchronized (rTh) {
			
			Thread.sleep(10000);
			
			rTh.stopThread();
			rTh.join(100);
			if(rTh.isAlive()) {
				rTh.interrupt();
			}
		}
	}
}
