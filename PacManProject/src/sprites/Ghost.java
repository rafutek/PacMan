package sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

import resources.ListImages;
import resources.Tiles;
import threads.RandomGhostTimer;

public abstract class Ghost extends MovingSprite {
	
	public static List<Integer> acceptedMazeValues;
	
	protected RandomGhostTimer directionTh = new RandomGhostTimer(this);

	public boolean isInTheBox = true;

	public Ghost(Position start_position, Tiles tiles, JPanel gamePanel) {
		super(start_position, tiles, gamePanel);
		Ghost.acceptedMazeValues = super.acceptedMazeValues;
	}
	
	@Override
	protected void createFullSpriteImages() {
		spriteFullImages = new ListImages();
		BufferedImage cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight, img;

		for (int i = 0; i < 16; i+=2) {
			cornerTopLeft = spriteImages.getImagesList().get(i);
			cornerTopRight = spriteImages.getImagesList().get(i+1);
			cornerBottomLeft = spriteImages.getImagesList().get(i+16);
			cornerBottomRight = spriteImages.getImagesList().get(i+16+1);
			img = createFullSpriteImage(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);
			spriteFullImages.add(img);			
		}
	}

	@Override
	protected void createNoMovementAnimation() {
		noMovementAnimation = goLeftAnimation; // no animation for no movement
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
		deathAnimation = goLeftAnimation; //no animation for death
	}
	

	/**
	 * Set ghost state to a random direction state.
	 */
	public void setRandomDirection() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 3 + 1);		
		if(randomNum == 0) {
			setState(MovingSpriteState.LEFT);
		}
		else if(randomNum == 1) {
			setState(MovingSpriteState.RIGHT);
		}
		else if(randomNum == 2) {
			setState(MovingSpriteState.UP);
		}
		else if(randomNum == 3) {
			setState(MovingSpriteState.DOWN);
		}
	}
	
	public abstract void startDirectionThread();
	
	public synchronized RandomGhostTimer getDirectionThread() {
		return directionTh;
	}
	
}
