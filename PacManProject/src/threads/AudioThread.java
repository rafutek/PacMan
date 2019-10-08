package threads;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import resources.Resources;

public abstract class AudioThread extends ThreadPerso{
	
	

	protected Resources rsc = new Resources();
	
	private AudioInputStream audioStream;
	protected Clip audioClip;
	private File soundFile;
	
	protected boolean isRunning;
	protected boolean isPlaying;
	protected float vol = 1;
	private float volBefore = vol;
	protected boolean mute = false;
	
	
	public AudioThread(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
	}
	
	/** 
	 * What the thread is doing while running
	 */
	protected void doThat() {
		
		if(audioClip != null && !audioClip.isRunning()) {
			isPlaying = false;
		}else if(audioClip != null && audioClip.isRunning()) {
			if(vol != volBefore) {
				setVolume(vol);
				volBefore = vol;
			}
		}
	}
	
	
	/**
     * Play the audio of your choice: 
     * 1 for beginning, 2 for eating pac-gom, 3 for death 
     * @throws IOException 
     * @throws UnsupportedAudioFileException 
     * @throws LineUnavailableException 
     */
	
	public synchronized void playAudio(String audioFilename) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
			if (!mute && !isPlaying) {
				if (audioClip != null) {
					audioClip.stop();
					audioClip.close();
				}
				soundFile = new File(rsc.getSoundPath(audioFilename));
		 		audioStream = AudioSystem.getAudioInputStream(soundFile);
		    	audioClip = AudioSystem.getClip();
		    	audioClip.open(audioStream);
		    	settings();
		    	System.out.println("play "+soundFile.getName());
		    	isPlaying = true;
			    audioClip.start(); // start playing sound 
			    
			}
	}
	
	/**
	 * Stop AudioThread Method
	 */
	
	protected void doThatAtStop() {
		
		if(isPlaying) {
			audioClip.stop();
			audioClip.close();
			isPlaying = false;
		}
		isRunning = false;
		this.notify();
	}
	
	/**
	 * Abstract Methods implemented in music and sound Thread
	 */
	
	protected abstract void settings() ;


	/**
	 * Methods to set up volume and mute
	 * @param vol2
	 */
	public boolean getMute() {
		return mute;
	}
	
	public void setMute(boolean mute) {
		this.mute = mute;
		if(audioClip != null) {
			System.out.println("Mute: " + this.mute);
			BooleanControl muteControl = (BooleanControl) audioClip.getControl( BooleanControl.Type.MUTE );
			muteControl.setValue(this.mute);	
		}
	}	 
	 
	protected synchronized void volumeUp() {
		if(!mute) {
			if(vol <= 0.9) {
				vol += 0.1;
			}
			System.out.println("Volume: " + vol);	
		}
		
	}
	 
	protected synchronized void volumeDown() {
		if(!mute) {
			if(vol >=0.1) {
				vol -= 0.1;
			}
			System.out.println("Volume: " + vol);	
		}
	}
	
	
	protected synchronized void setVolume(float volume)
	{
		if (audioClip != null) {
			if (audioClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl gainControl = (FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN);
				float range = gainControl.getMaximum( ) - gainControl.getMinimum( );
				float gain = (range * volume) + gainControl.getMinimum( );
				gainControl.setValue(gain);
			}
			else
				System.out.println("No Volume controls available");
		}
	}
	
	/**
	 * methods that returns the current audioClip
	 * @return
	 */
	public synchronized Clip getClip() {
		return audioClip;
	}
	
}
