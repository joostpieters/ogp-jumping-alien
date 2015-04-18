package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.part2.internal.Resources;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests for the AutomaticObject class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class AutomaticObjectTest {
	
	Plant plant;
	World world;
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
		plant = new Plant(world, 250, 0, plantSprites);
		
		world.addObject(plant);
		world.addObject(mazub);
	}

	@Test
	public void automaticObjectConstructorTest() {
		assertEquals(0,plant.getTimer(),1e-4);
	}
	

}
