package sprites;

import java.util.List;

import resources.Tiles;

public class Sprite {

	private Position position;
	private Animation spriteImagesBuffer;
	private Tiles tiles;
	
	public Sprite(Position start_position, Tiles tiles) {
		this.position = start_position;
		this.tiles = tiles;
	}
	
	public void setImagesBuffer(List<Integer> tilesNumbers) {
		spriteImagesBuffer = new Animation(tiles, tilesNumbers);
	}
	public Position getPosition() {
		return position;
	}

}
