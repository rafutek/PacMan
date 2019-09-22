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
	
	private boolean fullScreen = false;
	private RenderThread renderTh;

	public GameFrame(int period)
	{ 
		super("PacMan");
		makeGUI(period);
		addWindowListener( this );

		pack();
		setResizable(true);
		setLocationRelativeTo(null); // center the window
		setVisible(true);
		setFocusable(true);
		requestFocus();    // the window has now has focus, so receives key events
		readyForTermination();		
		readyForFullScreen();
		readyForResize();
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
		setPanelLayout(gamePanel, 1, 0, 1, 0.9, 0.9);
		add(gamePanel);
		
		statusBarPanel = new StatusBarPanel();
		setPanelLayout(statusBarPanel, 1, 1, 1, 0.9, 0.1);
		add(statusBarPanel);
		
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.CYAN);
		setPanelLayout(leftPanel, 0, 0, 2, 0.1, 1);
		add(leftPanel);
		
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.darkGray);		
		setPanelLayout(rightPanel, 2, 0, 2, 0.1, 1);
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
	}  // end of readyForTermination()
	
	
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
		    	GraphicsConfiguration gc = getGraphicsConfiguration( );
		    	Rectangle screenRect = gc.getBounds( ); // screen dimensions
		    	Toolkit tk = Toolkit.getDefaultToolkit( );
		    	Insets desktopInsets = tk.getScreenInsets(gc);
		    	Insets frameInsets = getInsets( );// only works after pack( )
		    	
		    	System.out.println("\n"+screenRect.width+" "+screenRect.height);
		    	System.out.println(gamePanel.getWidth()+" "+(gamePanel.getHeight()+desktopInsets.top+desktopInsets.bottom+frameInsets.top+frameInsets.bottom+50));
		    	
		    	if(gamePanel.getWidth() > 900) {
		    		System.out.println("ok");

		    		setPanelLayout(gamePanel, 1, 0, 1, 0.5, 0.9);
		    		setPanelLayout(statusBarPanel, 1, 1, 1, 0.5, 0.1);
		    		setPanelLayout(leftPanel, 0, 0, 2, 0.5, 1);
		    		setPanelLayout(rightPanel, 2, 0, 2, 0.5, 1);
					
		    	}
		    }
		});
	}
	


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
