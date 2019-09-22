package main;

import view.GameFrame;

public class Main {
	
	private static int DEFAULT_FPS = 80;

	public static void main(String[] args) {

		int fps = DEFAULT_FPS;

		int period = (int) 1000.0/fps;
		System.out.println("fps: " + fps + "; period: " + period + " ms");

		new GameFrame(period);    // ms
	}

}
