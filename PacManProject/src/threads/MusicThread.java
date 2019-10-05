package threads;

import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends AudioThread{

	
	public MusicThread(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Play the music sound when the thread is started
	 */
	@Override
	protected void actionOnStart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		playAudio("beginning.wav");
	}

	/**
	 * Stop the thread when the music is finished
	 */
	@Override
	protected void settings() {
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	@Override
	protected void doThatAtStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doThat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doThatAtStop() {
		// TODO Auto-generated method stub
		
	}
}
