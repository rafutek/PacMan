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
import resources.Tiles;
import threads.CheckPageThread;

public class HightScoresPanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	
	private String strLine;
	private String position[];
	private String name[]; 
	private String score[];
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
	
	private CheckPageThread checkPageThread;
	
	public HightScoresPanel() throws IOException {
		readHightScoresFile();
		fillArrays();
		
		setBackground(Color.black);	
		setLayout(null);
		try {
			t = new Tiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		hightScore1.setText(position[0]+" - "+name[0]+" : "+score[0]);
		hightScore1.setBounds(180, 220, 250, 50);
		
		hightScore2 = new JLabel();
		hightScore2.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		hightScore2.setForeground(Color.WHITE);
		hightScore2.setText(position[1]+" - "+name[1]+" : "+score[1]);
		hightScore2.setBounds(180, 270, 250, 50);
		
		hightScore3 = new JLabel();
		hightScore3.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		hightScore3.setForeground(Color.WHITE);
		hightScore3.setText(position[2]+" - "+name[2]+" : "+score[2]);
		hightScore3.setBounds(180, 320, 250, 50);
		
		hightScore4 = new JLabel();
		hightScore4.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		hightScore4.setForeground(Color.WHITE);
		hightScore4.setText(position[3]+" - "+name[3]+" : "+score[3]);
		hightScore4.setBounds(180, 370, 250, 50);
	
		hightScore5 = new JLabel();
		hightScore5.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		hightScore5.setForeground(Color.WHITE);
		hightScore5.setText(position[4]+" - "+name[4]+" : "+score[4]);
		hightScore5.setBounds(180, 420, 250, 50);
		
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
		public void readHightScoresFile() throws IOException {
			hightScors = new String[16];
			position = new String[5];
			name = new String[5];
			score = new String[5];
			Scanner s = null; 
			try { 
				s = new Scanner(new BufferedReader(new FileReader("src/resources/highScores/hightScores.txt")));
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
					name[j] = hightScors[i];
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public String[] getScore() {
		return score;
	}

	

}
