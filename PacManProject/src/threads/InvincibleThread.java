package threads;

import sprites.PacMan;

public class InvincibleThread extends TimerThread {

	PacMan pacMan;
	
	public InvincibleThread(PacMan pacMan) {
		super(10,800);
		setName("Invincibilite");
		this.pacMan=pacMan;
	}

	@Override
	protected void doThatWhileWaiting() {
		pacMan.setInvincible(true);
		// TODO Auto-generated method stub
	}

	@Override
	protected void finallyDoThat() {
		pacMan.setInvincible(false);
		stopThread();
	}

	@Override
	protected void doThatAtStart() {
		// TODO Auto-generated method stub
	}
}
