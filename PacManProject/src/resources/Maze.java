package resources;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sprites.Energizer;
import sprites.MovingSprite;
import sprites.PacDot;
import sprites.PacMan;
import sprites.Position;
import sprites.Sprite;
import sprites.Sprites;

public class Maze {
	
	private Resources rsc = new Resources();
	
	List<List<Integer>> mazeValues = new ArrayList<List<Integer>>();
	
	
	private Tiles tiles;
	private BufferedImage originalMazeImg = null;
	private BufferedImage copyMazeImg;
	
	//sprites 
	private List<Position> pacDotsMazeFilePositions = new ArrayList<Position>();
	private List<Position> pacDotsMazeImagePositions = new ArrayList<Position>();
	
	private List<Position> energizersMazeFilePositions = new ArrayList<Position>();
	private List<Position> energizersMazeImagePositions = new ArrayList<Position>();
	
	private Position pacManMazeFilePosition;
	private Position pacManMazeImagePosition;
	
	
	private Sprites energizers = new Sprites();
	private Sprites pacDots = new Sprites();
	private MovingSprite pacMan;
	
	
	/**
	 * Constructor that creates the maze image and sprites thanks to a text file.
	 * @throws IOException
	 */
	public Maze() throws IOException {
		tiles = new Tiles();
		chargeMazeValues("maze.txt");
		createMazeAndSprites();
		computeSpritesPositions();
		fillSpritesPositions();
	}
	
	
	public Tiles getTiles() {
		return tiles;
	}
	

	/**
	 * Charge the maze text file numbers in a 2D matrix.
	 * @param mazeFilename
	 * @throws IOException
	 */
	private void chargeMazeValues(String mazeFilename) throws IOException {
		System.out.println("Charge maze values");
		
		@SuppressWarnings("resource")
		FileReader fr = new FileReader(rsc.getMazePath(mazeFilename));
		
		int c = 0; 
		int weight = 0;
		int number = 0;
		int line_nb = 0;
		boolean new_line_for_nb = true;
		
		while ((c=fr.read()) != -1) { //end of file

			if(c >= 48 && c <= 57) { // integer
				if(new_line_for_nb) {
					mazeValues.add(new ArrayList<Integer>()); // add the first line for number
					new_line_for_nb = false;
				}
				c -= 48;
				number = number*weight + c;
				weight += 10;
			}
			else { // must be a coma after the number, so add the number to the list
				weight = 0;
				mazeValues.get(line_nb).add(number); // add the number to the line
				
				if(c == 10) { //end of line
					line_nb++; //increase line nb
					new_line_for_nb = true;
				}
			}
		}
	}
	
	/**
	 * Print the maze numbers in the console as a 2D matrix.
	 */
	private void printMazeValues() {
		for (List<Integer> mazeLine : mazeValues) {
			for (Integer integer : mazeLine) {
				System.out.print(integer);
				if(integer > 9) {
					System.out.print(" ");
				}
				else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Go throught the maze values to create the maze image and the sprites with their position.
	 */
	private void createMazeAndSprites() {
		int number;
		BufferedImage mazeLineImg = null;
		
		for (int y = 0; y < mazeValues.size(); y++) {
			for (int x = 0; x < mazeValues.get(y).size(); x++) {
				
				number = mazeValues.get(y).get(x);
				if(number == 0 || number == 15 || number == 13 || number == 97) {
					// 0: no tile with that number

					if(number == 13) { // pac-dot
						pacDotsMazeFilePositions.add(new Position(x, y));
					}
					else if(number == 15) { // energizer
						energizersMazeFilePositions.add(new Position(x, y));
					}
					else if(number == 97) { // pac-man
						pacManMazeFilePosition = new Position(x, y); // only one pac-man
					}						
					
					// -> black tile instead, the sprites will be displayed by the render thread
					number = Tiles.NB_TILES_X * Tiles.NB_TILES_Y; 
				}
				
				if(mazeLineImg == null) {
					mazeLineImg = tiles.getTileNumber(number); // first tile
				}
				else {
					mazeLineImg = Tiles.joinToRight(mazeLineImg, tiles.getTileNumber(number)); // create a line of the maze
				}
			}
			// next line
			if(mazeLineImg != null && originalMazeImg == null) {
				originalMazeImg = mazeLineImg;
			}
			else if(mazeLineImg != null && originalMazeImg != null){
				originalMazeImg = Tiles.joinBelow(originalMazeImg, mazeLineImg);
			}
			if(mazeLineImg != null) {
				mazeLineImg.flush(); //release memory allocated by a line of images of the maze
			}
			mazeLineImg = null;
		}
		//finally save the final maze image in a copy
		copyMazeImg = Tiles.copy(originalMazeImg);	
	}
	

	/**
	 * Method that computes the position of each sprite in the maze image. 
	 */
	private void computeSpritesPositions() {
		int pixelX, pixelY;
		
		//pac-dots
		for (Position position : pacDotsMazeFilePositions) {
			pixelX = (position.getX() * getMazeImg().getWidth()) / mazeValues.get(0).size();
			pixelY = (position.getY() *getMazeImg().getHeight()) / mazeValues.size();
			pacDotsMazeImagePositions.add(new Position(pixelX, pixelY));
		}
		pacDotsMazeFilePositions.clear();		
		
		//energizers
		for (Position position : energizersMazeFilePositions) {
			pixelX = (position.getX() * getMazeImg().getWidth()) / mazeValues.get(0).size();
			pixelY = (position.getY() *getMazeImg().getHeight()) / mazeValues.size();
			energizersMazeImagePositions.add(new Position(pixelX, pixelY));
		}
		energizersMazeFilePositions.clear();
		
		//pac-man
		int tile_width = getMazeImg().getWidth() / mazeValues.get(0).size();
		pixelX = (pacManMazeFilePosition.getX() * getMazeImg().getWidth()) / mazeValues.get(0).size();
		pixelY = (pacManMazeFilePosition.getY() *getMazeImg().getHeight()) / mazeValues.size();
		pacManMazeImagePosition = new Position(pixelX+(tile_width/2), pixelY); 
		
		//...
		
	}
	
	private void fillSpritesPositions() {
		
		//pac-dots
		for (Position position : pacDotsMazeImagePositions) {
			pacDots.add(new PacDot(position, tiles));
		}
		pacDotsMazeImagePositions.clear();
		
		//energizers
		for (Position position : energizersMazeImagePositions) {
			energizers.add(new Energizer(position, tiles));
		}
		energizersMazeImagePositions.clear();
		
		//pac-man
		pacMan = new PacMan(pacManMazeImagePosition, tiles);
		
		//...
	}
	
	
	/**
	 * Get the maze image, created or not.
	 * @return the maze image
	 */
	public BufferedImage getMazeImg() {
		return Tiles.copy(originalMazeImg);
	}
	
	/**
	 * Resize the maze image, if not null, with dimension parameters
	 * @param width
	 * @param height
	 */
	public void resizeMazeAndSprites(int width, int height) {
		if(originalMazeImg != null) {
			//resize maze into a copy
			copyMazeImg = Tiles.resize(originalMazeImg, new Dimension(width,height));
		    
		    //reposition and resize the sprites in the resized maze
		    repositionSpritesInMaze(width, height);
		    resizeSpritesInMaze(width, height);
		}
	}
	
	/**
	 * Reposition the sprites in the maze of different size.
	 * @param width the new maze width
	 * @param height the new maze height
	 */
	private void repositionSpritesInMaze(int width, int height) {
		
		int newX, newY;
		
		//pac-dots
		for (Sprite pacdot : pacDots.getSprites()) {
			newX = (width * pacdot.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (height * pacdot.getMazePosition().getY()) / originalMazeImg.getHeight();
			pacdot.setCurrentPosition(new Position(newX, newY));
		}
		
		//energizers
		for (Sprite e : energizers.getSprites()) {
			newX = (width * e.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (height * e.getMazePosition().getY()) / originalMazeImg.getHeight();
			e.setCurrentPosition(new Position(newX, newY));
		}
		
		//pac-man
		newX = (width * pacMan.getMazePosition().getX()) / originalMazeImg.getWidth() ;
		newY = (height * pacMan.getMazePosition().getY()) / originalMazeImg.getHeight();
		pacMan.setCurrentPosition(new Position(newX, newY));
		
		//...
	}
	
	private void resizeSpritesInMaze(int width, int height) {
		
		int newWidth, newHeight;
		
		//pac-dots
		for (Sprite pacdot : pacDots.getSprites()) {
			newWidth = (width * pacdot.getOriginalSize().width) / originalMazeImg.getWidth() ;
			newHeight = (height * pacdot.getOriginalSize().height) / originalMazeImg.getHeight();
			pacdot.setCurrentSize(new Dimension(newWidth, newHeight));
		}
		
		//energizers
		for (Sprite e : energizers.getSprites()) {
			newWidth = (width * e.getOriginalSize().width) / originalMazeImg.getWidth() ;
			newHeight = (height * e.getOriginalSize().height) / originalMazeImg.getHeight();
			e.setCurrentSize(new Dimension(newWidth, newHeight));
		}
		
		//pac-man
		newWidth = (width * pacMan.getOriginalSize().width) / originalMazeImg.getWidth() ;
		newHeight = (height * pacMan.getOriginalSize().height) / originalMazeImg.getHeight();
		pacMan.setCurrentSize(new Dimension(newWidth, newHeight));
		
		//...
	}
	
	public Sprites getEnergizers() {
		return energizers;
	}
	
	public Sprites getPacDots() {
		return pacDots;
	}
	
	public MovingSprite getPacMan() {
		return pacMan;
	}
	
	
	public void draw(Graphics g) {
		if(g != null && copyMazeImg != null) {
			g.drawImage(copyMazeImg,0,0, null);
		}
	}
	
	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException {
		Maze maze = new Maze();
		maze.printMazeValues();
//		maze.resizeMazeAndSprites(500, 600);
//		Tiles.displayImg(maze.getMazeImg());
		
	}

}
