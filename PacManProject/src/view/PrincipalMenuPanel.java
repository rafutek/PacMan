package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;
import resources.Resources;
import resources.Tiles;
import threads.CheckPageThread;
import threads.TimerThread;

public class PrincipalMenuPanel extends JPanel implements KeyListener{
	Resources rsc = new Resources();
	Tiles t;
	private static final long serialVersionUID = 1L;

	private JLabel pacManTitle;
	private JLabel startGame;
	private JLabel resumeGame;
	private JLabel AudioGame;
	private JLabel audio;
	private JLabel exitGame;
	private JLabel controls;
	private JLabel hightScores;
	private JLabel pacManIcon;
	private int coordX=100;
	private int coordY= 200;
	public boolean CloseGame=false;
	
	CheckPageThread checkPageThread;
	
	public PrincipalMenuPanel() {
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
		BufferedImage p1 = t.getTileNumber(73);
		BufferedImage p2 = t.joinToRight(p1 , t.getTileNumber(74));
		BufferedImage p3 = t.joinToRight(p2 , t.getTileNumber(75));
		BufferedImage p4 = t.joinToRight(p3 , t.getTileNumber(76));
		BufferedImage p5 = t.joinToRight(p4 , t.getTileNumber(77));
		BufferedImage p6 = t.joinToRight(p5 , t.getTileNumber(78));
		BufferedImage p7 = t.joinToRight(p6 , t.getTileNumber(79));
		BufferedImage p8 = t.joinToRight(p7 , t.getTileNumber(80));
		BufferedImage p9 = t.getTileNumber(89);
		BufferedImage p10 = t.joinToRight(p9 , t.getTileNumber(90));
		BufferedImage p11 = t.joinToRight(p10 , t.getTileNumber(91));
		BufferedImage p12 = t.joinToRight(p11 , t.getTileNumber(92));
		BufferedImage p13 = t.joinToRight(p12 , t.getTileNumber(93));
		BufferedImage p14 = t.joinToRight(p13 , t.getTileNumber(94));
		BufferedImage p15 = t.joinToRight(p14 , t.getTileNumber(95));
		BufferedImage p16 = t.joinToRight(p15 , t.getTileNumber(96));	
		BufferedImage PACMAN = t.joinBelow(p8, p16);
		
		PACMAN = t.resize(PACMAN, new Dimension(400,100));
		pacManTitle.setIcon(new ImageIcon(PACMAN));
		pacManTitle.setBounds(100, 0, 400, 100);
		
		//if(statut==0) {
		startGame = new JLabel();
		BufferedImage s = t.getTileNumber(57);
		BufferedImage st = t.joinToRight(s , t.getTileNumber(58));
		BufferedImage sta = t.joinToRight(st , t.getTileNumber(39));
		BufferedImage star = t.joinToRight(sta , t.getTileNumber(56));
		BufferedImage start = t.joinToRight(star , t.getTileNumber(58));
		start = t.resize(start, new Dimension(180,50));
		startGame.setIcon(new ImageIcon(start));
		startGame.setBounds(160, 200, 250, 50);
		add(startGame);
		//}
		//if(statut==1) {
		resumeGame = new JLabel();
		//resumeGame.setIcon(new ImageIcon(getClass().getResource("../ressources/images/resumeGame.PNG")));
		resumeGame.setBounds(160, 200, 230, 50);
		add(resumeGame);
		//}
		audio= new JLabel();
		BufferedImage a = t.getTileNumber(39);
		BufferedImage au = t.joinToRight(a , t.getTileNumber(59));
		BufferedImage aud = t.joinToRight(au , t.getTileNumber(42));
		BufferedImage audi = t.joinToRight(aud , t.getTileNumber(47));
		BufferedImage audioImage = t.joinToRight(audi , t.getTileNumber(53));
		audioImage = t.resize(audioImage, new Dimension(180,50));
		audio.setIcon(new ImageIcon(audioImage));
		audio.setBounds(160, 280, 300, 50);
		
		controls = new JLabel();
		BufferedImage c = t.getTileNumber(41);
		BufferedImage co = t.joinToRight(c , t.getTileNumber(53));
		BufferedImage con = t.joinToRight(co , t.getTileNumber(52));
		BufferedImage cont = t.joinToRight(con , t.getTileNumber(58));
		BufferedImage contr = t.joinToRight(cont , t.getTileNumber(56));
		BufferedImage contro = t.joinToRight(contr , t.getTileNumber(53));
		BufferedImage control = t.joinToRight(contro , t.getTileNumber(50));
		BufferedImage controlsImage = t.joinToRight(control , t.getTileNumber(57));
		controlsImage = t.resize(controlsImage, new Dimension(250,50));
		controls.setIcon(new ImageIcon(controlsImage));
		controls.setBounds(160, 360, 300, 50);
		
		hightScores = new JLabel();
		
		BufferedImage h = t.getTileNumber(46);
		BufferedImage hi = t.joinToRight(h , t.getTileNumber(47));
		BufferedImage hig = t.joinToRight(hi , t.getTileNumber(45));
		BufferedImage high = t.joinToRight(hig , t.getTileNumber(46));
		BufferedImage hight = t.joinToRight(high , t.getTileNumber(58));
		BufferedImage hight_ = t.joinToRight(hight , t.getTileNumber(352));
		BufferedImage hight_s = t.joinToRight(hight_ , t.getTileNumber(57));
		BufferedImage hight_sc = t.joinToRight(hight_s , t.getTileNumber(41));
		BufferedImage hight_sco = t.joinToRight(hight_sc , t.getTileNumber(53));
		BufferedImage hight_scor = t.joinToRight(hight_sco , t.getTileNumber(56));
		BufferedImage hight_scors = t.joinToRight(hight_scor , t.getTileNumber(57));
		hight_scors = t.resize(hight_scors, new Dimension(300,50));
		hightScores.setIcon(new ImageIcon(hight_scors));
		hightScores.setBounds(160, 440, 300, 50);
		
		exitGame = new JLabel();
		
		BufferedImage q = t.getTileNumber(55);
		BufferedImage qu = t.joinToRight(q , t.getTileNumber(59));
		BufferedImage qui = t.joinToRight(qu , t.getTileNumber(47));
		BufferedImage quit = t.joinToRight(qui , t.getTileNumber(58));
		quit = t.resize(quit, new Dimension(140,50));
		exitGame.setIcon(new ImageIcon(quit));
		exitGame.setBounds(160, 520, 300, 50);
		
	
		pacManIcon= new JLabel();
		BufferedImage p = t.getTileNumber(105);
		BufferedImage pa = t.joinToRight(p , t.getTileNumber(106));
		BufferedImage pac = t.getTileNumber(121);
		BufferedImage pacM = t.joinToRight(pac , t.getTileNumber(122));
		BufferedImage pacManImage = t.joinBelow(pa, pacM);
		pacManImage = t.resize(pacManImage, new Dimension(40,40));
		pacManIcon.setIcon(new ImageIcon(pacManImage));
		pacManIcon.setBounds(coordX, coordY, 50, 50);
		
		add(pacManTitle);
		add(hightScores);
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
			//if(statut==0) {
			if(getCoordY()==200) {
				System.out.println("start game");	
				Main.getGlobalFrame().setPage("Game");
				System.out.println(Main.getGlobalFrame().getPage());
				checkPageThread= new CheckPageThread("CheckPageThread");

			}
			//}
			/*if(statut==1) {
			 if(getCoordY()==200) {
				
				
				}
			}
				*/
			
			if(getCoordY()==280) {
				System.out.println("Audio");
				
			}
			if(getCoordY()==360) {
				System.out.println("Control");
				
			}
			if(getCoordY()==440) {
				System.out.println("hightScores");
				
				
			}if(getCoordY()==520) {
				System.out.println("Quit Game");
				
				
			}}
			

		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
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
