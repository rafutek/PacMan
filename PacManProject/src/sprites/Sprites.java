package sprites;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Sprites {
	
	protected List<Sprite> sprites = new ArrayList<Sprite>();
	
	
	public void add(Sprite e) {
		sprites.add(e);
	}
	
	public List<Sprite> getSprites(){
		return sprites;
	}
	
	public Sprite getSpriteNb(int nb) {
		return sprites.get(nb);
	}
	
	public void removeSpriteNb(int nb) {
		sprites.remove(nb);
	}
	
	public void updateImg() {
		for (Sprite sprite : sprites) {
			synchronized(sprite) {
				sprite.updateImg();
			}
		}
	}
	
	public void draw(Graphics g) {
		for (Sprite sprite : sprites) {
			sprite.draw(g);
		}
	}
}
