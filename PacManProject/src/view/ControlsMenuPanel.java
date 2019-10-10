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

import main.Main;
import resources.Tiles;
import threads.CheckPageThread;

public class ControlsMenuPanel extends JPanel implements KeyListener{
	
	private static final long serialVersionUID = 1L;

	private Tiles t;
	public JLabel pacManTitle;
	public JLabel pacManIcon;
	public JLabel controls;
	public JLabel arrows;
	public JLabel pause;
	public JLabel resume;
	public JLabel mute;
	public JLabel escape;
	public JLabel goBack;
	
	
	CheckPageThread checkPageThread;

	public ControlsMenuPanel() {
		setBackground(Color.black);	
		setLayout(null);
		try {
			t = new Tiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pacManTitle = new JLabel("");		
		BufferedImage p1 = Tiles.createWord(t.getTileNumber(73), t.getTileNumber(74),t.getTileNumber(75),t.getTileNumber(76),t.getTileNumber(77),t.getTileNumber(78),t.getTileNumber(79),t.getTileNumber(80));
		BufferedImage p2 = Tiles.createWord(t.getTileNumber(89),t.getTileNumber(90),t.getTileNumber(91), t.getTileNumber(92), t.getTileNumber(93), t.getTileNumber(94),t.getTileNumber(95), t.getTileNumber(96));
		BufferedImage PACMAN = Tiles.joinBelow(p1, p2);
		PACMAN = Tiles.resize(PACMAN, new Dimension(400,100));
		pacManTitle.setIcon(new ImageIcon(PACMAN));
		pacManTitle.setBounds(100, 0, 400, 100);
		
		controls = new JLabel();
		BufferedImage controlsImage = Tiles.createWord(t.getTileNumber(41),t.getTileNumber(53),t.getTileNumber(52),t.getTileNumber(58),t.getTileNumber(56),t.getTileNumber(53), t.getTileNumber(50),t.getTileNumber(57));
		controlsImage = Tiles.resize(controlsImage, new Dimension(250,50));
		controls.setIcon(new ImageIcon(controlsImage));
		controls.setBounds(160, 120, 250, 50);
		
		arrows = new JLabel();
		BufferedImage arrows_for_pacman = Tiles.createWord(t.getTileNumber(39),t.getTileNumber(56),t.getTileNumber(56),t.getTileNumber(53),t.getTileNumber(61),t.getTileNumber(57),t.getTileNumber(352),t.getTileNumber(44),t.getTileNumber(53),t.getTileNumber(56),t.getTileNumber(352), t.getTileNumber(54),t.getTileNumber(39),t.getTileNumber(41),t.getTileNumber(51), t.getTileNumber(39),t.getTileNumber(52));
		arrows_for_pacman = Tiles.resize(arrows_for_pacman, new Dimension(500,30));
		arrows.setIcon(new ImageIcon(arrows_for_pacman));
		arrows.setBounds(70, 200, 500, 50);
		pause = new JLabel();
		BufferedImage p_for_pause = Tiles.createWord(t.getTileNumber(54),t.getTileNumber(352),t.getTileNumber(44),t.getTileNumber(53),t.getTileNumber(56),t.getTileNumber(352),t.getTileNumber(54),t.getTileNumber(39),t.getTileNumber(59),t.getTileNumber(57),t.getTileNumber(43));
		p_for_pause = Tiles.resize(p_for_pause, new Dimension(330,30));
		pause.setIcon(new ImageIcon(p_for_pause));
		pause.setBounds(70, 250, 400, 50);
		
		resume = new JLabel();
		BufferedImage r_for_resume = Tiles.createWord(t.getTileNumber(56),t.getTileNumber(352),t.getTileNumber(44),t.getTileNumber(53), t.getTileNumber(56),t.getTileNumber(352),t.getTileNumber(56),t.getTileNumber(43),t.getTileNumber(57),t.getTileNumber(59),t.getTileNumber(51),t.getTileNumber(43));
		r_for_resume = Tiles.resize(r_for_resume, new Dimension(335,30));
		resume.setIcon(new ImageIcon(r_for_resume));
		resume.setBounds(70, 300, 500, 50);
		mute = new JLabel();
		BufferedImage m_for_mute_and_unmute = Tiles.createWord(t.getTileNumber(51),t.getTileNumber(352),t.getTileNumber(44),t.getTileNumber(53), t.getTileNumber(56),t.getTileNumber(352),t.getTileNumber(51),t.getTileNumber(59),t.getTileNumber(58),t.getTileNumber(43),t.getTileNumber(352),t.getTileNumber(39),t.getTileNumber(52),t.getTileNumber(42),t.getTileNumber(352),t.getTileNumber(59),t.getTileNumber(52),t.getTileNumber(51),t.getTileNumber(59),t.getTileNumber(58),t.getTileNumber(43));
		m_for_mute_and_unmute = Tiles.resize(m_for_mute_and_unmute, new Dimension(500,30));
		mute.setIcon(new ImageIcon(m_for_mute_and_unmute));
		mute.setBounds(70, 350, 500, 50);
		escape = new JLabel();
		BufferedImage esc_for_menu = Tiles.createWord(t.getTileNumber(43),t.getTileNumber(57),t.getTileNumber(41),t.getTileNumber(352),t.getTileNumber(44),t.getTileNumber(53), t.getTileNumber(56),t.getTileNumber(352),t.getTileNumber(51),t.getTileNumber(43),t.getTileNumber(52),t.getTileNumber(59));
		esc_for_menu = Tiles.resize(esc_for_menu, new Dimension(300,30));
		escape.setIcon(new ImageIcon(esc_for_menu));
		escape.setBounds(70, 400, 300, 50);
		
		pacManIcon= new JLabel();
		BufferedImage pacManImage = Tiles.createFullSpriteImage(t.getTileNumber(105), t.getTileNumber(106), t.getTileNumber(121), t.getTileNumber(122));
		pacManImage = Tiles.resize(pacManImage, new Dimension(30,30));
		pacManIcon.setIcon(new ImageIcon(pacManImage));
		pacManIcon.setBounds(120, 500, 50, 50);
		goBack = new JLabel();
		BufferedImage goBackImg = Tiles.createWord(t.getTileNumber(45),t.getTileNumber(53),t.getTileNumber(352),t.getTileNumber(40),t.getTileNumber(39),t.getTileNumber(41),t.getTileNumber(49));
		goBackImg = Tiles.resize(goBackImg, new Dimension(250,30));
		goBack.setIcon(new ImageIcon(goBackImg));
		goBack.setBounds(160, 500, 550, 50);
		add(pacManTitle);
		add(controls);
		add(arrows);
		add(pause);
		add(resume);
		add(mute);
		add(escape);
		add(goBack);
		add(pacManIcon);

	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_ENTER) {
			System.out.println("start principal menu");	
			Main.getGlobalFrame().setPage("PrincipalMenu");
			System.out.println(Main.getGlobalFrame().getPage());
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
	public void buildTiles(BufferedImage ...img) {
		
	}
}
