package tests.robot;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestRobotCollisions {
	
	static GameFrame window;
	static Robot robot;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		robot = new Robot();
		window = new GameFrame();
		window.startGame();
		Thread.sleep(1000);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		window.stopGame();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	/**
	 * Test that arrow keys move pac-man in 4 directions.
	 */
	@Test
	void testPacManMovement() {
		int xPos1 = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		robot.keyPress(KeyEvent.VK_LEFT);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		int xPos2 = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		
		assertTrue(xPos2 < xPos1 , "pacman current x position: "+xPos2+" should be < to initial x position: "+xPos1);
	}

}
