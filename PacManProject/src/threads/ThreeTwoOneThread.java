package threads;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import resources.ListImages;
import resources.Tiles;

public class ThreeTwoOneThread extends TimerThread {
	
	// one loop is 1000 ms = 1 s
	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 100; 
	
	private Tiles tiles;
	private List<Integer> tilesNumbers = new ArrayList<Integer>();
	private ListImages animationImages;
	
	private JPanel gamePanel;
	Graphics dbg;
	private int posX;
	private int posY;
	private Dimension dim = new Dimension();
	
	private int draw_nb = 0;

	public ThreeTwoOneThread(Tiles tiles, JPanel gamePanel) {
		super(WAIT_TIME, NB_WAITS);
		setName("3,2,1");
		this.tiles = tiles;
		this.gamePanel = gamePanel;
		
		chooseAnimationTiles();
		setAnimationImagesArray();
		setDimension();
		setPosition();
	}

	@Override
	protected void doThatAtStart() {
		drawOrDie();
	}
	
	@Override
	protected void doThatWhileWaiting() {
		setDimension(); // if game panel is resized
		setPosition();
	}

	@Override
	protected void finallyDoThat() {
		drawOrDie();
	}
	
	private void chooseAnimationTiles() {
		tilesNumbers.add(32); // 3
		tilesNumbers.add(31); // 2
		tilesNumbers.add(30); // 1
	}
	
	private void setAnimationImagesArray() {
		animationImages = new ListImages(tiles, tilesNumbers);
	}
	
	
	private void setDimension() {
		dim.width = gamePanel.getWidth()/8;
		dim.height = dim.width;
	}

	private void setPosition() {
		
		posX = gamePanel.getWidth()/2 - dim.width/2;
		posY = gamePanel.getHeight()/2 - dim.height/2;
	}
	

	private boolean draw() {
		if(draw_nb < animationImages.getImagesList().size()) {
			
			BufferedImage img = animationImages.getImagesList().get(draw_nb);
						
			Graphics g;
			try {
				g = gamePanel.getGraphics();
				if ((g != null) && (img != null))
					g.drawImage(img,posX, posY, dim.width, dim.height, null);
				Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
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
			this.stopThread(); // stop the thread if all images have been drawn
		}
	}

}
