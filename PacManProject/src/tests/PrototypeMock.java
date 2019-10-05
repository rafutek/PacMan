package tests;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.Maze;
import sprites.Ghost;
import sprites.PacMan;
import sprites.Sprites;
import threads.AnimationThread;
import threads.PhysicsThread;


public class PrototypeMock extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private int windowWidth = 650;   
	private int windowHeight = 900; 
	private JPanel panel = new JPanel();
	private volatile boolean running = true;
	
	//rendering
	private Graphics dbg; 
	private Image dbImage = null;
	
	//maze
	private Maze maze;
	
	//sprites
	private Sprites energizers;
	private Sprites pacDots;
	private PacMan pacMan;
	private Ghost blinky;
	private Ghost pinky;
	private Ghost clyde;
	private Ghost inky;
	
	//sprites animation
	private AnimationThread animationTh;
	
	//physics
	private PhysicsThread physicsTh;
	
	
	public PrototypeMock() throws IOException {
		super("Prototype");
		panel.setBackground(Color.black);	
		add(panel);
		setPreferredSize(new Dimension(windowWidth, windowHeight)); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocus();
		
		 maze = new Maze(panel, "mazeProto.txt");
		 energizers = maze.getEnergizers();
		 pacDots = maze.getPacDots();
		 pacMan = maze.getPacMan();
		 blinky = maze.getBlinky();
		 pinky = maze.getPinky();
		 clyde = maze.getClyde();
		 inky = maze.getInky();
		 
		animationTh = new AnimationThread(energizers, pacMan, blinky, pinky, clyde, inky);
		physicsTh = new PhysicsThread(maze.getMazeValues(), panel, pacMan, blinky, pinky, clyde, inky, pacDots, energizers);

		startRenderLoop();
		animationTh.startThread();
		physicsTh.startThread();
		
		readyForArrowsEvents();
	}
	
	
	private void startRenderLoop() {
		Thread renderLoopTh = new Thread(renderLoop);
		renderLoopTh.start();
	}
	
	
	public void stop() {
		running = false;
		physicsTh.stopThread();
		animationTh.stopThread();
		System.out.println("stop");
		System.exit(0);
	}
	
	Runnable renderLoop  = new Runnable() {
		@Override
		public void run() {
			while(running) {
				try { Thread.sleep(10); } catch (InterruptedException e) {}
				
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
					System.out.println("draw pacdots");
					pacDots.draw(dbg); 
				}
				if(energizers.getSprites() != null && !energizers.getSprites().isEmpty()) {
					System.out.println("draw energizers");
					energizers.draw(dbg); 
				}
				if(pacMan != null) {
					System.out.println("draw pacman");
					pacMan.updatePos();
					pacMan.draw(dbg);
				}
				if(blinky != null) {
					System.out.println("draw blinky");
					blinky.updatePos();
					blinky.draw(dbg);
				}
				if(pinky != null) {
					System.out.println("draw pinky");
					pinky.updatePos();
					pinky.draw(dbg);
				}
				if(clyde != null) {
					System.out.println("draw clyde");
					clyde.updatePos();
					clyde.draw(dbg);
				}
				if(inky != null) {
					System.out.println("draw inky");
					inky.updatePos();
					inky.draw(dbg);	
				}
					
				
				
				paintScreen();
			}
		}
	};
	
	
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
	
	private void readyForArrowsEvents() {
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
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
		});
	}

//------------------------------------------------------------------------------------
	
	public static void main(String[] arg) throws InterruptedException, IOException {
		PrototypeMock proto = new PrototypeMock();
		
		Thread.sleep(5000);
		proto.stop();
	}

}
