package sprites;

import java.util.List;

import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;
import threads.PhysicsThread;

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
		Position posGhost = PhysicsThread.mazeToMatrixPosition(this.currentPosition, gamePanel, mazeValues);
		MovingSpriteState pacmanDir=pacMan.getState();
		if (pacmanDir==MovingSpriteState.LEFT && wallInRow(posGhost,posGhost.setX(posGhost.getX()-2))) {
			
		}
		return true;
	}


	@Override
	public void launchSpecific() {
		// TODO Auto-generated method stub
		
	}
}
