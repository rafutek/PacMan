package threads;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import resources.Tiles;

public class ScoreInvicibiltyGhostThread extends TimerThread{
	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 100; 
	private int posX=0;
	private int posY=0;
	
	private Tiles tiles;
	
	private JPanel gamePanel;
	Graphics dbg;
	
	private Dimension dim = new Dimension();
	
	private int draw_nb = 0;

	public ScoreInvicibiltyGhostThread(Tiles tiles, JPanel gamePanel) {
		super(WAIT_TIME, NB_WAITS);
		setName("ScoreInvicibiltyGhostThread");
		this.tiles = tiles;
		this.gamePanel = gamePanel;	
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	@Override
	protected void doThatAtStart() {
		drawOrDie();
	}
	
	@Override
	protected void doThatWhileWaiting() {}

	@Override
	protected void finallyDoThat() {
		drawOrDie();
	}
	

	public BufferedImage picture200() {
		
		BufferedImage P200 = tiles.createFullSpriteImage(tiles.getTileNumber(65), tiles.getTileNumber(66), tiles.getTileNumber(81), tiles.getTileNumber(82));
		return P200;

	}
	public BufferedImage picture400() {
		
		BufferedImage P400 = tiles.createFullSpriteImage(tiles.getTileNumber(67), tiles.getTileNumber(68), tiles.getTileNumber(83), tiles.getTileNumber(84));
		return P400;

	}
	public BufferedImage picture800() {
		
		BufferedImage P800 = tiles.createFullSpriteImage(tiles.getTileNumber(69), tiles.getTileNumber(70), tiles.getTileNumber(85), tiles.getTileNumber(86));
		return P800;
	}
	public BufferedImage picture1600() {
	
		BufferedImage P1600 = tiles.createFullSpriteImage(tiles.getTileNumber(71), tiles.getTileNumber(72), tiles.getTileNumber(87), tiles.getTileNumber(88));
		return P1600;

	}


	

	private boolean draw() {
		if(draw_nb < 1) {
			BufferedImage pic200 = picture200();
			BufferedImage pic400 = picture400();
			BufferedImage pic800 = picture800();
			BufferedImage pic1600 = picture1600();
			Graphics g;
			try {
				g = gamePanel.getGraphics();
				if ((g != null) ) {
					if(pic200!=null && PhysicsThread.getScoreInvGhost()==200) {
						g.drawImage(pic200,posX, posY, 50, 30, null);
					}
					else if(pic400!=null && PhysicsThread.getScoreInvGhost()==400) {
						g.drawImage(pic400,posX, posY, 50, 30, null);
					}
					else if(pic800!=null && PhysicsThread.getScoreInvGhost()==800) {
						g.drawImage(pic800,posX, posY,50, 30, null);
					}
					else if(pic1600!=null && PhysicsThread.getScoreInvGhost()==1600) {
						g.drawImage(pic1600,posX, posY, 50, 30, null);
					}
				}
					
				Toolkit.getDefaultToolkit().sync();  
				g.dispose();
			}
			catch (Exception e)
			{ System.out.println("Graphics error: " + e);  }
			
			draw_nb++;
			return true;
		}
		else {
			draw_nb = 0;
			return false;
		}
	}
	

	
	private void drawOrDie() {
		if(!draw()) {
			this.stopThread();
		}
	}
	
	

}
