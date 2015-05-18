/**
 * 
 */
package jumpingalien.model.statements;

import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class PrintStatement extends TrivialStatement {

	
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
