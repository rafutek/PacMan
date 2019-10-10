package threads;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundThread extends AudioThread {
	
	private ArrayList<Boolean> eatGomme = new ArrayList<Boolean>();
	private ArrayList<Boolean> death = new ArrayList<Boolean>();
	private ArrayList<Boolean> eatGhost = new ArrayList<Boolean>();
	private ArrayList<Boolean> life = new ArrayList<Boolean>();

	

	public SoundThread() {
		super("Sound"); 
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
	
	public synchronized void addLife() {
		life.add(true);
	}
	
	@Override
	protected void settings() {}


	@Override
	protected void doThatAtStart() {}


	@Override
	protected void doThat() {
		try {
			if (eatGomme.size()>0) {
				eatGomme.remove(0);
				isPlaying = false;
				playAudio("chomp.wav");
				setVolume(vol);

			}else if(death.size()>0) {
				isPlaying = false;
				playAudio("death.wav");
				setVolume(vol);

			} else if(eatGhost.size()>0) {
				isPlaying = false;
				playAudio("eatghost.wav");
				setVolume(vol);

			} else if (life.size()>0) {
				isPlaying = false;
				playAudio("extrapac.wav");
				setVolume(vol);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void doThatAtStop() {}

}
