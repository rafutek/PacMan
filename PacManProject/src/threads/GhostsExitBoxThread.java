package threads;

import java.io.IOException;

import resources.Tiles;
import sprites.Blinky;
import sprites.Clyde;
import sprites.Ghost;
import sprites.Inky;
import sprites.Pinky;
import sprites.Position;

public class GhostsExitBoxThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 200; 
	
	private Ghost blinky, pinky, clyde, inky;
	private boolean ghostWantsToGoOut, blinkyWantsToGoOut, pinkyWantsToGoOut, clydeWantsToGoOut, inkyWantsToGoOut;
	
	public GhostsExitBoxThread(Ghost blinky, Ghost pinky, Ghost clyde, Ghost inky) {
		super(WAIT_TIME, NB_WAITS);
		this.blinky = blinky;
		this.pinky = pinky;
		this.clyde = clyde;
		this.inky = inky;
	}
	
	@Override
	protected void doThatAtStart() {}
	
	@Override
	protected void doThatWhileWaiting() {
		if(!ghostWantsToGoOut) {
			if(blinky.isInTheBox) {
				ghostWantsToGoOut = true;
				blinkyWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0; // reset the timer to make the ghost wait until...
			}
			else if(pinky.isInTheBox) {
				System.out.println("pinky wants to go out");
				ghostWantsToGoOut = true;
				pinkyWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0;
			}
			else if(clyde.isInTheBox) {
				System.out.println("clyde wants to go out");
				ghostWantsToGoOut = true;
				clydeWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0; 
			}
			else if(inky.isInTheBox) {
				System.out.println("inky wants to go out");
				ghostWantsToGoOut = true;
				inkyWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0; 
			}
		}
	}

	@Override
	protected void finallyDoThat() {
		
		if(ghostWantsToGoOut) {
			if(blinkyWantsToGoOut) {
				System.out.println("blinky goes out !"); // ...until he get out
				blinky.isInTheBox = false;
				blinkyWantsToGoOut = false;
				ghostWantsToGoOut = false;
				
			}
			else if(pinkyWantsToGoOut) {
				System.out.println("pinky goes out !");
				pinky.isInTheBox = false;
				pinkyWantsToGoOut = false;
				ghostWantsToGoOut = false;
			}
			else if(clydeWantsToGoOut) {
				System.out.println("clyde goes out !");
				clyde.isInTheBox = false;
				clydeWantsToGoOut = false;
				ghostWantsToGoOut = false;
			}
			else if(inkyWantsToGoOut) {
				System.out.println("inky goes out !");
				inky.isInTheBox = false;
				inkyWantsToGoOut = false;
				ghostWantsToGoOut = false;
			}
		}
	}


	//-------------------------------------------------------
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Ghost a,b,c,d;
		Tiles tiles = new Tiles();
		a = new Blinky(new Position(0, 0), tiles, null);
		b = new Pinky(new Position(0, 0), tiles, null);
		c = new Clyde(new Position(0, 0), tiles, null);
		d = new Inky(new Position(0, 0), tiles, null);
		
		a.isInTheBox = false;
		
		GhostsExitBoxThread gExitTh = new GhostsExitBoxThread(a, b, c, d);
		gExitTh.startThread();
		
		synchronized(gExitTh) {
			Thread.sleep(5000);
			gExitTh.stopThread();
			gExitTh.join(100);
			if(gExitTh.isRunning()) {
				gExitTh.interrupt();
			}
		}
	
	}

}
