package jumpingalien.model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;

import org.junit.Before;
import org.junit.Test;

public class ProgramFactoryTest {

	
	IProgramFactory<Expression, Statement, Type, Program> factory = new ProgramFactory();
	Program program = ((ProgramFactory)factory).MY_PROGRAM;
	Map<String, Type> map = new HashMap<>();
	
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
				
		
		map.put("aa", Type.DOUBLE);
		map.put("bb", Type.DOUBLE);
		program.initialiseVariables(map);
		program.setVariableValue("aa", Type.DOUBLE, (Double) 5.0);
		program.setVariableValue("bb", Type.DOUBLE, (Double) 7.0);
		
		e11 = factory.createReadVariable("aa", Type.DOUBLE, null);
		e12 = factory.createReadVariable("bb", Type.DOUBLE, null);
		e13 = factory.createAddition(e11, e12, null);
		
		//Moet aan eval nog een Programma meegegeven worden?
		//Eruit gesloopt en werkt!
	}
	
	@Test
	public void test() {
		System.out.println(e13.eval());
		program.setVariableValue("aa", Type.DOUBLE, (Double) 55.0);
		System.out.println(e13.eval());
		//KLOPT WEL. LAMBDA'S LIJKEN VROEGEN TE BINDEN
		

	}

}
