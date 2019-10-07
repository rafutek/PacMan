package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Resources;
import resources.Tiles;
import sprites.PacMan;

public class StatusBarPanel extends JPanel {
	PacMan pacman;
	Resources rsc = new Resources();
	public static Tiles t;
	private static final long serialVersionUID = 1L;
	public Integer s = 0;
	GridLayout g ;
	static JPanel Top;
	JPanel Bottom;
	public JLabel score;
	public static JLabel gameover;
	private static JLabel lives;
	JLabel direction;
	JLabel statut;
	public static JLabel livesImg;
	JLabel level;
	JLabel fps;
	JLabel valueFps;
	JLabel valueDirection;
	JLabel valueStatut;
	public static JLabel valueScore;
	JLabel valueLevel;
	public static BufferedImage Lives;
	public JPanel panelGameOver;
	public static BufferedImage gameOver;
	
	public StatusBarPanel() {
		setLayout(new GridLayout(2,0));
		TopPanel();
		BottomPanel();
		add(Top);
		add(Bottom);
	}
	public BufferedImage chargerPanelGameOver() {
		gameover= new JLabel();
		BufferedImage g = t.getTileNumber(45);
		BufferedImage ga = t.joinToRight(g , t.getTileNumber(39));
		BufferedImage gam = t.joinToRight(ga , t.getTileNumber(51));
		BufferedImage game= t.joinToRight(gam , t.getTileNumber(43));
		BufferedImage game_ = t.joinToRight(game ,t.getTileNumber(352));
		BufferedImage game_o = t.joinToRight(game_ ,t.getTileNumber(53));
		BufferedImage game_ov = t.joinToRight(game_o ,t.getTileNumber(60));
		BufferedImage game_ove = t.joinToRight(game_ov ,t.getTileNumber(43));
		gameOver = t.joinToRight(game_ove ,t.getTileNumber(56));
		gameOver = t.resize(Lives, new Dimension(72,18));
		//gameover.setIcon(new ImageIcon(gameOver));
		return gameOver;
	}
	
	
	
	
	public void gameOverPanel() {
		chargerPanelGameOver();
		panelGameOver.add(gameover);
	}
	public void TopPanel() {
		
		Top = new JPanel();
		Top.setLayout(new GridLayout(0,6));
		Top.setBackground(Color.black);
		try {
			t = new Tiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		score = new JLabel();
		score.setFont(new Font("Tahoma", Font.PLAIN, 10));
		score.setForeground(Color.WHITE);
		BufferedImage s = t.getTileNumber(57);
		BufferedImage sc = t.joinToRight(s , t.getTileNumber(41));
		BufferedImage sco = t.joinToRight(sc , t.getTileNumber(53));
		BufferedImage scor = t.joinToRight(sco , t.getTileNumber(56));
		BufferedImage scoref = t.joinToRight(scor , t.getTileNumber(43));
		scoref = t.resize(scoref, new Dimension(50,18));
		score.setIcon(new ImageIcon(scoref));
		
		valueScore = new JLabel("000000");
		valueScore.setFont(new Font("Tahoma", Font.PLAIN, 17));
		valueScore.setForeground(Color.WHITE);
		
		
		lives = new JLabel();
		BufferedImage L = t.getTileNumber(50);
		BufferedImage LI = t.joinToRight(L , t.getTileNumber(47));
		BufferedImage LIV = t.joinToRight(LI , t.getTileNumber(60));
		BufferedImage LIVE = t.joinToRight(LIV , t.getTileNumber(43));
		BufferedImage LIVES = t.joinToRight(LIVE , t.getTileNumber(57));
		LIVES = t.resize(LIVES, new Dimension(50,18));
		lives.setIcon(new ImageIcon(LIVES));
		
		livesImg = new JLabel();
		if(pacman!=null) {
			setImageLives(pacman.getLife());
		}
		if(Lives!=null) {
			livesImg.setIcon(new ImageIcon(Lives));

		}
		
		
		level = new JLabel();
		level.setFont(new Font("Tahoma", Font.PLAIN, 17));
		level.setForeground(Color.WHITE);
		BufferedImage L2 = t.getTileNumber(50);
		BufferedImage LE = t.joinToRight(L2 , t.getTileNumber(43));
		BufferedImage LEV = t.joinToRight(LE , t.getTileNumber(60));
		BufferedImage LEVE = t.joinToRight(LEV , t.getTileNumber(43));
		BufferedImage LEVEL = t.joinToRight(LEVE , t.getTileNumber(50));
		LEVEL = t.resize(LEVEL, new Dimension(50,18));
		level.setIcon(new ImageIcon(LEVEL));
		
		valueLevel = new JLabel("1");
		valueLevel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		valueLevel.setForeground(Color.WHITE);
		Top.add(score);
		Top.add(valueScore);
		Top.add(lives);
		Top.add(livesImg);
		Top.add(level);
		Top.add(valueLevel);
	}

	public void setPacman(PacMan pacman) {
		this.pacman = pacman;
	}
	public void BottomPanel() {
		Bottom = new JPanel();
		Bottom.setLayout(new GridLayout(0,6));
		Bottom.setBackground(new Color(0,0,0,100));

		
		try {
			t = new Tiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		direction = new JLabel("direction = ");
		direction.setFont(new Font("Tahoma", Font.PLAIN, 17));
		direction.setForeground(Color.WHITE);
		
		valueDirection = new JLabel("value");
		valueDirection.setFont(new Font("Tahoma", Font.PLAIN, 17));
		valueDirection.setForeground(Color.WHITE);

		
		
		statut= new JLabel("statut = ");
		statut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		statut.setForeground(Color.WHITE);
		
		valueStatut = new JLabel("Play");
		valueStatut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		valueStatut.setForeground(Color.WHITE);
		
		fps = new JLabel("FPS = ");
		fps.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fps.setForeground(Color.WHITE);
		
		valueFps = new JLabel("value");
		valueFps.setFont(new Font("Tahoma", Font.PLAIN, 17));
		valueFps.setForeground(Color.WHITE);

		
		
		
		Bottom.add(direction);
		Bottom.add(valueDirection);
		Bottom.add(statut);
		Bottom.add(valueStatut);
		Bottom.add(fps);
		Bottom.add(valueFps);
		
	}
	
	public JLabel getScore() {
		return valueStatut;
	}

	public void setScore(JLabel statut) {
		this.valueStatut = statut;
	}
	
	public static void setImageLives(int vie) {
		if(vie == 1) {
			Lives = t.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
			Lives = t.resize(Lives, new Dimension(18,18));
			
		} else if (vie == 2) {
			BufferedImage L1 = t.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
			Lives = t.joinToRight(L1, L1);
			Lives = t.resize(Lives, new Dimension(36,18));
			
		}else if (vie == 3) {
			BufferedImage L1 = t.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
			BufferedImage L2 = t.joinToRight(L1, L1);
			Lives = t.joinToRight(L2, L1);
			Lives = t.resize(Lives, new Dimension(54,18));
		}
		
		else if(vie==4){
			BufferedImage L1 = t.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
			BufferedImage L2 = t.joinToRight(L1, L1);
			BufferedImage L3 = t.joinToRight(L2, L1);
			Lives = t.joinToRight(L3, L1);
			Lives = t.resize(Lives, new Dimension(72,18));
		}
		else if(vie==0)  {
			BufferedImage g = t.getTileNumber(45);
			BufferedImage ga = t.joinToRight(g , t.getTileNumber(39));
			BufferedImage gam = t.joinToRight(ga , t.getTileNumber(51));
			BufferedImage game= t.joinToRight(gam , t.getTileNumber(43));
			BufferedImage game_ = t.joinToRight(game ,t.getTileNumber(352));
			BufferedImage game_o = t.joinToRight(game_ ,t.getTileNumber(53));
			BufferedImage game_ov = t.joinToRight(game_o ,t.getTileNumber(60));
			BufferedImage game_ove = t.joinToRight(game_ov ,t.getTileNumber(43));
			Lives = t.joinToRight(game_ove ,t.getTileNumber(56));
			Lives = t.resize(Lives, new Dimension(72,18));
			
			 
		}
	}
	static boolean f=true;
	
	public JLabel getLevel() {
		return valueStatut;
	}

	public void setLevel(JLabel statut) {
		this.valueStatut = statut;
	}
	
	public JLabel getFps() {
		return valueFps;
	}

	public void setFps(JLabel fps) {
		this.valueFps = fps;
	}
	
	public JLabel getDirection() {
		return valueDirection;
	}

	public void setDirection(JLabel direction) {
		this.valueDirection = direction;
	}
	
	public JLabel getStatut() {
		return valueStatut;
	}

	public void setStatut(JLabel statut) {
		this.valueStatut = statut;
	}
}


