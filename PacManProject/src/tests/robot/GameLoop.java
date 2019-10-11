package tests.robot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.Maze;
import sprites.Blinky;
import sprites.Clyde;
import sprites.Inky;
import sprites.MovingSpriteState;
import sprites.PacMan;
import sprites.Pinky;
import sprites.Sprites;
import threads.AnimationThread;
import threads.PhysicsThread;
import threads.SoundThread;
import threads.ThreadPerso;

public class GameLoop extends ThreadPerso {
	
	// where to draw the game in the window
	JPanel panel;
	
	//maze
	private Maze maze;
	
	//sprites
	private Sprites energizers;
	private Sprites pacDots;
	private PacMan pacMan;
	private Blinky blinky;
	private Pinky pinky;
	private Clyde clyde;
	private Inky inky;
	
	//sprites animation
	private AnimationThread animationTh;
	
	//physics
	private PhysicsThread physicsTh;
	
	//rendering
	private Graphics dbg; 
	private Image dbImage = null;
	
	//sounds
	private SoundThread soundTh;
	

	public GameLoop(JPanel panel) {
		super("Game Loop");
		this.panel = panel;
	}

	@Override
	protected void doThatAtStart() {
		
		 try {
			maze = new Maze(panel, "mazeProto.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 energizers = maze.getEnergizers();
		 pacDots = maze.getPacDots();
		 pacMan = maze.getPacMan();
		 blinky = maze.getBlinky(); blinky.setWantedState(MovingSpriteState.STOP); blinky.setState(blinky.getWantedState());
		 pinky = maze.getPinky();
		 clyde = maze.getClyde();
		 inky = maze.getInky();
		 
		soundTh = new SoundThread()	;
		animationTh = new AnimationThread(energizers, pacMan, blinky, pinky, clyde, inky);
		physicsTh = new PhysicsThread(maze.getMazeValues(), panel, pacMan, blinky, pinky, clyde, inky, pacDots, energizers, null, soundTh);
		animationTh.startThread();
		physicsTh.startThread();
	}

	@Override
	protected void doThat() {
		gameUpdate();
		gameRender();
	}

	@Override
	protected void doThatAtStop() {
		physicsTh.stopThread();
		animationTh.stopThread();
	}

	public void readyForArrowsEvents(JFrame window) {
		window.addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				if(pacMan != null) {
					// listen for arrows events
					if (keyCode == KeyEvent.VK_LEFT) {
						pacMan.wantToGoLeft();						
					}
					
					else if(keyCode == KeyEvent.VK_RIGHT){
						pacMan.wantToGoRight();
						
					}
					else if(keyCode == KeyEvent.VK_UP){
						pacMan.wantToGoUp();
						
					}
					else if(keyCode == KeyEvent.VK_DOWN){
						pacMan.wantToGoDown();
						
					}					
				}
				
			}
		});
	}
	
	private void gameUpdate() {
		if(pacMan != null) {
			pacMan.updatePos();
		}
		
		if(blinky != null) {
			blinky.updatePos();
		}

		if(pinky != null) {
			pinky.updatePos();
		}
		
		if(clyde != null) {
			clyde.updatePos();
		}
		if(inky != null) {
			inky.updatePos();
		}
	}
	
	private void gameRender() {
		
		dbImage = panel.createImage(panel.getWidth(), panel.getHeight());
		
		if (dbImage == null) {
			System.out.println("dbImage is null");
			return;
		}
		else {
			dbg = dbImage.getGraphics();
		}
		
		//draw maze (background)
		maze.draw(dbg); 
		//draw all the sprites at their respective position, 
		//with their respective dimension
		if(pacDots.getSprites() != null && !pacDots.getSprites().isEmpty()) {
			pacDots.draw(dbg); 
		}
		if(energizers.getSprites() != null && !energizers.getSprites().isEmpty()) {
			energizers.draw(dbg); 
		}
		
		if(pacMan != null) {
			pacMan.draw(dbg);
		}
		
		if(blinky != null) {
			blinky.draw(dbg);
		}

		if(pinky != null) {
			pinky.draw(dbg);
		}
		
		if(clyde != null) {
			clyde.draw(dbg);
		}
		if(inky != null) {
			inky.draw(dbg);
		}
		
		paintScreen();
	}
	
	
	/**
	 * Draw the buffered image to the graphics of the game panel.
	 */
	private void paintScreen()
	// use active rendering to put the buffered image on-screen
	{ 
		Graphics g;
		try {
			g = panel.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
			g.dispose();
		}
		catch (Exception e)
		{ System.out.println("Graphics error: " + e);  }
	}
	
	
	public PacMan getPacMan() {
		return pacMan;
	}
	
	public Blinky getBlinky() {
		return blinky;
	}
	
}

