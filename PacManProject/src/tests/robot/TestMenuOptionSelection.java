package tests.robot;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import view.GameFrame;

class TestMenuOptionSelection {

	GameFrame gameFrame;
	Robot robot;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {}

	@AfterAll
	static void tearDownAfterClass() throws Exception {}

	@BeforeEach
	void setUp() throws Exception {
		robot = new Robot();
		gameFrame = new GameFrame(20);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
	}

	@AfterEach
	void tearDown() throws Exception {
		gameFrame.closeGame();
	}

	@Test
	void testPrincipalMenuAtStart() {
		assertTrue("PrincipalMenu".matches(gameFrame.getPage()), "Menu appearing should be principal");
	}
	
	@Test
	void testSelectControlsOption() {
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
		assertEquals("Controls", gameFrame.getPage(), "Menu appearing must be Controls");
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		assertEquals("PrincipalMenu", gameFrame.getPage(), "Menu appearing must be Principal Menu");
		
		

		
	}	
	
	@Test
	void testSelectHighScoresOption() {
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		assertEquals("HighScores", gameFrame.getPage(), "Menu appearing must be HighScores");
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		assertEquals("PrincipalMenu", gameFrame.getPage(), "Menu appearing must be Principal Menu");
		
	}	
	
	@Test
	void testSelectAudioOption() {
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		
		assertEquals("Audio", gameFrame.getPage(), "Menu appearing must be Audio");
		
		//Test Audio Off
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		assertTrue(gameFrame.getSoundTh().getMute(), "the boolean mute should be true");
		
		//Test Audio On
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		assertFalse(gameFrame.getSoundTh().getMute(), "the boolean mute should be false");
		
		
		//Test Audio Down
		
		float vol1 = gameFrame.getSoundTh().getVol();
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		float vol2 = gameFrame.getSoundTh().getVol();
		assertTrue(vol2<vol1, "the volume has droped so this should be true");
		
		//Test Audio Up
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		float vol3 = gameFrame.getSoundTh().getVol();
		assertTrue(vol3>vol2, "the volume has risen so this should be true");
		
		// Test Music Off
		
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		assertTrue(gameFrame.getMusicTh().getMute(), "the boolean mute should be true");
		
		//Test Music On
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		assertFalse(gameFrame.getMusicTh().getMute(), "the boolean mute should be false");
		
		
		//Test Music Down
		
		float vol4 = gameFrame.getMusicTh().getVol();
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		float vol5 = gameFrame.getMusicTh().getVol();
		assertTrue(vol5<vol4, "the volume has droped so this should be true");
		
		//Test Music Up
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_LEFT);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		try {
			Thread.sleep(500); 
		} catch (InterruptedException e) {}
		float vol6 = gameFrame.getMusicTh().getVol();
		assertTrue(vol6>vol5, "the volume has risen so this should be true");
		
		
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_DOWN);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		assertEquals("PrincipalMenu", gameFrame.getPage(), "Menu appearing must be Principal Menu");
		
	}	
	
	@Test
	void testSelectGameOption() {
	
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		assertEquals("Game", gameFrame.getPage(), "Menu appearing must be HighScores");
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}

		robot.keyPress(KeyEvent.VK_P);
		robot.keyRelease(KeyEvent.VK_P);		
		robot.keyPress(KeyEvent.VK_P);
		robot.keyRelease(KeyEvent.VK_P);		
		robot.keyPress(KeyEvent.VK_P);
		robot.keyRelease(KeyEvent.VK_P);		
		robot.keyPress(KeyEvent.VK_P);
		robot.keyRelease(KeyEvent.VK_P);
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {}
		
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		assertEquals("PrincipalMenu", gameFrame.getPage(), "Menu appearing must be Principal Menu");
		
	}
	

}
