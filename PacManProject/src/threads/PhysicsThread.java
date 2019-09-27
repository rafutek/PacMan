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
			
			Position currentMatrixPos = mazeToMatrixPosition(pacMan.getCurrentPosition());
			
			int wantedBoxValue = -1;
			if(pacManWantedState == MovingSpriteState.LEFT) {
				// check the box value where pac-man wants to go
				wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()-1);
			}
			else if(pacManWantedState == MovingSpriteState.RIGHT) {
				wantedBoxValue = mazeValues.get(currentMatrixPos.getY()).get(currentMatrixPos.getX()+1);
			}
			else if(pacManWantedState == MovingSpriteState.UP) {
				wantedBoxValue = mazeValues.get(currentMatrixPos.getY()-1).get(currentMatrixPos.getX());
			}
			else if(pacManWantedState == MovingSpriteState.DOWN) {
				wantedBoxValue = mazeValues.get(currentMatrixPos.getY()+1).get(currentMatrixPos.getX());
			}
			
			System.out.println(wantedBoxValue);
			if(wantedBoxValue == 0 || wantedBoxValue == 97 || wantedBoxValue == 13) {
				pacMan.setState(pacManWantedState); // if pac-man can go to that state
			}else {
				pacMan.setState(MovingSpriteState.STOP);
			}
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
		int matPosX = (int)Math.round((gamePanelPos.getX() * mazeValues.get(0).size()) / (double)gamePanel.getWidth());
		int matPosY = (int)Math.round((gamePanelPos.getY() * mazeValues.size()) / (double)gamePanel.getHeight());
		return new Position(matPosX, matPosY);
	}

}
