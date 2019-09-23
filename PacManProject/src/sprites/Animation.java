package sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

/**
 * Class that creates a buffer of images from a set of tiles. 
 * These images has to be read by the render thread to create an animation.
 *
 */
public class Animation {
	
	Tiles tiles;
	List<Integer> tilesNumbers;
	List<BufferedImage> animationTiles = new ArrayList<BufferedImage>();
	
	public Animation(Tiles tiles, List<Integer> tilesNumbers) {
		this.tiles = tiles;
		setListTilesNumbers(tilesNumbers);
		fillAnimationBuffer();
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
	
	private void fillAnimationBuffer() {
		for (Integer integer : tilesNumbers) {
			BufferedImage tile = tiles.getTileNumber(integer);
			animationTiles.add(tile);
		}
	}
	
	public List<BufferedImage> getAnimationBuffer(){
		return animationTiles;
	}
	
	//-------------------------------------------------------
	
	public static void main(String[] args) throws IOException {
		List<Integer> tilesWanted = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			tilesWanted.add(i);
		}
		
		Animation animation = new Animation(new Tiles(), tilesWanted);
		for (BufferedImage bufferedImage : animation.getAnimationBuffer()) {
			Tiles.displayImg(bufferedImage);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			
		}
	}
}
