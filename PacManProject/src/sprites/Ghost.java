package sprites;

import java.awt.image.BufferedImage;
import resources.Tiles;

public abstract class Ghost extends MovingSprite {

	public Ghost(Position start_position, Tiles tiles) {
		super(start_position, tiles);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	protected void createFullSpriteImages() {
		spriteFullImages = new SpriteImages();
		BufferedImage cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight, img;

		System.out.println("sprite tiles array size = "+spriteImages.getSpriteImages().size());
		for (int i = 0; i < 16; i+=2) {
			cornerTopLeft = spriteImages.getSpriteImages().get(i);
			cornerTopRight = spriteImages.getSpriteImages().get(i+1);
			cornerBottomLeft = spriteImages.getSpriteImages().get(i+16);
			cornerBottomRight = spriteImages.getSpriteImages().get(i+16+1);
			img = createFullSpriteImage(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);
			spriteFullImages.add(img);			
		}
	}

	@Override
	protected void createNoMovementAnimation() {
		noMovementAnimation = animationOrder; // no animation for no movement
	}

	@Override
	protected void createGoLeftAnimation() {
		goLeftAnimation.add(4);
		goLeftAnimation.add(5);
	}

	@Override
	protected void createGoRightAnimation() {
		goRightAnimation.add(0);
		goRightAnimation.add(1);
	}

	@Override
	protected void createGoUpAnimation() {
		goUpAnimation.add(6);
		goUpAnimation.add(7);
	}

	@Override
	protected void createGoDownAnimation() {
		goDownAnimation.add(2);
		goDownAnimation.add(3);
	}

	@Override
	protected void createDeathAnimation() {
		deathAnimation = animationOrder; //no animation for death
	}
	

}
