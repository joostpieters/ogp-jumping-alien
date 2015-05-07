package jumpingalien.model;

import static org.junit.Assert.*;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.Test;

public class ProgramFactoryTest {

	
	IProgramFactory<Expression, Statement, Type, Program> factory = new ProgramFactory<>();
	Expression e;
	Expression e2;
	Expression e3;
	Expression e4;
	Expression e5;
	Expression e6;
	Expression e7;
	Expression e8;
	Expression e9;
	Expression e10;
	Expression e11;
	Expression e12;
	Expression e13;

	
	@Before
	public void initialize() {
		e = factory.createDoubleConstant(64, new SourceLocation(10,11));
		e2 = factory.createDoubleConstant(65, new SourceLocation(10,11));
		e3 = factory.createAddition(e, e2, new SourceLocation(10,11));
		e4 = factory.createAddition(e3, e, new SourceLocation(10,11));
		e5 = factory.createSqrt(e, new SourceLocation(10,11));
		e6 = factory.createRandom(e2, new SourceLocation(10,11));
		e7 = factory.createTrue(null);
		e8 = factory.createFalse(null);
		e9 = factory.createNot(e7, null);
		e10 = factory.createNotEquals(e, e2, null);
				
	}
	
	@Test
	public void test() {
		System.out.println(e10.eval(new Program()));

	}

}
