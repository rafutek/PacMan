package threads;

import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import view.GameFrame;



public class LayoutManagerThread extends ThreadPerso {

	
	private GameFrame window;
	
	private Double gamePanelScale = null;
	private Double menuPanelScale = null;
	private double gamePanelWeightX = 1; //initial weight (percentage) of game panel width in the window
	private double gamePanelWeightY;
	private double menuPanelWeightX = 1; //initial weight (percentage) of game panel width in the window
	private double menuPanelWeightY =1;
	private double statusBarPanelWeightX ;
	private double statusBarPanelWeightY;
	private double leftPanelWeightX;
	private double rightPanelWeightX;
	
	private static int DEFAULT_FPS = 50;
	int fps = DEFAULT_FPS;
	int period = (int) 1000.0/fps;
	GameFrame gameFrame;

	
	
	public LayoutManagerThread( GameFrame window) 
	{
		super("Layout Manager");
		
		this.window = window;
		setPanelsWeights(gamePanelWeightX);
		setAllPanelsLayout();
		//gameFrame = new GameFrame(period);
	}
	

	@Override
	protected void doThatAtStart() {
		// nothing to do at start
	}


	@Override
	protected void doThat() {
		if(gamePanelScale == null) {
			synchronized(window) {
				gamePanelScale = getPanelScale(window.getGamePanel()); // get the original panel scale
				menuPanelScale = getPanelScale(window.getPrincipalMenuPanel()); // get the original panel scale
			}
		}else {
			adaptPanels(); // adapt the panels in the window to maintain the game panel scale
		}
		
	}


	@Override
	protected void doThatAtStop() {
		// nothing to do at stop
	}

	
	/**
	 * Set all panels weights depending on the game panel x weight
	 * @param gamePanelWeightX
	 */
	public void setPanelsWeights(double gamePanelWeightX) {
		this.gamePanelWeightX = gamePanelWeightX;
		gamePanelWeightY = 0.9;
		statusBarPanelWeightX = gamePanelWeightX;
		statusBarPanelWeightY = 1 - gamePanelWeightY;
		leftPanelWeightX = (1 - gamePanelWeightX)/2.;
		rightPanelWeightX = leftPanelWeightX;
		menuPanelWeightX = 1;
		menuPanelWeightY = 1;
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
		if(scale == null || scale.isNaN() || scale.isInfinite()){
			return null;
		}
		return scale;
	}
	
	public void setAllPanelsLayout() {
		synchronized(window) {
			System.out.println("set panels layout");
			setPanelLayout(window.getGamePanel(), 1, 0, 1, gamePanelWeightX, gamePanelWeightY);
			setPanelLayout(window.getStatusBarPanel(), 1, 1, 1, statusBarPanelWeightX, statusBarPanelWeightY);
			setPanelLayout(window.getLeftPanel(), 0, 0, 2, leftPanelWeightX, 1);
			setPanelLayout(window.getRightPanel(), 2, 0, 2, rightPanelWeightX, 1);		
			setPanelLayout(window.getPrincipalMenuPanel(), 1, 0, 2, menuPanelWeightX, menuPanelWeightY);
		}
	}
	
	/**
	 * Adapt all the weights so that the game panel scale is the same 
	 */
	public void adaptPanels() {
		synchronized(window) {
			
			double gamePanelCurrentScale = getPanelScale(window.getGamePanel());
			double menuPanelCurrentScale = getPanelScale(window.getPrincipalMenuPanel());
			
			if( gamePanelCurrentScale < gamePanelScale - 0.1 || gamePanelCurrentScale > gamePanelScale + 0.1) {
				
				System.out.println("wanted scale: "+gamePanelScale+" current scale: "+gamePanelCurrentScale);
		    	gamePanelWeightX = (gamePanelScale*(double)window.getHeight()*gamePanelWeightY)/(double)window.getWidth();
				setPanelsWeights(gamePanelWeightX);
				setAllPanelsLayout();					
			}
			if( menuPanelCurrentScale < menuPanelScale - 0.1 || menuPanelCurrentScale > menuPanelScale + 0.1) {
				
				System.out.println("wanted scale: "+menuPanelScale+" current scale: "+menuPanelCurrentScale);
		    	menuPanelWeightX = (menuPanelScale*(double)window.getHeight()*menuPanelWeightY)/(double)window.getWidth();
				setPanelsWeights(menuPanelWeightX);
				setAllPanelsLayout();					
			}
		
		}
	}


}
