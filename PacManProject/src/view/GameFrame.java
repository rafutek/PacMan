package view;


import javax.swing.*;

import threads.LayoutManagerThread;
import threads.RenderThread;

import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame implements WindowListener,WindowFocusListener, WindowStateListener
{

	private static final long serialVersionUID = 1L;
	
	private final int period;
	
	private int windowWidth = 620;   
	private int windowHeight = 700; 
	
	private GridBagLayout gridbag;
	private GamePanel gamePanel;
	private StatusBarPanel statusBarPanel;
	private JPanel leftPanel, rightPanel;
	
	
	private boolean fullScreen = false;
	private RenderThread renderTh;
	private LayoutManagerThread layoutTh;
	
	private boolean gamePaused = false;

	public GameFrame(int period)
	{ 
		super("PacMan");
		this.period = period;
		
		makeGUI();
		addWindowListener(this);
		addWindowStateListener(this);

		pack();
		setResizable(true);
		setLocationRelativeTo(null); // center the window
		setVisible(true);
		setFocusable(true);
		requestFocus();    // the window has now has focus, so receives key events
		readyForTermination();		
		readyForFullScreen();
		readyForArrowsEvents();
		readyForPause();
	}  
	
	
	public synchronized GridBagLayout getGridbag() {
		return gridbag;
	}


	public synchronized GamePanel getGamePanel() {
		return gamePanel;
	}


	public synchronized StatusBarPanel getStatusBarPanel() {
		return statusBarPanel;
	}


	public synchronized JPanel getLeftPanel() {
		return leftPanel;
	}


	public synchronized JPanel getRightPanel() {
		return rightPanel;
	}
	

	/**
	 * Create game and status bar panels, and the rendering thread
	 * @param period
	 */
	private void makeGUI()
	{
		gridbag = new GridBagLayout();
		setLayout(gridbag);
		
		gamePanel = new GamePanel();
		statusBarPanel = new StatusBarPanel();
		
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.CYAN);
		
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.darkGray);	
		

		
		add(gamePanel);
		add(statusBarPanel);
		add(leftPanel);
		add(rightPanel);

		setPreferredSize(new Dimension(windowWidth, windowHeight)); // set window size
		
	} 
	
	
	/**
	 * Called when the window components are packed.
	 * This method launch the render and layout manager threads
	 */
	public void addNotify()
	{ 
		super.addNotify();   // creates the peer
		layoutTh = new LayoutManagerThread(this);
		renderTh = new RenderThread(period, gamePanel, statusBarPanel);
		
		layoutTh.startThread();
		renderTh.startThread();
	}
	
	private void readyForTermination()
	{
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				// listen for esc, q, end, ctrl-c on the canvas to
				// allow a convenient exit from the full screen configuration
				if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
						(keyCode == KeyEvent.VK_END) ||
						((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
					renderTh.stopThread();
					layoutTh.stopThread();
				}
			}
		});
	} 
	
	
	private void readyForFullScreen() {
		
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				// listen for f to go on full screen or normal sized window
				if (keyCode == KeyEvent.VK_F) {
					if(!fullScreen) {
						fullScreen = true;				
						setExtendedState(JFrame.MAXIMIZED_BOTH);
					}
					else {
						fullScreen = false;
						setExtendedState(JFrame.NORMAL);
					}
				}
			}
		});
	}
	
	private void readyForArrowsEvents() {
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				// listen for arrows events
				if (keyCode == KeyEvent.VK_LEFT) {
					renderTh.getPacMan().wantToGoLeft();
				}
				else if(keyCode == KeyEvent.VK_RIGHT){
					renderTh.getPacMan().wantToGoRight();
				}
				else if(keyCode == KeyEvent.VK_UP){
					renderTh.getPacMan().wantToGoUp();
				}
				else if(keyCode == KeyEvent.VK_DOWN){
					renderTh.getPacMan().wantToGoDown();
				}
				
			}
		});
	}
	
	private void readyForPause() {
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				// listen for arrows events
				if (keyCode == KeyEvent.VK_P) {
					if(!gamePaused) {
						System.out.println("Game paused");
						gamePaused = true;
						renderTh.pauseThread();
					}
					else if(gamePaused) {
						System.out.println("Game resumed");
						gamePaused = false;
						renderTh.resumeThread();
					}
				}
			}
		});
	}


// ----------------- window listener methods -------------

	public void windowActivated(WindowEvent e) 
	{ 
		renderTh.resumeThread();
		layoutTh.resumeThread();
	}
	
	public void windowDeactivated(WindowEvent e) 
	{  
		renderTh.pauseThread();
		layoutTh.pauseThread();
	}
	
	
	public void windowDeiconified(WindowEvent e) 
	{  
		renderTh.resumeThread(); 
		layoutTh.resumeThread();
	}
	
	public void windowIconified(WindowEvent e) 
	{  
		renderTh.pauseThread();
		layoutTh.pauseThread();
	}
	
	
	public void windowClosing(WindowEvent e)
	{  
		renderTh.stopThread(); 
		layoutTh.stopThread();
	}
	
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
	public void windowStateChanged(WindowEvent e) {}
	
	public void windowGainedFocus(WindowEvent e) {}
	public void windowLostFocus(WindowEvent e) {}


} 
