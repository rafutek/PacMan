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
					playAudio("beginning.wav");
				} else if(isPlaying) {
						InvinsibilityIsPlaying();
						playAudio("intermission.wav");
					
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
	
	public static void setInvincibility(boolean b) {
		invincible = b;
	}
	
	public void InvinsibilityIsPlaying() {
		if(!invincibleIsPlaying) {
			audioClip.stop();
			audioClip.close();
			isPlaying = false;
			this.invincibleIsPlaying = true;
		}
	}
	
//	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
//		
//
//		int wait_delay = 10000, join_delay = 100;
//		
//		MusicThread musicth = new MusicThread("music");
//		musicth.start();
//		
//		synchronized(musicth) {
//			//musicth.playAudio("chomp.wav");
//			musicth.wait(wait_delay);
//			musicth.stopThread();
//			musicth.stopAudioThread();
//			//thread should be down now
//			musicth.wait(join_delay);
//			if(musicth.isAlive()) {
//				musicth.interrupt();
//			}
//		}
//	}
}
