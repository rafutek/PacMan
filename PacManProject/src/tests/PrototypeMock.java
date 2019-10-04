package tests;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
	
	private int windowWidth = 600;   
	private int windowHeight = 600; 
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
	
	
	Runnable renderLoop  = new Runnable() {
		@Override
		public void run() {
			while(running) {
				try { Thread.sleep(10); } catch (InterruptedException e) {}
				
				System.out.println("draw");
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
				pacDots.draw(dbg); 
				energizers.draw(dbg); 
				pacMan.draw(dbg);
				blinky.draw(dbg);
				pinky.draw(dbg);
				clyde.draw(dbg);
				inky.draw(dbg);		
				
				
				paintScreen();
			}
		}
	};
	
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
		physicsTh = new PhysicsThread(maze.getMazeValues(), panel, pacMan, blinky, pinky, clyde, inky);
		
		startRenderLoop();
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
	
	private void startRenderLoop() {
		Thread renderLoopTh = new Thread(renderLoop);
		renderLoopTh.start();
	}
	
	
	public void stop() {
		running = false;
		System.out.println("stop");
		System.exit(0);
	}
	
	public static void main(String[] arg) throws InterruptedException, IOException {
		PrototypeMock proto = new PrototypeMock();
		
		Thread.sleep(5000);
		proto.stop();
	}

}
