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
 * Tests for the Slime class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class SlimeTest {

	World world;
	Slime slime1;
	Slime slime2;
	Slime slime3;
	School school1;
	School school2;
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
		world = new World(5000, 5000, 70, new int[] {500, 500}, 0, 0);
		school1 = new School();
		school2 = new School();
		mazub = new Mazub(world, 70, 1000, mazubSprites);
		slime1 = new Slime(world, 70, 0, slimeSprites, school1);
		slime1.substractHitPoints(5, false);
		slime2 = new Slime(world, 70, 200, slimeSprites, school2);
		slime2.substractHitPoints(5, false);
		slime3 = new Slime(world, 70, 500, slimeSprites, school1);
		slime3.substractHitPoints(5, false);
		for(int i = 0; i < 20; i++ ) {
			world.setTerrainAt(0,i, TerrainType.SOLID_GROUND);
			world.setTerrainAt(2,i, TerrainType.SOLID_GROUND);
		}
		world.addObject(mazub);
		world.addObject(slime1);
		world.addObject(slime2);
		world.addObject(slime3);
	}

	@Test
	public void slimeConstructorTest() {
		assertEquals(school2, slime2.getSchool());
		assertTrue(school2.hasAsSlime(slime2));
	}
	
	@Test
	public void transferToSchoolTest() {
		slime1.transferToSchool(school2);
		assertEquals(school2,slime1.getSchool());
		assertFalse(school1.hasAsSlime(slime1));
		assertTrue(school2.hasAsSlime(slime1));
		
		assertEquals(96, slime3.getHitPoints());
		assertEquals(95,slime1.getHitPoints());
		assertEquals(94,slime2.getHitPoints());
	}
	
	@Test
	public void substractHitPointsTest() {
		slime1.substractHitPoints(2);
		assertEquals(93,slime1.getHitPoints());
		assertEquals(94,slime3.getHitPoints());
		
		assertEquals(95,slime2.getHitPoints());
		
	}
	
	@Test
	public void advanceTimeTest() {
		world.advanceTime(0.19);
		assertEquals(0.19,slime1.getTimer(),1e-4);
		world.advanceTime(0.19);
		assertEquals(0.38,slime1.getTimer(),1e-4);
		for(int i = 0; i < 100; i++) {
			world.advanceTime(0.19);
			assertTrue(slime1.getTimer()<slime1.getGoal());
		}
	}
	
	@Test
	public void handleSlimeCollisionTest() {
		for(int i = 0; i < 10; i++)
			world.advanceTime(0.19);
		assertEquals(school1, slime2.getSchool());
	}
	
	@Test
	public void handleMazubCollisionTest() {
		world.removeObject(slime1);
		world.removeObject(slime2);
		for(int i = 0; i < 11; i++)
			world.advanceTime(0.19);
		assertEquals(45,slime3.getHitPoints());
		world.advanceTime(0.19);
		assertEquals(0,slime3.getHitPoints());
	}
	
	
	@Test
	public void handleSharkCollisionTest() {
		world.removeObject(slime1);
		world.removeObject(slime2);
		assertEquals(95, slime3.getHitPoints());
		Shark shark = new Shark(world, 70, 0, sharkSprites);
		world.addObject(shark);
		for(int i = 0; i < 7; i++)
			world.advanceTime(0.19);
		assertEquals(null,slime3.touches(Mazub.class));
		assertEquals(shark,slime3.touches(Shark.class));
		assertEquals(45, slime3.getHitPoints());
		
	}
	
	@Test
	public void handleWaterTest() {
		world.removeObject(slime2);
		world.removeObject(slime3);
		world.setTerrainAt(1,0, TerrainType.WATER);
		world.advanceTime(0.15);
		assertEquals(95,slime1.getHitPoints());
		world.advanceTime(0.15);
		assertEquals(93,slime1.getHitPoints());
		world.advanceTime(0.05);
		assertEquals(93,slime1.getHitPoints());
	}
	
	@Test
	public void handleMagmaTest() {
		world.removeObject(slime2);
		world.removeObject(slime3);
		world.setTerrainAt(1,0, TerrainType.MAGMA);
		world.advanceTime(0.01);
		assertEquals(45,slime1.getHitPoints());
		world.advanceTime(0.15);
		assertEquals(45,slime1.getHitPoints());
		world.advanceTime(0.04);
		assertEquals(0,slime1.getHitPoints());
	}

	//getCurrentSprite(), startNewMovement() to be tested visually
	
	

}
