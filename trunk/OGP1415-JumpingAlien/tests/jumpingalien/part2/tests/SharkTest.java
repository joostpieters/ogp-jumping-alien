/**
 * 
 */
package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.Mazub;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.World.TerrainType;
import jumpingalien.part2.internal.Resources;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the Shark class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class SharkTest {
	
	
	World world;
	Shark shark;
	Mazub mazub;
	
	private Sprite[] mazubSprites = JumpingAlienSprites.ALIEN_SPRITESET;
	private Sprite[] slimeSprites = {Resources.SLIME_SPRITE_LEFT,
												Resources.SLIME_SPRITE_RIGHT};
	private Sprite[] sharkSprites = {Resources.SHARK_SPRITE_LEFT,Resources.SHARK_SPRITE_RIGHT};

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		world = new World(5000, 5000, 10, new int[] {500, 500}, 0, 0);
		shark = new Shark(world, 10, 0, sharkSprites);
		mazub = new Mazub(world, 10, 400, mazubSprites);

		for(int i = 0; i < 20; i++ ) {
			world.setTerrainAt(0,i, TerrainType.SOLID_GROUND);
			world.setTerrainAt(10,i, TerrainType.SOLID_GROUND);
		}
		world.addObject(mazub);
		world.addObject(shark);
	}
	
	@Test
	public void sharkConstructorTest() {
		assertEquals(0,shark.getCounter());
	}
	
	@Test
	public void advanceTimeTest() {
		world.advanceTime(0.19);
		assertEquals(0.19,shark.getTimer(),1e-4);
		world.advanceTime(0.19);
		assertEquals(0.38,shark.getTimer(),1e-4);
		for(int i = 0; i < 100; i++) {
			world.advanceTime(0.19);
			assertTrue(shark.getTimer()<=shark.getGoal());
		}
	}
	

	
	//getCurrentSprite(), startNewMovement() to be tested visually
	
	@Test
	public void touchesAirTest() {
		world.advanceTime(0.1);
		assertEquals(100,shark.getHitPoints());
		world.advanceTime(0.1);
		assertEquals(94,shark.getHitPoints());
		for(int j = 0; j < 500; j++)
			for(int i = 1; i < 10; i++)
				world.setTerrainAt(i,j,TerrainType.WATER);
		for(int i = 0; i < 4; i++)
			world.advanceTime(0.1);
		assertEquals(94,shark.getHitPoints());
	}
	
	@Test
	public void touchesMagmaTest() {
		assertEquals(100, shark.getHitPoints());
		world.setTerrainAt(1,0,TerrainType.MAGMA);
		world.advanceTime(0.01);
		assertEquals(50, shark.getHitPoints());
		world.advanceTime(0.19);
		assertEquals(0, shark.getHitPoints());
		
	}
	
	@Test
	public void touchesMazubTest() {
		world = new World(5000, 5000, 10, new int[] {500, 500}, 0, 0);
		shark = new Shark(world, 10, 0, sharkSprites);
		mazub = new Mazub(world, 10, 0, mazubSprites);
		world.addObject(shark);
		world.addObject(mazub);
		world.advanceTime(0.01);
		world.advanceTime(0.01);
		assertEquals(50, shark.getHitPoints());
	}

	
	@Test
	public void touchesSlimeTest() {
		world = new World(5000, 5000, 10, new int[] {500, 500}, 0, 0);
		shark = new Shark(world, 10, 0, sharkSprites);
		mazub = new Mazub(world, 10, 500, mazubSprites);
		School school = new School();
		Slime slime = new Slime(world, 10,0,slimeSprites, school);
		world.addObject(shark);
		world.addObject(mazub);
		world.addObject(slime);
		world.advanceTime(0.01);
		world.advanceTime(0.01);
		assertEquals(50, shark.getHitPoints());
	}
	
	
	@Test
	public void canJumpTest() {
		assertTrue(shark.canJump());
		Shark shark2 = new Shark(world, 10, 200, sharkSprites);
		assertFalse(shark2.canJump());
		world.setTerrainAt(3,20,TerrainType.WATER);
		assertTrue(shark2.canJump());
	}
	
	@Test
	public void isSubmergedTest() {
		assertFalse(shark.isSubmerged());
		for(int i = 1; i < 8; i++)
			world.setTerrainAt(i,0,TerrainType.WATER);
		assertFalse(shark.isSubmerged());
		for(int j = 1; j < 5; j++)
			for(int i = 1; i < 8; i++)
				world.setTerrainAt(i,j,TerrainType.WATER);
		assertTrue(shark.isSubmerged());
		
	}
	
	@Test
	public void getYAccelerationTest() {
		for(int j = 1; j < 5; j++)
			for(int i = 1; i < 8; i++)
				world.setTerrainAt(i,j,TerrainType.WATER);
		assertEquals(0,shark.getYAcceleration(),1e-4);
		
		Shark shark2 = new Shark(world,10,200,sharkSprites);
		assertEquals(-1000,shark2.getYAcceleration(),1e-4);
		
		
	}
	
	
	@Test
	public void getDivingAccelerationTest() {
		for(int i = 1; i < 100; i++) {
			world.advanceTime(0.19);
			assertTrue(Math.abs(shark.getDivingAcceleration())<=20);
		}
		
	}

	
}
