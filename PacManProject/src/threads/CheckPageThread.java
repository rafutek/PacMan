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
			gameFrame.removeKeyListener(gameFrame.getHightScoresPanel());
			gameFrame.removeKeyListener(gameFrame.getHightScoresPanel());
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
			gameFrame.removeKeyListener(gameFrame.getHightScoresPanel());
			//gameFrame.readyForArrowsEvents();
			renderThread = new RenderThread(period,gameFrame.getGamePanel() , gameFrame.getStatusBarPanel(), gameFrame.getMusicTh(), gameFrame.getSoundTh());
			renderThread.startThread();
			layoutManagerThread = new LayoutManagerThread(gameFrame);
			layoutManagerThread.startThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			gameFrame.getControlsMenuPanel().setVisible(false);
			gameFrame.getAudioMenuPanel().setVisible(false);
			gameFrame.getHightScoresPanel().setVisible(false);
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
			gameFrame.getHightScoresPanel().setVisible(false);
			gameFrame.getAudioMenuPanel().setVisible(false);
			System.out.println("......................... menu controls is Visible.................");
			gameFrame.getControlsMenuPanel().setVisible(true);
			System.out.println("......................... menu controls is added.................");
			gameFrame.addKeyListener(gameFrame.getControlsMenuPanel());
		}
		else if(gameFrame.getPage()=="Audio") {
			gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
			gameFrame.renderTh.pauseThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			gameFrame.getControlsMenuPanel().setVisible(false);
			gameFrame.getHightScoresPanel().setVisible(false);
			System.out.println("......................... menu audio is Visible.................");
			gameFrame.getAudioMenuPanel().setVisible(true);
			System.out.println("......................... menu audio is added.................");
			gameFrame.addKeyListener(gameFrame.getAudioMenuPanel());
		}else if(gameFrame.getPage()=="HighScores") {
			gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
			gameFrame.removeKeyListener(gameFrame.getAudioMenuPanel());
			gameFrame.removeKeyListener(gameFrame.getControlsMenuPanel());
			gameFrame.removeKeyListener(gameFrame.getNewHighScorePanel());
			gameFrame.renderTh.pauseThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			gameFrame.getControlsMenuPanel().setVisible(false);
			gameFrame.getAudioMenuPanel().setVisible(false);
			System.out.println("......................... menu HighScores is Visible.................");
			gameFrame.getHightScoresPanel().setVisible(true);
			System.out.println("......................... menu HighScores is added.................");
			gameFrame.addKeyListener(gameFrame.getHightScoresPanel());
		}else if(gameFrame.getPage()=="NewHighScore") {
			gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
			gameFrame.renderTh.pauseThread();
			gameFrame.getPrincipalMenuPanel().setVisible(false);
			gameFrame.getControlsMenuPanel().setVisible(false);
			gameFrame.getAudioMenuPanel().setVisible(false);
			gameFrame.getHightScoresPanel().setVisible(false);
			gameFrame.getGamePanel().setVisible(false);
			gameFrame.getStatusBarPanel().setVisible(false);
			gameFrame.getLeftPanel().setVisible(false);
			gameFrame.getRightPanel().setVisible(false);
			System.out.println("......................... menu NewHighScore is Visible.................");
			gameFrame.getNewHighScorePanel().setVisible(true);
			System.out.println("......................... menu NewHighScore is added.................");
			gameFrame.addKeyListener(gameFrame.getNewHighScorePanel());
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
