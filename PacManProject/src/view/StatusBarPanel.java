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
import sprites.Sprite;

public class StatusBarPanel extends JPanel {

	Resources rsc = new Resources();
	Tiles t;
	private static final long serialVersionUID = 1L;
	public Integer s = 0;
	GridLayout g ;
	public JLabel score;
	JLabel lives;
	JLabel fullScreen;
	JLabel direction;
	JLabel statut;
	JLabel livesImg;
	JLabel niveau;
	JLabel fps;
	
	public StatusBarPanel() {
		
		setLayout(new GridLayout(0,4));
		setBackground(Color.black);
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
		
		
		lives = new JLabel();
		BufferedImage L = t.getTileNumber(50);
		BufferedImage LI = t.joinToRight(L , t.getTileNumber(47));
		BufferedImage LIV = t.joinToRight(LI , t.getTileNumber(60));
		BufferedImage LIVE = t.joinToRight(LIV , t.getTileNumber(43));
		BufferedImage LIVES = t.joinToRight(LIVE , t.getTileNumber(57));
		LIVES = t.resize(LIVES, new Dimension(50,18));
		lives.setIcon(new ImageIcon(LIVES));
		
		livesImg = new JLabel();
		BufferedImage life = t.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
		life = t.resize(life, new Dimension(18,18));
		livesImg.setIcon(new ImageIcon(life));
		
		/*
		direction = new JLabel();
		direction.setFont(new Font("Tahoma", Font.PLAIN, 17));
		direction.setForeground(Color.WHITE);
		direction.setText("Error");
		
		
		statut= new JLabel();
		statut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		statut.setForeground(Color.WHITE);
		statut.setText("play");
		*/
		niveau = new JLabel();
		niveau.setFont(new Font("Tahoma", Font.PLAIN, 17));
		niveau.setForeground(Color.WHITE);
		BufferedImage L2 = t.getTileNumber(50);
		BufferedImage LE = t.joinToRight(L2 , t.getTileNumber(43));
		BufferedImage LEV = t.joinToRight(LE , t.getTileNumber(60));
		BufferedImage LEVE = t.joinToRight(LEV , t.getTileNumber(43));
		BufferedImage LEVEL = t.joinToRight(LEVE , t.getTileNumber(50));
		LEVEL = t.resize(LEVEL, new Dimension(50,18));
		niveau.setIcon(new ImageIcon(LEVEL));
		
		/*
		fps = new JLabel("FPS");
		fps.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fps.setForeground(Color.WHITE);
		*/

		add(score);
		add(lives);
		add(livesImg);
		/*add(direction);
		add(statut)*/;
		add(niveau);
		//add(fps);
	}

}
