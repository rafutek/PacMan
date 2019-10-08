package sprites;

import java.util.List;
import javax.swing.JPanel;

import resources.Tiles;
import threads.GhostBehaviorThread;

public class Blinky extends Ghost {
	
	public Blinky(Position start_position, Tiles tiles, JPanel gamePanel, List<List<Integer>> mazeValues, PacMan pacMan) {
		super(start_position, tiles, gamePanel, mazeValues, pacMan);
		setInTheBox(false); // blinky is already out of the box
		state = MovingSpriteState.LEFT;
		wantedState = state;
	}
	
	@Override
	protected void chooseSpecificGhostTiles() {
		for (int tile_nb = 193; tile_nb < 225; tile_nb++) {
			tilesNumbers.add(tile_nb); // add blinky tiles numbers
		}
		
	}

	
	@Override
	protected void chooseInitialAnimation() {
		animationOrder = goLeftAnimation; // eyes to the left for blinky
	}
	
	
	@Override
	public void startBehaviorThread() {
		behaviorTh = new GhostBehaviorThread(this, pacMan);
		behaviorTh.setName("Blinky behavior");
		behaviorTh.startThread();
	}
	@Override
	public  void stopBehaviorThread() {
		behaviorTh.stopThread();
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
	 * Go to the last seen position of pac-man.
	 */
	@Override
	public void launchSpecific() {
		goingToLastSeenPos = true;
		chooseDirectionToGoTo(lastSeenPacManMatrixPos());
	}



	/*public static void main(String[] args) throws IOException {
		Blinky b = new Blinky(null,new Tiles(),null,null,null);
		for (int i = 0; i < b.spriteFullImages.getImagesList().size(); i++) {
			Tiles.displayImg(b.spriteFullImages.getImagesList().get(i));
			System.out.println("yo!");
		}
	}*/

}
