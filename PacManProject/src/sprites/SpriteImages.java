package sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

/**
 * Class that creates a buffer of images from a set of tiles. 
 * These images are all the tiles needed to draw a sprite.
 *
 */
public class SpriteImages {
	
	List<BufferedImage> spriteImagesTiles = new ArrayList<BufferedImage>();
	
	/**
	 * Constructor that store the tiles, and fill an image buffer with the list of tiles wanted.
	 * @param tiles
	 * @param tilesNumbers the tiles wanted in the images buffer.
	 */
	public SpriteImages(Tiles tiles, List<Integer> tilesNumbers) {
		fillSpriteImagesBuffer(tiles,tilesNumbers);
	}
	
	private boolean verifyListTilesNumbers(List<Integer> tilesNumbers) {
		if(tilesNumbers == null) {
			System.out.println("The list of tiles numbers is null !");
			return false;
		}
		else if(tilesNumbers.isEmpty()) {
			System.out.println("The list of tiles numbers is empty !");
			return false;
		}
		return true;
	}
	
	
	private void fillSpriteImagesBuffer(Tiles tiles, List<Integer> tilesNumbers) {
		if(tiles != null && verifyListTilesNumbers(tilesNumbers)) {
			for (Integer integer : tilesNumbers) {
				if(integer == 0) {
					integer = Tiles.NB_TILES_X * Tiles.NB_TILES_Y; //replace by a black tile
				}
				BufferedImage tile = tiles.getTileNumber(integer);
				spriteImagesTiles.add(tile);
			}			
		}
	}
	
	/**
	 * Get the sprite images.
	 * @return the sprite images.
	 */
	public List<BufferedImage> getSpriteImages(){
		return spriteImagesTiles;
	}
	
	/**
	 * Get a copy of the sprite images.
	 * @return a copy.
	 */
	public List<BufferedImage> copySpriteImages(){
		List<BufferedImage> copy = new ArrayList<BufferedImage>();
		for (BufferedImage image : spriteImagesTiles) {
			copy.add(image);
		}
		return copy;
	}
	
	public SpriteImages copyObject() {
		SpriteImages obj = new SpriteImages(null, null);
		obj.spriteImagesTiles = copySpriteImages();
		return obj;
	}
	
	//-------------------------------------------------------
	
	public static void main(String[] args) throws IOException {
		List<Integer> tilesWanted = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			tilesWanted.add(i);
		}
		
		SpriteImages spriteImages = new SpriteImages(new Tiles(), tilesWanted);
		for (BufferedImage bufferedImage : spriteImages.getSpriteImages()) {
			Tiles.displayImg(bufferedImage);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			
		}
	}
}
