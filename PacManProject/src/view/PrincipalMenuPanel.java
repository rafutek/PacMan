package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Resources;
import resources.Tiles;

public class PrincipalMenuPanel extends JPanel implements KeyListener{
	Tiles t;
	private static final long serialVersionUID = 1L;

	private JLabel pacManTitle;
	private JLabel startGame;
	private JLabel audio;
	private JLabel exitGame;
	private JLabel controls;
	private JLabel highScores;
	private JLabel pacManIcon;
	private int coordX=100;
	private int coordY= 200;
	public boolean CloseGame=false;
	
	
	private GameFrame gameFrame;
	
	public PrincipalMenuPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		setBackground(Color.black);	
		setSize(620, 700);
		setLayout(null);
		try {
			t = new Tiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pacManTitle = new JLabel("");
		// Build the PACMAN Title tile from the tileSheet
		BufferedImage p1 = Tiles.createWord(t.getTileNumber(73), t.getTileNumber(74),t.getTileNumber(75),t.getTileNumber(76),t.getTileNumber(77),t.getTileNumber(78),t.getTileNumber(79),t.getTileNumber(80));
		BufferedImage p2 = Tiles.createWord(t.getTileNumber(89),t.getTileNumber(90),t.getTileNumber(91), t.getTileNumber(92), t.getTileNumber(93), t.getTileNumber(94),t.getTileNumber(95), t.getTileNumber(96));
		BufferedImage PACMAN = Tiles.joinBelow(p1, p2);		
		PACMAN = Tiles.resize(PACMAN, new Dimension(400,100));
		pacManTitle.setIcon(new ImageIcon(PACMAN));
		pacManTitle.setBounds(100, 0, 400, 100);
		
		startGame = new JLabel();
		BufferedImage start = Tiles.createWord(t.getTileNumber(57),t.getTileNumber(58),t.getTileNumber(39),t.getTileNumber(56),t.getTileNumber(58));
		start = Tiles.resize(start, new Dimension(180,50));
		startGame.setIcon(new ImageIcon(start));
		startGame.setBounds(160, 200, 250, 50);
		add(startGame);
	

		audio= new JLabel();
		BufferedImage audioImage = Tiles.createWord(t.getTileNumber(39),t.getTileNumber(59),t.getTileNumber(42),t.getTileNumber(47),t.getTileNumber(53));
		audioImage = Tiles.resize(audioImage, new Dimension(180,50));
		audio.setIcon(new ImageIcon(audioImage));
		audio.setBounds(160, 280, 300, 50);
		
		controls = new JLabel();
		BufferedImage controlsImage = Tiles.createWord(t.getTileNumber(41),t.getTileNumber(53),t.getTileNumber(52),t.getTileNumber(58),t.getTileNumber(56),t.getTileNumber(53), t.getTileNumber(50),t.getTileNumber(57));
		controlsImage = Tiles.resize(controlsImage, new Dimension(250,50));
		controls.setIcon(new ImageIcon(controlsImage));
		controls.setBounds(160, 360, 300, 50);
		
		highScores = new JLabel();
		
		BufferedImage high_scores = Tiles.createWord(t.getTileNumber(46),t.getTileNumber(47),t.getTileNumber(45),t.getTileNumber(46),t.getTileNumber(352),t.getTileNumber(57),t.getTileNumber(41),t.getTileNumber(53),t.getTileNumber(56),t.getTileNumber(43),t.getTileNumber(57));
		high_scores = Tiles.resize(high_scores, new Dimension(300,50));
		highScores.setIcon(new ImageIcon(high_scores));
		highScores.setBounds(160, 440, 300, 50);
		
		exitGame = new JLabel();
		BufferedImage quit = Tiles.createWord(t.getTileNumber(55),t.getTileNumber(59),t.getTileNumber(47),t.getTileNumber(58));
		quit = Tiles.resize(quit, new Dimension(140,50));
		exitGame.setIcon(new ImageIcon(quit));
		exitGame.setBounds(160, 520, 300, 50);
		
	
		pacManIcon= new JLabel();
		BufferedImage pacManImage = Tiles.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
		pacManImage = Tiles.resize(pacManImage, new Dimension(40,40));
		pacManIcon.setIcon(new ImageIcon(pacManImage));
		pacManIcon.setBounds(coordX, coordY, 50, 50);
		
		add(pacManTitle);
		add(highScores);
		add(exitGame);
		add(controls);
		add(pacManIcon);
		add(audio);
				
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key== KeyEvent.VK_DOWN) {
			if(getCoordY()<520) {
				setCoordY(getCoordY()+80);
				pacManIcon.setBounds(coordX, getCoordY(), 50, 50);
			}
			else {
				setCoordY(200);
				pacManIcon.setBounds(coordX, getCoordY(), 50, 50);
			}	
		}
		if(key== KeyEvent.VK_UP) {
			if(getCoordY()>200) {
				setCoordY(getCoordY()-80);
				pacManIcon.setBounds(coordX, getCoordY(), 50, 50);
			}
			else {
				setCoordY(520);
				pacManIcon.setBounds(coordX, getCoordY(), 50, 50);
			}	
		}
		
		if(key== KeyEvent.VK_ENTER) {
			if(getCoordY()==200) {
				System.out.println("start game");	
				gameFrame.setPage("Game");
				System.out.println(gameFrame.getPage());
			}
			if(getCoordY()==280) {
				System.out.println("Audio");
				System.out.println("start Audio");	
				gameFrame.setPage("Audio");
				System.out.println("page audio ................"+gameFrame.getPage());
				System.out.println(gameFrame.getPage());
			}
			if(getCoordY()==360) {
				System.out.println("Control");
				System.out.println("start ControlsMenu");	
				gameFrame.setPage("Controls");
				System.out.println("page audio ................"+gameFrame.getPage());
				System.out.println(gameFrame.getPage());
			}
			if(getCoordY()==440) {
				System.out.println("hightScores");
				System.out.println("start HighScoresMenu");	
				gameFrame.setPage("HighScores");
				System.out.println("page audio ................"+gameFrame.getPage());
				System.out.println(gameFrame.getPage());				
			}
			if(getCoordY()==520) {
				System.out.println("Quit Game");
				if(gameFrame != null) {
					gameFrame.closeGame();
					System.exit(0);   // so window disappears	

				}
			}
		}		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	public int getCoordX() {
		return coordX;
	}
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}
	public int getCoordY() {
		return coordY;
	}
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}
	public JLabel getStartGame() {
		return startGame;
	}

	public void setStartGame(JLabel startGame) {
		this.startGame = startGame;
	}

	
	/*public static void main(String[] args) {
		JFrame f = new JFrame();
		PrincipalMenuPanel p = new PrincipalMenuPanel();
		f.setSize(620, 700);
		f.add(p);
		f.addKeyListener(p);
		f.setVisible(true);
	}
*/

}
