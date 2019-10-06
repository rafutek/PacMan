package view;


import javax.swing.*;

import threads.LayoutManagerThread;
import threads.MusicThread;
import threads.PhysicsThread;
import threads.RenderThread;
import threads.SoundThread;

import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;
	
	private final int period;
	
	private int windowWidth = 620;   
	private int windowHeight = 700; 
	
	private GridBagLayout gridbag;
	private GamePanel gamePanel;
	private StatusBarPanel statusBarPanel;
	private JPanel leftPanel, rightPanel;
	private JLabel statut;
	
	
	private boolean fullScreen = false;
	public RenderThread renderTh;
	private LayoutManagerThread layoutTh;
	private MusicThread musicTh;
	
	private boolean gamePaused = false;
	private boolean gameMute = false;

	public GameFrame(int period)
	{ 
		super("PacMan");
		this.period = period;
		
		makeGUI();
		addWindowListener(this);

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
		readyForMute();
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
		musicTh = new MusicThread("musicTh");
		
		
		layoutTh.startThread();
		renderTh.startThread();
		musicTh.startThread();
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
					closeGame();
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
					synchronized(renderTh) {
						renderTh.getPacMan().wantToGoLeft();						
					}
				}
				else if(keyCode == KeyEvent.VK_RIGHT){
					synchronized(renderTh) {
						renderTh.getPacMan().wantToGoRight();
					}
				}
				else if(keyCode == KeyEvent.VK_UP){
					synchronized(renderTh) {
						renderTh.getPacMan().wantToGoUp();
					}
				}
				else if(keyCode == KeyEvent.VK_DOWN){
					synchronized(renderTh) {
						renderTh.getPacMan().wantToGoDown();
					}
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
						synchronized (renderTh) {
							renderTh.pauseThread();
						}
						statut = statusBarPanel.getStatut();
						statut.setText("Paused");
						statusBarPanel.setStatut(statut);
					}
					else if(gamePaused) {
						System.out.println("Game resumed");
						gamePaused = false;
						synchronized (renderTh) {
							renderTh.resumeThread();
						}
						statut = statusBarPanel.getStatut();
						statut.setText("Resumed");
						statusBarPanel.setStatut(statut);
					}
				}
			}
		});
	}
	
	private void readyForMute() {
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				// listen for arrows events
				if (keyCode == KeyEvent.VK_M) {
					if(!gameMute) {
						System.out.println("Sound mute");
						gameMute = true;
						synchronized (musicTh) {
							musicTh.setMute(true);
						}
					}
					else if(gameMute) {
						System.out.println("Sound on");
						gameMute = false;
						synchronized (musicTh) {
							musicTh.setMute(false);
						}
					}
				} 
			}
		});
	}

	private void closeGame() {
		
		layoutTh.stopThread();		
		renderTh.stopThread(); 
		synchronized(renderTh) {
			try {
				renderTh.join(100);
				if(renderTh.isRunning()) {
					renderTh.interrupt();
				}
			} catch (InterruptedException e) {}
		}
		renderTh.printStats();
		System.exit(0);   // so window disappears	
	}

// ----------------- window listener methods -------------

	public void windowActivated(WindowEvent e) 
	{ 
		System.out.println("window activated");
		synchronized (renderTh){
			renderTh.resumeThread();
		}
		synchronized (layoutTh){
			layoutTh.resumeThread();
		}
		synchronized (musicTh){
			musicTh.setMute(false);
		}
	}
	
	public void windowDeactivated(WindowEvent e) 
	{  
		System.out.println("window deactivated");
		synchronized (renderTh){
			renderTh.pauseThread();
		}
		synchronized (layoutTh){
			layoutTh.pauseThread();
		}
		
		synchronized (musicTh){
			musicTh.setMute(true);
		} 
	}
	
	public void windowOpened(WindowEvent e) {}
	
	public void windowDeiconified(WindowEvent e) {}
	
	public void windowIconified(WindowEvent e) {}
	
	
	public void windowClosing(WindowEvent e)
	{  
		closeGame();
	}
	
	public void windowClosed(WindowEvent e) {}

	
	


} 
