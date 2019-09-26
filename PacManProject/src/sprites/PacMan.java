package sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

public class PacMan extends Sprite {
	
	List<Integer> noMovementAnimation = new ArrayList<Integer>();
	List<Integer> goLeftAnimation = new ArrayList<Integer>();
	List<Integer> goRightAnimation = new ArrayList<Integer>();
	List<Integer> goUpAnimation = new ArrayList<Integer>();
	List<Integer> goDownAnimation = new ArrayList<Integer>();
	List<Integer> deathAnimation = new ArrayList<Integer>();
	
	
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
		createAnimationOrderLists();
	}

	/**
	 * Join the tiles and resize the combined image to create the pac-man full images.
	 */
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

	
	
	private void createAnimationOrderLists() {
		createNoMovementAnimation();
		createGoLeftAnimation();
		createGoRightAnimation();
		createGoUpAnimation();
		createGoDownAnimation();
		createDeathAnimation();
		
		createAnimationOrderList();
	}
	
	
	/*
	 * Pac-man has multiple animations, like the no-moving one, the going-to-left one, the dying one, etc..
	 * So this method creates all the lists of animation, and set the animationOrder main list as one of them.
	 */
	@Override
	protected void createAnimationOrderList() {
		animationOrder = goUpAnimation;
	}

	
	private void createNoMovementAnimation() {
		noMovementAnimation.add(8); // no movement animation contains only the full pac-man index image
	}
	
	private void createGoLeftAnimation() {
		goLeftAnimation.add(0);
		goLeftAnimation.add(2); // two images for moving animations		
	}
	
	private void createGoRightAnimation() {
		goRightAnimation.add(4);
		goRightAnimation.add(6);
	}
	
	private void createGoUpAnimation() {
		goUpAnimation.add(1);
		goUpAnimation.add(3);
	}

	private void createGoDownAnimation() {
		goDownAnimation.add(5);
		goDownAnimation.add(7);
	}
	
	private void createDeathAnimation() {
		for (int i = 8; i < 20; i++) {
			deathAnimation.add(i);
		}
		
		
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
