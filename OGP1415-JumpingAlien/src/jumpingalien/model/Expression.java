package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Expression {
	
	protected Expression (ExpressionLambda myFunction, Object[] parameterArray, SourceLocation sourceLocation, Type type) {
		MY_FUNCTION = myFunction;
		PARAMETER_ARRAY = parameterArray;
		SOURCE_LOCATION = sourceLocation;
		TYPE = type;
	}
	
	private final ExpressionLambda MY_FUNCTION;

	private final Object[] PARAMETER_ARRAY;
	
	private final SourceLocation SOURCE_LOCATION;
	
	public ExpressionLambda getMyFunction() {
		return MY_FUNCTION;
	}

	public Object[] getParameterArray() {
		return PARAMETER_ARRAY;
	}
	
	public SourceLocation getSourceLocation() {
		return SOURCE_LOCATION;
	}
	
	public Type getType() {
		return TYPE;
	}
	
	private final Type TYPE;

	public Object eval() throws ClassCastException {
		return getMyFunction().f(getParameterArray());
	}
}
