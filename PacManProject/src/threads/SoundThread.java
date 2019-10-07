package threads;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundThread extends AudioThread {
	

	

	public SoundThread(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void settings() {
		// TODO Auto-generated method stub
		
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

//	
//	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
//		
//
//		int wait_delay = 3000, join_delay = 100;
//		
//		SoundThread soundsTh = new SoundThread("soundsTh");
//		soundsTh.start();
//		
//		synchronized(soundsTh) {
//			soundsTh.playAudio("chomp.wav");
//			soundsTh.wait(wait_delay);
//			soundsTh.playAudio("chomp.wav");
//			soundsTh.wait(wait_delay);
//			soundsTh.playAudio("chomp.wav");
//			soundsTh.wait(wait_delay);
//			soundsTh.playAudio("death.wav");
//			soundsTh.wait(wait_delay);
//			soundsTh.stopAudioThread();
//			//thread should be down now
//			soundsTh.wait(join_delay);
//			if(soundsTh.isAlive()) {
//				soundsTh.interrupt();
//			}
//		}
//	}

}
