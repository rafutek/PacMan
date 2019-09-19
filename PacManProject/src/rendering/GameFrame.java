package rendering;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;


	private GamePanel gamePanel;        // where the game is drawn


	public GameFrame(int period)
	{ 
		super("PacMan");
		makeGUI(period);

		addWindowListener( this );
		pack();
		setResizable(false);
		setLocationRelativeTo(null); // center the window
		setVisible(true);
	}  


private void makeGUI(int period)
{
	Container c = getContentPane();    // default BorderLayout used

	gamePanel = new GamePanel(period);
	c.add(gamePanel, "Center");
	
	StatusBarPanel statusBarPanel = new StatusBarPanel();
	c.add(statusBarPanel, "South");

//	JPanel ctrls = new JPanel();   // a row of textfields
//	ctrls.setLayout( new BoxLayout(ctrls, BoxLayout.X_AXIS));
//
//	jtfBox = new JTextField("Boxes used: 0");
//	jtfBox.setEditable(false);
//	ctrls.add(jtfBox);
//
//	jtfTime = new JTextField("Time Spent: 0 secs");
//	jtfTime.setEditable(false);
//	ctrls.add(jtfTime);
//
//	c.add(ctrls, "South");
}  // end of makeGUI()



// ----------------- window listener methods -------------

public void windowActivated(WindowEvent e) 
{ gamePanel.resumeGame();  }

public void windowDeactivated(WindowEvent e) 
{  gamePanel.pauseGame();  }


public void windowDeiconified(WindowEvent e) 
{  gamePanel.resumeGame();  }

public void windowIconified(WindowEvent e) 
{  gamePanel.pauseGame(); }


public void windowClosing(WindowEvent e)
{  gamePanel.stopGame();  }

public void windowClosed(WindowEvent e) {}
public void windowOpened(WindowEvent e) {}



} 
