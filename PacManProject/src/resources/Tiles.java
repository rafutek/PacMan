package resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Tiles {
	
	private Resources rsc = new Resources();
	private BufferedImage tilesImg;
	
	public static final int NB_TILES_X = 16;
	public static final int NB_TILES_Y = 22;
	private final int SPACE = 1; //px
	
	private ArrayList<List<BufferedImage>> tilesImages = new ArrayList<List<BufferedImage>>(); //create list for x
	
	
	public Tiles() throws IOException {
		tilesImg = ImageIO.read(new File(rsc.getImagePath("pacmanTilesSheet.png")));
		fillTilesList();
	}
	
	public BufferedImage getTilesImage() {
		return tilesImg;
	}
	
	private void fillTilesList() {
		
		int tileWidth = tilesImg.getWidth()/NB_TILES_X;
		int tileHeight = tilesImg.getHeight()/NB_TILES_Y;
		int posX, posY;
		
		for (int x = 0; x < NB_TILES_X; x++) {
			
			posX = x * tileWidth;
			tilesImages.add(new ArrayList<BufferedImage>()); // create list for y
		
			for (int y = 0; y < NB_TILES_Y; y++) {
				posY = y * tileHeight;
				//extract the appropriate tile with some internal borders
				BufferedImage tile = tilesImg.getSubimage(posX+SPACE, posY+SPACE, tileWidth-SPACE, tileHeight-SPACE);
				tilesImages.get(x).add(tile); // add the tile to the list
			}
		}
	}
	
	public BufferedImage getTileAt(int x, int y) {
		if(x >= NB_TILES_X) {
			System.out.println("There is only "+(NB_TILES_X-1)+" tiles in x !");
			return null;
		}
		if(y >= NB_TILES_Y) {
			System.out.println("There is only "+(NB_TILES_Y-1)+" tiles in y !");
			return null;
		}
	
		return tilesImages.get(x).get(y);
	}
	
	private void displayImg(BufferedImage img) {
        JFrame frame=new JFrame();
        frame.setSize(img.getWidth(),img.getHeight());
        JLabel lbl=new JLabel();
        lbl.setIcon(new ImageIcon(img));
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) throws IOException {
		Tiles tiles = new Tiles();
		tiles.displayImg(tiles.getTileAt(15, 20));
	}

}
