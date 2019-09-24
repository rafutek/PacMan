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
	
	Tiles tiles;
	List<Integer> tilesNumbers;
	List<BufferedImage> spriteImagesTiles = new ArrayList<BufferedImage>();
	
	/**
	 * Constructor that store the tiles, and fill an image buffer with the list of tiles wanted.
	 * @param tiles
	 * @param tilesNumbers the tiles wanted in the images buffer.
	 */
	public SpriteImages(Tiles tiles, List<Integer> tilesNumbers) {
		this.tiles = tiles;
		setListTilesNumbers(tilesNumbers);
		fillSpriteImagesBuffer();
	}
	
	private void setListTilesNumbers(List<Integer> tilesNumbers) {
		if(tilesNumbers == null) {
			System.out.println("The list of tiles numbers is null !");
		}
		else if(tilesNumbers.isEmpty()) {
			System.out.println("The list of tiles numbers is empty !");
		}
		this.tilesNumbers = tilesNumbers;
	}
	
	
	public void printTilesNumbers() {
		for (Integer integer : tilesNumbers) {
			System.out.println(integer);
		}
	}
	
	private void fillSpriteImagesBuffer() {
		for (Integer integer : tilesNumbers) {
			if(integer == 0) {
				integer = Tiles.NB_TILES_X * Tiles.NB_TILES_Y; //replace by a black tile
			}
			BufferedImage tile = tiles.getTileNumber(integer);
			spriteImagesTiles.add(tile);
		}
	}
	
	public List<BufferedImage> getSpriteImages(){
		return spriteImagesTiles;
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
