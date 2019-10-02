package tests;


import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.Maze;

public class PrototypeMock extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private int windowWidth = 500;   
	private int windowHeight = 500; 
	private JPanel panel = new JPanel();
	private volatile boolean running = true;
	private Maze maze;
	
	Runnable renderLoop  = new Runnable() {
		@Override
		public void run() {
			while(running) {
				try { Thread.sleep(10); } catch (InterruptedException e) {}
				
				System.out.println("draw");
			}
		}
	};
	
	public PrototypeMock() throws IOException {
		super("Prototype");
		panel.setBackground(Color.black);	
		add(panel);
		setPreferredSize(new Dimension(windowWidth, windowHeight)); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		requestFocus();
		
		 maze = new Maze(panel, "mazeProto.txt");
		
		startRenderLoop();
	}
	
	
	private void startRenderLoop() {
		Thread renderLoopTh = new Thread(renderLoop);
		renderLoopTh.start();
	}
	
	
	public void stop() {
		running = false;
		System.out.println("stop");
		System.exit(0);
	}
	
	public static void main(String[] arg) throws InterruptedException, IOException {
		PrototypeMock proto = new PrototypeMock();
		
		Thread.sleep(5000);
		proto.stop();
	}

}
