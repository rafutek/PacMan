package sprites;

import java.util.List;

import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;

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
