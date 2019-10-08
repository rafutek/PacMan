package sprites;

import java.util.List;

import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;
import threads.PhysicsThread;

public class Pinky extends Ghost {
	
	MovingSpriteState nextState;

	public Pinky(Position start_position, Tiles tiles, JPanel gamePanel,  List<List<Integer>> mazeValues, PacMan pacMan) {
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
		behaviorTh = new GhostBehaviorThread(this,pacMan);
		behaviorTh.setName("Pinky behavior");
		behaviorTh.startThread();
	}
	@Override
	public  void stopDirectionThread() {
		behaviorTh.stopThread();
	}

	@Override
	public boolean specificAvailable() {
		// if can go in parallel
		/* si pac man go HORIZONTAL (LEFT||RIGHT)
		 * -> ghost x < pacman x si hdir=left
		 *    -> ghost x  go RIGHT si pas de mur
		 * -> ghost x > pacman x
		 *    -> ghost x go LEFT si pas de mur
		 * 
		 si pac man go VERTICAL
		 * -> ghost y < pacman y
		 *    -> ghost x  go DOWN si pas de mur
		 * -> ghost y > pacman y
		 *    -> ghost x go UP si pas de mur
		*/
		
		Position posGhost = PhysicsThread.mazeToMatrixPosition(this.currentPosition, gamePanel, mazeValues);
		//Position posPacMan = PhysicsThread.mazeToMatrixPosition(pacMan.currentPosition, gamePanel, mazeValues);
		int rightBoxValue;
		int leftBoxValue;
		int upBoxValue;
		int downBoxValue;
		
		nextState = getState();
		
		if(pacMan.getState() == MovingSpriteState.LEFT || pacMan.getState() == MovingSpriteState.RIGHT) {
			if(this.currentPosition.getX() < pacMan.getCurrentPosition().getX()) { // want to go to right
				rightBoxValue = mazeValues.get(posGhost.getY()).get(posGhost.getX()+1);
				if(rightBoxValue == 0 || rightBoxValue == 13 || rightBoxValue == 15) {
					nextState=MovingSpriteState.RIGHT;
				}
			}
			else if(this.currentPosition.getX() > pacMan.getCurrentPosition().getX()) { // want to go to left
				leftBoxValue = mazeValues.get(posGhost.getY()).get(posGhost.getX()-1);
				if(leftBoxValue == 0 || leftBoxValue == 13 || leftBoxValue == 15) {
					nextState=MovingSpriteState.LEFT;
				}
			}
		}
		else if(pacMan.getState() == MovingSpriteState.UP || pacMan.getState() == MovingSpriteState.DOWN) {
			if(this.currentPosition.getY() < pacMan.getCurrentPosition().getY()) { // want to go to right
				downBoxValue = mazeValues.get(posGhost.getY()+1).get(posGhost.getX());
				if(downBoxValue == 0 || downBoxValue == 13 || downBoxValue == 15) {
					nextState=MovingSpriteState.DOWN;
				}
			}
			else if(this.currentPosition.getX() > pacMan.getCurrentPosition().getX()) { // want to go to left
				upBoxValue = mazeValues.get(posGhost.getY()-1).get(posGhost.getX());
				if(upBoxValue == 0 || upBoxValue == 13 || upBoxValue == 15) {
					nextState=MovingSpriteState.UP;
				}
			}
		}
		
		return true;
	}


	@Override
	public void launchSpecific() {
		this.setState(nextState);
		
	}

}
