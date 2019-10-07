package sprites;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

import resources.ListImages;
import resources.Tiles;
import threads.PhysicsThread;
import threads.GhostBehaviorThread;

public abstract class Ghost extends MovingSprite {
	
	public static List<Integer> acceptedMazeValues;
	
	
	

	public  boolean isInTheBox = true;
	
	protected JPanel gamePanel;
	protected List<List<Integer>> mazeValues;
	protected PacMan pacMan;
	
	protected GhostBehaviorThread behaviorTh = new GhostBehaviorThread(this, pacMan);
	
	private Position lastSeenPacManMatrixPos;
	protected boolean goingToLastSeenPos, escaping;


	public Ghost(Position start_position, Tiles tiles, JPanel gamePanel, List<List<Integer>> mazeValues, PacMan pacMan) {
		super(start_position, tiles, gamePanel);
		Ghost.acceptedMazeValues = super.acceptedMazeValues;
		this.gamePanel = gamePanel;
		this.mazeValues = mazeValues;
		this.pacMan = pacMan;
	}
	
	@Override
	public void chooseTilesNumbers() {
		chooseSpecificGhostTiles();
		for (int i=169; i<=175; i+=2) {
			tilesNumbers.add(i);
			tilesNumbers.add(i+1);
			tilesNumbers.add(i+16);
			tilesNumbers.add(i+17);
		}
	}
	
	protected  abstract void chooseSpecificGhostTiles();
		
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
		for (int i = spriteImages.getImagesList().size()-16; i < spriteImages.getImagesList().size()-3; i+=4) {
			cornerTopLeft = spriteImages.getImagesList().get(i);
			cornerTopRight = spriteImages.getImagesList().get(i+1);
			cornerBottomLeft = spriteImages.getImagesList().get(i+2);
			cornerBottomRight = spriteImages.getImagesList().get(i+3);
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
	public abstract void stopDirectionThread();
	
	public synchronized GhostBehaviorThread getBehaviorThread() {
		return behaviorTh;
	}
	
	
	/**
	 * Specific behavior defined by child
	 */
	public abstract boolean specificAvailable();
	public abstract void launchSpecific();
	public abstract void replacementOnDeath();
	
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
		
		if (posGhost != null && posPacMan != null && posGhost.getX() == posPacMan.getX()) {
			if(wallInColumn(posGhost,posPacMan)) {
				return false;
			}
			setLastSeenPacManMatrixPos(new Position(posPacMan.getX(),posPacMan.getY()));
			return true;
		}
		
		if (posGhost != null && posPacMan != null && posGhost.getY() == posPacMan.getY()) {
			if(wallInRow(posGhost,posPacMan)) {
				return false;
			}
			setLastSeenPacManMatrixPos(new Position(posPacMan.getX(),posPacMan.getY()));
			return true;
		}
		
		return false;
	}
	
	/**
	 * If the ghost and the matrix position are on the same row or column, 
	 * the ghost state is changed to the direction of the matrix position.
	 * @param matrixPos is the point in the matrix to go to.
	 */
	protected void chooseDirectionToGoTo(Position matrixPos) {
		Position ghostMatrixPos = PhysicsThread.mazeToMatrixPosition(this.currentPosition, gamePanel, mazeValues);
		
		if (ghostMatrixPos.getY() == matrixPos.getY()) {
			if(ghostMatrixPos.getX() < matrixPos.getX()) {
				setState(MovingSpriteState.RIGHT);
			}
			else {
				setState(MovingSpriteState.LEFT);
			}
		}
		else if(ghostMatrixPos.getX() == matrixPos.getX()) {
			if(ghostMatrixPos.getY() < matrixPos.getY()) {
				setState(MovingSpriteState.DOWN);
			}
			else {
				setState(MovingSpriteState.UP);
			}
		}
	}
	
	/**
	 * If the ghost and the matrix position are on the same row or column, 
	 * the ghost state is changed to escape from the matrix position.
	 * @param matrixPos is the point in the matrix to escape from.
	 */
	public void chooseDirectionToEscapeFrom(Position matrixPos) {
		Position ghostMatrixPos = PhysicsThread.mazeToMatrixPosition(this.currentPosition, gamePanel, mazeValues);
		
		if (ghostMatrixPos.getY() == matrixPos.getY()) {
			if(ghostMatrixPos.getX() < matrixPos.getX()) {
				setState(MovingSpriteState.LEFT);
			}
			else {
				setState(MovingSpriteState.RIGHT);
			}
		}
		else if(ghostMatrixPos.getX() == matrixPos.getX()) {
			if(ghostMatrixPos.getY() < matrixPos.getY()) {
				setState(MovingSpriteState.UP);
			}
			else {
				setState(MovingSpriteState.DOWN);
			}
		}
	}
	
	public boolean goingToLastSeenPosition() {
		return goingToLastSeenPos;
	}
	
	public void notGoingToLastSeenPosition() {
		goingToLastSeenPos = false;
	}
	
	public boolean escaping() {
		if (pacMan.invincible()){
			this.escaping = true;
		}
		return escaping;
	}	
	
	public void notEscape() {
		escaping = false;
	}
	
	/**
	 * Check, if ghost is going to the last seen position,
	 * if its current matrix position is the same as the last seen position of pac-man.
	 */
	public void checkAtLastSeenPosition() {
		if(goingToLastSeenPos) {
			Position currentMatrixPos = PhysicsThread.mazeToMatrixPosition(currentPosition, gamePanel, mazeValues);
			if(currentMatrixPos.getX() == lastSeenPacManMatrixPos().getX() && currentMatrixPos.getY() == lastSeenPacManMatrixPos().getY()) {
				goingToLastSeenPos = false;
			}
		}
	}

	public Position lastSeenPacManMatrixPos() {
		return lastSeenPacManMatrixPos;
	}

	public void setLastSeenPacManMatrixPos(Position lastSeenPacManMatrixPos) {
		this.lastSeenPacManMatrixPos = lastSeenPacManMatrixPos;
	}
}
