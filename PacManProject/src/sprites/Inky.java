package sprites;

import javax.swing.JPanel;

import resources.Tiles;
import threads.RandomGhostTimer;

public class Inky extends Ghost {

	public Inky(Position start_position, Tiles tiles, JPanel gamePanel) {
		super(start_position, tiles, gamePanel);
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
		directionTh = new RandomGhostTimer(this);
		directionTh.setName("Inky direction");
		directionTh.startThread();
	}


}
