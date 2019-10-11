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
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		assertEquals("Controls", gameFrame.getPage(), "Menu appearing must be Controls");
	}	

}
