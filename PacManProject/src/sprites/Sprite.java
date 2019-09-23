package sprites;

import java.util.List;

import resources.Tiles;

public class Sprite extends Position{

	private Animation spriteImagesBuffer;
	private Tiles tiles;
	
	public Sprite(int initial_x, int initial_y, Tiles tiles) {
		super(initial_x, initial_y);
		this.tiles = tiles;
	}
	
	public void setImagesBuffer(List<Integer> tilesNumbers) {
		spriteImagesBuffer = new Animation(tiles, tilesNumbers);
	}

}
