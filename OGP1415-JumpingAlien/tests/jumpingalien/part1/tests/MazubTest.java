/**
 * 
 */
package jumpingalien.part1.tests;
import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jonathan
 *
 */
public class MazubTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	
	Mazub normalMazub;
	final double E = 1e-4;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Sprite[] sprites = JumpingAlienSprites.ALIEN_SPRITESET;
		normalMazub = new Mazub(0,0,sprites);
		
	}

	@Test
	public void normalMazubConstructorTest() {
		assertEquals(0,normalMazub.getX(),E);
		assertEquals(0,normalMazub.getY(),E);
		assertEquals(0,normalMazub.getXVelocity(),E);
		assertEquals(0,normalMazub.getYVelocity(),E);
		assertEquals(0,normalMazub.getXAcceleration(),E);
		assertEquals(0,normalMazub.getYAcceleration(),E);
	}

	@Test
	public void normalMazubRunningRightTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		assertEquals(100,normalMazub.getXVelocity(),E); //Is die 100 ok? Via getter? Maar dan ook elders gebruiken?
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
		
		for(int i = 0; i < 19; i++)
			normalMazub.advanceTime(1.0/9);
		assertEquals(290,normalMazub.getXVelocity(),E);
		normalMazub.advanceTime(1.0/9);
		assertEquals(300,normalMazub.getXVelocity(),E);
		assertEquals(4000.0/9,normalMazub.getX(),E);
	}
	
	@Test
	public void NormalMazubRightBoundaryTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		
		for(int i = 0; i < 100; i++)
			normalMazub.advanceTime(1.0/9);
		assertEquals((int) normalMazub.getX(),1023); //zelfde verhaal
		assertEquals(normalMazub.getXVelocity(),0,E);
		assertEquals(normalMazub.getXAcceleration(),0,E);
		
	}
	
	//...
	
	
	public void NormalMazubRunningLeftTest() {
		normalMazub.startMove(Mazub.Direction.RIGHT);
		normalMazub.advanceTime(1.0/9);
	}
}
