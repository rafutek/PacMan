package sprites;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import resources.ListImages;
import resources.Tiles;

public abstract class Sprite {

	protected List<Integer> tilesNumbers = new ArrayList<Integer>();	
	protected Position mazePosition, currentPosition;
	protected Dimension originalSize, currentSize;
	protected ListImages spriteImages, spriteFullImages;
	protected Tiles tiles;
	protected List<Integer> animationOrder;
	
	
	public Sprite(Position start_position, Tiles tiles) {
		mazePosition = start_position;
		currentPosition = mazePosition;
		this.tiles = tiles;
		
		chooseTilesNumbers();
		setImagesArray(tilesNumbers); // fill an image array with these tiles
		createFullSpriteImages();
		setOriginalSize();
		createAnimationOrderList();
	}
	
	/**
	 * Choose the tiles to add to the image array.
	 */
	public abstract void chooseTilesNumbers();
	
	public void setImagesArray(List<Integer> tilesNumbers) {
		spriteImages = new ListImages(tiles, tilesNumbers);
	}
	
	public synchronized Position getMazePosition() {
		return mazePosition;
	}

	public synchronized Position getCurrentPosition() {
		return currentPosition;
	}	
	
	public synchronized void setCurrentPosition(Position newCurrentPos) {
		currentPosition = newCurrentPos;
	}
	
	/**
	 * Some sprites need to join some tiles to create a single image (like pacman)
	 */
	protected abstract void createFullSpriteImages();
	
	/**
	 * Takes four images in parameter and return the combined image, with the dimension of one.
	 * The images must have the same dimension.
	 * @param cornerTopLeft
	 * @param cornerTopRight
	 * @param cornerBottomLeft
	 * @param cornerBottomRight
	 * @return the combined image
	 */
	protected BufferedImage createFullSpriteImage(BufferedImage cornerTopLeft, BufferedImage cornerTopRight,
			BufferedImage cornerBottomLeft, BufferedImage cornerBottomRight) {
		
		BufferedImage imgTop = Tiles.joinToRight(cornerTopLeft, cornerTopRight);
		BufferedImage imgBottom = Tiles.joinToRight(cornerBottomLeft, cornerBottomRight);
		BufferedImage fullBigImg = Tiles.joinBelow(imgTop, imgBottom);
		return Tiles.resize(fullBigImg, new Dimension(cornerTopLeft.getWidth(), cornerTopLeft.getHeight()));
	}
	
	
	/**
	 * Set the original dimension of the sprite, its dimension in the original maze.
	 * The original size of a full image must be the same as a tile !
	 */
	protected void setOriginalSize() {
		// the original size of the sprite is the dimension of one of its full images
		originalSize = new Dimension(spriteFullImages.getImagesList().get(0).getWidth(), 
									spriteFullImages.getImagesList().get(0).getHeight());
	}
	
	
	/**
	 * Get the original dimension of a sprite's full image in the original maze.
	 * @return sprite's original size
	 */
	public synchronized Dimension getOriginalSize() {
		return originalSize;
	}
	
	/**
	 * Set the current size of the sprite.
	 * @param newDimension
	 */
	public synchronized void setCurrentSize(Dimension newDimension) {
		currentSize = newDimension;
	}
	
	/**
	 * Get the current size of the sprite.
	 * @return its current dimension.
	 */
	public synchronized Dimension getCurrentSize() {
		return currentSize;
	}
	
	/**
	 * Sets the list of numbers that represents the order of images for animation
	 */
	public synchronized void setAnimationOrder(List<Integer> animationOrder) {
		this.animationOrder = animationOrder;
	}
	
	protected abstract void createAnimationOrderList();
	
	/**
	 * Update the animation order buffer. More precisely takes the first item of the list and
	 * place it at the end, so that the draw method always draw the first but different full image of the sprite.
	 */
	public synchronized void updateImg() {
		animationOrder.add(animationOrder.remove(0));
	}
	
	/**
	 * Draw an image of the full images list. The number of the image to draw is 
	 * given by the first index of the animation order list.
	 * The full image is drawn at the current position, with the current size wanted.
	 * @param g is the graphics where to draw the sprite.
	 */
	public synchronized void draw(Graphics g) {
		g.drawImage(spriteFullImages.getImagesList().get(animationOrder.get(0)), 
				currentPosition.getX(), currentPosition.getY(), currentSize.width, currentSize.height, null);
	}
}
