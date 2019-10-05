package threads;

import main.Main;
import view.GameFrame;

public class CheckPageThread extends ThreadPerso{
	private static int DEFAULT_FPS = 50;
	int fps = DEFAULT_FPS;
	int period = (int) 1000.0/fps;
	GameFrame gameFrame;
	RenderThread renderThread;
	LayoutManagerThread layoutManagerThread;
	
	public CheckPageThread(String threadName) {
		super(threadName);
		setName("CheckPageThread");
		
		gameFrame = Main.getGlobalFrame();
		if(gameFrame.getPage()=="PrincipalMenu") {
			gameFrame.renderTh.pauseThread();
			gameFrame.getGamePanel().setVisible(false);
			gameFrame.getStatusBarPanel().setVisible(false);
			gameFrame.getLeftPanel().setVisible(false);
			gameFrame.getRightPanel().setVisible(false);
			System.out.println("......................... menu is Visible.................");
			gameFrame.getPrincipalMenuPanel().setVisible(true);
			System.out.println("......................... menu is added.................");
			gameFrame.addKeyListener(gameFrame.getPrincipalMenuPanel());

		}else if(gameFrame.getPage()=="Game"){
			gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
			//gameFrame.readyForArrowsEvents();
			renderThread = new RenderThread(period,gameFrame.getGamePanel() , gameFrame.getStatusBarPanel());
			renderThread.startThread();
			layoutManagerThread = new LayoutManagerThread(gameFrame);
			layoutManagerThread.startThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			//gameFrame.add(gameFrame.getGamePanel());
			gameFrame.getGamePanel().setVisible(true);
			//gameFrame.add(gameFrame.getStatusBarPanel());
			gameFrame.getStatusBarPanel().setVisible(true);
			//gameFrame.add(gameFrame.getLeftPanel());
			gameFrame.getLeftPanel().setVisible(true);
			//gameFrame.add(gameFrame.getRightPanel());
			gameFrame.getRightPanel().setVisible(true);
		}
	}

	@Override
	protected void doThatAtStart()  {
	}

	@Override
	protected void doThat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doThatAtStop() {
		// TODO Auto-generated method stub
		
	}

}
