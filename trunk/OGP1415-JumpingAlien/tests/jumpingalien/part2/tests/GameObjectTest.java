package jumpingalien.part2.tests;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.JumpingException;
import jumpingalien.model.School;
import jumpingalien.model.World;
import jumpingalien.model.World.TerrainType;
import jumpingalien.model.elements.GameObject;
import jumpingalien.model.elements.Mazub;
import jumpingalien.model.elements.Plant;
import jumpingalien.model.elements.Slime;
import jumpingalien.model.elements.GameObject.Direction;
import jumpingalien.part2.internal.Resources;
import jumpingalien.util.Sprite;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the GameObject class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class GameObjectTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	
	Mazub obj;
	World world;
	Slime slime;
	School school;
	Plant plant;
	
	private final double E = 1e-4;
	
	//Default values
	private static final double X_ACCELERATION = 90;
	private final double X_INITIAL_VELOCITY = 100;
	private final double X_VELOCITY_LIMIT = 300;
	private final double Y_INITIAL_VELOCITY = 800;
	private final double Y_ACCELERATION = 1000;
	private final double DUCKED_VELOCITY_LIMIT = 100;
	
	private final Sprite[] sprites = JumpingAlienSprites.ALIEN_SPRITESET;
	private final Sprite[] plantSprites = {Resources.PLANT_SPRITE_LEFT,
			Resources.PLANT_SPRITE_RIGHT};
	private final Sprite[] slimeSprites = {Resources.SLIME_SPRITE_LEFT, Resources.SLIME_SPRITE_RIGHT};
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Sprite[] sprites = JumpingAlienSprites.ALIEN_SPRITESET;
		int[] window = {1000,600};
		world = new World(1023,768,1,window,500,500);
		obj = new Mazub(world,0,0,sprites);
		plant = new Plant(world, 0, 0, plantSprites, null);
		school = new School();
		slime = new Slime(world, 100, 100, slimeSprites, school, null);
		world.addObject(obj);
		world.addObject(plant);
		world.addObject(slime);
	}

	@Test
	public void objConstructorTest() {
		
		assertEquals(world, obj.getMyWorld());
		assertEquals(500, obj.getMaxHitPoints());
		assertEquals(100, obj.getHitPoints());
		assertArrayEquals(sprites, obj.getSprites());
		
		assertEquals(0,obj.getX(),E);
		assertEquals(0,obj.getY(),E);
		assertEquals(0,obj.getXVelocity(),E);
		assertEquals(0,obj.getYVelocity(),E);
		assertEquals(0,obj.getXAcceleration(),E);
		assertEquals(0,obj.getYAcceleration(),E);
		
		
		assertEquals(X_VELOCITY_LIMIT,obj.getXVelocityLimit(),E);
		assertEquals(X_VELOCITY_LIMIT, obj.getPreviousXVelocityLimit(),E);
		assertEquals(X_INITIAL_VELOCITY, obj.getXInitialVelocity(),E);
		assertEquals(Y_INITIAL_VELOCITY, obj.getYInitialVelocity(),E);
		assertEquals(DUCKED_VELOCITY_LIMIT, obj.getDuckedVelocityLimit(), E);
		
		assertTrue(obj.getTimeSinceLastMove()>1);
		assertFalse(obj.isDucking());
		assertFalse(obj.getToEndDuck());
		assertFalse(obj.isStillMoving());
		
		assertEquals(0,obj.getTimeToBeImmune(),0);
		
		assertEquals(0,obj.getWaterTimer(),E);
		assertEquals(0.2,obj.getMagmaTimer(),E);
		assertEquals(0,obj.getAirTimer(),E);
		
		assertTrue(obj.isSolid());
		assertFalse(obj.isTerminated());
	}
	
	
	
	@Test
	public void canHaveAsPositionTest() {
		assertFalse(obj.canHaveAsPosition(100,-1));
		assertTrue(obj.canHaveAsPosition(100,0));
		
		assertTrue(obj.canHaveAsPosition(0,0));
		assertFalse(obj.canHaveAsPosition(100,100));
		assertTrue(obj.canHaveAsPosition(slime.getPosition()[0]-obj.getWidth(),100));
		assertTrue(obj.canHaveAsPosition(slime.getPosition()[0]-obj.getWidth()+1,100));
		assertFalse(obj.canHaveAsPosition(slime.getPosition()[0]-obj.getWidth()+2,100));
		
		assertTrue(obj.canHaveAsPosition(100,slime.getPosition()[1]+slime.getHeight()-1));
		assertFalse(obj.canHaveAsPosition(100,slime.getPosition()[1]+slime.getHeight()-2));
		
		world.setTerrainAt(0, 1, TerrainType.SOLID_GROUND);
		assertTrue(obj.canHaveAsPosition(obj.getPosition()[0], obj.getPosition()[1]));
		world.setTerrainAt(1, 0, TerrainType.SOLID_GROUND);
		assertTrue(obj.canHaveAsPosition(obj.getPosition()[0], obj.getPosition()[1]));
		world.setTerrainAt(obj.getWidth()-1, 5, TerrainType.SOLID_GROUND);
		assertTrue(obj.canHaveAsPosition(obj.getPosition()[0], obj.getPosition()[1]));
		world.setTerrainAt(5, obj.getHeight()-1, TerrainType.SOLID_GROUND);
		assertTrue(obj.canHaveAsPosition(obj.getPosition()[0], obj.getPosition()[1]));
		world.setTerrainAt(5, obj.getHeight()-1, TerrainType.MAGMA);
		assertTrue(obj.canHaveAsPosition(obj.getPosition()[0], obj.getPosition()[1]));
		world.setTerrainAt(1, 1, TerrainType.SOLID_GROUND);
		assertFalse(obj.canHaveAsPosition(obj.getPosition()[0], obj.getPosition()[1]));
		
	}
	
	@Test
	public void rectanglesCollideTest() {
		assertFalse(GameObject.rectanglesCollide(0, 0, 5, 5, 5, 5, 10, 10));
		assertFalse(GameObject.rectanglesCollide(5, 5, 5, 5, 0, 0, 5, 5));
		assertTrue(GameObject.rectanglesCollide(0, 0, 5, 5, 4, 4, 10, 10));
		assertTrue(GameObject.rectanglesCollide(0,0,10,10,2,2,2,2));
		assertTrue(GameObject.rectanglesCollide(0,0,10,10,7,9,2,2));
		assertTrue(GameObject.rectanglesCollide(0,0,10,10,7,9,2,2));
		assertTrue(GameObject.rectanglesCollide(0,0,10,10,7,9,2,2));
		
		//Advance time in sufficiently small increments!
		assertFalse(GameObject.rectanglesCollide(0,10,20,10,5,5,5,40));
	}
	
	@Test
	public void pointInRectangleTest() {
		assertTrue(GameObject.pointInRectangle(2,2,0,0,5,5));
		assertFalse(GameObject.pointInRectangle(5,3,0,0,5,5));
		assertFalse(GameObject.pointInRectangle(2,6,0,0,5,5));
		assertTrue(GameObject.pointInRectangle(0,0,0,0,5,5));
		assertTrue(GameObject.pointInRectangle(4,4,0,0,5,5));
	}
	
	
	@Test
	public void getPositionTest() {
		assertArrayEquals(new int[] {0,0}, obj.getPosition());
	}
	
	@Test
	public void immediateTerminationTest() {
		assertFalse(world.isGameOver());
		obj.terminate(true);
		world.advanceTime(0.01);
		assertTrue(world.isGameOver());
	}
	
	@Test
	public void deferredTerminationTest() {
		obj.terminate(false);
		world.advanceTime(0.19);
		assertFalse(world.isGameOver());
		for(int i = 0; i < 3; i++)
			world.advanceTime(0.15);
		assertTrue(world.isGameOver());
	}
	
	//advanceTime is tested implicitly in other test cases
	
	@Test
	public void preciseCollisionTest() {
		obj.startJump();
		double prev = Y_INITIAL_VELOCITY+1;
		for(int i = 0; i < 100; i++ ) {
			world.advanceTime(0.19);
			assertTrue(obj.getYVelocity() == 0 || obj.getYVelocity() < prev);
			prev = obj.getYVelocity();
		}
	}
	
	@Test
	public void touchesPlantTest() {
		assertEquals(null,obj.touches(Slime.class));
		assertEquals(plant,obj.touches(Plant.class));
	}
	
	@Test
	public void touchesTerrainTest() {
		assertFalse(obj.touches(TerrainType.MAGMA));
		assertTrue(obj.touches(TerrainType.AIR));
		world.setTerrainAt(obj.getPosition()[0]+obj.getWidth()-1,5,TerrainType.WATER);
		assertTrue(obj.touches(TerrainType.WATER));
	}

	@Test
	public void canHaveAsXVelocityTest() {
		assertTrue(obj.canHaveAsXVelocity(0));
		assertTrue(obj.canHaveAsXVelocity(X_INITIAL_VELOCITY));
		assertTrue(obj.canHaveAsXVelocity(X_VELOCITY_LIMIT));
		assertFalse(obj.canHaveAsXVelocity(0.01));
		assertFalse(obj.canHaveAsXVelocity(X_VELOCITY_LIMIT+0.1));
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
		assertTrue(obj.canHaveAsXAcceleration(0));
		obj.startMove(Mazub.Direction.LEFT);
		assertTrue(obj.canHaveAsXAcceleration(-1));
		obj.startMove(Mazub.Direction.RIGHT);
		assertTrue(obj.canHaveAsXAcceleration(1));
	}
	
	@Test
	public void startMoveTest() {
		obj.startMove(Mazub.Direction.RIGHT);
		assertEquals(obj.getXDirection(),Mazub.Direction.RIGHT);
		assertEquals(obj.getXVelocity(),X_INITIAL_VELOCITY,E);
		obj.startMove(Mazub.Direction.LEFT);
		assertEquals(obj.getXDirection(),Mazub.Direction.LEFT);
		assertEquals(obj.getXVelocity(),-X_INITIAL_VELOCITY,E);
	}
	
	@Test
	public void endMoveTest() {
		obj.startMove(Mazub.Direction.LEFT);
		obj.endMove();
		assertEquals(0,obj.getXVelocity(),E);
		assertEquals(Mazub.Direction.LEFT,obj.getXDirection());
		
	}
	
	
	@Test
	public void startJumpTest() {
		obj.startJump();
		assertEquals(Y_INITIAL_VELOCITY,obj.getYVelocity(),E);
		obj.advanceTime(0.1);
		assertTrue(obj.isJumping());
	}
	
	@Test(expected=JumpingException.class)
	public void startJumpExceptionTest() {
		for(int i = 0; i < 5; i++) {
			obj.startJump();
			obj.advanceTime(0.19);
		}
	}
	
	@Test
	public void endJumpTest() {
		obj.startJump();
		obj.advanceTime(0.19);
		obj.endJump();
		assertEquals(0,obj.getYVelocity(),E);
		assertTrue(obj.isJumping());
	}
	
	@Test(expected=JumpingException.class)
	public void endJumpExceptionTest() {
		obj.endJump();
	}
	
	@Test
	public void startDuckRightTest() {
		obj.startMove(Mazub.Direction.RIGHT);
		for(int i = 0; i < 5; i++) {
			obj.advanceTime(0.19);
		}
		obj.startDuck();
		assertTrue(obj.isDucking());
		assertEquals(obj.getXVelocityLimit(),DUCKED_VELOCITY_LIMIT,E);
		assertEquals(DUCKED_VELOCITY_LIMIT,obj.getXVelocity(),E);
		obj.advanceTime(0.1);
		assertEquals(DUCKED_VELOCITY_LIMIT,obj.getXVelocity(),E);
	}
	
	@Test
	public void startDuckLeftTest() {
		obj.startMove(Mazub.Direction.LEFT);
		obj.advanceTime(0.19);
		obj.startDuck();
		assertTrue(obj.isDucking());
		assertEquals(obj.getXVelocityLimit(),-DUCKED_VELOCITY_LIMIT,E);
	}

	@Test(expected=IllegalStateException.class)
	public void startDuckExceptionTest() {
		obj.startDuck();
		obj.startDuck();
	}
	
	@Test
	public void endDuckTest() {
		startDuckRightTest();
		obj.endDuck();
		startDuckLeftTest();
		obj.endDuck();
		assertEquals(obj.getXVelocityLimit(),-X_VELOCITY_LIMIT,E);
		assertFalse(obj.isDucking());
	}
	
	@Test(expected=IllegalStateException.class)
	public void endDuckExceptionTest() {
		obj.endDuck();
	}
	
	
	
	@Test
	public void timeSinceLastMoveTest() {
		obj.startMove(Mazub.Direction.RIGHT);
		obj.advanceTime(0.1);
		assertEquals(0,obj.getTimeSinceLastMove(),E);
		obj.endMove();
		obj.advanceTime(0.1);
		assertEquals(0.1,obj.getTimeSinceLastMove(),E);
	}
	
	@Test
	public void getXVelocityLimitTest() {
		obj.startMove(Mazub.Direction.RIGHT);
		assertEquals(X_VELOCITY_LIMIT,obj.getXVelocityLimit(),E);
		obj.startMove(Mazub.Direction.LEFT);
		assertEquals(-X_VELOCITY_LIMIT,obj.getXVelocityLimit(),E);
	}
	
	
	@Test
	public void getXAccelerationTest() {
		//Standing at (0,0)
		assertEquals(0,obj.getXAcceleration(),E);
		obj.startMove(Mazub.Direction.RIGHT);
		assertEquals(X_ACCELERATION,obj.getXAcceleration(),E);
		obj.advanceTime(0.125);
		obj.endMove();

		obj.startMove(Mazub.Direction.LEFT);
		assertEquals(-X_ACCELERATION,obj.getXAcceleration(),E);
	}
	
	@Test
	public void getYAccelerationTest() {
		assertEquals(0,obj.getYAcceleration(),E);
		obj.startJump();
		obj.advanceTime(0.01);
		assertEquals(-Y_ACCELERATION,obj.getYAcceleration(),E);
		obj.endJump();
		obj.advanceTime(0.19);
		assertEquals(0,obj.getYAcceleration(),E);
	}
	
	//getWidth, getHeight, getCurrentSprite: to be verified visually
	
	@Test
	public void getCurrentSpriteTest() {
		assertEquals(sprites[0],obj.getCurrentSprite());
		obj.startMove(Mazub.Direction.RIGHT);
		obj.advanceTime(0.01);
		assertEquals(sprites[8],obj.getCurrentSprite());
		obj.advanceTime(0.07);
		assertEquals(sprites[9],obj.getCurrentSprite());
		obj.endMove();
		obj.advanceTime(0.01);
		assertEquals(sprites[2],obj.getCurrentSprite());
		obj.startMove(Mazub.Direction.LEFT);
		obj.startJump();
		obj.advanceTime(0.01);
		assertEquals(sprites[5],obj.getCurrentSprite());
		obj.endMove();
		obj.startDuck();
		assertEquals(sprites[7],obj.getCurrentSprite());
	}
	
	@Test
	public void isRunningNormallyTest() {
		assertFalse(obj.isRunningNormally());
		obj.startMove(Mazub.Direction.LEFT);
		obj.advanceTime(0.1);
		assertTrue(obj.isRunningNormally());
		obj.startMove(Mazub.Direction.RIGHT);
		obj.advanceTime(0.1);
		assertTrue(obj.isRunningNormally());
		obj.startDuck();
		obj.advanceTime(0.1);
		assertFalse(obj.isRunningNormally());
		obj.endDuck();
		obj.startJump();
		obj.advanceTime(0.1);
		assertFalse(obj.isRunningNormally());
	}
	
	
	//additional tests
	
	@Test
	public void objRunningRightTest() {
		obj.startMove(Mazub.Direction.RIGHT);
		assertEquals(100,obj.getXVelocity(),E);
		assertEquals(0,obj.getY(),E);
		assertEquals(obj.getXDirection(),Mazub.Direction.RIGHT);
		
		obj.advanceTime(0.1);
		assertEquals(10.45,obj.getX(),E);
		assertEquals(0,obj.getY(),E);
	}
	
	
	@Test
	public void objMaxSpeedRightTest() {
		//Max speed after 20.0/9 seconds
		obj.startMove(Mazub.Direction.RIGHT);
		assertEquals(100,obj.getXVelocity(),E);
		obj.advanceTime(1.0/9);
		assertEquals(11.666666,obj.getX(),E);
		
		for(int i = 0; i < 18; i++)
			obj.advanceTime(1.0/9);
		assertEquals(290,obj.getXVelocity(),E);
		obj.advanceTime(1.0/9);
		assertEquals(300,obj.getXVelocity(),E);
		assertEquals(4000.0/9,obj.getX(),E);
	}
	

	
	@Test
	public void objRunningLeftTest() {
		obj.startMove(Mazub.Direction.RIGHT);
		obj.advanceTime(0.1);
		obj.endMove();
		obj.startMove(Mazub.Direction.LEFT);
		obj.startJump();
		obj.advanceTime(0.09);
		assertFalse(obj.getX()==0);
		obj.advanceTime(0.01);
		assertEquals(0,obj.getX(),E);
	}
	
	@Test
	public void canJumpTest() {
		assertTrue(obj.canJump());
		world.setTerrainAt(obj.getPosition()[0]+obj.getWidth()+1,5,TerrainType.SOLID_GROUND);
		obj.startJump();
		
		obj.startMove(Direction.RIGHT);
		world.advanceTime(0.05);
		obj.endMove();
		assertFalse(obj.canJump());
		for(int i = 0; i < 100; i++)
			world.advanceTime(0.1);
		assertTrue(obj.canJump());
	}
}
