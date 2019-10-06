package tests.robot;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestRobotCollisions {
	
	GameFrame window;
	Robot robot;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {}
	@AfterAll
	static void tearDownAfterClass() throws Exception {}

	@BeforeEach
	void setUp() throws Exception {
		robot = new Robot();
		window = new GameFrame();
		window.startGame();
		sleepFor(500);
	}
	

	@AfterEach
	void tearDown() throws Exception {
		window.stopGame();
	}

	
	/**
	 * Test that arrow keys move pac-man in 4 directions.
	 */
	@Test
	void testPacManMovement() {
		int xPos1 = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		robot.keyPress(KeyEvent.VK_LEFT);
		sleepFor(2000);
		int xPos2 = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		assertTrue(xPos2 < xPos1 , "pacman did not go to left");
		
		robot.keyPress(KeyEvent.VK_RIGHT);
		sleepFor(2000);
		int xPos3 = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		assertTrue(xPos3 > xPos2 , "pacman did not go to right");
		
		int yPos1 = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		robot.keyPress(KeyEvent.VK_UP);
		sleepFor(2000);
		int yPos2 = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		assertTrue(yPos2 < yPos1 , "pacman did not go up");
		
		robot.keyPress(KeyEvent.VK_DOWN);
		sleepFor(2000);
		int yPos3 = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		assertTrue(yPos3 > yPos2 , "pacman did not go down");
		
	}
	
	
	/**
	 * Test that normal pac-man loses a life when collision with ghost.
	 */
	@Test
	void testPacManGhostCollision() {
		assertTimeout((Duration.ofMillis(5000), () -> {
			int yPosPacMan = window.getGameLoop().getPacMan().getCurrentPosition().getY();
			int yPosGhost = window.getGameLoop().getBlinky().getCurrentPosition().getY();
			assertEquals(yPosPacMan, yPosGhost, "pac-man and ghost must be at the same line for testing");
			
			int xPosPacMan = window.getGameLoop().getPacMan().getCurrentPosition().getX();
			int xPosGhost = window.getGameLoop().getBlinky().getCurrentPosition().getX();
			assertTrue(xPosPacMan < xPosGhost, "pac-man must be at the left of ghost for testing");
			
			int nbLives = window.getGameLoop().getPacMan().getLife();
			
			robot.keyPress(KeyEvent.VK_RIGHT);	
			
	    });
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void sleepFor(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}

}
