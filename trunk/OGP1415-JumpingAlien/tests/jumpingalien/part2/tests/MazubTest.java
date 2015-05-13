
package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.School;
import jumpingalien.model.World;
import jumpingalien.model.World.TerrainType;
import jumpingalien.model.elements.Mazub;
import jumpingalien.model.elements.Plant;
import jumpingalien.model.elements.Shark;
import jumpingalien.model.elements.Slime;
import jumpingalien.part2.internal.Resources;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the Mazub class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class MazubTest {

	World world;
	Mazub mazub;
	Slime slime;
	Shark shark;

	private Sprite[] mazubSprites = JumpingAlienSprites.ALIEN_SPRITESET;
	private Sprite[] plantSprites = {Resources.PLANT_SPRITE_LEFT, Resources.PLANT_SPRITE_RIGHT};
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
		mazub = new Mazub(world, 0, 0, mazubSprites);
		world.addObject(mazub);
	}

	@Test
	public void touchesWaterTest() {
		world.setTerrainAt(0, 0, TerrainType.WATER);
		world.advanceTime(0.1);
		assertEquals(100,mazub.getHitPoints());
		world.advanceTime(0.1);
		assertEquals(98,mazub.getHitPoints());
	}
	
	@Test
	public void touchesMagmaTest() {
		world.setTerrainAt(0, 0, TerrainType.MAGMA);
		world.advanceTime(0.01);
		assertEquals(50,mazub.getHitPoints());
		world.advanceTime(0.1);
		assertEquals(50,mazub.getHitPoints());
		world.advanceTime(0.1);
		assertEquals(0,mazub.getHitPoints());
	}
	
	@Test
	public void touchesPlantTest(){
		Plant plant = new Plant(world, mazub.getPosition()[0]+mazub.getWidth()+5, 0, plantSprites, null);
		world.addObject(plant);
		for(int i = 0; i < 3; i++)
			world.advanceTime(0.1);
		assertEquals(150, mazub.getHitPoints());
		assertFalse(world.getGameObjects().contains(plant));
	}
	
	
	@Test
	public void touchesSharkTest() {
		Shark shark = new Shark(world, 0, 0, sharkSprites, null);
		world.addObject(shark);
		world.advanceTime(0.1);
		assertEquals(50,mazub.getHitPoints());
		world.advanceTime(0.1);
		assertEquals(50,mazub.getHitPoints());
	}
	
	@Test
	public void touchesSlimeTest() {
		School school = new School();
		Slime slime = new Slime(world, 0, 0, sharkSprites, school, null);
		world.addObject(slime);
		world.advanceTime(0.1);
		assertEquals(50,mazub.getHitPoints());
		world.advanceTime(0.1);
		assertEquals(50,mazub.getHitPoints());
	}
	
	//getCurrentSprite() to be tested visually

}
