package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.World;
import jumpingalien.model.elements.Mazub;
import jumpingalien.model.elements.Plant;
import jumpingalien.part2.internal.Resources;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests for the Plant class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class PlantTest {

	
	World world;
	Plant plant;
	Mazub mazub;
	
	private Sprite[] mazubSprites = JumpingAlienSprites.ALIEN_SPRITESET;
	private Sprite[] plantSprites = {Resources.PLANT_SPRITE_LEFT, Resources.PLANT_SPRITE_RIGHT};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		world = new World(5000, 5000, 10, new int[] {500, 500}, 0, 0);
		mazub = new Mazub(world, 0, 0, mazubSprites);
		plant = new Plant(world, 250, 0, plantSprites, null);
		
		world.addObject(plant);
		world.addObject(mazub);
		
	}

	@Test
	public void plantConstructorTest() {
		assertEquals(0,plant.getTimer(),1e-4);
		assertEquals(0.5,plant.getGoal(),1e-4);
	}
	
	@Test
	public void advanceTimeTest() {
		double maxX = plant.getX();
		int i;
		for(i = 0; i < 500; i++)
			world.advanceTime(0.001);
		double minX = plant.getX();
		for(i = 0; i < 150; i++) {
			world.advanceTime(0.123);
			assertTrue(plant.getTimer()<plant.getGoal());
			assertTrue(plant.getTimer()>=0);
			assertTrue(plant.getX()<=maxX);
			assertTrue(plant.getX()>=minX);
		}
			
		
	}
	
	@Test
	public void terminateTest() {
		assertFalse(plant.isTerminated());
		assertTrue(world.getGameObjects().contains(plant));
		plant.terminate(false);
		world.advanceTime(0.01);
		assertFalse(world.getGameObjects().contains(plant));
	}

	
	//getCurrentSprite(), startNewMovement() to be tested visually
}
