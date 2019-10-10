package threads;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import resources.Tiles;
import sprites.MovingSpriteState;
import sprites.PacMan;

public class DeathAnimationThread extends ThreadPerso {

	private PacMan pacMan;
	
	Graphics dbg;
	
	private PhysicsThread physicsTh;
	
	public DeathAnimationThread(PhysicsThread physicsTh, PacMan pacMan) {
		super("DeathAnimationThread");
		this.pacMan=pacMan;
		this.physicsTh=physicsTh;
	}
	

	@Override
	protected void doThatAtStart() {
		physicsTh.pauseThread();
		pacMan.setState(MovingSpriteState.DEATH);

	}

	@Override
	protected void doThat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doThatAtStop() {
		physicsTh.resumeThread();
		
	}
	

}
