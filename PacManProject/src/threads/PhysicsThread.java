package threads;

import java.util.List;

import javax.swing.JPanel;

import sprites.Ghost;
import sprites.MovingSprite;
import sprites.MovingSpriteState;
import sprites.Position;

public class PhysicsThread extends ThreadPerso {

	private List<List<Integer>> mazeValues;
	private JPanel gamePanel;
	
	private MovingSprite pacMan;
	private MovingSpriteState pacManWantedState;
	
	private Ghost blinky;
	private MovingSpriteState blinkyWantedState;	
	/**
	 * The class needs the maze number matrix, the game panel size and of course the moving sprites,
	 * in order to locate them in the matrix.
	 * The location of each sprite in the matrix allow them to go or not in the wanted direction.
	 * @param mazeValues
	 * @param gamePanel
	 * @param pacMan
	 */
	public PhysicsThread(List<List<Integer>> mazeValues, JPanel gamePanel, MovingSprite pacMan, Ghost blinky) {
		super("Physics");
		this.mazeValues = mazeValues;
		this.gamePanel = gamePanel;
		this.pacMan = pacMan;
		this.blinky = blinky;
	}

	@Override
	protected void doThatAtStart() {}

	@Override
	protected void doThat() {
		
		//pac-man
		if(pacMan.getCurrentPosition() != null && pacMan.getCurrentSize() != null) {
			pacManWantedState = pacMan.getWantedState();
			if(pacManWantedState != MovingSpriteState.STOP) {
				
				// we need to use a little bit changed position 
				// so that pac-man can go a little bit farther in the maze
				int adaptedCurrentPosX;
				int adaptedCurrentPosY;
				Position currentMatrixPos; // the position of the sprite in the matrix
				int wantedBoxValue = -1; // the next box value where pac-man wants to go
				
				if(pacManWantedState == MovingSpriteState.LEFT) {
					adaptedCurrentPosX = pacMan.getCurrentPosition().getX() + pacMan.getCurrentSize().width/2;
					adaptedCurrentPosY = pacMan.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
				}
				else if(pacManWantedState == MovingSpriteState.RIGHT) {
					adaptedCurrentPosX = pacMan.getCurrentPosition().getX() - pacMan.getCurrentSize().width/2;
					adaptedCurrentPosY = pacMan.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
				}
				else if(pacManWantedState == MovingSpriteState.UP) {
					adaptedCurrentPosX = pacMan.getCurrentPosition().getX();
					adaptedCurrentPosY = pacMan.getCurrentPosition().getY() + pacMan.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
				}
				else if(pacManWantedState == MovingSpriteState.DOWN) {
					adaptedCurrentPosX = pacMan.getCurrentPosition().getX();
					adaptedCurrentPosY = pacMan.getCurrentPosition().getY() - pacMan.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
				}
				
				if(wantedBoxValue == 0 || wantedBoxValue == 97 || wantedBoxValue == 13 || wantedBoxValue == 15 || wantedBoxValue == 193) {
					pacMan.setState(pacManWantedState); // pac-man can be in that state
				}else {
					pacMan.setState(MovingSpriteState.STOP);
				}
			}			
		}

		
		//blinky
		if(blinky.getCurrentPosition() != null && blinky.getCurrentSize() != null) {
			blinkyWantedState = blinky.getState();
			if(blinkyWantedState != MovingSpriteState.STOP) {
				
				// we need to use a little bit changed position 
				// so that pac-man can go a little bit farther in the maze
				int adaptedCurrentPosX;
				int adaptedCurrentPosY;
				Position currentMatrixPos; // the position of the sprite in the matrix
				int wantedBoxValue = -1; // the next box value where pac-man wants to go
				
				if(blinkyWantedState == MovingSpriteState.LEFT) {
					adaptedCurrentPosX = blinky.getCurrentPosition().getX() + blinky.getCurrentSize().width/2;
					adaptedCurrentPosY = blinky.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
				}
				else if(blinkyWantedState == MovingSpriteState.RIGHT) {
					adaptedCurrentPosX = blinky.getCurrentPosition().getX() - blinky.getCurrentSize().width/2;
					adaptedCurrentPosY = blinky.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
				}
				else if(blinkyWantedState == MovingSpriteState.UP) {
					adaptedCurrentPosX = blinky.getCurrentPosition().getX();
					adaptedCurrentPosY = blinky.getCurrentPosition().getY() + blinky.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
				}
				else if(blinkyWantedState == MovingSpriteState.DOWN) {
					adaptedCurrentPosX = blinky.getCurrentPosition().getX();
					adaptedCurrentPosY = blinky.getCurrentPosition().getY() - blinky.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
				}
				
				if(wantedBoxValue == 0 || wantedBoxValue == 97 || wantedBoxValue == 13 || wantedBoxValue == 15|| wantedBoxValue == 193) {
					blinky.setState(blinkyWantedState); // pac-man can be in that state
				}else {					
					if(blinky.getDirectionThread() == null || !blinky.getDirectionThread().isRunning()) {
						blinky.startDirectionThread();
					}
					// blinky set another possible direction
					blinky.getDirectionThread().changeDirection();
				}
			}			
		}
	}

	
	/**
	 * Stop doing the actions defined in doThat() method.
	 */
	public synchronized void pauseThread() {
		paused = true;
		if(blinky.getDirectionThread() != null) {
			blinky.getDirectionThread().pauseThread();
		}
	}
	
	/**
	 * Start again or continue doing the actions defined in doThat() method.
	 */
	public synchronized void resumeThread() {
		paused = false;
		if(blinky.getDirectionThread() != null) {
			blinky.getDirectionThread().resumeThread();;
		}
	}
	
	
	@Override
	protected void doThatAtStop() {
		if(blinky.getDirectionThread() != null && blinky.getDirectionThread().isRunning()) {
			blinky.getDirectionThread().stopThread();
		}
	}
	
	/**
	 * Transform a game panel position in a maze matrix position.
	 * @param gamePanelPos is the position in the game panel.
	 * @return the position in the maze numbers matrix.
	 */
	private Position mazeToMatrixPosition(Position gamePanelPos) {
		int matPosX = (int)Math.round((gamePanelPos.getX() * mazeValues.get(0).size()) / (double)gamePanel.getWidth());
		int matPosY = (int)Math.round((gamePanelPos.getY() * mazeValues.size()) / (double)gamePanel.getHeight());
		return new Position(matPosX, matPosY);
	}

}
