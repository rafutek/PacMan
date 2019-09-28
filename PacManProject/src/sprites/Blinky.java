package sprites;

import java.io.IOException;

import resources.Tiles;

public class Blinky extends Ghost {

	public Blinky(Position start_position, Tiles tiles) {
		super(start_position, tiles);
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


	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException {
		new Blinky(null, new Tiles());
		
	}
}
