package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.*;
public class Expression {

	
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
	
	public Object eval() {
		return getMyFunction().f(getParameterArray());
	}
	
}
