package threads;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.Main;
import resources.Tiles;
import view.ControlsMenuPanel;
import view.GameFrame;

public class CheckPageThread extends ThreadPerso{
	private static int DEFAULT_FPS = 50;
	int fps = DEFAULT_FPS;
	int period = (int) 1000.0/fps;
	GameFrame gameFrame;
	RenderThread renderThread;
	LayoutManagerThread layoutManagerThread;
	Tiles t;
	
	public CheckPageThread(String threadName) {
		super(threadName);
		setName("CheckPageThread");
		try {
			t = new Tiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameFrame = Main.getGlobalFrame();
		if(gameFrame.getPage()=="PrincipalMenu") {
			System.out.println("..................................."+gameFrame.getStatutMenu());
			gameFrame.renderTh.pauseThread();
			gameFrame.removeKeyListener(gameFrame.getControlsMenuPanel());
			gameFrame.removeKeyListener(gameFrame.getAudioMenuPanel());
			gameFrame.getGamePanel().setVisible(false);
			gameFrame.getStatusBarPanel().setVisible(false);
			gameFrame.getLeftPanel().setVisible(false);
			gameFrame.getRightPanel().setVisible(false);
			System.out.println("......................... menu is Visible.................");
			if(gameFrame.getStatutMenu()==1) {
			JLabel label = gameFrame.getPrincipalMenuPanel().getStartGame();
			BufferedImage resume = t.createWord(t.getTileNumber(56),t.getTileNumber(43),t.getTileNumber(57),t.getTileNumber(59),t.getTileNumber(51),t.getTileNumber(43));
			resume = t.resize(resume, new Dimension(250,50));
			label.setIcon(new ImageIcon(resume));
			gameFrame.getPrincipalMenuPanel().setStartGame(label);
			}
			gameFrame.getPrincipalMenuPanel().setVisible(true);
			System.out.println("......................... menu is added.................");
			gameFrame.addKeyListener(gameFrame.getPrincipalMenuPanel());

		}else if(gameFrame.getPage()=="Game"){
			gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
			gameFrame.removeKeyListener(gameFrame.getControlsMenuPanel());
			gameFrame.removeKeyListener(gameFrame.getAudioMenuPanel());
			//gameFrame.readyForArrowsEvents();
			renderThread = new RenderThread(period,gameFrame.getGamePanel() , gameFrame.getStatusBarPanel());
			renderThread.startThread();
			layoutManagerThread = new LayoutManagerThread(gameFrame);
			layoutManagerThread.startThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			gameFrame.getControlsMenuPanel().setVisible(false);
			gameFrame.getAudioMenuPanel().setVisible(false);
			//gameFrame.add(gameFrame.getGamePanel());
			gameFrame.getGamePanel().setVisible(true);
			//gameFrame.add(gameFrame.getStatusBarPanel());
			gameFrame.getStatusBarPanel().setVisible(true);
			//gameFrame.add(gameFrame.getLeftPanel());
			gameFrame.getLeftPanel().setVisible(true);
			//gameFrame.add(gameFrame.getRightPanel());
			gameFrame.getRightPanel().setVisible(true);
		}else if(gameFrame.getPage()=="Controls") {
			gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
			gameFrame.renderTh.pauseThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			System.out.println("......................... menu controls is Visible.................");
			gameFrame.getControlsMenuPanel().setVisible(true);
			System.out.println("......................... menu controls is added.................");
			gameFrame.addKeyListener(gameFrame.getControlsMenuPanel());
		}
		else if(gameFrame.getPage()=="Audio") {
			gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
			gameFrame.renderTh.pauseThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			System.out.println("......................... menu controls is Visible.................");
			gameFrame.getAudioMenuPanel().setVisible(true);
			System.out.println("......................... menu controls is added.................");
			gameFrame.addKeyListener(gameFrame.getAudioMenuPanel());
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