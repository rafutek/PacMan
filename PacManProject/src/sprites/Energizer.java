package sprites;


import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

public class Energizer extends Sprite{
	
	private List<Integer> tilesNumbers = new ArrayList<Integer>();	
	
	public Energizer(Position start_position, Tiles tiles) {
		super(start_position, tiles);
		
		tilesNumbers.add(13);
		tilesNumbers.add(14);
		tilesNumbers.add(15);
		
		setImagesBuffer(tilesNumbers);
		createFullSpriteImages();
		setAnimationOrder(tilesNumbers);
	}

	@Override
	public void createFullSpriteImages() {
		spriteFullImages = spriteImages.copyObject(); // no need to create full images for energizer
		setOriginalSize(); // an energizer full image is a tile so ok
	}


	
}
