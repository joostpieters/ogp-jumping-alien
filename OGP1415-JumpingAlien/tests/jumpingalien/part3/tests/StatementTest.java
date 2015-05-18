package jumpingalien.part3.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import jumpingalien.model.statements.Statement;
import jumpingalien.part2.internal.Resources;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.util.Sprite;

import org.junit.Before;
import org.junit.Test;

public class StatementTest {


	ProgramFactory factory;
	Program program;
	SourceLocation location;
	Expression e;
	Map<String, Type> variables = new HashMap<>();
	Statement s;
	
	Mazub myMazub;
	Slime slime;
	World world;
	
	@Before
	public void setUp() throws Exception {
		factory = new ProgramFactory();
		program = factory.getMyProgram();
		variables.put("A", Type.DOUBLE);
		variables.put("B", Type.GAME_ELEMENT);
		variables.put("C", Type.DIRECTION);
		variables.put("D", Type.BOOLEAN);
		variables.put("E", Type.DOUBLE);
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
		
		e = factory.createReadVariable("A", Type.DOUBLE, null);
	}

	@Test
	public void createAssignmentTest() {
		assertEquals(e.eval(),0.0);
		s = factory.createAssignment("A", Type.DOUBLE, 
				factory.createDoubleConstant(7.0, null), null);
		s.execute();
		assertEquals(e.eval(),7.0);
		s = factory.createAssignment("C", Type.DIRECTION, 
				factory.createDirectionConstant(Direction.RIGHT, null), null);
		s.execute();
		e = factory.createReadVariable("C", Type.DIRECTION, null);
		assertEquals(e.eval(),Direction.RIGHT);
	}
	
	@Test
	public void createWhileSequenceTest() {
		//A++
		Statement body1 = factory.createAssignment("A", Type.DOUBLE, 
				factory.createAddition(e,factory.createDoubleConstant(1.0, null), null), null);
		//E=A
		Statement body2 = factory.createAssignment("E", Type.DOUBLE, 
				factory.createReadVariable("A", Type.DOUBLE, null), null);
		List<Statement> lst = new ArrayList<>();
		lst.add(body1);
		lst.add(body2);
		Statement body = factory.createSequence(lst, null);
		s = factory.createWhile(
				factory.createLessThan(e, factory.createDoubleConstant(10, null), null), body, null);
		factory.createProgram(s, variables).advanceTime(5);
		assertEquals(e.eval(), 10.0);
		assertEquals(program.getVariableValue("E", Type.DOUBLE), 10.0);
		
	}
	
	@Test
	public void createForEachPrintTest() {
		
		
		//OVER ALLES
		program.setVariableValue("A", Type.DOUBLE, 0.0);
		//A++
		Statement body = factory.createAssignment("A", Type.DOUBLE, 
				factory.createAddition(e,factory.createDoubleConstant(1.0, null), null), null);
		Statement foreach = factory.createForEach("B", Kind.ANY, 
				null, null, null, body, null);
		Statement hang = factory.createWhile(factory.createTrue(null), 
				factory.createSkip(null), null);
		List<Statement> lst = new ArrayList<>();
		lst.add(foreach);
		lst.add(hang);
		Statement s = factory.createSequence(lst, null);
		
		factory.createProgram(s, variables).advanceTime(5);
		assertEquals(e.eval(),2.0);
		
		
		//OVER SLIMES
		program.setVariableValue("A", Type.DOUBLE, 0.0);
		//A++
		body = factory.createAssignment("A", Type.DOUBLE, 
				factory.createAddition(e,factory.createDoubleConstant(1.0, null), null), null);
		foreach = factory.createForEach("B", Kind.SLIME, 
				null, null, null, body, null);
		hang = factory.createWhile(factory.createTrue(null), 
				factory.createSkip(null), null);
		lst = new ArrayList<>();
		lst.add(foreach);
		lst.add(hang);
		s = factory.createSequence(lst, null);
		
		factory.createProgram(s, variables).advanceTime(5);
		assertEquals(e.eval(),1.0);
		
		//OVER x < 50
		program.setVariableValue("A", Type.DOUBLE, 0.0);
		//A++
		body = factory.createAssignment("A", Type.DOUBLE, 
				factory.createAddition(e,factory.createDoubleConstant(1.0, null), null), null);
		foreach = factory.createForEach("B", Kind.MAZUB, 
						factory.createLessThan(
								factory.createGetX(
										factory.createReadVariable("B", Type.GAME_ELEMENT, null), null),
								factory.createDoubleConstant(50, null), 
						null), 
						null, null, body, null);
		hang = factory.createWhile(factory.createTrue(null), 
				factory.createSkip(null), null);
		lst = new ArrayList<>();
		lst.add(foreach);
		lst.add(hang);
		s = factory.createSequence(lst, null);
		
		factory.createProgram(s, variables).advanceTime(5);
		assertEquals(e.eval(),0.0);
		
		//OVER ALLES, GESORTEERD OP X
		System.out.println("This should print a mazub and a slime. The mazub should be listed first.");
		body = factory.createPrint(factory.createReadVariable("B", Type.GAME_ELEMENT, null), null);
		foreach = factory.createForEach("B", Kind.ANY, 
				null, factory.createGetX(factory.createReadVariable("B", Type.GAME_ELEMENT, null), null), 
							SortDirection.DESCENDING, body, null);
		hang = factory.createWhile(factory.createTrue(null), 
				factory.createSkip(null), null);
		lst = new ArrayList<>();
		lst.add(foreach);
		lst.add(hang);
		s = factory.createSequence(lst, null);
		
		factory.createProgram(s, variables).advanceTime(5);
		
	}
	
	@Test
	public void createBreakTest() {

		//A++
		Statement body1 = factory.createAssignment("A", Type.DOUBLE, 
				factory.createAddition(e,factory.createDoubleConstant(1.0, null), null), null);
		Statement body2 = factory.createBreak(null);
		List<Statement> lst = new ArrayList<>();
		lst.add(body2);
		lst.add(body1);
		Statement body = factory.createSequence(lst, null);
		s = factory.createWhile(
				factory.createLessThan(e, factory.createDoubleConstant(10, null), null), body, null);
		factory.createProgram(s, variables).advanceTime(20);
		assertEquals(e.eval(), 0.0);
			
	}
	
	@Test
	public void createIfTest() {
		s = factory.createAssignment("A", Type.DOUBLE, factory.createDoubleConstant(1.0, null), null);
		Statement s2 = factory.createAssignment("A", Type.DOUBLE, 
				factory.createDoubleConstant(2.0, null), null);
		Statement ifelse = factory.createIf(factory.createTrue(null), s, s2, null);
		factory.createProgram(ifelse, variables).advanceTime(20);
		assertEquals(e.eval(), 1.0);
		ifelse = factory.createIf(factory.createFalse(null), s, s2, null);
		factory.createProgram(ifelse, variables).advanceTime(20);
		assertEquals(e.eval(), 2.0);
	}
	
	
	//Start- en stopstatements testen we visueel
	
	// Wait en skip testen we bij het tijdsgedrag in Program
	//createProgram wordt impliciet getest, net zoals checkArguments

}
