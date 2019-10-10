package sprites;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import resources.Tiles;

public abstract class MovingSprite extends Sprite{
	
	protected JPanel gamePanel;
	
	protected List<Integer> noMovementAnimation;
	protected List<Integer> goLeftAnimation;
	protected List<Integer> goRightAnimation;
	protected List<Integer> goUpAnimation;
	protected List<Integer> goDownAnimation;
	protected List<Integer> deathAnimation;
	protected List<List<Integer>> mazeValues;
	
	protected List<Integer> acceptedMazeValues = new ArrayList<Integer>();

	protected MovingSpriteState state = MovingSpriteState.STOP; // initially stopped
	protected MovingSpriteState wantedState = state;
	private int speed = 1;
	

	/**
	 * A moving sprite has some extra behavior, as he moves he needs to know where he can go.
	 * So the mazeValues and the game panel are necessary.
	 * It has also multiple animations.
	 * @param start_position
	 * @param tiles
	 * @param mazeValues
	 */
	public MovingSprite(Position start_position, Tiles tiles, JPanel gamePanel) {
		super(start_position, tiles);
		this.gamePanel = gamePanel;
		acceptedMazeValues.add(0); // nothing
		acceptedMazeValues.add(13); // pac-dot
		acceptedMazeValues.add(97); // initial pac-man position
		acceptedMazeValues.add(15); // energizers
		acceptedMazeValues.add(193); // blinky initial position
		acceptedMazeValues.add(225); // pinky -----------------
		acceptedMazeValues.add(257); // clyde -----------------
		acceptedMazeValues.add(289); // inky ------------------
	}

	/**
	 * Create all the animations lists and choose the initial one.
	 */
	@Override
	protected void createAnimationOrderList() {
		noMovementAnimation = new ArrayList<Integer>();
		goLeftAnimation = new ArrayList<Integer>();
		goRightAnimation = new ArrayList<Integer>();
		goUpAnimation = new ArrayList<Integer>();
		goDownAnimation = new ArrayList<Integer>();
		deathAnimation = new ArrayList<Integer>();
		
		createNoMovementAnimation();
		createGoLeftAnimation();
		createGoRightAnimation();
		createGoUpAnimation();
		createGoDownAnimation();
		createDeathAnimation();
		chooseInitialAnimation();
	}
	
	protected abstract void createNoMovementAnimation();
	protected abstract void createGoLeftAnimation();
	protected abstract void createGoRightAnimation();
	protected abstract void createGoUpAnimation();
	protected abstract void createGoDownAnimation();
	protected abstract void createDeathAnimation();
	protected abstract void chooseInitialAnimation();
	
	public synchronized void updatePos() {
		if(state != MovingSpriteState.STOP && state != MovingSpriteState.DEATH) {
			if(state == MovingSpriteState.LEFT) {
				if(currentPosition.getX()-speed() >= 0) {
					currentPosition.setX(currentPosition.getX()-speed());
				}
				else {
					currentPosition.setX(gamePanel.getWidth()-10);
				}			
			}
			else if(state == MovingSpriteState.RIGHT) {
				if(currentPosition.getX()+speed() <= gamePanel.getWidth()-10) {
					currentPosition.setX(currentPosition.getX()+speed());
				}
				else {
					currentPosition.setX(0);
				}
			}
			else if(state == MovingSpriteState.UP) {
				currentPosition.setY(currentPosition.getY()-speed());
			}
			else if(state == MovingSpriteState.DOWN) {
				currentPosition.setY(currentPosition.getY()+speed());
			}
		}
	}
	
	public synchronized MovingSpriteState getState() {
		return state;
	}
	
	public synchronized void setState(MovingSpriteState state) {
		this.state = state;
	}
	
	public synchronized void wantToGoLeft() {
		wantedState = MovingSpriteState.LEFT;
	}
	public synchronized void wantToGoRight() {
		wantedState = MovingSpriteState.RIGHT;
	}
	public synchronized void wantToGoUp() {
		wantedState = MovingSpriteState.UP;
	}
	public synchronized void wantToGoDown() {
		wantedState = MovingSpriteState.DOWN;
	}
	
	public synchronized MovingSpriteState getWantedState() {
		return wantedState;
	}
	
	public synchronized void setWantedState(MovingSpriteState wantedState) {
		this.wantedState = wantedState;
	}
	
	public synchronized void setNoMovementAnimation() {
		setAnimationOrder(noMovementAnimation);
	}
	public synchronized void setGoLeftAnimation() {
		setAnimationOrder(goLeftAnimation);
	}
	public synchronized void setGoRightAnimation() {
		setAnimationOrder(goRightAnimation);
	}
	public synchronized void setGoUpAnimation() {
		setAnimationOrder(goUpAnimation);
	}
	public synchronized void setGoDownAnimation() {
		setAnimationOrder(goDownAnimation);
	}
	public synchronized void setDeathAnimation() {
		setAnimationOrder(deathAnimation);
	}

	public int speed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
