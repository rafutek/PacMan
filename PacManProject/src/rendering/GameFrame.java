package rendering;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameFrame extends JFrame implements WindowListener
{

	private static final long serialVersionUID = 1L;

	private static int DEFAULT_FPS = 80;

	private GamePanel wp;        // where the worm is drawn
	private JTextField jtfBox;   // displays no.of boxes used
	private JTextField jtfTime;  // displays time spent in game


	public GameFrame(int period)
	{ 
		super("Game");
		makeGUI(period);

		addWindowListener( this );
		pack();
		setResizable(false);
		setVisible(true);
	}  


private void makeGUI(int period)
{
 Container c = getContentPane();    // default BorderLayout used

 wp = new GamePanel(this, period);
 c.add(wp, "Center");

 JPanel ctrls = new JPanel();   // a row of textfields
 ctrls.setLayout( new BoxLayout(ctrls, BoxLayout.X_AXIS));

 jtfBox = new JTextField("Boxes used: 0");
 jtfBox.setEditable(false);
 ctrls.add(jtfBox);

 jtfTime = new JTextField("Time Spent: 0 secs");
 jtfTime.setEditable(false);
 ctrls.add(jtfTime);

 c.add(ctrls, "South");
}  // end of makeGUI()


public void setBoxNumber(int no)
{  jtfBox.setText("Boxes used: " + no);  }

public void setTimeSpent(long t)
{  jtfTime.setText("Time Spent: " + t + " secs"); }


// ----------------- window listener methods -------------

public void windowActivated(WindowEvent e) 
{ wp.resumeGame();  }

public void windowDeactivated(WindowEvent e) 
{  wp.pauseGame();  }


public void windowDeiconified(WindowEvent e) 
{  wp.resumeGame();  }

public void windowIconified(WindowEvent e) 
{  wp.pauseGame(); }


public void windowClosing(WindowEvent e)
{  wp.stopGame();  }

public void windowClosed(WindowEvent e) {}
public void windowOpened(WindowEvent e) {}

// ----------------------------------------------------

public static void main(String args[])
{ 
	int fps = DEFAULT_FPS;
	if (args.length != 0)
		fps = Integer.parseInt(args[0]);

	int period = (int) 1000.0/fps;
	System.out.println("fps: " + fps + "; period: " + period + " ms");

	new GameFrame(period);    // ms
}

} // end of WormChase class
