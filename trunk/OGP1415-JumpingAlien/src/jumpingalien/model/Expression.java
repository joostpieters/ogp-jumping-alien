package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.*;
public class Expression {

//	public Expression(boolean isLiteral, int type, Object param1, Object param2, SourceLocation location) {
//		
//		IS_LITERAL = isLiteral;
//		TYPE = type;
//		PARAM1 = param1;
//		PARAM2 = param2;
//		LOCATION = location;
//	}
//	
//	@Basic
//	public boolean isLiteral() {
//		return IS_LITERAL;
//	}
//	
//	@Basic
//	public int getType() {
//		return TYPE;
//	}
//	
//	@Basic
//	public Object getParam(int num) {
//		if(num == 1)
//			return PARAM1;
//		return PARAM2;
//	}
//	
//	@Basic
//	public SourceLocation getSourceLocation() {
//		return LOCATION;
//	}
//	
//	public Object eval(Program caller) throws IllegalStateException{
//		
//		if(getType() == 1)
//			return caller.getVariableValue((String) getParam(1));
//		
//		if(getType() == 2)
//			return getParam(1);
//		
//		if(getType() == 3)
//			return true;
//		
//		if(getType() == 4)
//			return false;
//		
//		if(getType() == 5)
//			return null;
//		
//		if(getType() == 6)
//			return caller.getGameObject();
//
//		
//		throw new IllegalStateException();
//	}
//	
//	private boolean IS_LITERAL;
//	private int TYPE;
//	private Object PARAM1;
//	private Object PARAM2;
//	private SourceLocation LOCATION;
	

	
	public Expression(ExpressionLambda myFunction, Object[] parameterArray, SourceLocation sourceLocation) {
		MY_FUNCTION = myFunction;
		PARAMETER_ARRAY = parameterArray;
		SOURCE_LOCATION = sourceLocation;
	}
	
	private final ExpressionLambda MY_FUNCTION;

	private final Object[] PARAMETER_ARRAY;
	
	private final SourceLocation SOURCE_LOCATION;
	
	@Immutable
	public ExpressionLambda getMyFunction() {
		return MY_FUNCTION;
	}

	@Immutable
	public Object[] getParameterArray() {
		return PARAMETER_ARRAY;
	}
	
	@Immutable
	public SourceLocation getSourceLocation() {
		return SOURCE_LOCATION;
	}
	
	public Object eval(Program myProgram) {
		return getMyFunction().f(getParameterArray());
	}
	
}