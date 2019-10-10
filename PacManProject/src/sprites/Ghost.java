package sprites;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;

import resources.ListImages;
import resources.Tiles;
import threads.PhysicsThread;
import threads.GhostBehaviorThread;

public abstract class Ghost extends MovingSprite {
	
	public static List<Integer> acceptedMazeValues;
	public List<MovingSpriteState> possibleDirections = new ArrayList<MovingSpriteState>();;
	private final int NUM_DIR = 4;
	

	public  boolean isInTheBox = true;
	
	protected JPanel gamePanel;
	protected List<List<Integer>> mazeValues;
	protected PacMan pacMan;
	
	protected GhostBehaviorThread behaviorTh = new GhostBehaviorThread(this, pacMan);
	protected List<Integer> escapingAnimation = new ArrayList<Integer>();
	private Position lastSeenPacManMatrixPos;
	protected boolean goingToLastSeenPos, escaping;


	public Ghost(Position start_position, Tiles tiles, JPanel gamePanel, List<List<Integer>> mazeValues, PacMan pacMan) {
		super(start_position, tiles, gamePanel);
		Ghost.acceptedMazeValues = super.acceptedMazeValues;
		this.gamePanel = gamePanel;
		this.mazeValues = mazeValues;
		this.pacMan = pacMan;
		createEscapingAnimation();
		createListDirections();
	}
	
	@Override
	public void chooseTilesNumbers() {
		chooseSpecificGhostTiles();
		for (int i=169; i<=175; i+=2) { // add escaping tiles for animation 
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

		if(spriteImages != null && spriteImages.getImagesList() != null && !spriteImages.getImagesList().isEmpty()) {
			for (int i = 0; i < 16; i+=2) {
				cornerTopLeft = spriteImages.getImagesList().get(i);
				cornerTopRight = spriteImages.getImagesList().get(i+1);
				cornerBottomLeft = spriteImages.getImagesList().get(i+16);
				cornerBottomRight = spriteImages.getImagesList().get(i+16+1);
				img = createFullSpriteImage(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);
				img = Tiles.resize(img, new Dimension(50, 50));
				spriteFullImages.add(img);			
			}
			for (int i = spriteImages.getImagesList().size()-16; i < spriteImages.getImagesList().size()-3; i+=4) {
				cornerTopLeft = spriteImages.getImagesList().get(i);
				cornerTopRight = spriteImages.getImagesList().get(i+1);
				cornerBottomLeft = spriteImages.getImagesList().get(i+2);
				cornerBottomRight = spriteImages.getImagesList().get(i+3);
				img = createFullSpriteImage(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);
				img = Tiles.resize(img, new Dimension(50, 50));
				spriteFullImages.add(img);	
			} 			
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

	protected void createEscapingAnimation() {
		escapingAnimation.add(8);
		escapingAnimation.add(9);
		escapingAnimation.add(10);
		escapingAnimation.add(11);
	}
	
	public synchronized void setEscapingAnimation() {
		setAnimationOrder(escapingAnimation);
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
	 * Set ghost state to a random wanted direction state.
	 */
	public void setRandomDirection() {
		if(possibleDirections != null && !possibleDirections.isEmpty()) {
			int randomIndex = ThreadLocalRandom.current().nextInt(0, possibleDirections.size());	
			MovingSpriteState direction = possibleDirections.get(randomIndex);
			if(direction == MovingSpriteState.LEFT) {
				wantToGoLeft();
			}
			else if(direction == MovingSpriteState.RIGHT) {
				wantToGoRight();
			}
			else if(direction == MovingSpriteState.UP) {
				wantToGoUp();
			}
			else if(direction == MovingSpriteState.DOWN) {
				wantToGoDown();
			}			
		}
	}
	
	public abstract void startBehaviorThread();
	public abstract void stopBehaviorThread();
	
	public synchronized GhostBehaviorThread getBehaviorThread() {
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
				wantToGoRight();
				setState(getWantedState());
			}
			else {
				wantToGoLeft();
				setState(getWantedState());
			}
		}
		else if(ghostMatrixPos.getX() == matrixPos.getX()) {
			if(ghostMatrixPos.getY() < matrixPos.getY()) {
				wantToGoDown();
				setState(getWantedState());
			}
			else {
				wantToGoUp();
				setState(getWantedState());
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
				if(possibleDirections.contains(MovingSpriteState.LEFT)) {
					wantToGoLeft();					
				}
			}
			else {
				if(possibleDirections.contains(MovingSpriteState.RIGHT)) {
					wantToGoRight();
				}
			}
		}
		else if(ghostMatrixPos.getX() == matrixPos.getX()) {
			if(ghostMatrixPos.getY() < matrixPos.getY()) {
				if(possibleDirections.contains(MovingSpriteState.UP)){
					wantToGoUp();
				}
			}
			else {
				if(possibleDirections.contains(MovingSpriteState.DOWN)) {
					wantToGoDown();
				}
			}
		}
		else {
			createListDirections();
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
		else {
			this.escaping = false;
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
	
	public void createListDirections() {
		if(possibleDirections.size() < NUM_DIR) {
			possibleDirections = new ArrayList<MovingSpriteState>();
			possibleDirections.add(MovingSpriteState.LEFT);
			possibleDirections.add(MovingSpriteState.RIGHT);
			possibleDirections.add(MovingSpriteState.UP);
			possibleDirections.add(MovingSpriteState.DOWN);	
		}
	}
	
	public void removeDirection(MovingSpriteState direction) {
		if(possibleDirections != null && !possibleDirections.isEmpty()) {
			possibleDirections.remove(direction);
		}
	}
}
