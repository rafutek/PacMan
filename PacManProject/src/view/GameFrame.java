package view; 

import javax.swing.*;

import threads.CheckPageThread;
import threads.LayoutManagerThread;
import threads.MusicThread;
import threads.RenderThread;
import threads.SoundThread;

import java.awt.*; 
import java.awt.event.*;
import java.io.IOException;


public class GameFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;
	
	private final int period;
	
	private int windowWidth = 620;   
	private int windowHeight = 700; 
	
	private  GridBagLayout gridbag;
	private  GamePanel gamePanel;
	private  PrincipalMenuPanel principalMenuPanel;
	private  ControlsMenuPanel controlsMenuPanel;
	private  AudioMenuPanel audioMenuPanel;
	private  HightScoresPanel hightScoresPanel;
	private  NewHighScorePanel newHighScorePanel;
	private StatusBarPanel statusBarPanel;
	private JPanel leftPanel, rightPanel;
	private JLabel statut;
	
	private String page = "PrincipalMenu";
	
	private boolean fullScreen = false;
	public RenderThread renderTh;
	private LayoutManagerThread layoutTh;
	
	private int statutMenu = 0;
	
	/**
	 * sound and music management
	 */
	private SoundThread soundTh;
	private MusicThread musicTh;
	private boolean gamePaused = false;
	private boolean gameMute = false;
	private CheckPageThread checkPageTh;

	public GameFrame(int period)
	{ 
		super("PacMan");
		this.period = period;
		this.setBackground(Color.BLACK);
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
		

	/**
	 * Create game and status bar panels, and the rendering thread
	 * @param period
	 */
	public void makeGUI()
	{
		gridbag = new GridBagLayout();
		setLayout(gridbag);
		musicTh = new MusicThread();
		soundTh = new SoundThread();
		musicTh.startThread();
		soundTh.startThread();
		principalMenuPanel = new PrincipalMenuPanel();
		controlsMenuPanel = new ControlsMenuPanel();
		audioMenuPanel = new AudioMenuPanel(renderTh , musicTh ,soundTh);
		try {
			hightScoresPanel = new HightScoresPanel();
			newHighScorePanel = new NewHighScorePanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gamePanel = new GamePanel();
		statusBarPanel = new StatusBarPanel();	
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.BLACK);
		
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLACK);	
		

		setPage("Principal Menu");
		principalMenuPanel.setVisible(true);
		System.out.println("......................... principal menu is Visible.................");
		add(principalMenuPanel);
		addKeyListener(principalMenuPanel);

		add(controlsMenuPanel);
		controlsMenuPanel.setVisible(false);
		add(audioMenuPanel);
		audioMenuPanel.setVisible(false);
		add(hightScoresPanel);
		hightScoresPanel.setVisible(false);
		add(newHighScorePanel);
		newHighScorePanel.setVisible(false);
		add(gamePanel);
		gamePanel.setVisible(false);
		add(statusBarPanel);
		statusBarPanel.setVisible(false);
		add(leftPanel);
		leftPanel.setVisible(false);
		add(rightPanel);
		rightPanel.setVisible(false);
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
		renderTh = new RenderThread(period, gamePanel, statusBarPanel, musicTh, soundTh);
		layoutTh.startThread();
		renderTh.startThread();
		
		checkPageTh = new CheckPageThread(this);
		checkPageTh.startThread();
	}
	
	/**
	 * @return the soundTh
	 */
	public SoundThread getSoundTh() {
		return soundTh;
	}


	/**
	 * @param soundTh the soundTh to set
	 */
	public void setSoundTh(SoundThread soundTh) {
		this.soundTh = soundTh;
	}


	/**
	 * @return the musicTh
	 */
	public MusicThread getMusicTh() {
		return musicTh;
	}


	/**
	 * @param musicTh the musicTh to set
	 */
	public void setMusicTh(MusicThread musicTh) {
		this.musicTh = musicTh;
	}


	private void readyForTermination()
	{
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();

				// listen for esc, q, end, ctrl-c on the canvas to
				// allow a convenient exit from the full screen configuration
				if ((keyCode == KeyEvent.VK_Q) ||
						(keyCode == KeyEvent.VK_END) ||
						((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
					closeGame();
				}

				if(keyCode == KeyEvent.VK_ESCAPE) {
					page="PrincipalMenu";
					statutMenu = 1;
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
	
	public void readyForArrowsEvents() {
		addKeyListener( new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{ 
				if(page=="Game") {
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
						statutMenu = 1;
						System.out.println("Game paused");
						gamePaused = true;
						synchronized (renderTh) {
							renderTh.pauseThread();
						}
						setAllSoundsMute(true);
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
						setAllSoundsMute(false);
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
						setAllSoundsMute(true);
						
					}
					else if(gameMute) {
						System.out.println("Sound on");
						gameMute = false;
						setAllSoundsMute(false);
					}
				} 
			}
		});
	}

	public void closeGame() {
		
		checkPageTh.stopThread();
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
		if(page=="Game") {
		synchronized (renderTh){
			renderTh.resumeThread();
		}
		synchronized (layoutTh){
			layoutTh.resumeThread();
		}
		synchronized (checkPageTh){
			checkPageTh.resumeThread();
		}
		setAllSoundsMute(false);
		}
	}
	
	public void windowDeactivated(WindowEvent e) 
	{  
		System.out.println("window deactivated");
		if(page=="Game") {
		synchronized (renderTh){
			renderTh.pauseThread();
		}
		synchronized (layoutTh){
			layoutTh.pauseThread();
		}
		synchronized (checkPageTh){
			checkPageTh.pauseThread();
		}
		setAllSoundsMute(true);
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
	
	public int getStatutMenu() {
		return statutMenu;
	}


	public void setStatutMenu(int statutMenu) {
		this.statutMenu = statutMenu;
	}
	

	
	public void setAllSoundsMute(boolean b) {
		synchronized(musicTh) {
			musicTh.setMute(b);
		}
		synchronized(soundTh) {
			soundTh.setMute(b);
		}
	}


	// ------------- getters and setters for frame elements ------------
	
	public synchronized GridBagLayout getGridbag() {
		return gridbag;
	}


	public synchronized  GamePanel getGamePanel() {
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
	
	public synchronized PrincipalMenuPanel getPrincipalMenuPanel() {
		return principalMenuPanel;
	}


	public synchronized ControlsMenuPanel getControlsMenuPanel() {
		return controlsMenuPanel;
	}


	public synchronized void setControlsMenuPanel(ControlsMenuPanel controlsMenuPanel) {
		this.controlsMenuPanel = controlsMenuPanel;
	}
	
	public synchronized HightScoresPanel getHightScoresPanel() {
		return hightScoresPanel;
	}


	public synchronized void setHightScoresPanel(HightScoresPanel hightScoresPanel) {
		this.hightScoresPanel = hightScoresPanel;
	}


	public synchronized NewHighScorePanel getNewHighScorePanel() {
		return newHighScorePanel;
	}


	public synchronized void setNewHighScorePanel(NewHighScorePanel newHighScorePanel) {
		this.newHighScorePanel = newHighScorePanel;
	}


	public synchronized AudioMenuPanel getAudioMenuPanel() {
		return audioMenuPanel;
	}


	public synchronized void setAudioMenuPanel(AudioMenuPanel audioMenuPanel) {
		this.audioMenuPanel = audioMenuPanel;
	}


	public synchronized void setPrincipalMenuPanel(PrincipalMenuPanel principalMenuPanel) {
		this.principalMenuPanel = principalMenuPanel;
	}
	

	public synchronized String getPage() {
		return page;
	}


	public synchronized void setPage(String page) {
		this.page = page;
	}

	


} 

