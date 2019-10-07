package resources;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ChangeLetter {
	private String letter;
	private JLabel l ;
	public Tiles t;
	
	public ChangeLetter() throws IOException {
		t = new Tiles();
		letter = "A";
		l = new JLabel();
		l.setIcon(new ImageIcon (t.getTileNumber(39)));
	}
	public void passUp() {
		if(letter=="A") {
			l.setIcon(new ImageIcon (t.getTileNumber(38)));
			letter = "9";
		}else if(letter=="9") {
			l.setIcon(new ImageIcon (t.getTileNumber(37)));
			letter="8";
		}else if(letter=="8") {
			l.setIcon(new ImageIcon (t.getTileNumber(36)));
			letter="7";
		}else if(letter=="7") {
			l.setIcon(new ImageIcon (t.getTileNumber(35)));
			letter="6";
		}else if(letter=="6") {
			l.setIcon(new ImageIcon (t.getTileNumber(34)));
			letter="5";
		}else if(letter=="5") {
			l.setIcon(new ImageIcon (t.getTileNumber(33)));
			letter="4";
		}else if(letter=="4") {
			l.setIcon(new ImageIcon (t.getTileNumber(32)));
			letter="3";
		}else if(letter=="3") {
			l.setIcon(new ImageIcon (t.getTileNumber(31)));
			letter="2";
		}else if(letter=="2") {
			l.setIcon(new ImageIcon (t.getTileNumber(30)));
			letter="1";
		}else if(letter=="1") {
			l.setIcon(new ImageIcon (t.getTileNumber(29)));
			letter="0";
		}else if(letter=="0") {
			l.setIcon(new ImageIcon (t.getTileNumber(64)));
			letter="Z";
		}else if(letter=="Z") {
			l.setIcon(new ImageIcon (t.getTileNumber(63)));
			letter="Y";
		}else if(letter=="Y") {
			l.setIcon(new ImageIcon (t.getTileNumber(62)));
			letter="X";
		}else if(letter=="X") {
			l.setIcon(new ImageIcon (t.getTileNumber(61)));
			letter="W";
		}else if(letter=="W") {
			l.setIcon(new ImageIcon (t.getTileNumber(60)));
			letter="V";
		}else if(letter=="V") {
			l.setIcon(new ImageIcon (t.getTileNumber(59)));
			letter="U";
		}else if(letter=="U") {
			l.setIcon(new ImageIcon (t.getTileNumber(58)));
			letter="T";
		}else if(letter=="T") {
			l.setIcon(new ImageIcon (t.getTileNumber(57)));
			letter="S";
		}else if(letter=="S") {
			l.setIcon(new ImageIcon (t.getTileNumber(56)));
			letter="R";
		}else if(letter=="R") {
			l.setIcon(new ImageIcon (t.getTileNumber(55)));
			letter="Q";
		}else if(letter=="Q") {
			l.setIcon(new ImageIcon (t.getTileNumber(54)));
			letter="P";
		}else if(letter=="P") {
			l.setIcon(new ImageIcon (t.getTileNumber(53)));
			letter="O";
		}else if(letter=="O") {
			l.setIcon(new ImageIcon (t.getTileNumber(52)));
			letter="N";
		}else if(letter=="N") {
			l.setIcon(new ImageIcon (t.getTileNumber(51)));
			letter="M";
		}else if(letter=="M") {
			l.setIcon(new ImageIcon (t.getTileNumber(50)));
			letter="L";
		}else if(letter=="L") {
			l.setIcon(new ImageIcon (t.getTileNumber(49)));
			letter="K";
		}else if(letter=="K") {
			l.setIcon(new ImageIcon (t.getTileNumber(48)));
			letter="J";
		}else if(letter=="J") {
			l.setIcon(new ImageIcon (t.getTileNumber(47)));
			letter="I";
		}else if(letter=="I") {
			l.setIcon(new ImageIcon (t.getTileNumber(46)));
			letter="H";
		}else if(letter=="H") {
			l.setIcon(new ImageIcon (t.getTileNumber(45)));
			letter="G";
		}else if(letter=="G") {
			l.setIcon(new ImageIcon (t.getTileNumber(44)));
			letter="F";
		}else if(letter=="F") {
			l.setIcon(new ImageIcon (t.getTileNumber(43)));
			letter="E";
		}else if(letter=="E") {
			l.setIcon(new ImageIcon (t.getTileNumber(42)));
			letter="D";
		}else if(letter=="D") {
			l.setIcon(new ImageIcon (t.getTileNumber(41)));
			letter="C";
		}else if(letter=="C") {
			l.setIcon(new ImageIcon (t.getTileNumber(40)));
			letter="B";
		}else if(letter=="B") {
			l.setIcon(new ImageIcon (t.getTileNumber(39)));
			letter="A";
		}
	}
	
	public void passDown() {
		if(letter.equals("A")) {
			l.setIcon(new ImageIcon (t.getTileNumber(40)));
			letter = "B";
		}else if(letter=="B") {
			l.setIcon(new ImageIcon (t.getTileNumber(41)));
			letter="C";
		}else if(letter=="C") {
			l.setIcon(new ImageIcon (t.getTileNumber(42)));
			letter="D";
		}else if(letter=="D") {
			l.setIcon(new ImageIcon (t.getTileNumber(43)));
			letter="E";
		}else if(letter=="E") {
			l.setIcon(new ImageIcon (t.getTileNumber(44)));
			letter="F";
		}else if(letter=="F") {
			l.setIcon(new ImageIcon (t.getTileNumber(45)));
			letter="G";
		}else if(letter=="G") {
			l.setIcon(new ImageIcon (t.getTileNumber(46)));
			letter="H";
		}else if(letter=="H") {
			l.setIcon(new ImageIcon (t.getTileNumber(47)));
			letter="I";
		}else if(letter=="I") {
			l.setIcon(new ImageIcon (t.getTileNumber(48)));
			letter="J";
		}else if(letter=="J") {
			l.setIcon(new ImageIcon (t.getTileNumber(49)));
			letter="K";
		}else if(letter=="K") {
			l.setIcon(new ImageIcon (t.getTileNumber(50)));
			letter="L";
		}else if(letter=="L") {
			l.setIcon(new ImageIcon (t.getTileNumber(51)));
			letter="M";
		}else if(letter=="M") {
			l.setIcon(new ImageIcon (t.getTileNumber(52)));
			letter="N";
		}else if(letter=="N") {
			l.setIcon(new ImageIcon (t.getTileNumber(53)));
			letter="O";
		}else if(letter=="O") {
			l.setIcon(new ImageIcon (t.getTileNumber(54)));
			letter="P";
		}else if(letter=="P") {
			l.setIcon(new ImageIcon (t.getTileNumber(55)));
			letter="Q";
		}else if(letter=="Q") {
			l.setIcon(new ImageIcon (t.getTileNumber(56)));
			letter="R";
		}else if(letter=="R") {
			l.setIcon(new ImageIcon (t.getTileNumber(57)));
			letter="S";
		}else if(letter=="S") {
			l.setIcon(new ImageIcon (t.getTileNumber(58)));
			letter="T";
		}else if(letter=="T") {
			l.setIcon(new ImageIcon (t.getTileNumber(59)));
			letter="U";
		}else if(letter=="U") {
			l.setIcon(new ImageIcon (t.getTileNumber(60)));
			letter="V";
		}else if(letter=="V") {
			l.setIcon(new ImageIcon (t.getTileNumber(61)));
			letter="W";
		}else if(letter=="W") {
			l.setIcon(new ImageIcon (t.getTileNumber(62)));
			letter="X";
		}else if(letter=="X") {
			l.setIcon(new ImageIcon (t.getTileNumber(63)));
			letter="Y";
		}else if(letter=="Y") {
			l.setIcon(new ImageIcon (t.getTileNumber(64)));
			letter="Z";
		}else if(letter=="Z") {
			l.setIcon(new ImageIcon (t.getTileNumber(29)));
			letter="0";
		}else if(letter=="0") {
			l.setIcon(new ImageIcon (t.getTileNumber(30)));
			letter="1";
		}else if(letter=="1") {
			l.setIcon(new ImageIcon (t.getTileNumber(31)));
			letter="2";
		}else if(letter=="2") {
			l.setIcon(new ImageIcon (t.getTileNumber(32)));
			letter="3";
		}else if(letter=="3") {
			l.setIcon(new ImageIcon (t.getTileNumber(33)));
			letter="4";
		}else if(letter=="4") {
			l.setIcon(new ImageIcon (t.getTileNumber(34)));
			letter="5";
		}else if(letter=="5") {
			l.setIcon(new ImageIcon (t.getTileNumber(35)));
			letter="6";
		}else if(letter=="6") {
			l.setIcon(new ImageIcon (t.getTileNumber(36)));
			letter="7";
		}else if(letter=="7") {
			l.setIcon(new ImageIcon (t.getTileNumber(37)));
			letter="8";
		}else if(letter=="8") {
			l.setIcon(new ImageIcon (t.getTileNumber(38)));
			letter="9";
		}else if(letter=="9") {
			l.setIcon(new ImageIcon (t.getTileNumber(39)));
			letter="A";
		}
	}
	
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public JLabel getL() {
		return l;
	}
	public void setL(JLabel l) {
		this.l = l;
	}
	

}
