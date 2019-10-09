package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.ChangedCharSetException;

import main.Main;
import resources.ChangeLetter;
import resources.Tiles;
import resources.WriteLetter;
import threads.CheckPageThread;

public class HightScoresPanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	
	private String strLine;
	private String[] position;
	private String[] Name; 
	private String[] score;
	private String[] hightScors;
	private FileInputStream fstream ;
	private BufferedReader br ;

	private Tiles t;
	public JLabel pacManTitle;
	public JLabel pacManIcon;
	public JLabel HightScores;
	public JLabel hightScore1;
	public JLabel hightScore2;
	public JLabel hightScore3;
	public JLabel hightScore4;
	public JLabel hightScore5;
	public JLabel goBack;
	
	private WriteLetter writeLetter;
	
	private CheckPageThread checkPageThread;
	
	public HightScoresPanel(String path) throws IOException { 
		readHightScoresFile(path);
		fillArrays(); 
		
		setBackground(Color.black);	
		setLayout(null);
		
		try {
			t = new Tiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeLetter = new WriteLetter();
		pacManTitle = new JLabel("");		
		BufferedImage p1 = t.createWord(t.getTileNumber(73), t.getTileNumber(74),t.getTileNumber(75),t.getTileNumber(76),t.getTileNumber(77),t.getTileNumber(78),t.getTileNumber(79),t.getTileNumber(80));
		BufferedImage p2 = t.createWord(t.getTileNumber(89),t.getTileNumber(90),t.getTileNumber(91), t.getTileNumber(92), t.getTileNumber(93), t.getTileNumber(94),t.getTileNumber(95), t.getTileNumber(96));
		BufferedImage PACMAN = t.joinBelow(p1, p2);
		PACMAN = t.resize(PACMAN, new Dimension(400,100));
		pacManTitle.setIcon(new ImageIcon(PACMAN));
		pacManTitle.setBounds(100, 0, 400, 100);
		
		HightScores = new JLabel();
		BufferedImage hightScores = t.createWord(t.getTileNumber(46),t.getTileNumber(47),t.getTileNumber(45),t.getTileNumber(46),t.getTileNumber(352),t.getTileNumber(57),t.getTileNumber(41),t.getTileNumber(53),t.getTileNumber(56),t.getTileNumber(43),t.getTileNumber(57));
		hightScores = t.resize(hightScores, new Dimension(300,50));
		HightScores.setIcon(new ImageIcon(hightScores));
		HightScores.setBounds(160, 120, 300, 50);
		
		hightScore1 = new JLabel();
		hightScore1.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		hightScore1.setForeground(Color.WHITE);
		BufferedImage img = t.getTileNumber(39);
		String letter =position[0];
		writeLetter.setLetter(letter);
		writeLetter.setL(img);
		writeLetter.write();
		img=writeLetter.getL();
		letter=writeLetter.getLetter();
		BufferedImage hightScore1Img = t.createWord(img,t.getTileNumber(352));
		BufferedImage img2 = t.getTileNumber(39);
		String letter2 =Name[0];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.createWord(hightScore1Img,t.getTileNumber(352));
		letter2 =score[0];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		
		
		/*BufferedImage hightScore1Img1 = writeLetter.getL();
		writeLetter.setLetter(name[0]);
		writeLetter.write();
		BufferedImage hightScore1Img2 = writeLetter.getL();
		writeLetter.setLetter(score[0]);
		writeLetter.write();
		BufferedImage hightScore1Img3 = writeLetter.getL();*/
		
		hightScore1Img = t.resize(hightScore1Img, new Dimension(300, 50));
		hightScore1.setIcon(new ImageIcon(hightScore1Img));
		hightScore1.setBounds(100, 220, 500, 50);
		
		hightScore2 = new JLabel();
		img = t.getTileNumber(39);
		letter =position[1];
		writeLetter.setLetter(letter);
		writeLetter.setL(img);
		writeLetter.write();
		img=writeLetter.getL();
		letter=writeLetter.getLetter();
		hightScore1Img = t.createWord(img,t.getTileNumber(352));
		img2 = t.getTileNumber(39);
		letter2 =Name[1];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.createWord(hightScore1Img,t.getTileNumber(352));
		letter2 =score[1];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.resize(hightScore1Img, new Dimension(300, 50));
		hightScore2.setIcon(new ImageIcon(hightScore1Img));
		hightScore2.setBounds(100, 270, 500, 50);
		
		hightScore3 = new JLabel();
		img = t.getTileNumber(39);
		letter =position[2];
		writeLetter.setLetter(letter);
		writeLetter.setL(img);
		writeLetter.write();
		img=writeLetter.getL();
		letter=writeLetter.getLetter();
		hightScore1Img = t.createWord(img,t.getTileNumber(352));
		img2 = t.getTileNumber(39);
		letter2 =Name[2];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.createWord(hightScore1Img,t.getTileNumber(352));
		letter2 =score[2];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.resize(hightScore1Img, new Dimension(300, 50));
		hightScore3.setIcon(new ImageIcon(hightScore1Img));
		hightScore3.setBounds(100, 320, 500, 50);
		
		hightScore4 = new JLabel();
		img = t.getTileNumber(39);
		letter =position[3];
		writeLetter.setLetter(letter);
		writeLetter.setL(img);
		writeLetter.write();
		img=writeLetter.getL();
		letter=writeLetter.getLetter();
		hightScore1Img = t.createWord(img,t.getTileNumber(352));
		img2 = t.getTileNumber(39);
		letter2 =Name[3];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.createWord(hightScore1Img,t.getTileNumber(352));
		letter2 =score[3];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.resize(hightScore1Img, new Dimension(300, 50));
		hightScore4.setIcon(new ImageIcon(hightScore1Img));
		hightScore4.setBounds(100, 370, 500, 50);
	
		hightScore5 = new JLabel();
		img = t.getTileNumber(39);
		letter =position[4];
		writeLetter.setLetter(letter);
		writeLetter.setL(img);
		writeLetter.write();
		img=writeLetter.getL();
		letter=writeLetter.getLetter();
		hightScore1Img = t.createWord(img,t.getTileNumber(352));
		img2 = t.getTileNumber(39);
		letter2 =Name[4];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			//System.out.println("letter ______________________"+cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.createWord(hightScore1Img,t.getTileNumber(352));
		letter2 =score[4];
		for(int i=0;i<letter2.length();i++) {
			Character c = letter2.charAt(i);
			String cs = c.toString();
			writeLetter.setLetter(cs);
			writeLetter.setL(img2);
			writeLetter.write();
			img2=writeLetter.getL();
			cs=writeLetter.getLetter();
			hightScore1Img = t.createWord(hightScore1Img,img2);
			
		}
		hightScore1Img = t.resize(hightScore1Img, new Dimension(300, 50));
		hightScore5.setIcon(new ImageIcon(hightScore1Img));
		hightScore5.setBounds(100, 420, 500, 50);
		
		pacManIcon= new JLabel();
		BufferedImage pacManImage = t.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
		pacManImage = t.resize(pacManImage, new Dimension(30,30));
		pacManIcon.setIcon(new ImageIcon(pacManImage));
		pacManIcon.setBounds(120, 500, 50, 50);
		
		
		goBack = new JLabel();
		BufferedImage goBackImg = t.createWord(t.getTileNumber(45),t.getTileNumber(53),t.getTileNumber(352),t.getTileNumber(40),t.getTileNumber(39),t.getTileNumber(41),t.getTileNumber(49));
		goBackImg = t.resize(goBackImg, new Dimension(250,30));
		goBack.setIcon(new ImageIcon(goBackImg));
		goBack.setBounds(160, 500, 550, 50);
		
		add(pacManTitle);
		add(HightScores);
		add(hightScore1);
		add(hightScore2);
		add(hightScore3);
		add(hightScore4);
		add(hightScore5);
		add(pacManIcon);
		add(goBack);
	}
	
	//this methode reads the values of the file hightScores and puts them in the array hightScors 
		public void readHightScoresFile(String path) throws IOException {
			hightScors = new String[16];
			position = new String[5];
			Name = new String[5];
			score = new String[5];
			Scanner s = null; 
			try { 
				s = new Scanner(new BufferedReader(new FileReader("src/resources/highScores/"+path)));
				s.useDelimiter(":"); 
				int i =0;
				while (s.hasNext()) {
					hightScors[i]=s.next();
					 i++;
				}
				} finally { 
					if (s != null) s.close(); 
				}
		} 
		//this methodes fills the arrays name , score and position 
		public void fillArrays() {
			int j=0;
			int k=0;
			for(int i=0;i<15;i++) {
				//fill the array position
				for(Integer l =1;l<6;l++) {
					position[l-1]=l.toString();
				}
				//fill the array names
				if(i==1||i==4||i==7||i==10||i==13) {
					Name[j] = hightScors[i];
					j++;
				}
				//fill the array score
				else if(i==2||i==5||i==8||i==11||i==14) {
					score[k] = hightScors[i];
					k++;
				}
				
			}	
			
		}


	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("start principal menu");	
		Main.getGlobalFrame().setPage("PrincipalMenu");
		System.out.println(Main.getGlobalFrame().getPage());
		checkPageThread= new CheckPageThread("CheckPageThread");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}


	public String[] getPosition() {
		return position;
	}

	public String[] getHightScors() {
		return hightScors;
	}

	public String[] getScore() {
		return score;
	}
	
//	public String[] getName() {
//		return Name;
//	}
	

	

}
