package resources;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sprites.Blinky;
import sprites.Clyde;
import sprites.Energizer;
import sprites.Ghost;
import sprites.MovingSprite;
import sprites.PacDot;
import sprites.PacMan;
import sprites.Pinky;
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
	private Sprites pacDots = new Sprites();	
	
	private List<Position> energizersMazeFilePositions = new ArrayList<Position>();
	private List<Position> energizersMazeImagePositions = new ArrayList<Position>();
	private Sprites energizers = new Sprites();	
	
	private Position pacManMazeFilePosition;
	private Position pacManMazeImagePosition;
	private MovingSprite pacMan;	
	
	private Position blinkyMazeFilePosition;
	private Position blinkyMazeImagePosition;	
	private Ghost blinky;
	
	private Position pinkyMazeFilePosition;
	private Position pinkyMazeImagePosition;	
	private Ghost pinky;
	
	private Position clydeMazeFilePosition;
	private Position clydeMazeImagePosition;	
	private Ghost clyde;
	
	
	/**
	 * Constructor that creates the maze image and sprites thanks to a text file.
	 * @throws IOException
	 */
	public Maze() throws IOException {
		tiles = new Tiles();
		chargeMazeValues("maze.txt");
		createMazeAndSprites();
		computeSpritesInitialPositions();
		createSprites();
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
		int weight = 1;
		int number = 0;
		int line_nb = 0;
		boolean new_line_for_nb = true;
		
		while ((c=fr.read()) != -1) { //end of file

			if(c >= 48 && c <= 57) { // ascii integer
				if(new_line_for_nb) {
					mazeValues.add(new ArrayList<Integer>()); // add the first line for number
					new_line_for_nb = false;
				}
				c -= 48; // ascii to int
				number = number*weight + c;
				weight = 10;
			}
			else { // must be a coma after the number, so add the number to the list
				weight = 1;
				mazeValues.get(line_nb).add(number); // add the number to the line
				number = 0;
				
				if(c == 10) { //ascii end of line
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
				if(integer > 99) {
					System.out.print(" ");
				}
				else if(integer > 9) {
					System.out.print("  ");
				}
				else {
					System.out.print("   ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Get the 2D matrix of numbers of maze file.
	 * @return
	 */
	public List<List<Integer>> getMazeValues(){
		return mazeValues;
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
				if(number == 0 || number == 15 || number == 13 || number == 97
					|| number == 193 || number == 225 || number == 257) 
				{
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
					else if(number == 193) {
						blinkyMazeFilePosition = new Position(x,y); // only one blinky (red ghost)
					}
					else if(number == 225) {
						pinkyMazeFilePosition = new Position(x,y); // only one pinky (pink ghost)
					}
					else if(number == 257) {
						clydeMazeFilePosition = new Position(x,y); // only one clyde (orange ghost)
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
	private void computeSpritesInitialPositions() {
		System.out.println("Compute sprites initial positions");
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
		
		
		int tile_width = getMazeImg().getWidth() / mazeValues.get(0).size();
		//pac-man
		pixelX = (pacManMazeFilePosition.getX() * getMazeImg().getWidth()) / mazeValues.get(0).size();
		pixelY = (pacManMazeFilePosition.getY() *getMazeImg().getHeight()) / mazeValues.size();
		pacManMazeImagePosition = new Position(pixelX+(tile_width/2), pixelY); 
		
		//blinky
		pixelX = (blinkyMazeFilePosition.getX() * getMazeImg().getWidth()) / mazeValues.get(0).size();
		pixelY = (blinkyMazeFilePosition.getY() *getMazeImg().getHeight()) / mazeValues.size();
		blinkyMazeImagePosition = new Position(pixelX+(tile_width/2), pixelY); 		

		//pinky
		pixelX = (pinkyMazeFilePosition.getX() * getMazeImg().getWidth()) / mazeValues.get(0).size();
		pixelY = (pinkyMazeFilePosition.getY() *getMazeImg().getHeight()) / mazeValues.size();
		pinkyMazeImagePosition = new Position(pixelX, pixelY); 	

		//clyde
		pixelX = (clydeMazeFilePosition.getX() * getMazeImg().getWidth()) / mazeValues.get(0).size();
		pixelY = (clydeMazeFilePosition.getY() *getMazeImg().getHeight()) / mazeValues.size();
		clydeMazeImagePosition = new Position(pixelX+(tile_width/2), pixelY); 	
		
		//...
		
	}
	
	private void createSprites() {
		System.out.println("Create sprites");
		
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
		
		//blinky
		blinky = new Blinky(blinkyMazeImagePosition, tiles);

		//pinky
		pinky = new Pinky(pinkyMazeImagePosition, tiles); 

		//clyde
		clyde = new Clyde(clydeMazeImagePosition, tiles); 
		
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
	public void resizeMazeAndSprites(boolean drawnOnce, Dimension lastDim, Dimension newDim) {
		if(originalMazeImg != null) {
			//resize maze into a copy
			copyMazeImg = Tiles.resize(originalMazeImg, newDim);
		    
		    //reposition and resize the sprites in the resized maze
		    repositionSpritesInMaze(drawnOnce, lastDim, newDim);
		    resizeSpritesInMaze(newDim);
		}
	}
	
	/**
	 * Reposition the sprites in the maze (game panel) of different size.
	 * The moving sprites are first repositioned thanks to the original maze image,
	 * but once drawn the last dimension of the game panel is used. Indeed we need the
	 * last game panel dimension to reposition the last current position of the moving sprites 
	 * in a new game panel.
	 * @param drawnOnce
	 * @param lastDim
	 * @param newDim
	 */
	private void repositionSpritesInMaze(boolean drawnOnce, Dimension lastDim, Dimension newDim) {
		
		int newX, newY;
		
		//pac-dots
		for (Sprite pacdot : pacDots.getSprites()) {
			newX = (newDim.width * pacdot.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (newDim.height * pacdot.getMazePosition().getY()) / originalMazeImg.getHeight();
			pacdot.setCurrentPosition(new Position(newX, newY));
		}
		
		//energizers
		for (Sprite e : energizers.getSprites()) {
			newX = (newDim.width * e.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (newDim.height * e.getMazePosition().getY()) / originalMazeImg.getHeight();
			e.setCurrentPosition(new Position(newX, newY));
		}
		
		//pac-man
		if(!drawnOnce) {
			newX = (newDim.width * pacMan.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (newDim.height * pacMan.getMazePosition().getY()) / originalMazeImg.getHeight();			
		}else {
			newX = (int)Math.round((newDim.width * pacMan.getCurrentPosition().getX()) / (double)lastDim.width) ;
			newY = (int)Math.round((newDim.height * pacMan.getCurrentPosition().getY()) / (double)lastDim.height);				
		}
		pacMan.setCurrentPosition(new Position(newX, newY));
		
		//blinky
		if(!drawnOnce) {
			newX = (newDim.width * blinky.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (newDim.height * blinky.getMazePosition().getY()) / originalMazeImg.getHeight();			
		}else {
			newX = (int)Math.round((newDim.width * blinky.getCurrentPosition().getX()) / (double)lastDim.width) ;
			newY = (int)Math.round((newDim.height * blinky.getCurrentPosition().getY()) / (double)lastDim.height);				
		}
		blinky.setCurrentPosition(new Position(newX, newY));		

		//pinky
		if(!drawnOnce) {
			newX = (newDim.width * pinky.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (newDim.height * pinky.getMazePosition().getY()) / originalMazeImg.getHeight();			
		}else {
			newX = (int)Math.round((newDim.width * pinky.getCurrentPosition().getX()) / (double)lastDim.width) ;
			newY = (int)Math.round((newDim.height * pinky.getCurrentPosition().getY()) / (double)lastDim.height);				
		}
		pinky.setCurrentPosition(new Position(newX, newY));		
		
		//clyde
		if(!drawnOnce) {
			newX = (newDim.width * clyde.getMazePosition().getX()) / originalMazeImg.getWidth() ;
			newY = (newDim.height * clyde.getMazePosition().getY()) / originalMazeImg.getHeight();			
		}else {
			newX = (int)Math.round((newDim.width * clyde.getCurrentPosition().getX()) / (double)lastDim.width) ;
			newY = (int)Math.round((newDim.height * clyde.getCurrentPosition().getY()) / (double)lastDim.height);				
		}
		clyde.setCurrentPosition(new Position(newX, newY));				
		
		//...
	}
	
	/**
	 * Resize the sprites in order to draw them with a size adapted to the new dimension.
	 * @param newDim
	 */
	private void resizeSpritesInMaze(Dimension newDim) {
		
		int newWidth, newHeight;
		
		//pac-dots
		for (Sprite pacdot : pacDots.getSprites()) {
			newWidth = (newDim.width * pacdot.getOriginalSize().width) / originalMazeImg.getWidth() ;
			newHeight = (newDim.height * pacdot.getOriginalSize().height) / originalMazeImg.getHeight();
			pacdot.setCurrentSize(new Dimension(newWidth, newHeight));
		}
		
		//energizers
		for (Sprite e : energizers.getSprites()) {
			newWidth = (newDim.width * e.getOriginalSize().width) / originalMazeImg.getWidth() ;
			newHeight = (newDim.height * e.getOriginalSize().height) / originalMazeImg.getHeight();
			e.setCurrentSize(new Dimension(newWidth, newHeight));
		}
		
		//pac-man
		newWidth = (newDim.width * pacMan.getOriginalSize().width) / originalMazeImg.getWidth() ;
		newHeight = (newDim.height * pacMan.getOriginalSize().height) / originalMazeImg.getHeight();
		pacMan.setCurrentSize(new Dimension(newWidth, newHeight));
		
		//blinky
		newWidth = (newDim.width * blinky.getOriginalSize().width) / originalMazeImg.getWidth() ;
		newHeight = (newDim.height * blinky.getOriginalSize().height) / originalMazeImg.getHeight();
		blinky.setCurrentSize(new Dimension(newWidth, newHeight));

		//pinky
		newWidth = (newDim.width * pinky.getOriginalSize().width) / originalMazeImg.getWidth() ;
		newHeight = (newDim.height * pinky.getOriginalSize().height) / originalMazeImg.getHeight();
		pinky.setCurrentSize(new Dimension(newWidth, newHeight));

		//clyde
		newWidth = (newDim.width * clyde.getOriginalSize().width) / originalMazeImg.getWidth() ;
		newHeight = (newDim.height * clyde.getOriginalSize().height) / originalMazeImg.getHeight();
		clyde.setCurrentSize(new Dimension(newWidth, newHeight));
		
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

	public Ghost getBlinky() {
		return blinky;
	}
	
	public Ghost getPinky() {
		return pinky;
	}
	
	public Ghost getClyde() {
		return clyde;
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
