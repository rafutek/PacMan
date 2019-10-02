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
		
		setLayout(new GridLayout(0,8));
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
		lives.setIcon(new ImageIcon(rsc.getImagePath("lives.PNG")));
		
		livesImg = new JLabel();
		livesImg.setIcon(new ImageIcon(rsc.getImagePath("lives3.png")));
		
		
		
		fullScreen = new JLabel("");
		fullScreen.setIcon(new ImageIcon(rsc.getImagePath("fullScreen.png")));
		fullScreen.setBounds(479, 393, 44, 35);
		
		
		direction = new JLabel();
		direction.setFont(new Font("Tahoma", Font.PLAIN, 17));
		direction.setForeground(Color.WHITE);
		direction.setText("Error");
		
		
		statut= new JLabel();
		statut.setFont(new Font("Tahoma", Font.PLAIN, 17));
		statut.setForeground(Color.WHITE);
		statut.setText("play");
		
		niveau = new JLabel();
		niveau.setFont(new Font("Tahoma", Font.PLAIN, 17));
		niveau.setForeground(Color.WHITE);
		niveau.setIcon(new ImageIcon(rsc.getImagePath("level.png")));
		
		fps = new JLabel("FPS");
		fps.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fps.setForeground(Color.WHITE);
		

		add(score);
		add(lives);
		add(livesImg);
		add(direction);
		add(statut);
		add(niveau);
		add(fps);
		add(fullScreen);
	}

}
