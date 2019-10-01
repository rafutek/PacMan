package sprites;

import resources.Tiles;
import threads.RandomGhostTimer;

public class Clyde extends Ghost {

	public Clyde(Position start_position, Tiles tiles) {
		super(start_position, tiles);
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
		directionTh = new RandomGhostTimer(this);
		directionTh.setName("Clyde direction");
		directionTh.startThread();
	}

}
