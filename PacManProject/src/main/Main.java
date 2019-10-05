package main;

import view.GameFrame;

public class Main {
	
	private static int DEFAULT_FPS = 50;
	static GameFrame globalFrame;

	public static void main(String[] args) {

		int fps = DEFAULT_FPS;

		int period = (int) 1000.0/fps;
		System.out.println("fps: " + fps + "; period: " + period + " ms");

		globalFrame = new GameFrame(period);    // ms
	}

	public static GameFrame getGlobalFrame() {
		return globalFrame;
	}

	public static void setGlobalFrame(GameFrame globalFrame) {
		Main.globalFrame = globalFrame;
	}


}
