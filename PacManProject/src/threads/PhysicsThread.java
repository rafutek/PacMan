package threads;

import java.util.List;

import javax.swing.JPanel;

import sprites.MovingSprite;
import sprites.MovingSpriteState;
import sprites.Position;

public class PhysicsThread extends ThreadPerso {

	private List<List<Integer>> mazeValues;
	private JPanel gamePanel;
	private MovingSprite pacMan;
	private MovingSpriteState pacManWantedState;
	
	/**
	 * The class needs the maze number matrix, the game panel size and of course the moving sprites,
	 * in order to locate them in the matrix.
	 * The location of each sprite in the matrix allow them to go or not in the wanted direction.
	 * @param mazeValues
	 * @param gamePanel
	 * @param pacMan
	 */
	public PhysicsThread(List<List<Integer>> mazeValues, JPanel gamePanel, MovingSprite pacMan) {
		super("Physics");
		this.mazeValues = mazeValues;
		this.gamePanel = gamePanel;
		this.pacMan = pacMan;
	}

	@Override
	protected void doThatAtStart() {}

	@Override
	protected void doThat() {
		
		//pac-man
		pacManWantedState = pacMan.getWantedState();
		if(pacManWantedState != MovingSpriteState.STOP) {
			
			pacMan.setState(pacManWantedState); // must change of course
			
		}

	}

	@Override
	protected void doThatAtStop() {}
	
	/**
	 * Transform a game panel position in a maze matrix position.
	 * @param gamePanelPos is the position in the game panel.
	 * @return the position in the maze numbers matrix.
	 */
	private Position mazeToMatrixPosition(Position gamePanelPos) {
		int matPosX = (gamePanelPos.getX() * mazeValues.get(0).size()) / gamePanel.getWidth();
		int matPosY = (gamePanelPos.getY() * mazeValues.size()) / gamePanel.getHeight();
		return new Position(matPosX, matPosY);
	}

}
