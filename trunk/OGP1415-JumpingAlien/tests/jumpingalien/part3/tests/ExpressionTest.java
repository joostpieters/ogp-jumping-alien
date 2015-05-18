package jumpingalien.part3.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.common.sprites.JumpingAlienSprites;
import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.model.ProgramFactory;
import jumpingalien.model.School;
import jumpingalien.model.Type;
import jumpingalien.model.World;
import jumpingalien.model.elements.Mazub;
import jumpingalien.model.elements.Slime;
import jumpingalien.model.elements.GameObject;
import jumpingalien.model.elements.GameTile;
import jumpingalien.part2.internal.Resources;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.Test;

public class ExpressionTest {

	ProgramFactory factory;
	Program program;
	SourceLocation location;
	Expression e;
	
	Mazub myMazub;
	Slime slime;
	World world;
	
	@Before
	public void setUp() throws Exception {
		factory = new ProgramFactory();
		program = factory.getMyProgram();
		Map<String, Type> variables = new HashMap<>();
		variables.put("A", Type.DOUBLE);
		variables.put("B", Type.GAME_ELEMENT);
		variables.put("C", Type.DIRECTION);
		variables.put("D", Type.BOOLEAN);
		program.initialiseVariables(variables);
		
		location = new SourceLocation(1, 2);
		myMazub = new Mazub(null, 100, 120,  JumpingAlienSprites.ALIEN_SPRITESET);
		slime = new Slime(null, 5, 120, new  Sprite[] {Resources.SLIME_SPRITE_LEFT,
				Resources.SLIME_SPRITE_RIGHT}, new School(), null);
		program.setGameObject(myMazub);
		world = new World(1000, 1000, 50, new int[] {400, 400}, 1000, 1000);
		world.addObject(myMazub);
		world.addObject(slime);
		assertEquals(world, myMazub.getMyWorld());
		assertEquals(world, slime.getMyWorld());
	}
	
	@Test
	public void getLocationTest() {
		e = factory.createReadVariable("A", Type.DOUBLE, location);
		assertEquals(e.getSourceLocation(), location);
		assertEquals(e.getType(), Type.DOUBLE);
		//Overige functionaliteit testen we indirect
	}

	@Test
	public void evalDefaultsTest() {
		e = factory.createReadVariable("A", Type.DOUBLE, null);
		assertEquals(e.eval(),0.0);
		e = factory.createReadVariable("B", Type.GAME_ELEMENT, null);
		assertEquals(e.eval(),null);
		e = factory.createReadVariable("C", Type.DIRECTION, null);
		assertEquals(e.eval(),null);
		e = factory.createReadVariable("D", Type.BOOLEAN, null);
		assertEquals(e.eval(),false);
		
	}
	
	@Test
	public void readVariableTest() {
		e = factory.createReadVariable("A", Type.DOUBLE, null);
		assertEquals(e.eval(),0.0);
		program.setVariableValue("A", Type.DOUBLE, 5.0);
		assertEquals(e.eval(),5.0);
		
	}
	
	@Test
	public void createTrivialTest() {
		e = factory.createDoubleConstant(7.0, null);
		assertEquals(e.eval(),7.0);
		e = factory.createTrue(null);
		assertEquals(e.eval(),true);
		e = factory.createFalse(null);
		assertEquals(e.eval(),false);
		e = factory.createNull(null);
		assertEquals(e.eval(),null);
	
		
		e = factory.createSelf(null);
		assertEquals(e.eval(),myMazub);
		
		e = factory.createDirectionConstant(Direction.UP, null);
		assertEquals(e.eval(),Direction.UP);
		
	}
	
	@Test
	public void createAdditionTest() {
		e = factory.createDoubleConstant(7.0, null);
		
		Expression e2;
		e2 = factory.createReadVariable("A", Type.DOUBLE, null);
		
		Expression sum = factory.createAddition(e,e2, null);
		assertEquals(sum.eval(), 7.0);
		
		program.setVariableValue("A", Type.DOUBLE, 5.0);
		
		assertEquals(sum.eval(), 12.0);
	}
	
	@Test
	public void createSubtractionTest() {
		e = factory.createDoubleConstant(7.0, null);
		
		Expression e2;
		e2 = factory.createReadVariable("A", Type.DOUBLE, null);
		
		Expression diff = factory.createSubtraction(e,e2, null);
		assertEquals(diff.eval(), 7.0);
		
		program.setVariableValue("A", Type.DOUBLE, 5.0);
		
		assertEquals(diff.eval(), 2.0);
	}
	
	@Test
	public void createMultiplicationTest() {
		e = factory.createDoubleConstant(7.0, null);
		
		Expression e2;
		e2 = factory.createReadVariable("A", Type.DOUBLE, null);
		
		Expression mult = factory.createMultiplication(e,e2, null);
		assertEquals(mult.eval(), 0.0);
		
		program.setVariableValue("A", Type.DOUBLE, 5.0);
		
		assertEquals(mult.eval(), 35.0);
	}
	
	@Test
	public void createDivisionTest() {
		e = factory.createDoubleConstant(7.0, null);
		
		Expression e2;
		e2 = factory.createReadVariable("A", Type.DOUBLE, null);
		program.setVariableValue("A", Type.DOUBLE, 1.0);
		
		Expression div = factory.createDivision(e,e2, null);
		assertEquals(div.eval(), 7.0);
		
		program.setVariableValue("A", Type.DOUBLE, 2.0);
		
		assertEquals(div.eval(), 3.5);
	}
	
	@Test
	public void createSqrtTest() {
		Expression e2 = factory.createReadVariable("A", Type.DOUBLE, null);
		program.setVariableValue("A", Type.DOUBLE, 64.0);
		
		e = factory.createSqrt(e2, null);
		assertEquals(e.eval(), 8.0);
		program.setVariableValue("A", Type.DOUBLE, 4.0);
		assertEquals(e.eval(), 2.0);
	}
	
	@Test
	public void createRandomTest() {
		//Moeilijk automatisch betrouwbaar te testen
		Expression e2 = factory.createReadVariable("A", Type.DOUBLE, null);
		
		e = factory.createRandom(e2 , null);
		
		System.out.println("This should look quite random (0 <= x < 100)");
		for(int i = 0; i < 10; i ++) {
			program.setVariableValue("A", Type.DOUBLE, 100.0);
			System.out.println(e.eval());
			assertTrue((double) e.eval() >= 0 && (double) e.eval() < 100);
			program.setVariableValue("A", Type.DOUBLE, 0.5);
			assertTrue((double) e.eval() >= 0 && (double) e.eval() < 5);
		}
		System.out.println("____________________");
		
	}
	
	@Test
	public void createLogicTest() {
		//Moeilijk automatisch betrouwbaar te testen
		Expression e2 = factory.createReadVariable("D", Type.BOOLEAN, null);
		Expression e3 = factory.createTrue(null);
		
		e = factory.createAnd(e2 , e3, null);
		assertEquals(e.eval(), false);
		e = factory.createOr(e2 , e3, null);
		assertEquals(e.eval(), true);
		e = factory.createNot(e2, null);
		assertEquals(e.eval(), true);
		
		program.setVariableValue("D", Type.BOOLEAN, true);
		
		e = factory.createAnd(e2 , e3, null);
		assertEquals(e.eval(), true);
		e = factory.createOr(factory.createNot(e3, null) , factory.createNot(e2, null), null);
		assertEquals(e.eval(), false);
		e = factory.createNot(e2, null);
		assertEquals(e.eval(), false);
		
	}
	
	@Test
	public void createEqualityTest() {
		Expression e2 = factory.createReadVariable("A", Type.DOUBLE, null);
		Expression e3 = factory.createDoubleConstant(5.0, null);
		
		
		e = factory.createLessThan(e2 , e3, null);
		assertEquals(e.eval(), true);
		e = factory.createLessThanOrEqualTo(e2 , e3, null);
		assertEquals(e.eval(), true);
		
		e = factory.createLessThanOrEqualTo(e2 , e2, null);
		assertEquals(e.eval(), true);
		
		e = factory.createGreaterThan(e2 , e3, null);
		assertEquals(e.eval(), false);
		e = factory.createGreaterThanOrEqualTo(e2 , e3, null);
		assertEquals(e.eval(), false);
		
		e = factory.createGreaterThanOrEqualTo(e2 , e2, null);
		assertEquals(e.eval(), true);
		
		e = factory.createEquals(e2 , e3, null);
		assertEquals(e.eval(), false);
		e = factory.createNotEquals(e2 , e3, null);
		assertEquals(e.eval(), true);
		
		program.setVariableValue("A", Type.DOUBLE, 5.0);
		
		e = factory.createLessThan(e2 , e3, null);
		assertEquals(e.eval(), false);
		e = factory.createLessThanOrEqualTo(e2 , e3, null);
		assertEquals(e.eval(), true);
		e = factory.createEquals(e2 , e3, null);
		assertEquals(e.eval(), true);
		e = factory.createNotEquals(e2 , e3, null);
		assertEquals(e.eval(), false);
		e = factory.createGreaterThan(e2 , e3, null);
		assertEquals(e.eval(), false);
		e = factory.createGreaterThanOrEqualTo(e2 , e3, null);
		assertEquals(e.eval(), true);
	}
	
	@Test
	public void getObjectPropertiesTest() {
		e = factory.createGetX(factory.createSelf(null), null);
		assertEquals(e.eval(), 100.0);
		e = factory.createGetY(factory.createSelf(null), null);
		assertEquals(e.eval(), 120.0);
		e = factory.createGetWidth(factory.createSelf(null), null);
		assertEquals(e.eval(), (double) myMazub.getWidth());
		e = factory.createGetHeight(factory.createSelf(null), null);
		assertEquals(e.eval(), (double) myMazub.getHeight());
		e = factory.createGetHitPoints(factory.createSelf(null), null);
		assertEquals(e.eval(), (double) myMazub.getHitPoints());
	}
	
	@Test
	public void createGetTileTest() {
		world.setTerrainAt(0,0, World.TerrainType.MAGMA);
		e = factory.createGetTile(factory.createDoubleConstant(49,  null), 
									factory.createDoubleConstant(1,  null),
										null);
		assertEquals(((GameTile)e.eval()).getTerrainType(), World.TerrainType.MAGMA);
	}
	
	@Test
	public void createSearchObjectTest() {
		world.setTerrainAt(0, 2, World.TerrainType.SOLID_GROUND);
		e = factory.createSearchObject(
				factory.createDirectionConstant(Direction.LEFT, null), null);
		assertEquals(e.eval(), slime);
		e = factory.createSearchObject(
				factory.createDirectionConstant(Direction.RIGHT, null), null);
		assertEquals(e.eval(), null);
		world.setTerrainAt(5, 2, World.TerrainType.SOLID_GROUND);
		assertEquals(((GameTile)(e.eval())).getTerrainType(), World.TerrainType.SOLID_GROUND);
		
	}
	
	@Test
	public void createIsTest() {
		world.setTerrainAt(5, 2, World.TerrainType.SOLID_GROUND);
		e = factory.createSearchObject(
				factory.createDirectionConstant(Direction.LEFT, null), null);
		assertTrue((boolean) factory.createIsSlime(e, null).eval());
		assertFalse((boolean) factory.createIsShark(e, null).eval());
		assertFalse((boolean) factory.createIsMazub(e, null).eval());
		assertFalse((boolean) factory.createIsPlant(e, null).eval());
		assertFalse((boolean) factory.createIsTerrain(e, null).eval());
		e = factory.createSearchObject(
				factory.createDirectionConstant(Direction.RIGHT, null), null);
		assertFalse((boolean) factory.createIsSlime(e, null).eval());
		assertFalse((boolean) factory.createIsShark(e, null).eval());
		assertFalse((boolean) factory.createIsMazub(e, null).eval());
		assertFalse((boolean) factory.createIsPlant(e, null).eval());
		assertTrue((boolean) factory.createIsTerrain(e, null).eval());
		
		assertFalse((boolean) factory.createIsPassable(e, null).eval());
		assertFalse((boolean) factory.createIsAir(e, null).eval());
		assertFalse((boolean) factory.createIsMagma(e, null).eval());
		assertFalse((boolean) factory.createIsWater(e, null).eval());
		
		e = factory.createSelf(null);
		assertFalse((boolean) factory.createIsDead(e, null).eval());
		assertFalse((boolean) factory.createIsMoving(e, 
				factory.createDirectionConstant(Direction.RIGHT, null), null).eval());
		assertFalse((boolean) factory.createIsJumping(e, null).eval());
		assertFalse((boolean) factory.createIsDucking(e, null).eval());
		myMazub.startDuck();
		assertTrue((boolean) factory.createIsDucking(e, null).eval());
		myMazub.startMove(GameObject.Direction.RIGHT);
		assertTrue((boolean) factory.createIsMoving(e, 
				factory.createDirectionConstant(Direction.RIGHT, null), null).eval());
		assertFalse((boolean) factory.createIsMoving(e, 
				factory.createDirectionConstant(Direction.LEFT, null), null).eval());
		//moet, want een object berekent pas in advanceTime of het aan het springen is
		world.advanceTime(0.1);
		assertTrue((boolean) factory.createIsJumping(e, null).eval());
		
	}
	

}
