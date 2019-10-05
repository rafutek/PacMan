package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Tiles;

public class ControlsMenuPanel extends JPanel implements KeyListener{
	
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
		controls.setBounds(160, 120, 250, 50);
		
		arrows = new JLabel();
		BufferedImage a = t.getTileNumber(39);
		BufferedImage ar = t.joinToRight(a , t.getTileNumber(56));
		BufferedImage arr = t.joinToRight(ar , t.getTileNumber(56));
		BufferedImage arro = t.joinToRight(arr , t.getTileNumber(53));
		BufferedImage arrow = t.joinToRight(arro , t.getTileNumber(61));
		BufferedImage arrowsImage = t.joinToRight(arrow , t.getTileNumber(57));
		BufferedImage arrows_ = t.joinToRight(arrowsImage , t.getTileNumber(352));
		BufferedImage arrows_f = t.joinToRight(arrows_ , t.getTileNumber(44));
		BufferedImage arrows_fo = t.joinToRight(arrows_f , t.getTileNumber(53));
		BufferedImage arrows_for = t.joinToRight(arrows_fo , t.getTileNumber(56));
		BufferedImage arrows_for_ = t.joinToRight(arrows_for , t.getTileNumber(352));
		BufferedImage arrows_for_p = t.joinToRight(arrows_for_ , t.getTileNumber(54));
		BufferedImage arrows_for_pa = t.joinToRight(arrows_for_p , t.getTileNumber(39));
		BufferedImage arrows_for_pac = t.joinToRight(arrows_for_pa , t.getTileNumber(41));
		BufferedImage arrows_for_pacm = t.joinToRight(arrows_for_pac , t.getTileNumber(51));
		BufferedImage arrows_for_pacma = t.joinToRight(arrows_for_pacm , t.getTileNumber(39));
		BufferedImage arrows_for_pacman = t.joinToRight(arrows_for_pacma , t.getTileNumber(52));
		arrows_for_pacman = t.resize(arrows_for_pacman, new Dimension(500,30));
		arrows.setIcon(new ImageIcon(arrows_for_pacman));
		arrows.setBounds(70, 200, 500, 50);
		pause = new JLabel();
		BufferedImage p = t.getTileNumber(54);
		BufferedImage p_ = t.joinToRight(p , t.getTileNumber(352));
		BufferedImage p_f = t.joinToRight(p_ , t.getTileNumber(44));
		BufferedImage p_fo = t.joinToRight(p_f , t.getTileNumber(53));
		BufferedImage p_for = t.joinToRight(p_fo , t.getTileNumber(56));
		BufferedImage p_for_ = t.joinToRight(p_for , t.getTileNumber(352));
		BufferedImage p_for_p = t.joinToRight(p_for_ , t.getTileNumber(54));
		BufferedImage p_for_pa = t.joinToRight(p_for_p , t.getTileNumber(39));
		BufferedImage p_for_pau = t.joinToRight(p_for_pa , t.getTileNumber(59));
		BufferedImage p_for_paus = t.joinToRight(p_for_pau , t.getTileNumber(57));
		BufferedImage p_for_pause = t.joinToRight(p_for_paus , t.getTileNumber(43));
		p_for_pause = t.resize(p_for_pause, new Dimension(330,30));
		pause.setIcon(new ImageIcon(p_for_pause));
		pause.setBounds(70, 250, 400, 50);
		
		resume = new JLabel();
		BufferedImage r = t.getTileNumber(56);
		BufferedImage r_ = t.joinToRight(r, t.getTileNumber(352));
		BufferedImage r_f = t.joinToRight(r_ , t.getTileNumber(44));
		BufferedImage r_fo = t.joinToRight(r_f , t.getTileNumber(53));
		BufferedImage r_for = t.joinToRight(r_fo , t.getTileNumber(56));
		BufferedImage r_for_ = t.joinToRight(r_for , t.getTileNumber(352));
		BufferedImage r_for_r = t.joinToRight(r_for_ , t.getTileNumber(56));
		BufferedImage r_for_re = t.joinToRight(r_for_r , t.getTileNumber(43));
		BufferedImage r_for_res = t.joinToRight(r_for_re , t.getTileNumber(57));
		BufferedImage r_for_resu = t.joinToRight(r_for_res , t.getTileNumber(59));
		BufferedImage r_for_resum = t.joinToRight(r_for_resu , t.getTileNumber(51));
		BufferedImage r_for_resume = t.joinToRight(r_for_resum , t.getTileNumber(43));
		r_for_resume = t.resize(r_for_resume, new Dimension(335,30));
		resume.setIcon(new ImageIcon(r_for_resume));
		resume.setBounds(70, 300, 500, 50);
		mute = new JLabel();
		BufferedImage m = t.getTileNumber(56);
		BufferedImage m_ = t.joinToRight(r, t.getTileNumber(352));
		BufferedImage m_f = t.joinToRight(r_ , t.getTileNumber(44));
		BufferedImage m_fo = t.joinToRight(r_f , t.getTileNumber(53));
		BufferedImage m_for = t.joinToRight(r_fo , t.getTileNumber(56));
		BufferedImage m_for_ = t.joinToRight(r_for , t.getTileNumber(352));
		BufferedImage m_for_m = t.joinToRight(r_for_ , t.getTileNumber(56));
		BufferedImage m_for_mu = t.joinToRight(r_for_r , t.getTileNumber(43));
		BufferedImage m_for_mut = t.joinToRight(r_for_re , t.getTileNumber(57));
		BufferedImage m_for_mute = t.joinToRight(r_for_res , t.getTileNumber(59));
		BufferedImage m_for_mute_ = t.joinToRight(r_for_resu , t.getTileNumber(51));
		BufferedImage m_for_mute_o = t.joinToRight(r_for_resum , t.getTileNumber(43));
		r_for_resume = t.resize(r_for_resume, new Dimension(335,30));
		//mute.setIcon(new ImageIcon(getClass().getResource("../ressources/images/mute.PNG")));
		mute.setBounds(70, 350, 500, 50);
		escape = new JLabel();
		//escape.setIcon(new ImageIcon(getClass().getResource("../ressources/images/escapeGame.PNG")));
		escape.setBounds(180, 400, 300, 50);
		
		pacManIcon= new JLabel();
		//pacManIcon.setIcon(new ImageIcon(getClass().getResource("../ressources/images/pacManRight1.png")));
		pacManIcon.setBounds(160, 590, 50, 50);
		goBack = new JLabel();
		//goBack.setIcon(new ImageIcon(getClass().getResource("../ressources/images/goBack.PNG")));
		goBack.setBounds(200, 590, 550, 50);
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
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public static void main(String[] args) {
		JFrame f = new JFrame();
		ControlsMenuPanel p = new ControlsMenuPanel();
		f.setSize(620, 700);
		f.add(p);
		f.addKeyListener(p);
		f.setVisible(true);
	}


}
