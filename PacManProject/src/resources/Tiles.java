package resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
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
	
	private ArrayList<List<BufferedImage>> tilesImages = new ArrayList<List<BufferedImage>>(); //create list for x
	
	private BufferedImage originalMazeImg = null;
	private BufferedImage copyMazeImg;
	/**
	 * Constructor that reads the tiles image, fill the list of tiles extracting them,
	 * and create the maze from the maze.txt file containing the number and position of tiles.
	 * @throws IOException
	 */
	public Tiles() throws IOException {
		tilesImg = ImageIO.read(new File(rsc.getImagePath("pacmanTilesSheet.png")));
		fillTilesList();
		createMazeFromText("maze.txt");
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
		
		return getTileAt(x, y);
		
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
	
	
	private BufferedImage joinToRight(BufferedImage img1, BufferedImage img2) {
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
	
	private BufferedImage joinBelow(BufferedImage img1, BufferedImage img2) {
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
	 * Create the maze from a text file, associating each number of the file to a certain tile, 
	 * and combining the tiles to create the final map.
	 * @param mazeFilename
	 * @throws IOException
	 */
	private void createMazeFromText(String mazeFilename) throws IOException {
		BufferedImage mazeLineImg = null;
		@SuppressWarnings("resource")
		FileReader fr = new FileReader(rsc.getMazePath(mazeFilename));
		int i = 0; 
		int weight = 0;
		int number = 0;
		boolean first_nb_read = false;
		
		while ((i=fr.read()) != -1) { //end of file

			if(i >= 48 && i <= 57) { // integer
				if(!first_nb_read) {
					first_nb_read = true;
				}
				i -= 48;
				number = number*weight + i;
				weight += 10;
			}
			else {
				weight = 0;
				if(first_nb_read) {
					if(number == 0) {
						number = NB_TILES_X*NB_TILES_Y; //there is no tile with number 0, so black tile instead
					}
					if(mazeLineImg == null) {
						mazeLineImg = getTileNumber(number); // first tile
					}
					else {
						mazeLineImg = joinToRight(mazeLineImg, getTileNumber(number)); // create a line of the maze
					}
				}
				if(i == 10) { //end of line
					if(mazeLineImg != null && originalMazeImg == null) {
						originalMazeImg = mazeLineImg;
					}
					else if(mazeLineImg != null && originalMazeImg != null){
						originalMazeImg = joinBelow(originalMazeImg, mazeLineImg);
					}
					if(mazeLineImg != null) {
						mazeLineImg.flush(); //release memory allocated by a line of images of the maze
					}
					mazeLineImg = null;
				}
			}
		}
		copyMazeImg = copy(originalMazeImg);	
	}
	
	
	/**
	 * Return a copy of the parameter image.
	 * @param original
	 * @return
	 */
	private BufferedImage copy(BufferedImage original) {
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
	 * Get the maze image, created or not.
	 * @return the maze image
	 */
	public BufferedImage getMazeImg() {
		return copy(originalMazeImg);
	}
	
	/**
	 * Resize the maze image, if not null, with dimension parameters
	 * @param width
	 * @param height
	 */
	public void resizeMazeImg(int width, int height) {
		if(originalMazeImg != null) {
			Image img = originalMazeImg.getScaledInstance(width,height,Image.SCALE_SMOOTH);
			copyMazeImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		    Graphics bGr = copyMazeImg.createGraphics();
		    bGr.drawImage(img, 0, 0, null);
		    bGr.dispose();			
		}
	}
	
	public void draw(Graphics g) {
		if(g != null && copyMazeImg != null) {
			g.drawImage(copyMazeImg,0,0, null);
		}
	}

	
	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException {
		Tiles tiles = new Tiles();
//		BufferedImage img1 = tiles.getTileNumber(1);
//		BufferedImage img2 = tiles.getTileNumber(17);
//		tiles.displayImg(tiles.joinToRight(img1, img2));
//		tiles.displayImg(tiles.joinBelow(img1, img2));
		tiles.resizeMazeImg(1000, 600);
		tiles.displayImg(tiles.getMazeImg());
		
	}

}
