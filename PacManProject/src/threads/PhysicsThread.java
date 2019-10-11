package threads;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import sprites.Ghost;
import sprites.MovingSpriteState;
import sprites.PacMan;
import sprites.Position;
import sprites.Sprites;
import view.StatusBarPanel;

public class PhysicsThread extends ThreadPerso {

	public List<List<Integer>> mazeValues;
	public JPanel gamePanel;
	
	public PacMan pacMan;
	private MovingSpriteState pacManWantedState;
	
	private Ghost blinky;
	private MovingSpriteState blinkyWantedState;	

	private Ghost pinky;
	private MovingSpriteState pinkyWantedState;	

	private Ghost clyde;
	private MovingSpriteState clydeWantedState;	
	
	private Ghost inky;
	private MovingSpriteState inkyWantedState;	
	
	private InvincibleThread invTh; 
	
	private Sprites pacDots; 
	private Sprites energizers;
	private int score=0;
	private boolean ScoreBonus=false;
	public boolean timerstarted=false;
	private int scoreInvGhost;
	
	/**
	 * Management of the sounds
	 */
	private SoundThread soundTh;
	private MusicThread musicTh;
	private boolean soundMute = false;
	private boolean gameOver=false;
	
	private int scoreLevelI=1;
	private boolean Level=false;
	private boolean collPacManGhostInv=false;
	
	
	/**
	 * The class needs the maze number matrix, the game panel size and of course the moving sprites,
	 * in order to locate them in the matrix.
	 * The location of each sprite in the matrix allow them to go or not in the wanted direction.
	 * @param mazeValues
	 * @param gamePanel
	 * @param pacMan
	 */
	public PhysicsThread(List<List<Integer>> mazeValues, JPanel gamePanel, PacMan pacMan, Ghost blinky,  Ghost pinky,  Ghost clyde,  Ghost inky, Sprites pacDots, Sprites energizer, MusicThread musicTh, SoundThread soundTh) {
		super("Physics");
		this.mazeValues = mazeValues;
		this.gamePanel = gamePanel;
		this.pacMan = pacMan;
		this.blinky = blinky;
		this.pinky = pinky;
		this.clyde = clyde;
		this.inky = inky;
		this.pacDots=pacDots;
		this.energizers=energizer;
		this.musicTh = musicTh;
		this.soundTh = soundTh;
		
	}
	public boolean isCollPacManGhostInv() {
		return collPacManGhostInv;
	}
	public void setCollPacManGhostInv(boolean collPacManGhostInv) {
		this.collPacManGhostInv = collPacManGhostInv;
	}
	public boolean isLevel() {
		return Level;
	}

	public void setLevel(boolean level) {
		Level = level;
	}
	
	public Sprites getPacDots() {
		return pacDots;
	}



	public void setPacDots(Sprites pacDots) {
		this.pacDots = pacDots;
	}



	public Sprites getEnergizer() {
		return energizers;
	}



	public void setEnergizer(Sprites energizer) {
		this.energizers = energizer;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	public int getScoreLevelI() {
		return scoreLevelI;
	}



	public void setScoreLevelI(int scoreLevelI) {
		this.scoreLevelI = scoreLevelI;
	}

	public int getScoreInvGhost() {
		return scoreInvGhost;
	}
	public void setScoreInvGhost(int scoreInvGhost) {
		this.scoreInvGhost = scoreInvGhost;
	}
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	public  InvincibleThread getInvTh() {
		return invTh;
	}
	public void setInvTh(InvincibleThread invTh) {
		this.invTh = invTh;
	}

	@Override
	protected void doThatAtStart() {}

	@Override
	protected void doThat() {
		
		// wall collisions
		
		//pac-man
		if(pacMan != null) {
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
							currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
							if(currentMatrixPos != null) {
								if(currentMatrixPos.getX()-1 > 0) {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
								}
								else {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(mazeValues.get(0).size()-1); // opposite maze value
								}								
							}
						}
						else if(pacManWantedState == MovingSpriteState.RIGHT) {
							adaptedCurrentPosX = pacMan.getCurrentPosition().getX() - pacMan.getCurrentSize().width/2;
							adaptedCurrentPosY = pacMan.getCurrentPosition().getY();
							currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
							if(currentMatrixPos != null) {
								if(currentMatrixPos.getX()+1 < mazeValues.get(currentMatrixPos.getY()).size()) {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
								}
								else {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(0); // opposite maze value
								}								
							}
						}
						else if(pacManWantedState == MovingSpriteState.UP) {
							adaptedCurrentPosX = pacMan.getCurrentPosition().getX();
							adaptedCurrentPosY = pacMan.getCurrentPosition().getY() + pacMan.getCurrentSize().height/2;
							currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
							if(currentMatrixPos != null) {
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
							}
						}
						else if(pacManWantedState == MovingSpriteState.DOWN) {
							adaptedCurrentPosX = pacMan.getCurrentPosition().getX();
							adaptedCurrentPosY = pacMan.getCurrentPosition().getY() - pacMan.getCurrentSize().height/2;
							currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
							if(currentMatrixPos != null) {
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
							}
						}
						
						if(PacMan.acceptedMazeValues.contains(wantedBoxValue)) {
							if(wantedBoxValue == 352 && !pacMan.isInTunnel()) {
								pacMan.setState(pacManWantedState); // pac-man can be in that state
								pacMan.setIsInTunnel(true);
							}
							else if(wantedBoxValue != 352) {
								pacMan.setState(pacManWantedState); // pac-man can be in that state
								pacMan.setIsInTunnel(false);
							}
							
						}else if(!pacMan.isInTunnel()) {
							pacMan.setState(MovingSpriteState.STOP);
						}
					}			
				}			
			}
		}

		//blinky
		if(blinky != null) {
			synchronized(blinky) {
				if(!blinky.isInTheBox()) {
					if(blinky.getCurrentPosition() != null && blinky.getCurrentSize() != null) {
						blinkyWantedState = blinky.getWantedState();
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
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								if(currentMatrixPos != null) {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
								}
							}
							else if(blinkyWantedState == MovingSpriteState.RIGHT) {
								adaptedCurrentPosX = blinky.getCurrentPosition().getX() - blinky.getCurrentSize().width/2;
								adaptedCurrentPosY = blinky.getCurrentPosition().getY();
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								if(currentMatrixPos != null) {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
								}
							}
							else if(blinkyWantedState == MovingSpriteState.UP) {
								adaptedCurrentPosX = blinky.getCurrentPosition().getX();
								adaptedCurrentPosY = blinky.getCurrentPosition().getY() + blinky.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								if(currentMatrixPos != null) {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
								}
							}
							else if(blinkyWantedState == MovingSpriteState.DOWN) {
								adaptedCurrentPosX = blinky.getCurrentPosition().getX();
								adaptedCurrentPosY = blinky.getCurrentPosition().getY() - blinky.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								if(currentMatrixPos != null) {
									wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
								}
							}
							

							if(Ghost.acceptedMazeValues.contains(wantedBoxValue) || blinky.isInTheBox()) {
								blinky.setState(blinkyWantedState);
								blinky.createListDirections(); // reset all possible directions
							}else {					
								if(blinky.getBehaviorThread() == null || !blinky.getBehaviorThread().isRunning()) {
									blinky.startBehaviorThread();
								}
								// blinky set another possible direction
								blinky.removeDirection(blinkyWantedState);
								blinky.getBehaviorThread().changeDirection();
							}													
						}			
					}							
				}
			}
		}

		
		//pinky
		if(pinky != null) {
			synchronized(pinky) {
				if(!pinky.isInTheBox()) {
					if(pinky.getCurrentPosition() != null && pinky.getCurrentSize() != null) {
						pinkyWantedState = pinky.getWantedState();
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
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
							}
							else if(pinkyWantedState == MovingSpriteState.RIGHT) {
								adaptedCurrentPosX = pinky.getCurrentPosition().getX() - pinky.getCurrentSize().width/2;
								adaptedCurrentPosY = pinky.getCurrentPosition().getY();
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
							}
							else if(pinkyWantedState == MovingSpriteState.UP) {
								adaptedCurrentPosX = pinky.getCurrentPosition().getX();
								adaptedCurrentPosY = pinky.getCurrentPosition().getY() + pinky.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
							}
							else if(pinkyWantedState == MovingSpriteState.DOWN) {
								adaptedCurrentPosX = pinky.getCurrentPosition().getX();
								adaptedCurrentPosY = pinky.getCurrentPosition().getY() - pinky.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
							}

							if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || pinky.isInTheBox()) {
								pinky.setState(pinkyWantedState);
								pinky.createListDirections();
							}else {					
								if(pinky.getBehaviorThread() == null || !pinky.getBehaviorThread().isRunning()) {
									pinky.startBehaviorThread();
								}
								// pinky set another possible direction
								pinky.removeDirection(pinkyWantedState);
								pinky.getBehaviorThread().changeDirection();
							}
						}			
					}							
				}
			}			
		}
		
		//clyde
		if(clyde != null) {
			synchronized(clyde) {
				if(!clyde.isInTheBox()) {
					if(clyde.getCurrentPosition() != null && clyde.getCurrentSize() != null) {
						clydeWantedState = clyde.getWantedState();
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
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
							}
							else if(clydeWantedState == MovingSpriteState.RIGHT) {
								adaptedCurrentPosX = clyde.getCurrentPosition().getX() - clyde.getCurrentSize().width/2;
								adaptedCurrentPosY = clyde.getCurrentPosition().getY();
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
							}
							else if(clydeWantedState == MovingSpriteState.UP) {
								adaptedCurrentPosX = clyde.getCurrentPosition().getX();
								adaptedCurrentPosY = clyde.getCurrentPosition().getY() + clyde.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
							}
							else if(clydeWantedState == MovingSpriteState.DOWN) {
								adaptedCurrentPosX = clyde.getCurrentPosition().getX();
								adaptedCurrentPosY = clyde.getCurrentPosition().getY() - clyde.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
							}

							if( Ghost.acceptedMazeValues.contains(wantedBoxValue)) { // can go to that direction
								clyde.setState(clydeWantedState);
								clyde.createListDirections();									
								
							}else { // cannot go to that direction
								if(clyde.getBehaviorThread() == null || !clyde.getBehaviorThread().isRunning()) {
									clyde.startBehaviorThread();
								}
								// clyde set another possible direction
								clyde.removeDirection(clydeWantedState);
								clyde.getBehaviorThread().changeDirection();
							}
						}			
					}				
				}
			}
		}

		
		//inky
		if(inky != null) {
			synchronized(inky) {
				if(!inky.isInTheBox()) {
					if(inky.getCurrentPosition() != null && inky.getCurrentSize() != null) {
						inkyWantedState = inky.getWantedState();
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
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
							}
							else if(inkyWantedState == MovingSpriteState.RIGHT) {
								adaptedCurrentPosX = inky.getCurrentPosition().getX() - inky.getCurrentSize().width/2;
								adaptedCurrentPosY = inky.getCurrentPosition().getY();
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
							}
							else if(inkyWantedState == MovingSpriteState.UP) {
								adaptedCurrentPosX = inky.getCurrentPosition().getX();
								adaptedCurrentPosY = inky.getCurrentPosition().getY() + inky.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
							}
							else if(inkyWantedState == MovingSpriteState.DOWN) {
								adaptedCurrentPosX = inky.getCurrentPosition().getX();
								adaptedCurrentPosY = inky.getCurrentPosition().getY() - inky.getCurrentSize().height/2;
								currentMatrixPos = mazeToMatrixPosition(new Position(adaptedCurrentPosX, adaptedCurrentPosY), gamePanel, mazeValues);
								wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
							}

							if( Ghost.acceptedMazeValues.contains(wantedBoxValue) || inky.isInTheBox()) {
								inky.setState(inkyWantedState); // pac-man can be in that state
								inky.createListDirections();
							}else {					
								if(inky.getBehaviorThread() == null || !inky.getBehaviorThread().isRunning()) {
									inky.startBehaviorThread();
								}
								// inky set another possible direction
								inky.removeDirection(inkyWantedState);
								inky.getBehaviorThread().changeDirection();
							}
						}			
					}						
				}
			}			
		}
		
		// ghost collisions
		if(ghostCollision()) {			
			timerstarted=true;
			if(pacMan.getLife()!=0 && pacMan.getLife()<=4) {
				int life=pacMan.getLife()-1;
				pacMan.setLife(life);
				if(life==0) {
					System.out.println("GAMEOVER");
					gameOver=true;
				}
			}
		}
		
		// pac-dots collisions
		pacDotsCollision();
		
		// energizers collisions
		energizerCollision();
		
		// manage lives
		if(score>=10000 && !ScoreBonus ) {
			pacMan.setLife(pacMan.getLife()+1);
			StatusBarPanel.setImageLives(pacMan.getLife()+1);
			StatusBarPanel.livesImg.setIcon(new ImageIcon(StatusBarPanel.Lives));
			ScoreBonus=true;
		}
	}
	
	
	/**
	 * Stop doing the actions defined in doThat() method.
	 */
	public synchronized void pauseThread() {
		paused = true;
		if(blinky != null) {
			synchronized(blinky) {
				if(blinky.getBehaviorThread() != null) {
					blinky.getBehaviorThread().pauseThread();
				}			
			}			
		}
		if(pinky != null) {
			synchronized(pinky) {
				if(pinky.getBehaviorThread() != null) {
					pinky.getBehaviorThread().pauseThread();
				}			
			}			
		}
		if(clyde != null) {
			synchronized(clyde) {
				if(clyde.getBehaviorThread() != null) {
					clyde.getBehaviorThread().pauseThread();
				}			
			}			
		}
		if(inky != null) {
			synchronized(inky) {
				if(inky.getBehaviorThread() != null) {
					inky.getBehaviorThread().pauseThread();
				}			
			}			
		}

	}
	
	/**
	 * Start again or continue doing the actions defined in doThat() method.
	 */
	public synchronized void resumeThread() {
		paused = false;
		if(blinky != null) {
			synchronized(blinky) {
				if(blinky.getBehaviorThread() != null) {
					blinky.getBehaviorThread().resumeThread();
				}			
			}			
		}
		if(pinky != null) {
			synchronized(pinky) {
				if(pinky.getBehaviorThread() != null) {
					pinky.getBehaviorThread().resumeThread();
				}			
			}			
		}
		if(clyde != null) {
			synchronized(clyde) {
				if(clyde.getBehaviorThread() != null) {
					clyde.getBehaviorThread().resumeThread();
				}			
			}			
		}
		if(inky != null) {
			synchronized(inky) {
				if(inky.getBehaviorThread() != null) {
					inky.getBehaviorThread().resumeThread();
				}			
			}			
		}

	}
	
	
	@Override
	protected void doThatAtStop() {
		if(blinky != null) {
			synchronized(blinky) {
				if(blinky.getBehaviorThread() != null) {
					blinky.getBehaviorThread().stopThread();
				}			
			}			
		}
		if(pinky != null) {
			synchronized(pinky) {
				if(pinky.getBehaviorThread() != null) {
					pinky.getBehaviorThread().stopThread();
				}			
			}			
		}
		if(clyde != null) {
			synchronized(clyde) {
				if(clyde.getBehaviorThread() != null) {
					clyde.getBehaviorThread().stopThread();
				}			
			}			
		}
		if(inky != null) {
			synchronized(inky) {
				if(inky.getBehaviorThread() != null) {
					inky.getBehaviorThread().stopThread();
				}			
			}			
		}

	}
	

	/**
	 * Transform a game panel position in a maze matrix position.
	 * @param panelPos is the position in the game panel.
	 * @param panel is the game panel
	 * @param mazeValues is the matrix numbers corresponding to the maze.
	 * @return the position in the maze numbers matrix.
	 */
	public static Position mazeToMatrixPosition(Position panelPos, JPanel panel, List<List<Integer>> mazeValues) {
		int matPosX = (int)Math.round((panelPos.getX() * mazeValues.get(0).size()) / (double)panel.getWidth());
		int matPosY = (int)Math.round((panelPos.getY() * mazeValues.size()) / (double)panel.getHeight());
		if(matPosX >= mazeValues.get(0).size() || matPosY >= mazeValues.size()) {
			System.out.println("matrix value out of the bounds !");
			return null;
		}
		return new Position(matPosX, matPosY);
	}
	
	/**
	 * Transform a matrix position in a game panel position.
	 * @param matrixPos is the position in the matrix mazeValues.
	 * @param panel is the game panel
	 * @param mazeValues is the matrix numbers corresponding to the csv maze file.
	 * @return the position in the game panel (in pixel).
	 */
	public static Position matrixToMazePosition(Position matrixPos, JPanel panel, List<List<Integer>> mazeValues) {
		int panelPosX = (int)Math.round((matrixPos.getX() * panel.getWidth()) / (double)mazeValues.get(0).size());
		int panelPosY = (int)Math.round((matrixPos.getY() * panel.getHeight()) / (double)mazeValues.size());
		if(panelPosX >= panel.getWidth() || panelPosY >= panel.getHeight()) {
			System.out.println("panel value out of the bounds !");
			return null;
		}
		return new Position(panelPosX, panelPosY);
	}
	
	

	public boolean ghostCollision() {
		synchronized(pacMan) {
			if(pacMan.getCurrentPosition() != null && pacMan.getCurrentSize() != null) {
				int pacman_left = pacMan.getCurrentPosition().getX();
				int pacman_right = pacMan.getCurrentPosition().getX() + pacMan.getCurrentSize().width;
				int pacman_up = pacMan.getCurrentPosition().getY();
				int pacman_down = pacMan.getCurrentPosition().getY() + pacMan.getCurrentSize().height;
				
				
				
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, blinky) && pacMan.invincible()) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addEatGhost();
						}
					}
					score+=200*(int)Math.pow(2,pacMan.eatenFantom());
					setScoreInvGhost(200*(int)Math.pow(2,pacMan.eatenFantom()));
					if(StatusBarPanel.valueScore != null) {
						StatusBarPanel.valueScore.setText(""+score);
					}
					pacMan.setEatenFantom(pacMan.eatenFantom()+1);
					setCollPacManGhostInv(true);
					replaceGhost(blinky);
					return false;
				}
				
				
				else if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, blinky)) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addDeath();
						}
						soundTh.setPacmanIsDead(true);

					}
					resetAllSprites();
					return true;
				}
				
				
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, pinky) && pacMan.invincible()) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addEatGhost();
						}
						
					}
					score+=200*(int)Math.pow(2,pacMan.eatenFantom());
					StatusBarPanel.valueScore.setText(""+score);
					setScoreInvGhost(200*(int)Math.pow(2,pacMan.eatenFantom()));
					pacMan.setEatenFantom(pacMan.eatenFantom()+1);
					setCollPacManGhostInv(true);
					replaceGhost(pinky);
					return false;
				}		
				
				else if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, pinky)) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addDeath();
						}
						soundTh.setPacmanIsDead(true);

					}
					resetAllSprites();
					return true;
				}
				
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, clyde) && pacMan.invincible()) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addEatGhost();
						}
					}
					score+=200*(int)Math.pow(2,pacMan.eatenFantom());
					StatusBarPanel.valueScore.setText(""+score);
					setScoreInvGhost(200*(int)Math.pow(2,pacMan.eatenFantom()));
					pacMan.setEatenFantom(pacMan.eatenFantom()+1);
					setCollPacManGhostInv(true);
					replaceGhost(clyde);
					return false;
				}
				
				else if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, clyde)) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addDeath();
						}
						soundTh.setPacmanIsDead(true);

					}
					resetAllSprites();
					return true;
				}
				
				if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, inky) && pacMan.invincible()) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addEatGhost();
						}
					}
					score+=200*(int)Math.pow(2,pacMan.eatenFantom());
					StatusBarPanel.valueScore.setText(""+score);
					setScoreInvGhost(200*(int)Math.pow(2,pacMan.eatenFantom()));
					pacMan.setEatenFantom(pacMan.eatenFantom()+1);
					setCollPacManGhostInv(true);
					replaceGhost(inky);
					return false;
				}
				
				else if(collisionWith(pacman_left, pacman_right, pacman_up, pacman_down, inky)) {
					if (!soundMute) {
						synchronized(soundTh) {
							soundTh.addDeath();
						}
						soundTh.setPacmanIsDead(true);
					}
					resetAllSprites();
					return true;
				}
			}
			return false;			
		}
	}
	
	
	private boolean pacDotsCollision() {
		synchronized(pacMan) {
			if(collisionWith(pacDots)) {
				if (!soundMute) {
					synchronized(soundTh) {
						soundTh.addEatGomme();
					}
				}
				return true;
			}
			return false;	
		}
	}
	
	
	private boolean energizerCollision() {
		synchronized(pacMan) {
			
			if(collisionWithE(energizers)) {
				score=score+50;
				StatusBarPanel.valueScore.setText(""+score);
				if (!soundMute) {
					synchronized(soundTh) {
						soundTh.addEatEnergizer();
					}
				}
				pacMan.setInvincible(true);
				invTh=new InvincibleThread(pacMan , musicTh);
				invTh.startThread();
				return true;
			}
			return false;
		}
	}
	
	private boolean collisionWith(Sprites pacDots) {
		if(pacDots != null) {
			synchronized(pacDots) {
				for(int i=0; i<pacDots.getSprites().size();i++){
					int positionX=pacDots.getSpriteNb(i).getCurrentPosition().getX();
					int positionY= pacDots.getSpriteNb(i).getCurrentPosition().getY();
					if(pacMan.getCurrentPosition().getX()<=positionX+(20/2) && pacMan.getCurrentPosition().getX()>= positionX-(20/2)  && pacMan.getCurrentPosition().getY()<=positionY+(20/2) && pacMan.getCurrentPosition().getY()>= positionY-(20/2) )  {
						pacDots.showX=pacDots.getSpriteNb(i).getCurrentPosition().getX();
						pacDots.showY=pacDots.getSpriteNb(i).getCurrentPosition().getY();
						pacDots.removeSpriteNb(i);
						score=score+10;
						StatusBarPanel.valueScore.setText(""+score);
						return true;
					}	
				}			
			}			
		}
		return false;
	}
	
	
	private boolean collisionWithE(Sprites energizers) {
		for(int i=0; i<energizers.getSprites().size();i++){
			int positionX=energizers.getSpriteNb(i).getCurrentPosition().getX();
			int positionY= energizers.getSpriteNb(i).getCurrentPosition().getY();
			if(pacMan.getCurrentPosition().getX()<=positionX+(20/2) && pacMan.getCurrentPosition().getX()>= positionX-(20/2)  && pacMan.getCurrentPosition().getY()<=positionY+(20/2) && pacMan.getCurrentPosition().getY()>= positionY-(20/2) )  {
				energizers.showX=energizers.getSpriteNb(i).getCurrentPosition().getX();
				energizers.showY=energizers.getSpriteNb(i).getCurrentPosition().getY();
				energizers.removeSpriteNb(i);
				return true;
			}	
		}
		return false;
	}
	
	
	
	private boolean collisionWith(int pacman_left, int pacman_right, int pacman_up, int pacman_down, Ghost ghost) {
		if(ghost != null) {
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
		return false;
	}

	
	private void resetAllSprites() {
		
		if(pacMan != null) {
			synchronized(pacMan) {
				pacMan.setCurrentPosition(matrixToMazePosition(pacMan.getMatrixPosition(), gamePanel, mazeValues));
			}
		}
		
		// stop the ghost that are replaced in the box before replacing them
		if(blinky != null) {
			replaceGhost(blinky);
		}		
		if(pinky != null) {
			replaceGhost(pinky);
		}		
		if(clyde != null) {
			replaceGhost(clyde);
		}
		if(inky != null) {
			replaceGhost(inky);
		}
	}
	
	private void replaceGhost(Ghost ghost) {
		synchronized(ghost) {
			ghost.setCurrentPosition(matrixToMazePosition(ghost.getMatrixPosition(), gamePanel, mazeValues));
			ghost.setInTheBox(true);
			ghost.setState(MovingSpriteState.STOP);
			ghost.notGoingToLastSeenPosition();			
		}
	}
	
}
