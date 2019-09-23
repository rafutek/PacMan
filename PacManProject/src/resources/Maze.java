package resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
	
	private Resources rsc = new Resources();
	private Tiles tiles;
	private BufferedImage originalMazeImg = null;
	private BufferedImage copyMazeImg;
	
	/**
	 * Constructor that creates the maze image and sprites thanks to a text file.
	 * @throws IOException
	 */
	public Maze() throws IOException {
		tiles = new Tiles();
		createMazeFromText("maze.txt");
	}
	
	
	public Tiles getTiles() {
		return tiles;
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
					if(number == 0 || number == 15) {
						// 0: no tile with that number
						// 15: energizer
						
						
						
						
						
						// -> black tile instead
						number = Tiles.NB_TILES_X * Tiles.NB_TILES_Y; 
					}
					if(mazeLineImg == null) {
						mazeLineImg = tiles.getTileNumber(number); // first tile
					}
					else {
						mazeLineImg = joinToRight(mazeLineImg, tiles.getTileNumber(number)); // create a line of the maze
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
		copyMazeImg = tiles.copy(originalMazeImg);	
	}
	
	
	/**
	 * Get the maze image, created or not.
	 * @return the maze image
	 */
	public BufferedImage getMazeImg() {
		return tiles.copy(originalMazeImg);
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
		Maze maze = new Maze();
		maze.resizeMazeImg(1000, 600);
		Tiles.displayImg(maze.getMazeImg());
		
	}

}
