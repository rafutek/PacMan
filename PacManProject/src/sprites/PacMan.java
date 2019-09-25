package sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import resources.Tiles;

public class PacMan extends Sprite {

	public PacMan(Position start_position, Tiles tiles) {
		super(start_position, tiles);

		// add all pac-man tiles
		for (int i = 97; i < 169; i++) { 
			tilesNumbers.add(i); 
		}
		for (int i = 177; i < 185; i++) { 
			tilesNumbers.add(i);
		}		
		
		setImagesArray(tilesNumbers); // fill an image array with these tiles
		createFullSpriteImages();
		setOriginalSize();
		createAnimationOrderList();
	}

	@Override
	protected void createFullSpriteImages() {

		spriteFullImages = new SpriteImages();
		
		BufferedImage cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight, img;
		
		int initial_tile_nb = 97;
		int tile_nb = initial_tile_nb;
		int interval = 16;
		int i = tile_nb - initial_tile_nb; 
		
		while(tile_nb <= 167) {

			cornerTopLeft = spriteImages.getSpriteImages().get(i);
			cornerTopRight = spriteImages.getSpriteImages().get(i+1);
			cornerBottomLeft = spriteImages.getSpriteImages().get(i+interval);
			cornerBottomRight = spriteImages.getSpriteImages().get(i+interval+1);
			img = createFullSpriteImage(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);
			spriteFullImages.add(img);
			
			tile_nb+=2;
			i = tile_nb - initial_tile_nb; 
			if(i%interval == 0) {
				tile_nb+=interval;
				i = tile_nb - initial_tile_nb;
			}
			if(tile_nb == 161) {
				interval = 8; // interval is not the same for the final line
			}
		}		
	}

	@Override
	protected void createAnimationOrderList() {
		
	}
	
	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		PacMan pac = new PacMan(new Position(0, 0), new Tiles());
		
		for (BufferedImage img : pac.spriteFullImages.getSpriteImages()) {
			Tiles.displayImg(img);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
//		
//		Tiles.displayImg(pac.spriteImages.getSpriteImages().get(0));
//		Thread.sleep(1000);
//		Tiles.displayImg(pac.spriteImages.getSpriteImages().get(pac.spriteImages.getSpriteImages().size()-1));
	}

}
