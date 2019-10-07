package threads;

import resources.Maze;
import sprites.Ghost;
import sprites.MovingSpriteState;
import sprites.Position;

public class GhostsExitBoxThread extends TimerThread {

	private static final int WAIT_TIME = 10;
	private static final int NB_WAITS = 100; 
	
	private Ghost blinky, pinky, clyde, inky;
	private Maze maze;
	private  boolean ghostWantsToGoOut, blinkyWantsToGoOut, pinkyWantsToGoOut, clydeWantsToGoOut, inkyWantsToGoOut;
	public static  boolean ghostCanGoOut, blinkyCanGoOut, pinkyCanGoOut, clydeCanGoOut, inkyCanGoOut;
	
	public GhostsExitBoxThread(Ghost blinky, Ghost pinky, Ghost clyde, Ghost inky, Maze maze) {
		super(WAIT_TIME, NB_WAITS);
		setName("Ghosts Exit");
		this.blinky = blinky;
		this.pinky = pinky;
		this.clyde = clyde;
		this.inky = inky;
		this.maze = maze;
	}
	
	@Override
	protected void doThatAtStart() {}
	
	@Override
	protected void doThatWhileWaiting() {
		if(!ghostWantsToGoOut) {
			if(blinky.isInTheBox()) {
				ghostWantsToGoOut = true;
				blinkyWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0; // reset the timer to make the ghost wait until...
			}
			else if(pinky.isInTheBox()) {
				ghostWantsToGoOut = true;
				pinkyWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0;
			}
			else if(clyde.isInTheBox()) {
				ghostWantsToGoOut = true;
				clydeWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0; 
			}
			else if(inky.isInTheBox()) {
				ghostWantsToGoOut = true;
				inkyWantsToGoOut = ghostWantsToGoOut;
				counterWaits = 0; 
			}
			
		}
		else if(ghostCanGoOut){ 
			if(blinkyCanGoOut) {
				if(manageGhostExit(blinky)) {
					blinkyWantsToGoOut = false;
					blinkyCanGoOut = false;
				}
			}
			else if(pinkyCanGoOut) {
				if(manageGhostExit(pinky)) {
					pinkyWantsToGoOut = false;
					pinkyCanGoOut = false;
				}
			}
			else if(clydeCanGoOut) {
				if(manageGhostExit(clyde)) {
					clydeWantsToGoOut = false;
					clydeCanGoOut = false;
				}
			}
			else if(inkyCanGoOut) {
				if(manageGhostExit(inky)) {
					inkyWantsToGoOut = false;
					inkyCanGoOut = false;
				}
			}
		}
	}

	@Override
	protected void finallyDoThat() {
		
		if(ghostWantsToGoOut && !ghostCanGoOut) {
			ghostCanGoOut = true;
			
			if(blinkyWantsToGoOut) {
				blinkyCanGoOut = true;
			}
			else if(pinkyWantsToGoOut) {
				pinkyCanGoOut = true;
			}
			else if(clydeWantsToGoOut) {
				clydeCanGoOut = true;
			}
			else if(inkyWantsToGoOut) {
				inkyCanGoOut = true;
			}
		}
	}

	/**
	 * Change the ghost state until he is out of the box, then launch its direction thread.
	 * @param ghost
	 * @return true if the ghost is out
	 */
	private boolean manageGhostExit(Ghost ghost) {
		Position doorPosition = maze.getDoorPosition();
		if(ghost.getCurrentPosition().getX() > doorPosition.getX() - 5 && ghost.getCurrentPosition().getX() < doorPosition.getX() + 5) {
			ghost.setState(MovingSpriteState.UP);
			if(ghost.getCurrentPosition().getY() < doorPosition.getY()-maze.tileDim.height/2){        // if the ghost is out, start its direction thread
				
				ghost.setInTheBox(false);
				ghostWantsToGoOut = false;
				ghostCanGoOut = false;
				
				if(ghost.getBehaviorThread() == null || !ghost.getBehaviorThread().isRunning()) {
					ghost.startDirectionThread();
				}
				ghost.getBehaviorThread().changeDirection();
				
				return true;
			}
		}
		else if(ghost.getCurrentPosition().getX() < doorPosition.getX()) {
			ghost.setState(MovingSpriteState.RIGHT);
		}
		else if(ghost.getCurrentPosition().getX() > doorPosition.getX()) {
			ghost.setState(MovingSpriteState.LEFT);
		}
		return false;			
	}

}
