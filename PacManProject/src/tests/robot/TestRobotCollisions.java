package tests.robot;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sprites.MovingSpriteState;

class TestRobotCollisions {
	
	MinimGameFrame window;
	Robot robot;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {}
	@AfterAll
	static void tearDownAfterClass() throws Exception {}

	@BeforeEach
	void setUp() throws Exception {
		robot = new Robot();
		window = new MinimGameFrame();
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
		robot.keyRelease(KeyEvent.VK_LEFT);
		sleepFor(1500);
		int xPos2 = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		assertTrue(xPos2 < xPos1 , "pacman did not go to left");
		
		robot.keyPress(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_RIGHT);
		sleepFor(1500);
		int xPos3 = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		assertTrue(xPos3 > xPos2 , "pacman did not go to right");
		
		int yPos1 = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
		sleepFor(1500);
		int yPos2 = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		assertTrue(yPos2 < yPos1 , "pacman did not go up");
		
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
		sleepFor(1500);
		int yPos3 = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		assertTrue(yPos3 > yPos2 , "pacman did not go down");
		
	}
	
	
	/**
	 * Test that normal pac-man loses a life when collision with ghost.
	 */
	@Test
	void testPacManGhostCollision() {
		
		int yPosPacMan = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		int yPosGhost = window.getGameLoop().getBlinky().getCurrentPosition().getY();
		assertEquals(yPosPacMan, yPosGhost, "pac-man and ghost must be at the same line for testing");
		
		int xPosPacMan = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		int xPosGhost = window.getGameLoop().getBlinky().getCurrentPosition().getX();
		assertTrue(xPosPacMan < xPosGhost, "pac-man must be at the left of ghost for testing");
		
		int nbLives = window.getGameLoop().getPacMan().getLife();
		
		robot.keyPress(KeyEvent.VK_RIGHT);	
		robot.keyRelease(KeyEvent.VK_RIGHT);
		sleepFor(3500);
		
		int newNbLives = window.getGameLoop().getPacMan().getLife();
		assertTrue(newNbLives == nbLives-1, "After a collision with a ghost, pac-man lives must decrease by one");
		
	}
	
	
	/**
	 * Test that invincible pac-man kills a ghost replacing it at its initial position.
	 */
	@Test
	void testInvinciblePacManGhostCollision() {
		
		int yPosPacMan = window.getGameLoop().getPacMan().getCurrentPosition().getY();
		int yPosGhost = window.getGameLoop().getBlinky().getCurrentPosition().getY();
		assertEquals(yPosPacMan, yPosGhost, "pac-man and ghost must be at the same line for testing");
		
		int xPosPacMan = window.getGameLoop().getPacMan().getCurrentPosition().getX();
		int xPosGhost = window.getGameLoop().getBlinky().getCurrentPosition().getX();
		assertTrue(xPosPacMan < xPosGhost, "pac-man must be at the left of ghost for testing");
		
		window.getGameLoop().getPacMan().setInvincible(true);
		
		window.getGameLoop().getBlinky().setState(MovingSpriteState.LEFT);
		sleepFor(3500);
		
		int xPosGhost2 = window.getGameLoop().getBlinky().getCurrentPosition().getX();
		int yPosGhost2 = window.getGameLoop().getBlinky().getCurrentPosition().getY();
		System.out.println(xPosGhost+" "+yPosGhost+" -> "+xPosGhost2+" "+yPosGhost2);
		assertTrue(xPosGhost== xPosGhost2 && yPosGhost== yPosGhost2,"ghost must be replaced at its initial position"); // error because the position is quite different
	}
	
	
	
	
	
	
	
	
	
	
	
	void sleepFor(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}

}
