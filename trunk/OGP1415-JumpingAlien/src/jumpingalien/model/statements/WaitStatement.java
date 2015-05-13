package jumpingalien.model.statements;

import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class WaitStatement extends TrivialStatement {

	public WaitStatement(Program caller, SourceLocation location, Expression duration) {
		super(caller, location);
		EXPRESSION = duration;
		setContainsAction(true);
	}

	private final Expression EXPRESSION;
	public Expression getExpression() {
		return EXPRESSION;
	}
	
	public void execute() throws ClassCastException {
		super.execute();
		getCaller().addWaitTime((Double)(getExpression().eval()));
	}
}
