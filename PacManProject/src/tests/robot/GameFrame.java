package tests.robot;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.Maze;
import sprites.Ghost;
import sprites.PacMan;
import sprites.Sprites;
import threads.AnimationThread;
import threads.PhysicsThread;


public class GameFrame extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	
	private int windowWidth = 650;   
	private int windowHeight = 900; 
	private JPanel panel = new JPanel();
	private GameLoop gameloopTh = new GameLoop(panel);
	
	
	public GameFrame() throws IOException {
		super("Minimized maze");
		panel.setBackground(Color.black);	
		add(panel);
		setPreferredSize(new Dimension(windowWidth, windowHeight)); 
		pack();
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocus();
		
		gameloopTh.readyForArrowsEvents(this);
	}
	

	public void startGame() {
		gameloopTh.startThread();
	}

	public void stopGame() {
		gameloopTh.stopThread();
		synchronized(gameloopTh) {
			do {
				try {
					gameloopTh.join(100);
				} catch (InterruptedException e) {}
			}while(gameloopTh.isRunning());
		}
	}
	

	@Override
	public void windowActivated(WindowEvent arg0) {}


	@Override
	public void windowClosed(WindowEvent arg0) {
		stopGame();
	}


	@Override
	public void windowClosing(WindowEvent arg0) {
		stopGame();
	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {}


	@Override
	public void windowDeiconified(WindowEvent arg0) {}


	@Override
	public void windowIconified(WindowEvent arg0) {}


	@Override
	public void windowOpened(WindowEvent arg0) {}
	


//------------------------------------------------------------------------------------
	
	public static void main(String[] arg) throws InterruptedException, IOException {
		GameFrame window = new GameFrame();
		window.startGame();
	}


}
