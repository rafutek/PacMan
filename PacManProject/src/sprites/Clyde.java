package sprites;

import java.util.List;

import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;

public class Clyde extends Ghost {

	public Clyde(Position start_position, Tiles tiles, JPanel gamePanel, List<List<Integer>> mazeValues, MovingSprite pacMan) {
		super(start_position, tiles, gamePanel, mazeValues, pacMan);
	}
	

	@Override
	public void chooseTilesNumbers() {
		for (int tile_nb = 257; tile_nb < 289; tile_nb++) {
			tilesNumbers.add(tile_nb); // add clyde tiles numbers
		}

	}
	
	@Override
	protected void chooseInitialAnimation() {
		animationOrder = goUpAnimation; // eyes upwards for clyde
	}
	
	
	@Override
	public void startDirectionThread() {
		behaviorTh = new GhostBehaviorThread(this);
		behaviorTh.setName("Clyde behavior");
		behaviorTh.startThread();
	}
	@Override
	public  void stopDirectionThread() {
		behaviorTh.stopThread();
	}
	



	@Override
	public boolean specificAvailable() {
		return false;
	}


	@Override
	public void launchSpecific() {
		System.out.println("Launch Clyde Specific behavior");		
	}


	@Override
	public void replacementOnDeath() {
		// TODO Auto-generated method stub
		this.setCurrentPosition(new Position(273, 253));
	}

}
