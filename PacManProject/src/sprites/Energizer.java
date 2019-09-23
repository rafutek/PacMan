package sprites;


import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

public class Energizer extends Sprite{
	
	private List<Integer> tilesNumbers = new ArrayList<Integer>();	
	
	public Energizer(int initial_x, int initial_y, Tiles tiles) {
		super(initial_x, initial_y, tiles);
		
		tilesNumbers.add(13);
		tilesNumbers.add(14);
		tilesNumbers.add(15);
		
		setImagesBuffer(tilesNumbers);
	}


	
}
