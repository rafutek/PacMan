package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;
import resources.ChangeLetter;
import resources.Tiles;
import threads.CheckPageThread;

public class NewHighScorePanel extends JPanel implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	private Tiles t;
	public JLabel pacManTitle;
	public JLabel pacManIcon;
	public JLabel newHighScore;
	public JLabel goBack;
	private JLabel letter1;
	private String Letter1S = "A";
	private JLabel letter2;
	private String Letter2S = "A";
	private JLabel letter3;
	private String Letter3S = "A";
	private JLabel BlueBar;
	private JLabel BlueBarSave;
	private JLabel newScore;
	private JLabel save;
	public ChangeLetter changeLetter;
	private String name=null;
	private int newPosition=0;
	private int newHightScore=0;
	
	private CheckPageThread checkPageThread;
	
	
	public NewHighScorePanel() throws IOException {
		setBackground(Color.black);	
		setLayout(null);
		
		t = new Tiles();
		
		changeLetter = new ChangeLetter();
		pacManTitle = new JLabel("");		
		BufferedImage p1 = t.createWord(t.getTileNumber(73), t.getTileNumber(74),t.getTileNumber(75),t.getTileNumber(76),t.getTileNumber(77),t.getTileNumber(78),t.getTileNumber(79),t.getTileNumber(80));
		BufferedImage p2 = t.createWord(t.getTileNumber(89),t.getTileNumber(90),t.getTileNumber(91), t.getTileNumber(92), t.getTileNumber(93), t.getTileNumber(94),t.getTileNumber(95), t.getTileNumber(96));
		BufferedImage PACMAN = t.joinBelow(p1, p2);
		PACMAN = t.resize(PACMAN, new Dimension(400,100));
		pacManTitle.setIcon(new ImageIcon(PACMAN));
		pacManTitle.setBounds(100, 0, 400, 100);
		
		newHighScore = new JLabel();
		BufferedImage hightScores = t.createWord(t.getTileNumber(52),t.getTileNumber(43),t.getTileNumber(61),t.getTileNumber(352),t.getTileNumber(46),t.getTileNumber(47),t.getTileNumber(45),t.getTileNumber(46),t.getTileNumber(352),t.getTileNumber(57),t.getTileNumber(41),t.getTileNumber(53),t.getTileNumber(56),t.getTileNumber(43));
		hightScores = t.resize(hightScores, new Dimension(350,50));
		newHighScore.setIcon(new ImageIcon(hightScores));
		newHighScore.setBounds(130, 120, 350, 50);
		
		newScore = new JLabel();
		newScore.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		newScore.setForeground(Color.white);
		newScore.setBounds(160, 220, 350, 50);
		
		save = new JLabel();
		BufferedImage saveImg = t.createWord(t.getTileNumber(57),t.getTileNumber(39),t.getTileNumber(60),t.getTileNumber(43));
		saveImg = t.resize(saveImg, new Dimension(150,50));
		save.setIcon(new ImageIcon(saveImg));
		save.setBounds(250, 325, 350, 50);
		
		BlueBar = new JLabel("");
		BlueBar.setIcon(new ImageIcon(getClass().getResource("../resources/images/barre.PNG")));
		BlueBar.setBounds(52, 330, 400, 100);
		
		BlueBarSave = new JLabel("");
		BlueBarSave.setIcon(new ImageIcon(getClass().getResource("../resources/images/barreSave.PNG")));
		

		letter1 = new JLabel("");
		letter1.setIcon(new ImageIcon (t.getTileNumber(39)));
		letter1.setBounds(50, 300, 400, 100);
		
		letter2 = new JLabel("");
		letter2.setIcon(new ImageIcon (t.getTileNumber(39)));
		letter2.setBounds(100, 300, 400, 100);
		
		letter3 = new JLabel("");
		letter3.setIcon(new ImageIcon (t.getTileNumber(39)));
		letter3.setBounds(150, 300, 400, 100);
		add(newScore);
		add(pacManTitle);
		add(newHighScore);
		add(save);
		add(BlueBar);
		add(BlueBarSave);
		add(letter1);
		add(letter2);
		add(letter3);
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key== KeyEvent.VK_DOWN && BlueBar.getX()==52) {
			System.out.println(Letter1S);
			changeLetter.setLetter(Letter1S);
			changeLetter.setL(letter1); 
			changeLetter.passDown();
			Letter1S = changeLetter.getLetter();
			letter1 = changeLetter.getL();
			System.out.println("... Ledtter1S ...."+Letter1S);
		}else if(key== KeyEvent.VK_DOWN && BlueBar.getX()==102) {
			changeLetter.setLetter(Letter2S);
			changeLetter.setL(letter2);
			changeLetter.passDown();
			Letter2S = changeLetter.getLetter();
			letter2 = changeLetter.getL();
		}
		else if(key== KeyEvent.VK_DOWN && BlueBar.getX()==152) {
			changeLetter.setLetter(Letter3S);
			changeLetter.setL(letter3);
			changeLetter.passDown();
			Letter3S = changeLetter.getLetter();
			letter3 = changeLetter.getL();
		}
		
		if(key== KeyEvent.VK_UP && BlueBar.getX()==52) {
			changeLetter.setLetter(Letter1S) ;
			changeLetter.setL(letter1);
			changeLetter.passUp();
			Letter1S = changeLetter.getLetter();
			letter1 = changeLetter.getL();
			System.out.println("... Ledtter1S ...."+Letter1S);
		}else if(key== KeyEvent.VK_UP && BlueBar.getX()==102) {
			changeLetter.setLetter(Letter2S) ;
			changeLetter.setL(letter2) ;
			changeLetter.passUp();
			Letter2S =changeLetter.getLetter();
			letter2 = changeLetter.getL();
		}
		else if(key== KeyEvent.VK_UP && BlueBar.getX()==152) {
			changeLetter.setLetter(Letter3S);
			changeLetter.setL(letter3);
			changeLetter.passUp();
			Letter3S = changeLetter.getLetter();
			letter3 = changeLetter.getL();
		}
		
		if(key== KeyEvent.VK_RIGHT && BlueBar.getX()==52) {
			BlueBar.setBounds(102, 330, 400, 100);
		}else if(key== KeyEvent.VK_RIGHT && BlueBar.getX()==102) {
			BlueBar.setBounds(152, 330, 400, 100);
		}else if(key== KeyEvent.VK_RIGHT && BlueBar.getX()==152) {
			BlueBar.setBounds(1000, 330, 400, 100);
			BlueBarSave.setBounds(250, 330, 400, 100);
		}else if(key== KeyEvent.VK_RIGHT && BlueBar.getX()==1000) {
			BlueBar.setBounds(52, 330, 400, 100);
			BlueBarSave.setBounds(1000, 330, 400, 100);
		}	
		
		if(key== KeyEvent.VK_LEFT && BlueBar.getX()==52) {
			BlueBar.setBounds(1000, 330, 400, 100);
			BlueBarSave.setBounds(250, 330, 400, 100);
		}else if(key== KeyEvent.VK_LEFT && BlueBar.getX()==1000) {
			BlueBar.setBounds(152, 330, 400, 100);
			BlueBarSave.setBounds(1000, 330, 400, 100);
		}else if(key== KeyEvent.VK_LEFT && BlueBar.getX()==152) {
			BlueBar.setBounds(102, 330, 400, 100);
		}else if(key== KeyEvent.VK_LEFT && BlueBar.getX()==102) {
			BlueBar.setBounds(52, 330, 400, 100);
		}	
		
		if(key== KeyEvent.VK_ENTER && BlueBar.getX()==1000) {
			name=Letter1S+Letter2S+Letter3S;
			System.out.println("name .........."+name);
			System.out.println("position .........."+getNewPosition());
			System.out.println("score........."+getNewHightScore());
			if(newPosition!=0) {
				updateHightScoreFile("hightScores.txt");
				System.out.println("start principal menu");	
				try {
					Main.getGlobalFrame().getHightScoresPanel().readHightScoresFile("hightScores.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Main.getGlobalFrame().closeGame();
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	} 
	public void updateHightScoreFile(String path) {
		 try { 
		        String searchText = Integer.toString(newPosition);
		        Path p = Paths.get("src/resources/highScores/"+path);
		        Path tempFile = Files.createTempFile(p.getParent(), "usersTemp", ".txt");
		        try (BufferedReader reader = Files.newBufferedReader(p);
		                BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
		            String line;
 
		            // copy everything until the id is found
		            while ((line = reader.readLine()) != null) {
		                String[] fields = line.split("[:]");
		                if (searchText.equals(fields[0])) {
		                    for (int i = 0; i < fields.length; ++i) {
		                        System.out.println(i + ": " + fields[i]);
		                    }
		                    fields[1] = name;
		                    fields[2] = Integer.toString(newHightScore);
		                   
		                    
		                }
		                writer.write(String.join(":", fields));
		                writer.write(":");
		                writer.newLine();
		            }
		        }

		        // copy new file & delete temporary file
		        Files.copy(tempFile, p, StandardCopyOption.REPLACE_EXISTING);
		        Files.delete(tempFile);
		    } catch (IOException ex) {
		        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		    }
	}
	
	
	public int getNewPosition() {
		return newPosition;
	}

	public void setNewPosition(int newPosition) {
		this.newPosition = newPosition;
	}

	public int getNewHightScore() {
		return newHightScore;
	}

	public void setNewHightScore(int newHightScore) {
		this.newHightScore = newHightScore;
	}
	public JLabel getNewScore() {
		return newScore;
	}

	public void setNewScore(JLabel newScore) {
		this.newScore = newScore;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	

}
