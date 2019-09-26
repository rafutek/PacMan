package sprites;

import java.util.ArrayList;
import java.util.List;

import resources.Tiles;

public abstract class MovingSprite extends Sprite{
	
	protected List<Integer> noMovementAnimation;
	protected List<Integer> goLeftAnimation;
	protected List<Integer> goRightAnimation;
	protected List<Integer> goUpAnimation;
	protected List<Integer> goDownAnimation;
	protected List<Integer> deathAnimation;
	protected List<List<Integer>> mazeValues;

	/**
	 * A moving sprite has some extra behavior, as he moves he needs to know where he can go.
	 * So the mazeValues and the game panel are necessary.
	 * It has also multiple animations.
	 * @param start_position
	 * @param tiles
	 * @param mazeValues
	 */
	public MovingSprite(Position start_position, Tiles tiles, List<List<Integer>> mazeValues) {
		super(start_position, tiles);
		this.mazeValues = mazeValues;
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
	
	

}
