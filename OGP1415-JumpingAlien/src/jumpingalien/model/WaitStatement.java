package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;

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
