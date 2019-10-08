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
		synchronized(pacMan) {
		pacMan.setInvincible(true);
		}
	}

	@Override
	protected void finallyDoThat() {
		synchronized(pacMan){
		pacMan.setInvincible(false);
		pacMan.setSpeed(1);
		pacMan.setEatenFantom(0);
		}
		stopThread();
	}

	@Override
	protected void doThatAtStart() {
		// TODO Auto-generated method stub
	}
}
