package sprites;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Sprites {
	
	protected List<Sprite> sprites = new ArrayList<Sprite>();
	
	
	public void add(Sprite e) {
		sprites.add(e);
	}
	
	public synchronized List<Sprite> getSprites(){
		return sprites;
	}
	
	public synchronized Sprite getSpriteNb(int nb) {
		return sprites.get(nb);
	}
	
	public synchronized void removeSpriteNb(int nb) {
		sprites.remove(nb);
	}
	
	public synchronized void updateImg() {
		for (Sprite sprite : sprites) {
			sprite.updateImg();
		}
	}
	
	public synchronized void draw(Graphics g) {
		for (Sprite sprite : sprites) {
			sprite.draw(g);
		}
	}
}
