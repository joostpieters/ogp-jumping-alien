/**
 * 
 */
package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.GameObject;
import jumpingalien.model.GameObject.Direction;
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
 * Tests for the World class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class WorldTest {

	
	World world;
	Mazub mazub;
	Slime slime1;
	Slime slime2;
	School school;
	private final Sprite[] mazubSprites = JumpingAlienSprites.ALIEN_SPRITESET;
	private final Sprite[] slimeSprites = {Resources.SLIME_SPRITE_LEFT,
												Resources.SLIME_SPRITE_RIGHT};
	
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
		int[] windowSize = {500,500};
		world = new World(5000, 5000, 100, windowSize, 0, 0);
		mazub = new Mazub(world, 110, 0, mazubSprites);
		school = new School();
		slime1 = new Slime(world, 200, 0, slimeSprites, school, null);
		slime2 = new Slime(world, 300, 200, slimeSprites, school, null);
	}

	@Test
	public void worldConstructorTest() {
		assertEquals(5000, world.getXLimit());
		assertEquals(5000, world.getYLimit());
		assertEquals(100, world.getTileLength());
		assertEquals(0, world.getGameObjects().size());
		assertArrayEquals(new int[] {500,500},world.getWindowSize());
		assertEquals(0, world.getTargetTileX());
		assertEquals(0, world.getTargetTileY());
		assertFalse(world.isGameOver());
		assertFalse(world.getDidPlayerWin());
		assertArrayEquals(new int[] {0,0},world.getWindowPosition());
		
		assertEquals(TerrainType.AIR, world.getTerrainAt(0,0));
		assertEquals(TerrainType.AIR, world.getTerrainAt(4999,4999));
		assertEquals(TerrainType.AIR, world.getTerrainAt(123,456));
		
		assertEquals(null, world.getMyMazub());
		
	}
	
	@Test
	public void addObjectTest() {
		world.addObject(mazub);
		assertEquals(1, world.getGameObjects().size());
		assertEquals(mazub, world.getMyMazub());
		for(GameObject obj: world.getGameObjects())
			assertEquals(mazub, obj);
		world.addObject(slime1);
		world.addObject(slime2);
		assertEquals(3, world.getGameObjects().size());
	}
	
	@Test
	public void instancesOfClassTest() {
		addObjectTest();
		assertEquals(1, world.getAllInstancesOf(Mazub.class).size());
		assertEquals(2, world.getAllInstancesOf(Slime.class).size());
		assertEquals(0, world.getAllInstancesOf(Shark.class).size());
	}
	
	@Test
	public void removeObjectTest() {
		addObjectTest();
		world.removeObject(slime1);
		assertEquals(2, world.getGameObjects().size());
	}
	
	@Test
	public void getGameObjectTest() {
		addObjectTest();
		assertEquals(3, world.getGameObjects().size());
		for(GameObject obj: world.getGameObjects())
			assertTrue(obj == slime1 || obj == mazub || obj == slime2);
	}

	@Test
	public void advanceTimeTest() {
		addObjectTest();
		mazub.startMove(Direction.LEFT);
		Slime slime3 = new Slime(world, 10000, 10000, slimeSprites, school, null);
		world.addObject(slime3);
		assertFalse(world.getDidPlayerWin());
		assertFalse(mazub.isTerminated());
		assertFalse(slime1.isTerminated());
		for(int i = 0; i < 6; i++)
			world.advanceTime(0.19);
		assertTrue(world.getDidPlayerWin());
		assertTrue(mazub.isTerminated());
		assertFalse(slime1.isTerminated());
		assertTrue(slime3.isTerminated());
	}
	
	@Test
	public void setWindowSizeTest() {
		world.setWindowSize(123, 456);
		assertArrayEquals(new int[] {123, 456}, world.getWindowSize());
	}
	
	@Test
	public void canHaveAsWindowPositionTest() {
		assertTrue(world.canHaveAsWindowPosition(0, 0));
		assertTrue(world.canHaveAsWindowPosition(4500, 4500));
		assertTrue(world.canHaveAsWindowPosition(4501, 4501));
		assertFalse(world.canHaveAsWindowPosition(4502, 4501));
		assertFalse(world.canHaveAsWindowPosition(-1, 0));
	}
	
	@Test
	public void terrainAtTest() {
		world.setTerrainAt(40, 40, TerrainType.WATER);
		assertEquals(TerrainType.WATER,world.getTerrainAt(4023, 4006));
		assertEquals(TerrainType.WATER,world.getTerrainAt(4000, 4000));
		assertEquals(TerrainType.AIR,world.getTerrainAt(4100, 4100));
		assertEquals(TerrainType.AIR,world.getTerrainAt(6000, -4000));
		assertEquals(TerrainType.AIR,world.getTerrainAt(-1, 0));
	}
	
	@Test
	public void getMatchingTileTest() {
		assertArrayEquals(new int[] {0,0}, world.getMatchingTile(0,0));
		assertArrayEquals(new int[] {1,0}, world.getMatchingTile(100,0));
		assertArrayEquals(new int[] {0,1}, world.getMatchingTile(50,100));
		assertArrayEquals(new int[] {0,0}, world.getMatchingTile(12,34));
	}
	
	@Test
	public void endGameTest() {
		assertFalse(world.isGameOver());
		world.endGame();
		assertTrue(world.isGameOver());
	}
	
	//adjustWindow(): to be tested visually
}
