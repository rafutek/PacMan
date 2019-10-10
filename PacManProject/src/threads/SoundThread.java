package threads;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundThread extends AudioThread {
	



	private static final long SLEEP_TIME = 780;
	private ArrayList<Boolean> eatGomme = new ArrayList<Boolean>();
	private ArrayList<Boolean> eatEnergizer = new ArrayList<Boolean>();
	private ArrayList<Boolean> death = new ArrayList<Boolean>();
	private ArrayList<Boolean> eatGhost = new ArrayList<Boolean>();
	private ArrayList<Boolean> life = new ArrayList<Boolean>();
	private boolean pacmanIsDead=false;

	

	public SoundThread() {
		super("Sound"); 
	}

	/**
	 * @param pacmanIsDead the pacmanIsDead to set
	 */
	public synchronized void setPacmanIsDead(boolean pacmanIsDead) {
		this.pacmanIsDead = pacmanIsDead;
	}
	
	public synchronized void addEatGomme() {
		eatGomme.add(true);
	}
	
	public synchronized void addDeath() {
		death.add(true);
	}
	
	public synchronized void addEatGhost() {
		eatGhost.add(true);
	}
	
	public synchronized void addEatEnergizer() {
		eatEnergizer.add(true);
	}
	
	public synchronized void addLife() {
		life.add(true);
	}
	
	@Override
	protected void settings() {}


	@Override
	protected void doThatAtStart() {}


	@Override
	protected void doThat() {
		// TODO Auto-generated method stub
		if (!pacmanIsDead) {
			if (death.size()>0) {
				death.remove(0);
				try {
					isPlaying = false;
					playAudio("death.wav");
					setVolume(vol);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(eatGomme.size()>0) {
				eatGomme.remove(0);
				try {
					isPlaying = false;
					playAudio("chomp.wav");
					setVolume(vol);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(eatGhost.size()>0) {
				eatGhost.remove(0);
				try {
					isPlaying = false;
					playAudio("eatghost.wav");
					setVolume(vol);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (life.size()>0) {
				life.remove(0);
				try {
					isPlaying = false;
					playAudio("extrapac.wav");
					setVolume(vol);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if (eatEnergizer.size()>0) {
				eatEnergizer.remove(0);
				try {
					isPlaying = false;
					playAudio("chomp.wav");
					setVolume(vol);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			eatEnergizer.clear();
			eatGomme.clear();
			eatGhost.clear();
			death.clear();
			pacmanIsDead = false;
		}
	}


	@Override
	protected void doThatAtStop() {
		// TODO Auto-generated method stub
		
	}
	
}
