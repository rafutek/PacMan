package sprites;

import javax.swing.JPanel;

import resources.Tiles;
import threads.RandomGhostTimer;

public class Pinky extends Ghost {

	public Pinky(Position start_position, Tiles tiles, JPanel gamePanel) {
		super(start_position, tiles, gamePanel);
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
		directionTh = new RandomGhostTimer(this);
		directionTh.setName("Pinky direction");
		directionTh.startThread();
	}
}
