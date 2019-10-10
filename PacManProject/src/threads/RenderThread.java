package threads;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import main.Main;
import resources.Maze;
import resources.Tiles;
import resources.WriteLetter;
import sprites.Blinky;
import sprites.Clyde;
import sprites.Inky;
import sprites.MovingSprite;
import sprites.PacMan;
import sprites.Pinky;
import sprites.Sprites;
import view.GamePanel;
import view.HightScoresPanel;
import view.NewHighScorePanel;
import view.StatusBarPanel;

public class RenderThread extends ThreadPerso{
	

	
	// record stats every 1 second (roughly)
	private static long MAX_STATS_INTERVAL = 1000L; 

	/* Number of frames with a delay of 0 ms before the animation thread yields
  	to other running threads. */
	private static final int NO_DELAYS_PER_YIELD = 16;

	// no. of frames that can be skipped in any one animation loop
	// i.e the games state is updated but not rendered
	private static int MAX_FRAME_SKIPS = 5; 

	// number of FPS values stored to get an average
	private static int NUM_FPS = 10;


	// used for gathering statistics
	private long statsInterval = 0L;    // in ms
	private long prevStatsTime;   
	private long totalElapsedTime = 0L;
	private long gameStartTime;
	private int timeSpentInGame = 0;    // in seconds

	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	private double averageFPS = 0.0;

	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	private double averageUPS = 0.0;


	private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp

	private int period; // period between drawing in _ms_

	// used in thread
	private boolean initStats = false;
	private long beforeTime, afterTime, timeDiff, sleepTime;
	private int overSleepTime;
	private int noDelays;
	private int excess;

	// off screen rendering
	private Graphics dbg; 
	private Image dbImage = null;
	
	
	// window panels to render content into
	private GamePanel gamePanel;
	private StatusBarPanel statusBarPanel;
	private HightScoresPanel hightScoresPanel;
	private WriteLetter writeLetter;
	private int finalScore=0;
	private int newPosition=0;
	
	//maze
	private Maze maze;
	private boolean drawnOnce = false;
	private int currentGamePanelWidth, currentGamePanelHeight;
	private int lastGamePanelWidth = 0, lastGamePanelHeight = 0;
	
	//sprites
	private Sprites energizers;
	private Sprites pacDots;
	private PacMan pacMan;
	private Blinky blinky;
	private Pinky pinky;
	private Clyde clyde;
	private Inky inky;
	
	//animations
	private AnimationThread animationTh;
	private ThreeTwoOneThread threeTwoOneTh;

	//physics
	private PhysicsThread physicsTh;
	
	//exit the ghost of the box
	private GhostsExitBoxThread ghostExitThread;
	private GameOverThread gameOverTh;
	private ScoreInvicibiltyGhostThread scoreInvicibiltyGhostThread;
	
	//sounds
	private MusicThread musicTh;
	private SoundThread soundTh;
	
	private int lastLife = 0;
	
	public RenderThread(int period, GamePanel gamePanel, StatusBarPanel statusBarPanel, MusicThread musicTh, SoundThread soundTh) {
		super("Render");
		
		this.gamePanel = gamePanel;
		this.statusBarPanel = statusBarPanel;
		try {
			hightScoresPanel = new HightScoresPanel();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// initialize timing elements
		this.period = period;
		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];
		for (int i=0; i < NUM_FPS; i++) {
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}
		
		//create maze image and sprites from the maze file
		try {
			maze = new Maze(gamePanel, "maze.csv");
		} catch (IOException e) {e.printStackTrace();}
		
		//get sprites
		energizers = maze.getEnergizers();
		pacDots = maze.getPacDots();
		pacMan = maze.getPacMan();
		blinky = maze.getBlinky();
		pinky = maze.getPinky();
		clyde = maze.getClyde();
		inky = maze.getInky();
		
		statusBarPanel.setPacman(pacMan);
		this.musicTh = musicTh;
		this.soundTh = soundTh;
		animationTh = new AnimationThread(energizers, pacMan, blinky, pinky, clyde, inky);
		physicsTh = new PhysicsThread(maze.getMazeValues(), gamePanel, pacMan, blinky, pinky, clyde, inky, pacDots, energizers, musicTh , soundTh);
		ghostExitThread = new GhostsExitBoxThread(blinky, pinky, clyde, inky, maze, pacMan);
		try {
			writeLetter = new WriteLetter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.paused = true;
	}
	
	private void checkResize() {
		// resize all elements if panel size changed
		currentGamePanelWidth = gamePanel.getWidth();
		currentGamePanelHeight = gamePanel.getHeight();
		
		if(lastGamePanelWidth <= 0 && lastGamePanelHeight <=0) {
			lastGamePanelWidth = currentGamePanelWidth;
			lastGamePanelHeight = currentGamePanelHeight;
		}
		else if(currentGamePanelWidth > 0 && currentGamePanelHeight > 0 && 
				(!drawnOnce || lastGamePanelWidth != currentGamePanelWidth || lastGamePanelHeight != currentGamePanelHeight)) 
		{
			maze.resizeMazeAndSprites(drawnOnce, new Dimension(lastGamePanelWidth, lastGamePanelHeight),
											new Dimension(currentGamePanelWidth, currentGamePanelHeight));
			lastGamePanelWidth = currentGamePanelWidth;
			lastGamePanelHeight = currentGamePanelHeight;
			if(!drawnOnce) {
				drawnOnce = true;
			}
		}
	}

	@Override
	protected void doThatAtStart() {
	//	do {		
			checkResize();
	//	}while(!animationDone);
		
		initializeStats();
	}

	private void initializeStats() {
		overSleepTime = 0;
		noDelays = 0;
		excess = 0;
		gameStartTime = System.currentTimeMillis();
		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;
		initStats = true;
	}

	@Override
	protected void doThat() {
		
		if(!initStats) {
			initializeStats();
		}
			
		gameUpdate(); // game state is updated
		gameRender();   // render the game to a buffer
		paintScreen();  // draw the buffer on-screen

		afterTime = System.currentTimeMillis();
		timeDiff = afterTime - beforeTime;
		sleepTime = (period - timeDiff) - overSleepTime;  

		if (sleepTime > 0) {   // some time left in this cycle
			try {
				Thread.sleep(sleepTime);  // already in ms
			}
			catch(InterruptedException ex){}
			overSleepTime = (int)((System.currentTimeMillis() - afterTime) - sleepTime);
		}
		else {    // sleepTime <= 0; the frame took longer than the period
			excess -= sleepTime;  // store excess time value
			overSleepTime = 0;

			if (++noDelays >= NO_DELAYS_PER_YIELD) {
				Thread.yield();   // give another thread a chance to run
				noDelays = 0;
			}
		}

		beforeTime = System.currentTimeMillis();

		/* If frame animation is taking too long, update the game state
	      without rendering it, to get the updates/sec nearer to
	      the required FPS. */
		int skips = 0;
		while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
			excess -= period;
			gameUpdate();    // update state but don't render
			skips++;
		}
		framesSkipped += skips;

		storeStats();

	}
	
	@Override
	protected void doThatAtStop() {}
	
	
	/**
	 * Method that starts the thread.
	 */
	public void startThread() {
		if(!running) {			
			this.start();
		}
	}
	
	
	/**
	 * Stop doing the actions defined in doThat() method.
	 */
	public synchronized void pauseThread() {
		paused = true;
		animationTh.pauseThread();
		physicsTh.pauseThread();
		ghostExitThread.pauseThread();
	}
	
	/**
	 * Start again or continue doing the actions defined in doThat() method.
	 */
	public synchronized void resumeThread() {
		if(paused) {
			initStats = false;
			paused = false;			
			if(!animationTh.isRunning()) {
				animationTh.startThread();
			}else {
				animationTh.resumeThread();
			}

			if(!physicsTh.isRunning()) {
				physicsTh.startThread();
			}else {
				physicsTh.resumeThread();
			}

			if(!ghostExitThread.isRunning()) {
				ghostExitThread.startThread();
			}else {
				ghostExitThread.resumeThread();
			}	
		}
	}
	
	/**
	 * Stop render and other launched threads.
	 */
	public void stopThread() {
		animationTh.stopThread();
		physicsTh.stopThread();
		ghostExitThread.stopThread();
		running = false;
	}
	
	
	/**
	 * Check if the game panel size changed to resize the maze and the sprites if necessary,
	 * and change the position of the moving sprites with their respective speed.
	 */
	private void gameUpdate() 
	{ 					
		// resize maze and sprites if necessary
		checkResize();
		
		//update sprites position (like fantom positions)
		//the image of the sprite to display is changed by the animation thread
		synchronized(pacMan) {
			pacMan.updatePos();
		}
		synchronized(blinky) {
			blinky.updatePos();		
		}
		synchronized(pinky) {
			pinky.updatePos();
		}
		synchronized(clyde) {
			clyde.updatePos();
		}
		synchronized(inky) {
			inky.updatePos();
		}
		
		statusBarPanel.getDirection().setText(pacMan.getState().toString());
	}

	/**
	 * Draw the wanted image to a buffered image.
	 */
	private void gameRender()
	{
		if (drawnOnce){
			dbImage = gamePanel.createImage(currentGamePanelWidth, currentGamePanelHeight);
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			}
			else {
				dbg = dbImage.getGraphics();
			}
			
			//draw maze (background)
			maze.draw(dbg); 
			
			//draw all the sprites at their respective position, 
			//with their respective dimension
			synchronized (pacDots) {
				pacDots.draw(dbg); 
			}
			synchronized (energizers) {
				energizers.draw(dbg); 
			}
			synchronized (pacMan) {
				pacMan.draw(dbg);
			}
			synchronized (blinky) {
				blinky.draw(dbg);
			}
			synchronized (pinky) {
				pinky.draw(dbg);
			}
			synchronized (clyde) {
				clyde.draw(dbg);
			}
			synchronized (inky) {
				inky.draw(dbg);		
			}
			
			if(physicsTh.isCollPacManGhostInv()) {
				scoreInvicibiltyGhostThread= new ScoreInvicibiltyGhostThread( maze.getTiles(), gamePanel, physicsTh.getScoreInvGhost());
				scoreInvicibiltyGhostThread.setPosX(pacMan.getCurrentPosition().getX()-20);
				scoreInvicibiltyGhostThread.setPosY(pacMan.getCurrentPosition().getY()-20);
				scoreInvicibiltyGhostThread.startThread();
				this.pauseThread();
				physicsTh.getInvTh().pauseThread();
				do {
					try {
					Thread.sleep(100);
					} catch (InterruptedException e) {}
				}while(scoreInvicibiltyGhostThread.isRunning());
				this.resumeThread();
				physicsTh.getInvTh().resumeThread();
				physicsTh.setCollPacManGhostInv(false);
			}
			
			if(physicsTh.isGameOver()) {
				gameOverTh = new GameOverThread(maze.getTiles(), gamePanel);
				gameOverTh.startThread();
				this.pauseThread();
				do {
					try {
					Thread.sleep(100);
					} catch (InterruptedException e) {}
				}while(gameOverTh.isRunning());
				
				physicsTh.setGameOver(false);
				
				finalScore = physicsTh.getScore();
				compareWithHightScores();
				

				if(newPosition == 0) {
					
				this.stopThread();
				Main.getGlobalFrame().setStatutMenu(0);
				Main.getGlobalFrame().setPage("PrincipalMenu");
				System.out.println(Main.getGlobalFrame().getPage()+"...........");
				System.out.println("Score ..................."+finalScore);
				}else {
					this.stopThread();
					Main.getGlobalFrame().setStatutMenu(0);
					Main.getGlobalFrame().setPage("NewHighScore");
					System.out.println(Main.getGlobalFrame().getPage()+"...........");
					NewHighScorePanel h = Main.getGlobalFrame().getNewHighScorePanel();
					h.setNewHightScore(finalScore);
					h.setNewPosition(newPosition);
					System.out.println(".............test ............"+h.getNewHightScore());
					
					BufferedImage img = maze.getTiles().getTileNumber(352);
					String letter ="0";
					
					BufferedImage hightScore1Img = Tiles.createWord(img);
					letter =h.getNewHightScore()+"";
					for(int i=0;i<letter.length();i++) {
						Character c = letter.charAt(i);
						String cs = c.toString();
						writeLetter.setLetter(cs);
						System.out.println("letter ______________________"+cs);
						writeLetter.setL(img);
						writeLetter.write();
						img=writeLetter.getL();
						cs=writeLetter.getLetter();
						hightScore1Img = Tiles.createWord(hightScore1Img,img);
						
					}
					hightScore1Img = Tiles.resize(hightScore1Img, new Dimension(150, 50));
					h.getNewScore().setIcon(new ImageIcon(hightScore1Img));
					h.setVisible(true);
					//checkPageThread = new CheckPageThread("CheckPageThread");
					System.out.println("Score ..................."+finalScore);
					
				}
				
				
			}
			
			if(pacMan.getLife()!=lastLife) {
				StatusBarPanel.setImageLives(pacMan.getLife());
				StatusBarPanel.livesImg.setIcon(new ImageIcon(StatusBarPanel.Lives));
			}
			
		}
	}  
	
	/**
	 * Get pac-man moving sprite.
	 * @return pac-man
	 */
	public synchronized MovingSprite getPacMan() {
		return pacMan;
	}
	
	/**
	 * Draw the buffered image to the graphics of the game panel.
	 */
	private void paintScreen()
	// use active rendering to put the buffered image on-screen
	{ 
		//if(GameFrame.getPage()=="Game") {
			Graphics g;
			try {
				g = gamePanel.getGraphics();
				if ((g != null) && (dbImage != null))
					g.drawImage(dbImage, 0, 0, null);
				Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
				g.dispose();
			}

			catch (Exception e)
			{ System.out.println("Graphics error: " + e);  }
		//}
	}


	private void storeStats()
	/* The statistics:
    - the summed periods for all the iterations in this interval
      (period is the amount of time a single frame iteration should take), 
      the actual elapsed time in this interval, 
      the error between these two numbers;

    - the total frame count, which is the total number of calls to run();

    - the frames skipped in this interval, the total number of frames
      skipped. A frame skip is a game update without a corresponding render;

    - the FPS (frames/sec) and UPS (updates/sec) for this interval, 
      the average FPS & UPS over the last NUM_FPSs intervals.

  The data is collected every MAX_STATS_INTERVAL  (1 sec).
	 */
	{ 
		frameCount++;
		statsInterval += period;

		if (statsInterval >= MAX_STATS_INTERVAL) {     // record stats every MAX_STATS_INTERVAL
			long timeNow = System.currentTimeMillis();
			timeSpentInGame = (int) ((timeNow - gameStartTime)/1000L);  // ms --> secs

			long realElapsedTime = timeNow - prevStatsTime;   // time since last stats collection
			totalElapsedTime += realElapsedTime;

			totalFramesSkipped += framesSkipped;

			double actualFPS = 0;     // calculate the latest FPS and UPS
			double actualUPS = 0;
			if (totalElapsedTime > 0) {
				actualFPS = (((double)frameCount / totalElapsedTime) * 1000L);
				actualUPS = (((double)(frameCount + totalFramesSkipped) / totalElapsedTime) 
						* 1000L);
			}

			// store the latest FPS and UPS
			fpsStore[ (int)statsCount%NUM_FPS ] = actualFPS;
			upsStore[ (int)statsCount%NUM_FPS ] = actualUPS;
			statsCount = statsCount+1;

			double totalFPS = 0.0;     // total the stored FPSs and UPSs
			double totalUPS = 0.0;
			for (int i=0; i < NUM_FPS; i++) {
				totalFPS += fpsStore[i];
				totalUPS += upsStore[i];
			}

			if (statsCount < NUM_FPS) { // obtain the average FPS and UPS
				averageFPS = totalFPS/statsCount;
				averageUPS = totalUPS/statsCount;
			}
			else {
				averageFPS = totalFPS/NUM_FPS;
				averageUPS = totalUPS/NUM_FPS;
			}

			framesSkipped = 0;
			prevStatsTime = timeNow;
			statsInterval = 0L;   // reset
			JLabel f= statusBarPanel.getFps();
			f.setText(Integer.toString((int)averageFPS));
			statusBarPanel.setFps(f);
		}
	}  // end of storeStats()


	public void printStats()
	{
		System.out.println("\nFrame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
		System.out.println("Average FPS: " + df.format(averageFPS));
		System.out.println("Average UPS: " + df.format(averageUPS));
		System.out.println("Time Spent: " + timeSpentInGame + " secs");
	}  // end of printStats()
	
	public void compareWithHightScores(){
		String[] h = hightScoresPanel.getScore() ;
		Integer[] hightScores = new Integer[5];
		for(int i =0; i<5;i++) {
			hightScores[i]= Integer.parseInt(h[i]);
		}
		if(finalScore>hightScores[0] || finalScore==hightScores[0]) {
			newPosition =1;
			System.out.println("position 1.......................");
		}else if((finalScore<hightScores[0] && finalScore>hightScores[1])|| finalScore==hightScores[1]) {
			newPosition =2;
			System.out.println("position 2.......................");
		}else if((finalScore<hightScores[1] && finalScore>hightScores[2])|| finalScore==hightScores[2]) {
			newPosition =3;
			System.out.println("position 3.......................");
		}else if((finalScore<hightScores[2] && finalScore>hightScores[3])|| finalScore==hightScores[3]) {
			newPosition =4;
			System.out.println("position 4.......................");
		}else if((finalScore<hightScores[3] && finalScore>hightScores[4])|| finalScore==hightScores[4]) {
			newPosition =5;
			System.out.println("position 5.......................");
		}
		
	}
	
	public AnimationThread getAnimationTh() {
		return animationTh;
	}

	public void setAnimationTh(AnimationThread animationTh) {
		this.animationTh = animationTh;
	}

	public ThreeTwoOneThread getThreeTwoOneTh() {
		return threeTwoOneTh;
	}

	public void setThreeTwoOneTh(ThreeTwoOneThread threeTwoOneTh) {
		this.threeTwoOneTh = threeTwoOneTh;
	}

	public PhysicsThread getPhysicsTh() {
		return physicsTh;
	}

	public void setPhysicsTh(PhysicsThread physicsTh) {
		this.physicsTh = physicsTh;
	}

	public GhostsExitBoxThread getGhostExitThread() {
		return ghostExitThread;
	}

	public void setGhostExitThread(GhostsExitBoxThread ghostExitThread) {
		this.ghostExitThread = ghostExitThread;
	}

	public synchronized void setMusicMute(boolean b) {
		synchronized (musicTh){
			musicTh.setMute(b);
		}
	}

	public synchronized void setSoundMute(boolean b) {
		synchronized(physicsTh) {
			physicsTh.setSoundMute(b);
		}
	}
	
	public synchronized void setAudioUp() {
		synchronized(soundTh) {
			soundTh.volumeUp();
		}
	}
	
	public synchronized void setAudioDown() {
		synchronized(soundTh) {
			soundTh.volumeDown();
		}
		
	}
	
	public synchronized void setMusicUp() {
		synchronized (musicTh) {
			musicTh.volumeUp();
		}
	}
	
	public synchronized void setMusicDown() {
		synchronized (musicTh) {
			musicTh.volumeDown();
		}
	}



}
