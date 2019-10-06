package resources;

import java.awt.Dimension;
import java.awt.Graphics;
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
	private final int SPACE = 2; //px
	
	private static ArrayList<List<BufferedImage>> tilesImages = new ArrayList<List<BufferedImage>>(); //create list for x
	

	/**
	 * Constructor that reads the tiles image, fill the list of tiles extracting them.
	 * @throws IOException
	 */
	public Tiles() throws IOException {
		System.out.println("Extract tiles");
		tilesImg = ImageIO.read(new File(rsc.getImagePath("pacmanTilesSheet.png")));
		fillTilesList();
	}
	
	
	/**
	 * Extract the tiles from the tile image and put them in the list.
	 */
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
	
	/**
	 * Get the tile at a certain position of the tile image
	 * @param x the tile number in abscissa
	 * @param y the tile number in ordinate
	 * @return the corresponding tile image
	 */
	public BufferedImage getTileAt(int x, int y) {
		if(x >= NB_TILES_X) {
			System.out.println("The last tile in x is at x = "+(NB_TILES_X-1)+" !");
			return null;
		}
		if(y >= NB_TILES_Y) {
			System.out.println("The last tile in y is at y = "+(NB_TILES_Y-1)+" !");
			return null;
		}
	
		return tilesImages.get(x).get(y);
	}
	
	/**
	 * Get the tile number nb in the tile image (from 1 in the corner top left to the corner bottom right)
	 * @param nb the number of the tile
	 * @return the corresponding tile image
	 */
	public BufferedImage getTileNumber(int nb) {
		if(nb <= 0) {
			System.out.println("The first tile is number 1 !");
			return null;
		}
		else if(nb > NB_TILES_X * NB_TILES_Y) {
			System.out.println("You want the tile number "+nb
					+ " but there are only "+(NB_TILES_X * NB_TILES_Y)+" tiles !");
			return null;
		}
		
		int y = 0;
		while(nb > (y+1)*NB_TILES_X) {
			y++;
		}
		int x = nb -1 - y*NB_TILES_X; 
		
		return this.getTileAt(x, y);
		
	}
	
	public static BufferedImage joinToRight(BufferedImage img1, BufferedImage img2) {
		if(img1.getHeight() != img2.getHeight()) {
			System.out.println("The images to join side by side don't have the same height !");
			return null;
		}
		int width = img1.getWidth() + img2.getWidth();
		int height = img1.getHeight();
		BufferedImage newImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImage.createGraphics();
		g.drawImage(img1, 0, 0, null);
		g.drawImage(img2, img1.getWidth(), 0, null);
		g.dispose();
		return newImage;
	}	
	
	public static BufferedImage joinBelow(BufferedImage img1, BufferedImage img2) {
		if(img1.getWidth() != img2.getWidth()) {
			System.out.println("The images to join on top of each other don't have the same width !");
			return null;
		}
		int width = img1.getWidth();
		int height = img1.getHeight() + img2.getHeight();
		BufferedImage newImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImage.createGraphics();
		g.drawImage(img1, 0, 0, null);
		g.drawImage(img2, 0, img1.getHeight(), null);
		g.dispose();
		return newImage;
	}
	
	
	/**
	 * Display the image in a JFrame.
	 * @param img
	 */
	public static void displayImg(BufferedImage img) {
		if(img == null) {
			System.out.println("Cannot display a null image !");
			return;
		}
        JFrame frame=new JFrame();
        frame.setSize(img.getWidth(),img.getHeight());
        JLabel lbl=new JLabel();
        lbl.setIcon(new ImageIcon(img));
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	
	/**
	 * Return a copy of the parameter image.
	 * @param original
	 * @return
	 */
	public static BufferedImage copy(BufferedImage original) {
		if(original == null) {
			System.out.println("The image you want to copy is null !");
			return null;
		}
		
		BufferedImage copy = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics bGr = copy.createGraphics();
	    bGr.drawImage(original, 0, 0, null);
	    bGr.dispose();			
		return copy;
	}
	
	/**
	 * Cast an image into a buffered image.
	 * @param img
	 * @return the associated buffered image
	 */
	public static BufferedImage cast(Image img) {
		BufferedImage buffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics bGr = buffImg.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();	
	    
		return buffImg;
	}

	/**
	 * Resize the original image to its new dimension.
	 * @param original
	 * @param newDim
	 * @return the image resized
	 */
	public static BufferedImage resize(BufferedImage original, Dimension newDim) {
		
		if(original == null) {
			System.out.println("Cannot resize a null image !");
			return null;
		}
		
		Image img = original.getScaledInstance(newDim.width,newDim.height,Image.SCALE_SMOOTH);
		return cast(img);
	}
	
	/**
	 * Takes four images in parameter and return the combined image, with the dimension of one.
	 * The images must have the same dimension.
	 * @param cornerTopLeft
	 * @param cornerTopRight
	 * @param cornerBottomLeft
	 * @param cornerBottomRight
	 * @return the combined image
	 */
	public BufferedImage createFullSpriteImage(BufferedImage cornerTopLeft, BufferedImage cornerTopRight,
			BufferedImage cornerBottomLeft, BufferedImage cornerBottomRight) {
		
		BufferedImage imgTop = Tiles.joinToRight(cornerTopLeft, cornerTopRight);
		BufferedImage imgBottom = Tiles.joinToRight(cornerBottomLeft, cornerBottomRight);
		BufferedImage fullBigImg = Tiles.joinBelow(imgTop, imgBottom);
		return Tiles.resize(fullBigImg, new Dimension(cornerTopLeft.getWidth(), cornerTopLeft.getHeight()));
	}


	
	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException {
		Tiles tiles = new Tiles();
		Tiles.displayImg(tiles.getTileNumber(17));		
	}

}
