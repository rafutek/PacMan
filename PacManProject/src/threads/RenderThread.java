package threads;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.DecimalFormat;

import view.GamePanel;
import view.StatusBarPanel;

public class RenderThread extends Thread{
	
	
	// window panels to render content into
	private GamePanel gamePanel;
	private StatusBarPanel statusBarPanel;

	
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

	private volatile boolean running = false;   // used to stop the animation thread
	private boolean isPaused = false;

	private int period; // period between drawing in _ms_



	// used at game termination
	private volatile boolean gameOver = false;
	private int score = 0;
	//private Font font;
	//private FontMetrics metrics;

	// off screen rendering
	private Graphics dbg; 
	private Image dbImage = null;
	
	public RenderThread(int period, GamePanel gamePanel, StatusBarPanel statusBarPanel) {
		setName("Render");
		
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
	}
	
	public void run()
	/* The frames of the animation are drawn inside the while loop. */
	{
		System.out.println("Start thread "+getName());

		long beforeTime, afterTime, timeDiff, sleepTime;
		int overSleepTime = 0;
		int noDelays = 0;
		int excess = 0;
		gameStartTime = System.currentTimeMillis();
		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;

		running = true;

		while(running) {
			gameUpdate(); 
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
		System.out.println("Stop thread "+getName());	

		printStats();
		System.exit(0);   // so window disappears
	} // end of run()

	
	/**
	 * Start the thread 
	 */
	public synchronized void startGame()
	{ 
		if (!running) {
			this.start();
		}
	} // end of startGame()
	
	
	// ------------- game life cycle methods ------------
	// called by the JFrame's window listener methods


	public void resumeGame()
	// called when the JFrame is activated / deiconified
	{  isPaused = false;  } 


	public void pauseGame()
	// called when the JFrame is deactivated / iconified
	{ isPaused = true;   } 


	public void stopGame() 
	// called when the JFrame is closing
	{  running = false;   }

	// ----------------------------------------------
	
	private void gameUpdate() 
	{ 
		if (!isPaused && !gameOver) {
					//fred.move();
		}
	}  // end of gameUpdate()


	private void gameRender()
	{
		if (gamePanel.getWidth() > 0 && gamePanel.getHeight() > 0 ){
			//dbImage = gamePanel.createImage(gamePanel.getWidth(), gamePanel.getHeight());
			if (dbImage == null) {
				//System.out.println("dbImage is null");
				return;
			}
			else
				dbg = dbImage.getGraphics();
		}



		// draw game elements: the obstacles and the worm
//		obs.draw(dbg);
//		fred.draw(dbg);

		if (gameOver)
			gameOverMessage(dbg);
	}  // end of gameRender()


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
