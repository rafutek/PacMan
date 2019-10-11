package tests.coverage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import view.GameFrame;
import view.HightScoresPanel;
import view.NewHighScorePanel;

class HighScoreTest {

	private NewHighScorePanel NH;
	private HightScoresPanel H;
	private GameFrame gf;
	
	public HighScoreTest() throws IOException {
		gf = new GameFrame(15);
		NH = new NewHighScorePanel(gf);
		H = new HightScoresPanel(gf);
		assertNotNull(H);
		assertNotNull(NH);
	}
	
	@Test
	void testsetAndGetNewPosition() {
		int test = 1;
		NH.setNewPosition(test);
		assertEquals(test, NH.getNewPosition());
	}
	
	@Test
	void testsetAndGetNewHightScore() {
		int test = 100;
		NH.setNewHightScore(test);
		assertEquals(test, NH.getNewHightScore());
	}
	
	@Test
	void testsetAndGetNewScore() {
		JLabel test = new JLabel();
		NH.setNewScore(test);
		assertEquals(test, NH.getNewScore());

	}
	
	@Test
	void testsetAndGetName() {
		String test = "ABC";
		NH.setName(test);
		assertEquals(test, NH.getName());

	}
	
	@Test
	public void testFillArrays() {
		String[] check = {"1","2","3","4","5"};
		String[] check2 = {"30000","3820","2040","35","32"};
		String[] check3 = {"TRY","CB9","M5F","CBA","9B9"};
		
		String[] position = H.getPosition();
		assertArrayEquals(check, position);


		String[] score = H.getScore();
		assertArrayEquals(check2, score);

		String[] name = H.getname();
		assertArrayEquals(check3,name);
	}
	
	@Test
	public void testUpdateAndReadHightScoresFile() throws IOException {
		NH.setNewHightScore(30000);
		NH.setName("TRY");
		NH.setNewPosition(1);
		NH.updateHightScoreFile("hightScores.txt");
		H.readHightScoresFile("hightScores.txt");
		String[] check = {"1","TRY","30000","\r\n2","CB9","3820","\r\n3","M5F","2040","\r\n4","CBA","35","\r\n5","9B9","32","\r\n"};
		String[] highScore = H.getHightScors();
		assertNotNull(highScore[0]); 
		assertArrayEquals(check, highScore);

	}
	

}
