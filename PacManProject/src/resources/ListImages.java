package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates a buffer of images from a set of tiles. 
 * These images are all the tiles needed to draw a sprite.
 *
 */
public class ListImages {
	
	private List<BufferedImage> spriteImagesTiles = new ArrayList<BufferedImage>();
	
	/**
	 * Constructor that store the tiles, and fill an image buffer with the list of tiles wanted.
	 * @param tiles
	 * @param tilesNumbers the tiles wanted in the images buffer.
	 */
	public ListImages(Tiles tiles, List<Integer> tilesNumbers) {
		fillSpriteImagesArray(tiles,tilesNumbers);
	}
	/**
	 * Constructor that only declare a list of images.
	 */
	public ListImages() {}
	
	/**
	 * Add the image to the list of sprite images.
	 * @param img
	 */
	public void add(BufferedImage img) {
		spriteImagesTiles.add(img);
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
	
	
	private void fillSpriteImagesArray(Tiles tiles, List<Integer> tilesNumbers) {
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
	public List<BufferedImage> getImagesList(){
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
	
	public ListImages copyObject() {
		ListImages obj = new ListImages(null, null);
		obj.spriteImagesTiles = copySpriteImages();
		return obj;
	}
	
	//-------------------------------------------------------
	
	public static void main(String[] args) throws IOException {
		List<Integer> tilesWanted = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			tilesWanted.add(i);
		}
		
		ListImages spriteImages = new ListImages(new Tiles(), tilesWanted);
		for (BufferedImage bufferedImage : spriteImages.getImagesList()) {
			Tiles.displayImg(bufferedImage);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			
		}
	}
}
