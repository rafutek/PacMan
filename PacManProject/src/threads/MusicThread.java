package threads;

import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends AudioThread{
	
	private static boolean invincible = false;
	private boolean invincibleIsPlaying = false;

	
	public MusicThread() {
		super("Music");
	}
	 
	/**
	 * Stop the thread when the music is finished
	 */
	@Override
	protected void settings() {
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Play the music sound when the thread is started
	 */
	@Override
	protected void doThatAtStart() {}

	@Override
	protected void doThat() {		
		try {
			if(!invincible) {
				IsPlaying();
				playAudio("beginning.wav");
				setVolume(vol);
			} else if(isPlaying) {
					InvinsibilityIsPlaying();
					playAudio("intermission.wav");
					setVolume(vol);					
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}		
	}

	@Override
	protected void doThatAtStop() {}
	
	public synchronized void setInvincibility(boolean b) {
		invincible = b;
	}
	
	public synchronized void InvinsibilityIsPlaying() {
		if(!invincibleIsPlaying) {
			audioClip.stop();
			audioClip.close();
			isPlaying = false;
			this.invincibleIsPlaying = true;
		}
	}
	
	public synchronized void IsPlaying() {
		if(invincibleIsPlaying) {
			audioClip.stop();
			audioClip.close();
			isPlaying = false;
			this.invincibleIsPlaying = false;
		}
	}
}
