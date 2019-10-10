package threads;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import resources.Tiles;


public class GameOverThread extends TimerThread {
	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 100; 
	
	private Tiles tiles;
	
	private JPanel gamePanel;
	Graphics dbg;
		
	private int draw_nb = 0;

	public GameOverThread(Tiles tiles, JPanel gamePanel) {
		super(WAIT_TIME, NB_WAITS);
		setName("gameOver");
		this.tiles = tiles;
		this.gamePanel = gamePanel;	
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
	

	public BufferedImage chargerPanelGameOver() {
		
		BufferedImage g = tiles.getTileNumber(45);
		BufferedImage ga = Tiles.joinToRight(g , tiles.getTileNumber(39));
		BufferedImage gam = Tiles.joinToRight(ga , tiles.getTileNumber(51));
		BufferedImage game= Tiles.joinToRight(gam , tiles.getTileNumber(43));
		BufferedImage game_ = Tiles.joinToRight(game ,tiles.getTileNumber(352));
		BufferedImage game_o = Tiles.joinToRight(game_ ,tiles.getTileNumber(53));
		BufferedImage game_ov = Tiles.joinToRight(game_o ,tiles.getTileNumber(60));
		BufferedImage game_ove = Tiles.joinToRight(game_ov ,tiles.getTileNumber(43));
		BufferedImage gameOver = Tiles.joinToRight(game_ove ,tiles.getTileNumber(56));
				
		return gameOver;
	}


	

	private boolean draw() {
		if(draw_nb < 3) {
			BufferedImage img = chargerPanelGameOver();			
			Graphics g;
			try {
				g = gamePanel.getGraphics();
				if ((g != null) && (chargerPanelGameOver() != null))
					g.drawImage(img,150, 250, 250, 50, null);
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
