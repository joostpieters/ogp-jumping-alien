package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;
import be.kuleuven.cs.som.annotate.*;
public class Expression {

	//Expression klasse is geparametriseerd in T. T geeft aan naar welk type de expressie zal evalueren.
	public Expression (ExpressionLambda myFunction, Object[] parameterArray, SourceLocation sourceLocation, Type type) {
		MY_FUNCTION = myFunction;
		PARAMETER_ARRAY = parameterArray;
		SOURCE_LOCATION = sourceLocation;
		TYPE = type;
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
	
	public Type getType() {
		return TYPE;
	}
	
	private final Type TYPE;

	public Object eval() throws ClassCastException {
		//try {
		return getMyFunction().f(getParameterArray());
		//}
		//catch (NullPointerException exc) {
			//return true;
		//}
	}
	
}
