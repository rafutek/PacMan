package threads;

import sprites.PacMan;

public class InvincibleThread extends TimerThread {

	PacMan pacMan;
	private MusicThread musicTh;
	
	public InvincibleThread(PacMan pacMan , MusicThread musicTh) {
		super(10,800);
		setName("Invincibilite");
		this.pacMan=pacMan;
		this.musicTh = musicTh;
	}

	/**
	 * Set pac-man to invincible while thread is running.
	 */
	@Override
	protected void doThatWhileWaiting() {
		synchronized(pacMan) {
			pacMan.setInvincible(true);
		}
		synchronized(musicTh) {
			musicTh.setInvincibility(true);
		}
	}

	/**
	 * Reset normal pac-man mode.
	 */
	@Override
	protected void finallyDoThat() {
		synchronized(pacMan){
			pacMan.setInvincible(false);
			pacMan.setSpeed(1);
			pacMan.setEatenFantom(0);
		}
		synchronized(musicTh) {
			musicTh.setInvincibility(false);
		}
		stopThread();
	}

	@Override
	protected void doThatAtStart() {
		// TODO Auto-generated method stub
	}
}
