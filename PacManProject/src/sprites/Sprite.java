package sprites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import resources.Tiles;

public abstract class Sprite {

	private Position mazePosition, currentPosition;
	private Dimension originalSize, currentSize;
	protected SpriteImages spriteImages, spriteOriginalFullImages, spriteCurrentFullImages;
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
	 * Set the original dimension of the sprite, its dimension in the original maze.
	 * The original size of a full image must be the same as a tile !
	 */
	protected void setOriginalSize() {
		// the original size of the sprite is the dimension of one of its full images
		originalSize = new Dimension(spriteOriginalFullImages.getSpriteImages().get(0).getWidth(), 
									spriteOriginalFullImages.getSpriteImages().get(0).getHeight());
	}
	
	
	/**
	 * Get the original dimension of a sprite's full image in the original maze.
	 * @return sprite's original size
	 */
	public Dimension getOriginalSize() {
		return originalSize;
	}
	
	/**
	 * Set the current size of the sprite.
	 * @param newDimension
	 */
	public void resizeCurrentImages(Dimension newDimension) {
		currentSize = newDimension;
		for (BufferedImage image : spriteCurrentFullImages.getSpriteImages()) {
			image = Tiles.resize(image, currentSize);
		}
	}
	
	/**
	 * Sets the list of numbers that represents the order of images for animation
	 */
	public void setAnimationOrder(List<Integer> animationOrder) {
		this.animationOrder = animationOrder;
	}
	
	public void draw(Graphics g) {
		// always draw the first image of the buffer
		g.drawImage(spriteOriginalFullImages.getSpriteImages().get(0), 
				currentPosition.getX(), currentPosition.getY(), null);
	}
}
