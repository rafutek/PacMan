package threads;

import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends AudioThread{
	
	private static boolean invincible = false;
	private boolean invincibleIsPlaying = false;

	
	public MusicThread(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
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
	protected void doThatAtStart() {
		// TODO Auto-generated method stub
		
		

	}

	@Override
	protected void doThat() {
		// TODO Auto-generated method stub
		
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
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

	@Override
	protected void doThatAtStop() {
		// TODO Auto-generated method stub
		
	}
	
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
