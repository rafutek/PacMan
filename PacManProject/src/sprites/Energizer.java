package sprites;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

public class Energizer extends Sprite{
	
	
	public Energizer(Position start_position, Tiles tiles) {
		super(start_position, tiles);
		
		tilesNumbers.add(13);
		tilesNumbers.add(14);
		tilesNumbers.add(15);
		
		setImagesBuffer(tilesNumbers);
		createFullSpriteImages();
		createAnimationOrderList();
	}

	@Override
	public void createFullSpriteImages() {
		spriteFullImages = spriteImages.copyObject(); // no need to create full images for energizer
		setOriginalSize(); // an energizer full image is a tile so ok
	}
	
	@Override
	protected void createAnimationOrderList() {
		List<Integer> animationOrder = new ArrayList<Integer>();
		for (int i = 0; i < tilesNumbers.size(); i++) {
			animationOrder.add(i); // the images of the animation are the first, the second, etc.. 
									// of the full images list
		}
		setAnimationOrder(animationOrder);
	}


	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException {
		Energizer e = new Energizer(new Position(0, 0), new Tiles());
		System.out.println(e.animationOrder);
		e.update();
		System.out.println(e.animationOrder);
	}

	
}
