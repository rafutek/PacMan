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
		
		// wall collisions
		
		//pac-man
		synchronized(pacMan) {
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
		}
		
		//blinky
		synchronized(blinky) {
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
					
					
					if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || blinky.isInTheBox()) {
						blinky.setState(blinkyWantedState);
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

		
		//pinky
		synchronized(pinky) {
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
					
					if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || pinky.isInTheBox()) {
						pinky.setState(pinkyWantedState);
					}else {					
						if(pinky.getDirectionThread() == null || !pinky.getDirectionThread().isRunning()) {
							pinky.startDirectionThread();
						}
						// pinky set another possible direction
						pinky.getDirectionThread().changeDirection();
					}
				}			
			}			
		}

		
		//clyde
		synchronized(clyde) {
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
					
					if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || clyde.isInTheBox()) {
						clyde.setState(clydeWantedState);
					}else {					
						if(clyde.getDirectionThread() == null || !clyde.getDirectionThread().isRunning()) {
							clyde.startDirectionThread();
						}
						// clyde set another possible direction
						clyde.getDirectionThread().changeDirection();
					}
				}			
			}			
		}
		
		//inky
		synchronized(inky) {
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
					
					if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || inky.isInTheBox()) {
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
		
		
		// pac-man and ghosts collisions
		if(ghostCollision()) {
			//...
		}
		
	}

	
	/**
	 * Stop doing the actions defined in doThat() method.
	 */
	public synchronized void pauseThread() {
		paused = true;
		synchronized(blinky) {
			if(blinky.getDirectionThread() != null) {
				blinky.getDirectionThread().pauseThread();
			}			
		}
		synchronized(pinky) {
			if(pinky.getDirectionThread() != null) {
				pinky.getDirectionThread().pauseThread();
			}			
		}
		synchronized(clyde) {
			if(clyde.getDirectionThread() != null) {
				clyde.getDirectionThread().pauseThread();
			}			
		}
		synchronized(inky) {
			if(inky.getDirectionThread() != null) {
				inky.getDirectionThread().pauseThread();
			}			
		}
	}
	
	/**
	 * Start again or continue doing the actions defined in doThat() method.
	 */
	public synchronized void resumeThread() {
		paused = false;
		synchronized(blinky) {
			if(blinky.getDirectionThread() != null) {
				blinky.getDirectionThread().resumeThread();
			}			
		}
		synchronized(pinky) {
			if(pinky.getDirectionThread() != null) {
				pinky.getDirectionThread().resumeThread();
			}			
		}
		synchronized(clyde) {
			if(clyde.getDirectionThread() != null) {
				clyde.getDirectionThread().resumeThread();
			}			
		}
		synchronized(inky) {
			if(inky.getDirectionThread() != null) {
				inky.getDirectionThread().resumeThread();
			}			
		}
	}
	
	
	@Override
	protected void doThatAtStop() {
		synchronized(blinky) {
			if(blinky.getDirectionThread() != null) {
				blinky.getDirectionThread().stopThread();
			}			
		}
		synchronized(pinky) {
			if(pinky.getDirectionThread() != null) {
				pinky.getDirectionThread().stopThread();
			}			
		}
		synchronized(clyde) {
			if(clyde.getDirectionThread() != null) {
				clyde.getDirectionThread().stopThread();
			}			
		}
		synchronized(inky) {
			if(inky.getDirectionThread() != null) {
				inky.getDirectionThread().stopThread();
			}			
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

	private boolean ghostCollision() {
		synchronized(pacMan) {
			if(pacMan.getCurrentPosition() != null && pacMan.getCurrentSize() != null) {
				int pacman_left = pacMan.getCurrentPosition().getX();
				int pacman_right = pacMan.getCurrentPosition().getX() + pacMan.getCurrentSize().width;
				int pacman_up = pacMan.getCurrentPosition().getY();
				int pacman_down = pacMan.getCurrentPosition().getY() + pacMan.getCurrentSize().height;
				
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, blinky)) {
					System.out.println("collision with blinky!");
					return true;
				}
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, pinky)) {
					System.out.println("collision with pinky!");
					return true;
				}		
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, clyde)) {
					System.out.println("collision with clyde!");
					return true;
				}
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, inky)) {
					System.out.println("collision with inky!");
					return true;
				}			
			}
	
			return false;			
		}
	}
	
	private boolean collisionWith(int pacman_left, int pacman_right, int pacman_up, int pacman_down, Ghost ghost) {
		synchronized(ghost) {
			int ghost_left = ghost.getCurrentPosition().getX();
			int ghost_right = ghost.getCurrentPosition().getX() + ghost.getCurrentSize().width;
			int ghost_up = ghost.getCurrentPosition().getY();
			int ghost_down = ghost.getCurrentPosition().getY() + ghost.getCurrentSize().height;		
		
			if( pacman_left < ghost_right && pacman_right > ghost_left && pacman_down > ghost_up && pacman_up < ghost_down ) {
				return true;
			}
			return false;
		}
	}
	

	
}
