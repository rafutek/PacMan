package sprites;

import java.util.List;

import resources.Tiles;

public class Sprite {

	private Position mazePosition, currentPosition;
	private Animation spriteImagesBuffer;
	private Tiles tiles;
	
	public Sprite(Position start_position, Tiles tiles) {
		mazePosition = start_position;
		currentPosition = mazePosition;
		this.tiles = tiles;
	}
	
	public void setImagesBuffer(List<Integer> tilesNumbers) {
		spriteImagesBuffer = new Animation(tiles, tilesNumbers);
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
}
