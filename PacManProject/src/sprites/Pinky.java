package sprites;

import java.util.List;

import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;

public class Pinky extends Ghost {

	public Pinky(Position start_position, Tiles tiles, JPanel gamePanel,  List<List<Integer>> mazeValues, MovingSprite pacMan) {
		super(start_position, tiles, gamePanel, mazeValues, pacMan);
	}
	

	@Override
	public void chooseTilesNumbers() {
		for (int tile_nb = 225; tile_nb < 257; tile_nb++) {
			tilesNumbers.add(tile_nb); // add pinky tiles numbers
		}

	}
	
	@Override
	protected void chooseInitialAnimation() {
		animationOrder = goDownAnimation; // eyes to the ground for pinky
	}
	
	
	@Override
	public void startDirectionThread() {
		behaviorTh = new GhostBehaviorThread(this);
		behaviorTh.setName("Pinky behavior");
		behaviorTh.startThread();
	}


	@Override
	public boolean specificAvailable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void launchSpecific() {
		// TODO Auto-generated method stub
		
	}
}
