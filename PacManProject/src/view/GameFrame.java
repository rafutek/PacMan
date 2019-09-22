package view;


import javax.swing.*;

import threads.RenderThread;

import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;
	
	private int windowWidth = 450;   
	private int windowHeight = 550; 
	
	GridBagLayout gridbag;
	GamePanel gamePanel;
	StatusBarPanel statusBarPanel;
	JPanel leftPanel, rightPanel;
	
	private double gamePanelScale;
	private double gamePanelWeightX = 1;
	private double gamePanelWeightY;
	private double statusBarPanelWeightX ;
	private double statusBarPanelWeightY;
	private double leftPanelWeightX;
	private double rightPanelWeightX;
	
	private boolean fullScreen = false;
	private RenderThread renderTh;

	public GameFrame(int period)
	{ 
		super("PacMan");
		makeGUI(period);
		addWindowListener(this);

		pack();
		setResizable(true);
		setLocationRelativeTo(null); // center the window
		setVisible(true);
		setFocusable(true);
		requestFocus();    // the window has now has focus, so receives key events
		readyForTermination();		
		readyForFullScreen();
		readyForResize();
		
		gamePanelScale = getPanelScale(gamePanel);
	}  
	

	/**
	 * Create game and status bar panels, and the rendering thread
	 * @param period
	 */
	private void makeGUI(int period)
	{
		gridbag = new GridBagLayout();
		setLayout(gridbag);
		
		gamePanel = new GamePanel();
		statusBarPanel = new StatusBarPanel();
		
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.CYAN);
		
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.darkGray);	
		
		setPanelsWeights(gamePanelWeightX);
		setAllPanelsLayout();
		
		add(gamePanel);
		add(statusBarPanel);
		add(leftPanel);
		add(rightPanel);

		setPreferredSize(new Dimension(windowWidth, windowHeight)); // set window size
	
		renderTh = new RenderThread(period, gamePanel, statusBarPanel);
		
	} 
	
	
	
	/**
	 * Called when the window listener is added to the window.
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
			public void keyPressed(KeyEvent e)
			{ 
				int keyCode = e.getKeyCode();
				
				// listen for esc, q, end, ctrl-c on the canvas to
				// allow a convenient exit from the full screen configuration
				if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
						(keyCode == KeyEvent.VK_END) ||
						((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
					renderTh.stopGame();
				}
			}
		});
	} 
	
	
	public void readyForFullScreen() {
		
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
	
	public void readyForResize() {
		addComponentListener(new ComponentAdapter() {

		    @Override
		    public void componentResized(ComponentEvent e) {
		    	   	
		    	//adapt all the weights si that the game panel scale is the same 
		    	gamePanelWeightX = (gamePanelScale*getHeight()*gamePanelWeightY)/(double)getWidth();
				setPanelsWeights(gamePanelWeightX);
				setAllPanelsLayout();
		  
		    }
		});
	}
	
	/**
	 * Set all panels weights depending on the game panel x weight
	 * @param gamePanelWeightX
	 */
	private void setPanelsWeights(double gamePanelWeightX) {
		this.gamePanelWeightX = gamePanelWeightX;
		gamePanelWeightY = 0.9;
		statusBarPanelWeightX = gamePanelWeightX;
		statusBarPanelWeightY = 1 - gamePanelWeightY;
		leftPanelWeightX = 1 - gamePanelWeightX;
		rightPanelWeightX = leftPanelWeightX;
	}

	/**
	 * Set or change the panel layout with some parameters
	 * @param panel
	 * @param gridx
	 * @param gridy
	 * @param gridheight
	 * @param weightx
	 * @param weighty
	 */
	private void setPanelLayout(JPanel panel, int gridx, int gridy, int gridheight, double weightx, double weighty) {
		GridBagConstraints layoutConstraints = new GridBagConstraints();		
		layoutConstraints.gridx = gridx; //position in the grid
		layoutConstraints.gridy = gridy;
		layoutConstraints.gridheight = gridheight;
		layoutConstraints.fill = GridBagConstraints.BOTH; //resized horizontally and vertically
		layoutConstraints.weightx = weightx; //percent of x taken
		layoutConstraints.weighty = weighty;
		gridbag.setConstraints(panel, layoutConstraints);
	}
	
	private double getPanelScale(JPanel panel) {
		return panel.getWidth()/(double)panel.getHeight();
	}
	
	private void setAllPanelsLayout() {
		setPanelLayout(gamePanel, 1, 0, 1, gamePanelWeightX, gamePanelWeightY);
		setPanelLayout(statusBarPanel, 1, 1, 1, statusBarPanelWeightX, statusBarPanelWeightY);
		setPanelLayout(leftPanel, 0, 0, 2, leftPanelWeightX, 1);
		setPanelLayout(rightPanel, 2, 0, 2, rightPanelWeightX, 1);
	}

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
