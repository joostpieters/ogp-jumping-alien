package jumpingalien.part2.tests;

import static org.junit.Assert.*;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.Mazub;
import jumpingalien.model.School;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part2.internal.Resources;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the School class.
 * 
 * @author Jonathan Oostvogels & Andreas Schryvers
 * @version 1.0
 */
public class SchoolTest {

	World world;
	Slime slime1, slime2, slime3;
	School school;
	Mazub mazub;
	
	private final Sprite[] mazubSprites = JumpingAlienSprites.ALIEN_SPRITESET;
	private final Sprite[] slimeSprites = {Resources.SLIME_SPRITE_LEFT,
												Resources.SLIME_SPRITE_RIGHT};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		world = new World(5000, 5000, 70, new int[] {500, 500}, 0, 0);
		school = new School();
		mazub = new Mazub(world, 4000, 0, mazubSprites);
	}

	@Test
	public void constructorTest() {
		assertEquals(0,school.getNbSlimes());
	}
	
	@Test
	public void addAsSlimeTest() {
		slime1 = new Slime(world, 70, 0, slimeSprites, school);
		assertEquals(1,school.getNbSlimes());
		assertTrue(school.hasAsSlime(slime1));
		assertFalse(school.hasAsSlime(slime2));
	}
	
	@Test
	public void removeAsSlimeTest() {
		slime1 = new Slime(world, 70, 0, slimeSprites, school);
		slime2 = new Slime(world, 70, 200, slimeSprites, school);
		School otherSchool = new School();
		slime1.transferToSchool(otherSchool);
		assertFalse(school.hasAsSlime(slime1));
		assertTrue(school.hasAsSlime(slime2));
		assertEquals(1,school.getNbSlimes());
	}
	
	@Test
	public void canHaveAsSlimeTest() {
		slime3 = new Slime(world, 70, 500, slimeSprites, school);
		assertFalse(school.canHaveAsSlime(null));
		assertTrue(school.canHaveAsSlime(slime3));
	}

}
