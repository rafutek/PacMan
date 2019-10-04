package sprites;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;
import threads.PhysicsThread;

public class Inky extends Ghost {

	public Inky(Position start_position, Tiles tiles, JPanel gamePanel,  List<List<Integer>> mazeValues, MovingSprite pacMan) {
		super(start_position, tiles, gamePanel, mazeValues, pacMan);
	}
	

	@Override
	public void chooseTilesNumbers() {
		for (int tile_nb = 289; tile_nb < 321; tile_nb++) {
			tilesNumbers.add(tile_nb); // add inky tiles numbers
		}

	}
	
	@Override
	protected void chooseInitialAnimation() {
		animationOrder = goDownAnimation; // eyes downwards for inky
	}
	
	
	@Override
	public void startDirectionThread() {
		behaviorTh = new GhostBehaviorThread(this);
		behaviorTh.setName("Inky behavior");
		behaviorTh.startThread();
	}


	/**
	 * Return true if pac-man is in the same corridor.
	 */
	@Override
	public boolean specificAvailable() {
		
		if(sameCorridor()) {
			return true;
		}
		return false;
	}

	/**
	 * Has 50% chances to go to the last seen position of pac-man, 
	 * else he goes in the opposite direction.
	 */
	@Override
	public void launchSpecific() {

		if(ThreadLocalRandom.current().nextInt(0, 100 + 1) < 50)
		{
			System.out.println("inky choose to follow pac-man");
			chooseDirectionToGoTo(lastSeenPacManMatrixPos);
			goingToLastSeenPos = true;
		}
		else {
			System.out.println("inky choose to go in the opposite direction");
			chooseDirectionToEscapeFrom(lastSeenPacManMatrixPos);
			escaping = true;
		}
	}

}
