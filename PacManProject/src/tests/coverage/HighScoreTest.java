package tests.coverage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import view.HightScoresPanel;
import view.NewHighScorePanel;

class HighScoreTest {

	private NewHighScorePanel NH;
	private HightScoresPanel H;

	
	public HighScoreTest() throws IOException {
		NH = new NewHighScorePanel();
		H = new HightScoresPanel("hightScoresTest.txt");
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
	public void testUpdateAndReadHightScoresFile() throws IOException {
		NH.setNewHightScore(100);
		NH.setName("TRY");
		NH.setNewPosition(1);
		NH.updateHightScoreFile("hightScoresTest.txt");
		H.readHightScoresFile("hightScoresTest.txt");
		String[] check = {"1","TRY","100","\r\n2","CB9","3820","\r\n3","M5F","2040","\r\n4","SEB","160","\r\n5","9B9","32","\r\n"};
		String[] highScore = H.getHightScors();
		assertNotNull(highScore[0]);
		assertEquals(highScore[0],check[0]);
		assertEquals(highScore[1],check[1]);
		assertEquals(highScore[2],check[2]);
		assertEquals(highScore[3],check[3]);
		assertEquals(highScore[4],check[4]);
		assertEquals(highScore[5],check[5]);
		assertEquals(highScore[6],check[6]);
		assertEquals(highScore[7],check[7]);
		assertEquals(highScore[8],check[8]);
		assertEquals(highScore[9],check[9]);
		assertEquals(highScore[10],check[10]);
		assertEquals(highScore[11],check[11]);
		assertEquals(highScore[12],check[12]);
		assertEquals(highScore[13],check[13]);
		assertEquals(highScore[14],check[14]);
		assertEquals(highScore[15],check[15]);

	}
	
	@Test
	public void testFillArrays() {
		String[] check = {"1","2","3","4","5"};
		String[] check2 = {"100","3820","2040","160","32"};
		String[] check3 = {"TRY","CB9","M5F","SEB","9B9"};
		
		String[] position = H.getPosition();
		assertEquals(check[0],position[0]);
		assertEquals(check[1],position[1]);
		assertEquals(check[2],position[2]);
		assertEquals(check[3],position[3]);
		assertEquals(check[4],position[4]);


		String[] score = H.getScore();
		assertEquals(check2[0],score[0]);
		assertEquals(check2[1],score[1]);
		assertEquals(check2[2],score[2]);
		assertEquals(check2[3],score[3]);
		assertEquals(check2[4],score[4]);

//		String[] name = H.getName();
//		assertEquals(check3,name);
	}

}
