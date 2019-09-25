package sprites;


import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

public class PacDot extends Sprite {
	

	public PacDot(Position start_position, Tiles tiles) {
		super(start_position, tiles);
		
		tilesNumbers.add(13);
		
		setImagesArray(tilesNumbers);
		createFullSpriteImages();
		createAnimationOrderList();

	}

	@Override
	protected void createFullSpriteImages() {
		spriteFullImages = spriteImages.copyObject(); // no need to create full images for pac-dots
		setOriginalSize(); // a pac-dot full image is a tile so ok
	}

	@Override
	protected void createAnimationOrderList() {
		List<Integer> animationOrder = new ArrayList<Integer>();
		for (int i = 0; i < tilesNumbers.size(); i++) {
			animationOrder.add(i); // there is only one image for a pac-dot, so there will be no animation
		}
		setAnimationOrder(animationOrder);
	}

}
