package tests.robot;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameFrame extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	
	private int windowWidth = 650;   
	private int windowHeight = 900; 
	private JPanel panel = new JPanel();
	private GameLoop gameloopTh = new GameLoop(panel);
	
	
	public GameFrame() throws IOException {
		super("Minimized maze");
		addWindowListener(this);
		
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
			try {
				gameloopTh.join(100);
			} catch (InterruptedException e) {}
			
			if(gameloopTh.isRunning()) {
				gameloopTh.interrupt();
			}
		}
	}
	

	@Override
	public void windowOpened(WindowEvent arg0) {}
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		stopGame();
		System.exit(0);
	}

	

//------------------------------------------------------------------------------------
	
	public static void main(String[] arg) throws InterruptedException, IOException {
		GameFrame window = new GameFrame();
		window.startGame();
	}


}
