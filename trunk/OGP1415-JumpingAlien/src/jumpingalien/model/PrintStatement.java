/**
 * 
 */
package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public class PrintStatement extends TrivialStatement {

	/**
	 * @param caller
	 * @param location
	 */
	public PrintStatement(Program caller, SourceLocation location, Expression expression) {
		super(caller, location);
		EXPRESSION = expression;
	}

	
	private final Expression EXPRESSION;
	
	public Expression getExpression() {
		return EXPRESSION;
	}
	
	public void execute() throws ClassCastException{
		super.execute();
		System.out.println(getExpression().eval());
	}
}
