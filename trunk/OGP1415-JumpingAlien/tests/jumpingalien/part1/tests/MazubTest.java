/**
 * Tests for the Mazub class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
package jumpingalien.part1.tests;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.JumpingException;
import jumpingalien.model.Mazub;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MazubTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	
	Mazub normalMazub;
	World world;
	
	private final double E = 1e-4;
	
	//Default values
	private static final double X_ACCELERATION = 90;
	private final double X_INITIAL_VELOCITY = 100;
	private final double X_VELOCITY_LIMIT = 300;
	private final double Y_INITIAL_VELOCITY = 800;
	private final double Y_ACCELERATION = 1000;
	private final double DUCKED_VELOCITY_LIMIT = 100;
	
	private final Sprite[] sprites = JumpingAlienSprites.ALIEN_SPRITESET;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Sprite[] sprites = JumpingAlienSprites.ALIEN_SPRITESET;
		int[] window = {1000,600};
		world = new World(1023,768,1,window,500,500);
		normalMazub = new Mazub(world,0,0,sprites);
		
	}

	@Test
	public void normalMazubConstructorTest() {
		assertEquals(0,normalMazub.getX(),E);
		assertEquals(0,normalMazub.getY(),E);
		assertEquals(0,normalMazub.getXVelocity(),E);
		assertEquals(0,normalMazub.getYVelocity(),E);
		assertEquals(0,normalMazub.getXAcceleration(),E);
		assertEquals(0,normalMazub.getYAcceleration(),E);
		
		assertEquals(X_VELOCITY_LIMIT,normalMazub.getXVelocityLimit(),E);
	}
	
	@Test
	public void isValidPositionTest() {
		assertFalse(normalMazub.canHaveAsPosition(100,-1));
		assertTrue(normalMazub.canHaveAsPosition(100,0));
	}
	
	

	@Test
	public void canHaveAsXVelocityTest() {
		assertTrue(normalMazub.canHaveAsXVelocity(0));
		assertTrue(normalMazub.canHaveAsXVelocity(X_INITIAL_VELOCITY));
		assertTrue(normalMazub.canHaveAsXVelocity(X_VELOCITY_LIMIT));
		assertFalse(normalMazub.canHaveAsXVelocity(0.01));
		assertFalse(normalMazub.canHaveAsXVelocity(X_VELOCITY_LIMIT+0.1));
	}
	
	
	@Test
	public void isValidXInitialVelocityAndXVelocityLimitTest() {
		assertFalse(Mazub.isValidXInitialVelocityAndXVelocityLimit(1-E,X_VELOCITY_LIMIT));
		assertFalse(Mazub.isValidXInitialVelocityAndXVelocityLimit(X_VELOCITY_LIMIT+E,X_VELOCITY_LIMIT));
		assertTrue(Mazub.isValidXInitialVelocityAndXVelocityLimit(1+E,X_VELOCITY_LIMIT));
		assertTrue(Mazub.isValidXInitialVelocityAndXVelocityLimit(X_VELOCITY_LIMIT-E,X_VELOCITY_LIMIT));
	}
	
	@Test
	public void canHaveAsXAccelerationTest() {
		assertTrue(normalMazub.canHaveAsXAcceleration(0));
		normalMazub.startMove(Mazub.Direction.LEFT);
		assertTrue(normalMazub.canHaveAsXAcceleration(-1));
		normalMazub.startMove(Mazub.Direction.RIGHT);
		assertTrue(normalMazub.canHaveAsXAcceleration(1));
	}
	
	@Test
	public void startMoveTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		assertEquals(normalMazub.getXDirection(),Mazub.Direction.RIGHT);
		assertEquals(normalMazub.getXVelocity(),X_INITIAL_VELOCITY,E);
		normalMazub.startMove(Mazub.Direction.LEFT);
		assertEquals(normalMazub.getXDirection(),Mazub.Direction.LEFT);
		assertEquals(normalMazub.getXVelocity(),-X_INITIAL_VELOCITY,E);
	}
	
	@Test
	public void endMoveTest() {
		normalMazub.startMove(Mazub.Direction.LEFT);
		normalMazub.endMove();
		assertEquals(0,normalMazub.getXVelocity(),E);
		assertEquals(Mazub.Direction.LEFT,normalMazub.getXDirection());
		
	}
	
	
	@Test
	public void startJumpTest() {
		normalMazub.startJump();
		assertEquals(Y_INITIAL_VELOCITY,normalMazub.getYVelocity(),E);
		normalMazub.advanceTime(0.1);
		assertTrue(normalMazub.isJumping());
	}
	
	@Test(expected=JumpingException.class)
	public void startJumpExceptionTest() {
		for(int i = 0; i < 5; i++) {
			normalMazub.startJump();
			normalMazub.advanceTime(0.19);
		}
	}
	
	@Test
	public void endJumpTest() {
		normalMazub.startJump();
		normalMazub.advanceTime(0.19);
		normalMazub.endJump();
		assertEquals(0,normalMazub.getYVelocity(),E);
		assertTrue(normalMazub.isJumping());
	}
	
	@Test(expected=JumpingException.class)
	public void endJumpExceptionTest() {
		normalMazub.endJump();
	}
	
	@Test
	public void startDuckRightTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		for(int i = 0; i < 5; i++) {
			normalMazub.advanceTime(0.19);
		}
		normalMazub.startDuck();
		assertTrue(normalMazub.isDucking());
		assertEquals(normalMazub.getXVelocityLimit(),DUCKED_VELOCITY_LIMIT,E);
		assertEquals(DUCKED_VELOCITY_LIMIT,normalMazub.getXVelocity(),E);
		normalMazub.advanceTime(0.1);
		assertEquals(DUCKED_VELOCITY_LIMIT,normalMazub.getXVelocity(),E);
	}
	
	@Test
	public void startDuckLeftTest() {
		normalMazub.startMove(Mazub.Direction.LEFT);
		normalMazub.advanceTime(0.19);
		normalMazub.startDuck();
		assertTrue(normalMazub.isDucking());
		assertEquals(normalMazub.getXVelocityLimit(),-DUCKED_VELOCITY_LIMIT,E);
	}

	@Test(expected=IllegalStateException.class)
	public void startDuckExceptionTest() {
		normalMazub.startDuck();
		normalMazub.startDuck();
	}
	
	@Test
	public void endDuckTest() {
		startDuckRightTest();
		normalMazub.endDuck();
		startDuckLeftTest();
		normalMazub.endDuck();
		assertEquals(normalMazub.getXVelocityLimit(),-X_VELOCITY_LIMIT,E);
		assertFalse(normalMazub.isDucking());
	}
	
	@Test(expected=IllegalStateException.class)
	public void endDuckExceptionTest() {
		normalMazub.endDuck();
	}
	
	
	
	@Test
	public void timeSinceLastMoveTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		normalMazub.advanceTime(0.1);
		assertEquals(0,normalMazub.getTimeSinceLastMove(),E);
		normalMazub.endMove();
		normalMazub.advanceTime(0.1);
		assertEquals(0.1,normalMazub.getTimeSinceLastMove(),E);
	}
	
	@Test
	public void getXVelocityLimitTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		assertEquals(X_VELOCITY_LIMIT,normalMazub.getXVelocityLimit(),E);
		normalMazub.startMove(Mazub.Direction.LEFT);
		assertEquals(-X_VELOCITY_LIMIT,normalMazub.getXVelocityLimit(),E);
	}
	
	
	@Test
	public void getXAccelerationTest() {
		//Standing at (0,0)
		assertEquals(0,normalMazub.getXAcceleration(),E);
		normalMazub.startMove(Mazub.Direction.RIGHT);
		assertEquals(X_ACCELERATION,normalMazub.getXAcceleration(),E);
		normalMazub.advanceTime(0.125);
		normalMazub.endMove();

		normalMazub.startMove(Mazub.Direction.LEFT);
		assertEquals(-X_ACCELERATION,normalMazub.getXAcceleration(),E);
	}
	
	@Test
	public void getYAccelerationTest() {
		assertEquals(0,normalMazub.getYAcceleration(),E);
		normalMazub.startJump();
		normalMazub.advanceTime(0.01);
		assertEquals(-Y_ACCELERATION,normalMazub.getYAcceleration(),E);
		normalMazub.endJump();
		normalMazub.advanceTime(0.19);
		assertEquals(0,normalMazub.getYAcceleration(),E);
	}
	
	//getWidth, getHeight, getCurrentSprite: to be verified visually
	
	@Test
	public void getCurrentSpriteTest() {
		assertEquals(sprites[0],normalMazub.getCurrentSprite());
		normalMazub.startMove(Mazub.Direction.RIGHT);
		normalMazub.advanceTime(0.01);
		assertEquals(sprites[8],normalMazub.getCurrentSprite());
		normalMazub.advanceTime(0.07);
		assertEquals(sprites[9],normalMazub.getCurrentSprite());
		normalMazub.endMove();
		normalMazub.advanceTime(0.01);
		assertEquals(sprites[2],normalMazub.getCurrentSprite());
		normalMazub.startMove(Mazub.Direction.LEFT);
		normalMazub.startJump();
		normalMazub.advanceTime(0.01);
		assertEquals(sprites[5],normalMazub.getCurrentSprite());
		normalMazub.endMove();
		normalMazub.startDuck();
		assertEquals(sprites[7],normalMazub.getCurrentSprite());
	}
	
	@Test
	public void isRunningNormallyTest() {
		assertFalse(normalMazub.isRunningNormally());
		normalMazub.startMove(Mazub.Direction.LEFT);
		normalMazub.advanceTime(0.1);
		assertTrue(normalMazub.isRunningNormally());
		normalMazub.startMove(Mazub.Direction.RIGHT);
		normalMazub.advanceTime(0.1);
		assertTrue(normalMazub.isRunningNormally());
		normalMazub.startDuck();
		normalMazub.advanceTime(0.1);
		assertFalse(normalMazub.isRunningNormally());
		normalMazub.endDuck();
		normalMazub.startJump();
		normalMazub.advanceTime(0.1);
		assertFalse(normalMazub.isRunningNormally());
	}
	
	
	//additional tests
	
	@Test
	public void normalMazubRunningRightTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		assertEquals(100,normalMazub.getXVelocity(),E);
		assertEquals(0,normalMazub.getY(),E);
		assertEquals(normalMazub.getXDirection(),Mazub.Direction.RIGHT);
		
		normalMazub.advanceTime(0.1);
		assertEquals(10.45,normalMazub.getX(),E);
		assertEquals(0,normalMazub.getY(),E);
	}
	
	
	@Test
	public void normalMazubMaxSpeedRightTest() {
		//Max speed after 20.0/9 seconds
		normalMazub.startMove(Mazub.Direction.RIGHT);
		assertEquals(100,normalMazub.getXVelocity(),E);
		normalMazub.advanceTime(1.0/9);
		assertEquals(11.666666,normalMazub.getX(),E);
		
		for(int i = 0; i < 18; i++)
			normalMazub.advanceTime(1.0/9);
		assertEquals(290,normalMazub.getXVelocity(),E);
		normalMazub.advanceTime(1.0/9);
		assertEquals(300,normalMazub.getXVelocity(),E);
		assertEquals(4000.0/9,normalMazub.getX(),E);
	}
	

	
	@Test
	public void NormalMazubRunningLeftTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		normalMazub.advanceTime(0.1);
		normalMazub.endMove();
		normalMazub.startMove(Mazub.Direction.LEFT);
		normalMazub.startJump();
		normalMazub.advanceTime(0.09);
		assertFalse(normalMazub.getX()==0);
		normalMazub.advanceTime(0.01);
		assertEquals(0,normalMazub.getX(),E);
	}
	
	
}
