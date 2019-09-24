package resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sprites.Energizer;
import sprites.Energizers;
import sprites.Position;

public class Maze {
	
	private Resources rsc = new Resources();
	private Tiles tiles;
	private BufferedImage originalMazeImg = null;
	private BufferedImage copyMazeImg;
	
	//sprites 
	private int nb_rows, nb_lines;
	private List<Position> energizersMazeFilePositions = new ArrayList<Position>();
	private List<Position> energizersMazeImagePositions = new ArrayList<Position>();
	private Energizers energizers = new Energizers();
	
	/**
	 * Constructor that creates the maze image and sprites thanks to a text file.
	 * @throws IOException
	 */
	public Maze() throws IOException {
		tiles = new Tiles();
		createMazeFromText("maze.txt");
		computeSpritesPositions();
		fillSpritesPositions();
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
		int c = 0; 
		int weight = 0;
		int number = 0;
		int row_nb = 0;
		int line_nb = 0;
		boolean first_nb_read = false;
		
		while ((c=fr.read()) != -1) { //end of file

			if(c >= 48 && c <= 57) { // integer
				if(!first_nb_read) {
					first_nb_read = true;
				}
				c -= 48;
				number = number*weight + c;
				weight += 10;
			}
			else {
				weight = 0;
				if(first_nb_read) {
					if(number == 0 || number == 15) {
						// 0: no tile with that number
						
						
						if(number == 15) { // 15: energizer
							energizersMazeFilePositions.add(new Position(row_nb, line_nb));
						}
						
						
						
						// -> black tile instead
						number = Tiles.NB_TILES_X * Tiles.NB_TILES_Y; 
					}
					if(mazeLineImg == null) {
						mazeLineImg = tiles.getTileNumber(number); // first tile
					}
					else {
						mazeLineImg = joinToRight(mazeLineImg, tiles.getTileNumber(number)); // create a line of the maze
					}
					row_nb++;
				}
				if(c == 10) { //end of line
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
					line_nb++; //increase line nb
					nb_rows = row_nb;
					row_nb = 0; //reset row number for next line
				}
			}
		}
		nb_lines = line_nb;
		copyMazeImg = tiles.copy(originalMazeImg);	
	}
	
	/**
	 * Method that computes the position of each sprite in the maze image. 
	 */
	private void computeSpritesPositions() {
		int pixelX, pixelY;
		
		//energizers
		for (Position position : energizersMazeFilePositions) {
			pixelX = (position.getX() * getMazeImg().getWidth()) / nb_rows;
			pixelY = (position.getY() *getMazeImg().getHeight()) / nb_lines;
			energizersMazeImagePositions.add(new Position(pixelX, pixelY));
		}
		energizersMazeFilePositions.clear();
		
		//...
		
	}
	
	private void fillSpritesPositions() {
		//energizers
		for (Position position : energizersMazeImagePositions) {
			energizers.add(new Energizer(position, tiles));
		}
		energizersMazeImagePositions.clear();
		
		//...
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
	public void resizeMazeAndSprites(int width, int height) {
		if(originalMazeImg != null) {
			//resize maze into a copy
			Image img = originalMazeImg.getScaledInstance(width,height,Image.SCALE_SMOOTH);
			copyMazeImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		    Graphics bGr = copyMazeImg.createGraphics();
		    bGr.drawImage(img, 0, 0, null);
		    bGr.dispose();		
		    
		    //reposition the sprites in the maze
		    repositionSpritesInMaze(width, height);
		}
	}
	
	/**
	 * Reposition the sprites in the maze of different size.
	 * @param width the new maze width
	 * @param height the new maze height
	 */
	private void repositionSpritesInMaze(int width, int height) {
		
		//energizers
		for (Energizer e : energizers.getEnergizers()) {
			int newX = (width * e.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			int newY = (height * e.getMazePosition().getY()) / originalMazeImg.getHeight();
			e.setCurrentPosition(new Position(newX, newY));
		}
		
		//...
	}
	
	public Energizers getEnergizers() {
		return energizers;
	}
	
	
	
	public void draw(Graphics g) {
		if(g != null && copyMazeImg != null) {
			g.drawImage(copyMazeImg,0,0, null);
		}
	}
	
	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException {
		Maze maze = new Maze();
		maze.resizeMazeAndSprites(500, 600);
//		Tiles.displayImg(maze.getMazeImg());
		
	}

}
