package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class WriteLetter {
	
		private String letter;
		private BufferedImage l ;
		public Tiles t;
		
		public WriteLetter() throws IOException {
			t = new Tiles();
			letter = "A";
			l = t.getTileNumber(39);
			
		}
		public void write() {
				if(letter.equals("A")) {
					l=t.getTileNumber(39);
				}else if(letter.equals("B")) {
					l=t.getTileNumber(40);
				}else if(letter.equals("C")) {
					l=t.getTileNumber(41);
				}else if(letter.equals("D")) {
					l=t.getTileNumber(42);
				}else if(letter.equals("E")) {
					l=t.getTileNumber(43);
				}else if(letter.equals("F")) {
					l=t.getTileNumber(44);
				}else if(letter.equals("G")) {
					l=t.getTileNumber(45);
				}else if(letter.equals("H")) {
					l=t.getTileNumber(46);
				}else if(letter.equals("I")) {
					l=t.getTileNumber(47);
				}else if(letter.equals("J")) {
					l=t.getTileNumber(48);
				}else if(letter.equals("K")) {
					l=t.getTileNumber(49);
				}else if(letter.equals("L")) {
					l=t.getTileNumber(50);
				}else if(letter.equals("M")) {
					l=t.getTileNumber(51);
				}else if(letter.equals("N")) {
					l=t.getTileNumber(52);
				}else if(letter.equals("O")) {
					l=t.getTileNumber(53);
				}else if(letter.equals("P")) {
					l=t.getTileNumber(54);
				}else if(letter.equals("Q")) {
					l=t.getTileNumber(55);
				}else if(letter.equals("R")) {
					l=t.getTileNumber(56);
				}else if(letter.equals("S")) {
					l=t.getTileNumber(57);
				}else if(letter.equals("T")) {
					l=t.getTileNumber(58);
				}else if(letter.equals("U")) {
					l=t.getTileNumber(59);
				}else if(letter.equals("V")) {
					l=t.getTileNumber(60);
				}else if(letter.equals("W")) {
					l=t.getTileNumber(61);
				}else if(letter.equals("X")) {
					l=t.getTileNumber(62);
				}else if(letter.equals("Y")) {
					l=t.getTileNumber(63);
				}else if(letter.equals("Z")) {
					l=t.getTileNumber(64);
				}else if(letter.equals("0")) {
					l=t.getTileNumber(29);
				}else if(letter.equals("1")) {
					l=t.getTileNumber(30);
				}else if(letter.equals("2")) {
					l=t.getTileNumber(31);
				}else if(letter.equals("3")) {
					l=t.getTileNumber(32);
				}else if(letter.equals("4")) {
					l=t.getTileNumber(33);
				}else if(letter.equals("5")) {
					l=t.getTileNumber(34);
				}else if(letter.equals("6")) {
					l=t.getTileNumber(35);
				}else if(letter.equals("7")) {
					l=t.getTileNumber(36);
				}else if(letter.equals("8")) {
					l=t.getTileNumber(37);
				}else if(letter.equals("9")) {
					l=t.getTileNumber(38);
				}
				
		}
		public String getLetter() {
			return letter;
		}
		public void setLetter(String letter) {
			this.letter = letter;
		}
		public BufferedImage getL() {
			return l;
		}
		public void setL(BufferedImage l) {
			this.l = l;
		}
		
		
	}

	
	

