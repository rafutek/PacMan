package sprites;

import java.util.List;

import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;

public class Blinky extends Ghost {
	
	public Blinky(Position start_position, Tiles tiles, JPanel gamePanel, List<List<Integer>> mazeValues, MovingSprite pacMan) {
		super(start_position, tiles, gamePanel, mazeValues, pacMan);
		setInTheBox(false); // blinky is already out of the box
		state = MovingSpriteState.LEFT;
		wantedState = state;
	}
	

	@Override
	public void chooseTilesNumbers() {
		for (int tile_nb = 193; tile_nb < 225; tile_nb++) {
			tilesNumbers.add(tile_nb); // add blinky tiles numbers
		}
	}
	
	@Override
	protected void chooseInitialAnimation() {
		animationOrder = goLeftAnimation; // eyes to the left for blinky
	}
	
	
	@Override
	public void startDirectionThread() {
		behaviorTh = new GhostBehaviorThread(this);
		behaviorTh.setName("Blinky behavior");
		behaviorTh.startThread();
	}


	@Override
	public boolean specificAvailable() {
		if(sameCorridor()) {
			System.out.println("pac-man and blinky in the same corridor !");
			return true;
		}
		return false;
	}


	@Override
	public void launchSpecific() {
		System.out.println("go to the last seen point");
		chooseDirectionToGoTo(lastSeenPacManMatrixPos);
	}

}
