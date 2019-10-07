package sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import resources.ListImages;
import resources.Tiles;

public class PacMan extends MovingSprite {
	
	public static List<Integer> acceptedMazeValues = new ArrayList<Integer>();
	private boolean isInTunnel = false;
	
	private boolean invincible = false;
	
	
	public PacMan(Position start_position, Tiles tiles, JPanel gamePanel) {
		super(start_position, tiles, gamePanel);
		for (Integer integer : super.acceptedMazeValues) {
			PacMan.acceptedMazeValues.add(integer);
		}
		PacMan.acceptedMazeValues.add(352); // tunnel
	}
	

	public boolean isInTunnel() {
		return isInTunnel;
	}


	public void setIsInTunnel(boolean isInTunnel) {
		this.isInTunnel = isInTunnel;
	}
	
	@Override
	public void chooseTilesNumbers() {
		// add all pac-man tiles
		for (int i = 97; i < 169; i++) { 
			tilesNumbers.add(i); 
		}
		for (int i = 177; i < 185; i++) { 
			tilesNumbers.add(i);
		}	
	}

	/**
	 * Join the tiles and resize the combined image to create the pac-man full images.
	 */
	@Override
	protected void createFullSpriteImages() {

		spriteFullImages = new ListImages();
		
		BufferedImage cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight, img;
		
		int initial_tile_nb = 97;
		int tile_nb = initial_tile_nb;
		int interval = 16;
		int i = tile_nb - initial_tile_nb; 
		
		while(tile_nb <= 167) {

			cornerTopLeft = spriteImages.getImagesList().get(i);
			cornerTopRight = spriteImages.getImagesList().get(i+1);
			cornerBottomLeft = spriteImages.getImagesList().get(i+interval);
			cornerBottomRight = spriteImages.getImagesList().get(i+interval+1);
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
	protected void createNoMovementAnimation() {
		noMovementAnimation.add(8); // no movement animation contains only the full pac-man index image
	}
	
	@Override
	protected void createGoLeftAnimation() {
		goLeftAnimation.add(0);
		goLeftAnimation.add(2); // two images for moving animations	
	}

	@Override
	protected void createGoRightAnimation() {
		goRightAnimation.add(4);
		goRightAnimation.add(6);
	}

	@Override
	protected void createGoUpAnimation() {
		goUpAnimation.add(1);
		goUpAnimation.add(3);
	}

	@Override
	protected void createGoDownAnimation() {
		goDownAnimation.add(5);
		goDownAnimation.add(7);
	}

	@Override
	protected void createDeathAnimation() {
		for (int i = 8; i < 20; i++) { // lot of images for death animation
			deathAnimation.add(i);
		}
	}

	@Override
	protected void chooseInitialAnimation() {
		animationOrder = noMovementAnimation;
	}

	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		PacMan pac = new PacMan(new Position(0, 0), new Tiles(), null);
		
		for (BufferedImage img : pac.spriteFullImages.getImagesList()) {
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


	public boolean invincible() {
		return invincible;
	}


	public synchronized void setInvincible(boolean invincible) {
		this.invincible = invincible;
		//System.out.println("PacMan est invincible");
	}

}
