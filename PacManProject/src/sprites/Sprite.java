package sprites;

import java.awt.Graphics;
import java.util.List;

import resources.Tiles;

public abstract class Sprite {

	private Position mazePosition, currentPosition;
	protected SpriteImages spriteImages, spriteFullImages;
	private Tiles tiles;
	protected List<Integer> animationOrder;
	
	public Sprite(Position start_position, Tiles tiles) {
		mazePosition = start_position;
		currentPosition = mazePosition;
		this.tiles = tiles;
	}
	
	public void setImagesBuffer(List<Integer> tilesNumbers) {
		spriteImages = new SpriteImages(tiles, tilesNumbers);
	}
	
	public Position getMazePosition() {
		return mazePosition;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}	
	
	public void setCurrentPosition(Position newCurrentPos) {
		currentPosition = newCurrentPos;
	}
	
	/**
	 * Some sprites need to join some tiles to create a single image (like pacman)
	 */
	public abstract void createFullSpriteImages();
	/**
	 * Sets the list of numbers that represents the order of images for animation
	 */
	public void setAnimationOrder(List<Integer> animationOrder) {
		this.animationOrder = animationOrder;
	}
	
	public void draw(Graphics g) {
		// always draw the first image of the buffer
		g.drawImage(spriteFullImages.getSpriteImages().get(0), 
				currentPosition.getX(), currentPosition.getY(), null);
	}
}
