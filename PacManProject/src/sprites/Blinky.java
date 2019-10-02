package sprites;

import javax.swing.JPanel;

import resources.Tiles;
import threads.RandomGhostTimer;

public class Blinky extends Ghost {

	public Blinky(Position start_position, Tiles tiles, JPanel gamePanel) {
		super(start_position, tiles, gamePanel);
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
		directionTh = new RandomGhostTimer(this);
		directionTh.setName("Blinky direction");
		directionTh.startThread();
	}

}
