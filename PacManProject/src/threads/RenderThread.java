package threads;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.DecimalFormat;

import resources.Maze;
import sprites.MovingSprite;
import sprites.Sprites;
import view.GamePanel;
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

//	private volatile boolean running = false;   // used to stop the animation thread
//	private boolean isPaused = false;

	private int period; // period between drawing in _ms_

	// used in thread
	private long beforeTime, afterTime, timeDiff, sleepTime;
	private int overSleepTime;
	private int noDelays;
	private int excess;


	// used at game termination
	private volatile boolean gameOver = false;
	private int score = 0;
	//private Font font;
	//private FontMetrics metrics;

	// off screen rendering
	private Graphics dbg; 
	private Image dbImage = null;
	
	
	// window panels to render content into
	private GamePanel gamePanel;
	private StatusBarPanel statusBarPanel;

	
	//maze
	private Maze maze;
	private boolean drawnOnce = false;
	private int currentGamePanelWidth, currentGamePanelHeight;
	private int lastGamePanelWidth = 0, lastGamePanelHeight = 0;
	
	//sprites
	private Sprites energizers;
	private Sprites pacDots;
	private MovingSprite pacMan;
	
	//animation
	private AnimationThread animationTh;
	
	//physics
	private PhysicsThread physicsTh;
	
	
	public RenderThread(int period, GamePanel gamePanel, StatusBarPanel statusBarPanel) {
		super("Render");
		
		System.out.println("Create render thread");
		
		this.gamePanel = gamePanel;
		this.statusBarPanel = statusBarPanel;
		
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
			maze = new Maze();
		} catch (IOException e) {e.printStackTrace();}
		
		//get sprites
		energizers = maze.getEnergizers();
		pacDots = maze.getPacDots();
		pacMan = maze.getPacMan();
		
		animationTh = new AnimationThread(energizers, pacMan);
		physicsTh = new PhysicsThread(maze.getMazeValues(), gamePanel, pacMan);
	}
	

	@Override
	protected void doThatAtStart() {
		overSleepTime = 0;
		noDelays = 0;
		excess = 0;
		gameStartTime = System.currentTimeMillis();
		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;
	}


	@Override
	protected void doThat() {
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
	protected void doThatAtStop() {
		System.out.println("Stop "+getName()+" and program");		
		printStats();
		System.exit(0);   // so window disappears	
	}
	
	
	/**
	 * Method that starts the thread.
	 */
	public void startThread() {
		if(!running) {
			this.start();
			animationTh.startThread();
			physicsTh.startThread();
		}
	}
	
	
	/**
	 * Stop doing the actions defined in doThat() method.
	 */
	public synchronized void pauseThread() {
		paused = true;
		animationTh.pauseThread();
		physicsTh.pauseThread();
	}
	
	/**
	 * Start again or continue doing the actions defined in doThat() method.
	 */
	public synchronized void resumeThread() {
		paused = false;
		animationTh.resumeThread();
		physicsTh.resumeThread();
	}
	
	/**
	 * Stop the thread.
	 */
	public void stopThread() {
		running = false;
		animationTh.stopThread();
		physicsTh.stopThread();
	}
	
	

	private void gameUpdate() 
	{ 
		if (!gameOver) {
			
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
				maze.resizeMazeAndSprites(currentGamePanelWidth, currentGamePanelHeight);
				lastGamePanelWidth = currentGamePanelWidth;
				lastGamePanelHeight = currentGamePanelHeight;
				if(!drawnOnce) {
					drawnOnce = true;
				}
			}
			
			//update sprites position (like fantom positions)
			//the image of the sprite to display is changed by the animation thread
			pacMan.updatePos();
			
		}
	}


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
		
			// draw game elements
			maze.draw(dbg); //draw maze (background)
			
			
			//draw all the sprites at their respective position, 
			//with their respective dimension
			pacDots.draw(dbg); 
			energizers.draw(dbg); 
			
			pacMan.draw(dbg);
			
			
	
			if (gameOver)
				gameOverMessage(dbg);
		
		}
	}  


	private void gameOverMessage(Graphics g)
	// center the game-over message in the panel
	{
		String msg = "Game Over. Your Score: " + score;
		System.out.println(msg);
//		int x = (PWIDTH - metrics.stringWidth(msg))/2; 
//		int y = (PHEIGHT - metrics.getHeight())/2;
//		g.setColor(Color.red);
//		g.setFont(font);
//		g.drawString(msg, x, y);
	}  // end of gameOverMessage()


	
	
	public MovingSprite getPacMan() {
		return pacMan;
	}
	
	
	private void paintScreen()
	// use active rendering to put the buffered image on-screen
	{ 
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
	} // end of paintScreen()


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
		}
	}  // end of storeStats()


	private void printStats()
	{
		System.out.println("Frame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
		System.out.println("Average FPS: " + df.format(averageFPS));
		System.out.println("Average UPS: " + df.format(averageUPS));
		System.out.println("Time Spent: " + timeSpentInGame + " secs");
	}  // end of printStats()




}
