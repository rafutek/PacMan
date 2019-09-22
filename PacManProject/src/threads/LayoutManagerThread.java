package threads;

import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import view.GameFrame;



public class LayoutManagerThread extends Thread {

	private volatile boolean running = false;
	private boolean paused = false;
	private final int SLEEP_TIME = 10;
	
	private GameFrame window;
	
	private Double gamePanelScale = null;
	private double gamePanelWeightX = 1; //initial weight (percentage) of game panel width in the window
	private double gamePanelWeightY;
	private double statusBarPanelWeightX ;
	private double statusBarPanelWeightY;
	private double leftPanelWeightX;
	private double rightPanelWeightX;
	
	
	public LayoutManagerThread( GameFrame window) 
	{
		setName("Layout Manager");
		this.window = window;
		setPanelsWeights(gamePanelWeightX);
		setAllPanelsLayout();
	}
	
	
	public void run() {
		System.out.println("Start thread "+getName());
		running = true;
				
		while(running) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {}
			
			if(gamePanelScale == null) {
				synchronized(window) {
					gamePanelScale = getPanelScale(window.getGamePanel());
				}
				System.out.println(gamePanelScale);
			}
			else {
				if(!paused) {
					adaptPanels();
					System.out.println(getPanelScale(window.getGamePanel()));
				}				
			}
		}
		System.out.println("Stop thread "+getName());
	}
	
	/**
	 * Start the thread 
	 */
	public void startLayoutManager()
	{ 
		if (!running) {
			this.start();
		}
	}
	
	
	// ------------- LayoutManager life cycle methods ------------
	// called by the JFrame's window listener methods


	public void resumeLayoutManager()
	// called when the JFrame is activated / deiconified
	{  paused = false;  } 


	public void pauseLayoutManager()
	// called when the JFrame is deactivated / iconified
	{ paused = true;   } 


	public void stopLayoutManager() 
	// called when the JFrame is closing
	{  running = false;   }

	// ----------------------------------------------
	
	/**
	 * Set all panels weights depending on the game panel x weight
	 * @param gamePanelWeightX
	 */
	public void setPanelsWeights(double gamePanelWeightX) {
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
	public void setPanelLayout(JPanel panel, int gridx, int gridy, int gridheight, double weightx, double weighty) {
		GridBagConstraints layoutConstraints = new GridBagConstraints();		
		layoutConstraints.gridx = gridx; //position in the grid
		layoutConstraints.gridy = gridy;
		layoutConstraints.gridheight = gridheight;
		layoutConstraints.fill = GridBagConstraints.BOTH; //resized horizontally and vertically
		layoutConstraints.weightx = weightx; //percent of x taken
		layoutConstraints.weighty = weighty;
		synchronized(window) {
			window.getGridbag().setConstraints(panel, layoutConstraints);
		}
	}
	
	private Double getPanelScale(JPanel panel) {
		Double scale = panel.getWidth()/(double)panel.getHeight();
		if(scale == null || scale.isNaN() || scale.isInfinite())
		{
			return null;
		}
		return scale;
	}
	
	public void setAllPanelsLayout() {
		synchronized(window) {
			setPanelLayout(window.getGamePanel(), 1, 0, 1, gamePanelWeightX, gamePanelWeightY);
			setPanelLayout(window.getStatusBarPanel(), 1, 1, 1, statusBarPanelWeightX, statusBarPanelWeightY);
			setPanelLayout(window.getLeftPanel(), 0, 0, 2, leftPanelWeightX, 1);
			setPanelLayout(window.getRightPanel(), 2, 0, 2, rightPanelWeightX, 1);			
		}
	}
	
	/**
	 * Adapt all the weights so that the game panel scale is the same 
	 */
	private void adaptPanels() {
		synchronized(window) {
	    	gamePanelWeightX = (gamePanelScale*(double)window.getHeight()*gamePanelWeightY)/(double)window.getWidth();
			setPanelsWeights(gamePanelWeightX);
			setAllPanelsLayout();			
		}
	}
}
