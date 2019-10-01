package threads;

import java.util.List;
import javax.swing.JPanel;

import sprites.Ghost;
import sprites.MovingSpriteState;
import sprites.PacMan;
import sprites.Position;

public class PhysicsThread extends ThreadPerso {

	private List<List<Integer>> mazeValues;
	private JPanel gamePanel;
	
	private PacMan pacMan;
	private MovingSpriteState pacManWantedState;
	
	private Ghost blinky;
	private MovingSpriteState blinkyWantedState;	

	private Ghost pinky;
	private MovingSpriteState pinkyWantedState;	

	private Ghost clyde;
	private MovingSpriteState clydeWantedState;	
	
	private Ghost inky;
	private MovingSpriteState inkyWantedState;	
	
	
	/**
	 * The class needs the maze number matrix, the game panel size and of course the moving sprites,
	 * in order to locate them in the matrix.
	 * The location of each sprite in the matrix allow them to go or not in the wanted direction.
	 * @param mazeValues
	 * @param gamePanel
	 * @param pacMan
	 */
	public PhysicsThread(List<List<Integer>> mazeValues, JPanel gamePanel, PacMan pacMan, Ghost blinky,  Ghost pinky,  Ghost clyde,  Ghost inky) {
		super("Physics");
		this.mazeValues = mazeValues;
		this.gamePanel = gamePanel;
		this.pacMan = pacMan;
		this.blinky = blinky;
		this.pinky = pinky;
		this.clyde = clyde;
		this.inky = inky;
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
					if(currentMatrixPos.getX()-1 > 0) {
						wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
					}
					else {
						wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(mazeValues.get(0).size()-1); // opposite maze value
					}
					
				}
				else if(pacManWantedState == MovingSpriteState.RIGHT) {
					adaptedCurrentPosX = pacMan.getCurrentPosition().getX() - pacMan.getCurrentSize().width/2;
					adaptedCurrentPosY = pacMan.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					if(currentMatrixPos.getX()+1 < mazeValues.get(currentMatrixPos.getY()).size()) {
						wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
					}
					else {
						wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(0); // opposite maze value
					}
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
				
				if(PacMan.acceptedMazeValues.contains(wantedBoxValue)) {
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
				
				
				if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || blinky.isInTheBox) {
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
		
		//pinky
		if(pinky.getCurrentPosition() != null && pinky.getCurrentSize() != null) {
			pinkyWantedState = pinky.getState();
			if(pinkyWantedState != MovingSpriteState.STOP) {
				
				// we need to use a little bit changed position 
				// so that pac-man can go a little bit farther in the maze
				int adaptedCurrentPosX;
				int adaptedCurrentPosY;
				Position currentMatrixPos; // the position of the sprite in the matrix
				int wantedBoxValue = -1; // the next box value where pac-man wants to go
				
				if(pinkyWantedState == MovingSpriteState.LEFT) {
					adaptedCurrentPosX = pinky.getCurrentPosition().getX() + pinky.getCurrentSize().width/2;
					adaptedCurrentPosY = pinky.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
				}
				else if(pinkyWantedState == MovingSpriteState.RIGHT) {
					adaptedCurrentPosX = pinky.getCurrentPosition().getX() - pinky.getCurrentSize().width/2;
					adaptedCurrentPosY = pinky.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
				}
				else if(pinkyWantedState == MovingSpriteState.UP) {
					adaptedCurrentPosX = pinky.getCurrentPosition().getX();
					adaptedCurrentPosY = pinky.getCurrentPosition().getY() + pinky.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
				}
				else if(pinkyWantedState == MovingSpriteState.DOWN) {
					adaptedCurrentPosX = pinky.getCurrentPosition().getX();
					adaptedCurrentPosY = pinky.getCurrentPosition().getY() - pinky.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
				}
				
				if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || pinky.isInTheBox) {
					pinky.setState(pinkyWantedState); // pac-man can be in that state
				}else {					
					if(pinky.getDirectionThread() == null || !pinky.getDirectionThread().isRunning()) {
						pinky.startDirectionThread();
					}
					// pinky set another possible direction
					pinky.getDirectionThread().changeDirection();
				}
			}			
		}
		
		//clyde
		if(clyde.getCurrentPosition() != null && clyde.getCurrentSize() != null) {
			clydeWantedState = clyde.getState();
			if(clydeWantedState != MovingSpriteState.STOP) {
				
				// we need to use a little bit changed position 
				// so that pac-man can go a little bit farther in the maze
				int adaptedCurrentPosX;
				int adaptedCurrentPosY;
				Position currentMatrixPos; // the position of the sprite in the matrix
				int wantedBoxValue = -1; // the next box value where pac-man wants to go
				
				if(clydeWantedState == MovingSpriteState.LEFT) {
					adaptedCurrentPosX = clyde.getCurrentPosition().getX() + clyde.getCurrentSize().width/2;
					adaptedCurrentPosY = clyde.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
				}
				else if(clydeWantedState == MovingSpriteState.RIGHT) {
					adaptedCurrentPosX = clyde.getCurrentPosition().getX() - clyde.getCurrentSize().width/2;
					adaptedCurrentPosY = clyde.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
				}
				else if(clydeWantedState == MovingSpriteState.UP) {
					adaptedCurrentPosX = clyde.getCurrentPosition().getX();
					adaptedCurrentPosY = clyde.getCurrentPosition().getY() + clyde.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
				}
				else if(clydeWantedState == MovingSpriteState.DOWN) {
					adaptedCurrentPosX = clyde.getCurrentPosition().getX();
					adaptedCurrentPosY = clyde.getCurrentPosition().getY() - clyde.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
				}
				
				if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || clyde.isInTheBox) {
					clyde.setState(clydeWantedState); // pac-man can be in that state
				}else {					
					if(clyde.getDirectionThread() == null || !clyde.getDirectionThread().isRunning()) {
						clyde.startDirectionThread();
					}
					// clyde set another possible direction
					clyde.getDirectionThread().changeDirection();
				}
			}			
		}
		
		//inky
		if(inky.getCurrentPosition() != null && inky.getCurrentSize() != null) {
			inkyWantedState = inky.getState();
			if(inkyWantedState != MovingSpriteState.STOP) {
				
				// we need to use a little bit changed position 
				// so that pac-man can go a little bit farther in the maze
				int adaptedCurrentPosX;
				int adaptedCurrentPosY;
				Position currentMatrixPos; // the position of the sprite in the matrix
				int wantedBoxValue = -1; // the next box value where pac-man wants to go
				
				if(inkyWantedState == MovingSpriteState.LEFT) {
					adaptedCurrentPosX = inky.getCurrentPosition().getX() + inky.getCurrentSize().width/2;
					adaptedCurrentPosY = inky.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
				}
				else if(inkyWantedState == MovingSpriteState.RIGHT) {
					adaptedCurrentPosX = inky.getCurrentPosition().getX() - inky.getCurrentSize().width/2;
					adaptedCurrentPosY = inky.getCurrentPosition().getY();
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
				}
				else if(inkyWantedState == MovingSpriteState.UP) {
					adaptedCurrentPosX = inky.getCurrentPosition().getX();
					adaptedCurrentPosY = inky.getCurrentPosition().getY() + inky.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
				}
				else if(inkyWantedState == MovingSpriteState.DOWN) {
					adaptedCurrentPosX = inky.getCurrentPosition().getX();
					adaptedCurrentPosY = inky.getCurrentPosition().getY() - inky.getCurrentSize().height/2;
					currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY));
					wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
				}
				
				if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || inky.isInTheBox) {
					inky.setState(inkyWantedState); // pac-man can be in that state
				}else {					
					if(inky.getDirectionThread() == null || !inky.getDirectionThread().isRunning()) {
						inky.startDirectionThread();
					}
					// inky set another possible direction
					inky.getDirectionThread().changeDirection();
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
		if(pinky.getDirectionThread() != null) {
			pinky.getDirectionThread().pauseThread();
		}
		if(clyde.getDirectionThread() != null) {
			clyde.getDirectionThread().pauseThread();
		}
		if(inky.getDirectionThread() != null) {
			inky.getDirectionThread().pauseThread();
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
		if(pinky.getDirectionThread() != null) {
			pinky.getDirectionThread().resumeThread();;
		}
		if(clyde.getDirectionThread() != null) {
			clyde.getDirectionThread().resumeThread();;
		}
		if(inky.getDirectionThread() != null) {
			inky.getDirectionThread().resumeThread();;
		}
	}
	
	
	@Override
	protected void doThatAtStop() {
		if(blinky.getDirectionThread() != null) {
			blinky.getDirectionThread().stopThread();
		}
		if(pinky.getDirectionThread() != null) {
			pinky.getDirectionThread().stopThread();
		}
		if(clyde.getDirectionThread() != null) {
			clyde.getDirectionThread().stopThread();
		}
		if(inky.getDirectionThread() != null) {
			inky.getDirectionThread().stopThread();
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
