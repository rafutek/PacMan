package threads;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.Main;
import resources.Tiles;
import view.GameFrame;

public class CheckPageThread extends ThreadPerso{
	
	private GameFrame gameFrame;
	private Tiles tiles;
	private String lastPage;
	
	public CheckPageThread(GameFrame gameFrame) {
		super("CheckPageThread");
		this.gameFrame = gameFrame;
		try {
			tiles = new Tiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doThatAtStart(){}

	@Override
	protected void doThat() {
		gameFrame = Main.getGlobalFrame();
		if(gameFrame != null) {
			
			String currentPage = gameFrame.getPage();
			if(lastPage == null) {
				lastPage = currentPage;
			}
			
			if(lastPage != null && !lastPage.matches(currentPage)) {
				if(currentPage =="PrincipalMenu") {
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
					BufferedImage resume = Tiles.createWord(tiles.getTileNumber(56),tiles.getTileNumber(43),tiles.getTileNumber(57),tiles.getTileNumber(59),tiles.getTileNumber(51),tiles.getTileNumber(43));
					resume = Tiles.resize(resume, new Dimension(250,50));
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
					gameFrame.getPrincipalMenuPanel().setVisible(false);
					gameFrame.getControlsMenuPanel().setVisible(false);
					gameFrame.getAudioMenuPanel().setVisible(false);
					gameFrame.getHightScoresPanel().setVisible(false);
					gameFrame.getGamePanel().setVisible(true);
					gameFrame.getStatusBarPanel().setVisible(true);
					gameFrame.getLeftPanel().setVisible(true);
					gameFrame.getRightPanel().setVisible(true);
				}
				
				else if(currentPage == "Controls") {
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
				
				else if(currentPage == "Audio") {
					gameFrame.removeKeyListener(gameFrame.getPrincipalMenuPanel());
					gameFrame.renderTh.pauseThread();
					gameFrame.getPrincipalMenuPanel().setVisible(false);
					gameFrame.getControlsMenuPanel().setVisible(false);
					gameFrame.getHightScoresPanel().setVisible(false);
					System.out.println("......................... menu audio is Visible.................");
					gameFrame.getAudioMenuPanel().setVisible(true);
					System.out.println("......................... menu audio is added.................");
					gameFrame.addKeyListener(gameFrame.getAudioMenuPanel());
				}
				
				else if(currentPage == "HighScores") {
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
				}
				
				else if(currentPage == "NewHighScore") {
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
			lastPage = currentPage;
		}

	}

	@Override
	protected void doThatAtStop() {}

}
