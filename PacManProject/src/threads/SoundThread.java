package threads;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundThread extends AudioThread {
	
	private float vol = 1;
	

	public SoundThread(String threadName) {
		super(threadName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized void volumeUp(int x) {
		if(!mute) {
			for (int i=1;i<=x;i++) {
				if(vol <= 0.9) {
					vol += 0.1;
				}
				System.out.println("Volume: " + vol);	
				setVolume(vol);
			}
		}
	}
	
	@Override
	public synchronized void volumeDown(int x) {
		if(!mute) {
				if(vol >= x*0.1) {
					vol = (float) (vol -x*0.1);
				}
				System.out.println("Volume: " + vol);	
				setVolume(vol);
			}
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
