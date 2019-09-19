package rendering;


import javax.swing.*;

import threads.RenderThread;

import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;


	private RenderThread renderTh;

	public GameFrame(int period)
	{ 
		super("PacMan");
		makeGUI(period);
		addWindowListener( this );

		pack();
		setResizable(false);
		setLocationRelativeTo(null); // center the window
		setVisible(true);
		setFocusable(true);
		requestFocus();    // the window has now has focus, so receives key events
		readyForTermination();		
	}  
	

/**
 * Create game and status bar panels, and the rendering thread
 * @param period
 */
private void makeGUI(int period)
{
	Container c = getContentPane();    // default BorderLayout used

	GamePanel gamePanel = new GamePanel();
	c.add(gamePanel, "Center");
	
	StatusBarPanel statusBarPanel = new StatusBarPanel();
	c.add(statusBarPanel, "South");

	renderTh = new RenderThread(period, gamePanel, statusBarPanel);
	
}  // end of makeGUI()

/**
 * Called when the window listener i added to the window (before pack() in the constructor).
 * This method launch the render thread
 */
public void addNotify()
{ 
	super.addNotify();   // creates the peer
	renderTh.startGame();    // start the thread
}

private void readyForTermination()
{
	addKeyListener( new KeyAdapter() {
		// listen for esc, q, end, ctrl-c on the canvas to
		// allow a convenient exit from the full screen configuration
		public void keyPressed(KeyEvent e)
		{ 
			int keyCode = e.getKeyCode();
			System.out.println("key");
			if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
					(keyCode == KeyEvent.VK_END) ||
					((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
				renderTh.stopGame();
			}
		}
	});
}  // end of readyForTermination()



// ----------------- window listener methods -------------

public void windowActivated(WindowEvent e) 
{ renderTh.resumeGame();  }

public void windowDeactivated(WindowEvent e) 
{  renderTh.pauseGame();  }


public void windowDeiconified(WindowEvent e) 
{  renderTh.resumeGame();  }

public void windowIconified(WindowEvent e) 
{  renderTh.pauseGame(); }


public void windowClosing(WindowEvent e)
{  renderTh.stopGame();  }

public void windowClosed(WindowEvent e) {}
public void windowOpened(WindowEvent e) {}



} 
