package sprites;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

import resources.ListImages;
import resources.Tiles;
import threads.PhysicsThread;
import threads.GhostBehaviorThread;

public abstract class Ghost extends MovingSprite {
	
	public static List<Integer> acceptedMazeValues;
	
	protected GhostBehaviorThread behaviorTh = new GhostBehaviorThread(this);

	private boolean isInTheBox = true;
	
	private JPanel gamePanel;
	private List<List<Integer>> mazeValues;
	private MovingSprite pacMan;
	

	public Ghost(Position start_position, Tiles tiles, JPanel gamePanel, List<List<Integer>> mazeValues, MovingSprite pacMan) {
		super(start_position, tiles, gamePanel);
		Ghost.acceptedMazeValues = super.acceptedMazeValues;
		this.gamePanel = gamePanel;
		this.mazeValues = mazeValues;
		this.pacMan = pacMan;
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
	
	public synchronized boolean isInTheBox() {
		return isInTheBox;
	}

	public synchronized void setInTheBox(boolean isInTheBox) {
		this.isInTheBox = isInTheBox;
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
	
	public synchronized GhostBehaviorThread getDirectionThread() {
		return behaviorTh;
	}
	
	
	/**
	 * Specific behavior defined by child
	 */
	public abstract boolean specificAvailable();
	public abstract void launchSpecific();
	
	private boolean wallInRow(Position pos1, Position pos2) {
		int petit= Math.min(pos1.getX(),pos2.getX());
		int grand= Math.max(pos1.getX(),pos2.getX());
		for (int i = petit+1; i < grand; i++) {
			int posI = mazeValues.get(pos1.getY()).get(i);
			if (posI!=0 && posI!=13 && posI!=15) {
				return true;
			}
		}
		return false;
	}

	private boolean wallInColumn(Position pos1, Position pos2) {
		int petit= Math.min(pos1.getY(),pos2.getY());
		int grand= Math.max(pos1.getY(),pos2.getY());
		for (int i = petit+1; i < grand; i++) {
			int posI = mazeValues.get(i).get(pos1.getX());
			if (posI!=0 && posI!=13 && posI!=15) {
				return true;
			}
		}
		return false;
	}
	
	public boolean sameCorridor() {
		Position posGhost = PhysicsThread.mazeToMatrixPosition(this.currentPosition, gamePanel, mazeValues);
		Position posPacMan = PhysicsThread.mazeToMatrixPosition(pacMan.currentPosition, gamePanel, mazeValues);
		
		if (posGhost.getX() == posPacMan.getX()) {
			System.out.println("pacman in same column");
			if(wallInColumn(posGhost,posPacMan)) {
				return false;
			}
			return true;
		}
		
		if (posGhost.getY() == posPacMan.getY()) {
			System.out.println("pacman in same line");
			if(wallInRow(posGhost,posPacMan)) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	
}
