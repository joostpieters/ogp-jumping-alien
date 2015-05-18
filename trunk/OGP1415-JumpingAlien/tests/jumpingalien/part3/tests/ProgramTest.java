package jumpingalien.part3.tests;

import static org.junit.Assert.*;
import jumpingalien.model.Program;
import jumpingalien.model.Type;
import jumpingalien.model.elements.Buzam;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.programs.ParseOutcome;

import org.junit.Before;
import org.junit.Test;

public class ProgramTest {

	//We testen via de facade
	
	Facade facade;
	ParseOutcome<?> outcome;
	
	@Before
	public void setUp() throws Exception {
		facade = new Facade();
		System.out.println("Ignore output");
		
	}

	@Test
	public void isWellFormedTest() {
		
		outcome = facade.parse("break;");
		assertFalse(((Program) outcome.getResult()).isWellFormed());
		outcome = facade.parse("print 1;");
		assertTrue(((Program) outcome.getResult()).isWellFormed());
		outcome = facade.parse("while true do skip; done");
		assertTrue(((Program) outcome.getResult()).isWellFormed());
		outcome = facade.parse("while false do skip; done break;");
		assertFalse(((Program) outcome.getResult()).isWellFormed());
		outcome = facade.parse("while false do break; done");
		assertTrue(((Program) outcome.getResult()).isWellFormed());
		outcome = facade.parse("object x;  foreach (any, x) do break; done");
		assertTrue(((Program) outcome.getResult()).isWellFormed());
		outcome = facade.parse("object x;  foreach (any, x) do skip; done");
		assertFalse(((Program) outcome.getResult()).isWellFormed());
		outcome = facade.parse("object x;  foreach (any, x) do break; done break;");
		assertFalse(((Program) outcome.getResult()).isWellFormed());
		
		assertTrue(((Program) outcome.getResult()).getContainsError());
	}
	
	@Test
	public void typeErrorAtCompileTest() {
		outcome = facade.parse("wait 4; print 1+self;");
		assertTrue(((Program) outcome.getResult()).getContainsError());
	}
	
	@Test
	public void typeErrorAtRuntimeTest() {
		outcome = facade.parse("object o; wait 4; print gethp o;");
		assertFalse(((Program) outcome.getResult()).getContainsError());
		((Program) outcome.getResult()).setGameObject(new Buzam(null, 0,0, null, null));
		((Program) outcome.getResult()).advanceTime(5);
		assertTrue(((Program) outcome.getResult()).getContainsError());
	}
	
	@Test
	public void timeTest() {
		outcome = facade.parse("double i; "
				+ "i := 1.0; wait(5.0); i := 2.0;"
									+ "while true do "
										+ "while true do break; done "
										+ "i := i + 1.0; skip; "
										+  "done");
	Program p = ((Program) outcome.getResult());
	p.advanceTime(5);
	assertEquals(p.getVariableValue("i", Type.DOUBLE), 1.0);
	p.advanceTime(0.0015);
	assertEquals(p.getVariableValue("i", Type.DOUBLE), 2.0);
	p.advanceTime(0.0024);
	assertEquals(p.getVariableValue("i", Type.DOUBLE), 2.0);
	p.advanceTime(0.0001);
	assertEquals(p.getVariableValue("i", Type.DOUBLE), 3.0);
	p.advanceTime(0.0041);
	assertEquals(p.getVariableValue("i", Type.DOUBLE), 4.0);
	}
	
}
