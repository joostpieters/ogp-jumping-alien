package jumpingalien.model.statements;

import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class WhileStatement extends Statement {

	public WhileStatement(Program caller, SourceLocation location, Expression expression, Statement body) {
		super(caller, location);
		setContainsAction(body.containsAction());
		EXPRESSION = expression;
		BODY = body;
		setIterating(false);
	}
	
	public Expression getExpression() {
		return EXPRESSION;
	}

	public Statement getBody() {
		return BODY;
	}
	
	private final Expression EXPRESSION;
	private final Statement BODY;
	
	@Override
	public void execute() throws ClassCastException {
		if (! isIterating()) {
			getCaller().addLoop(this);
			setIterating(true);
		}		
		
		getCaller().substractTimeRemaining(0.001);
		if ((boolean)(getExpression().eval()) == true) {
			getBody().setNextStatement(this);
			getCaller().setCurrentStatement(getBody());
			//het volgende statement van het programma is de body van de while-statement
		}
		else {
			getCaller().setCurrentStatement(getNextStatement());
			getCaller().popLoop();
			setIterating(false);
		}
	}
	
	
	private boolean isIterating;
	
	public boolean isIterating() {
		return isIterating;
	}
	
	public void setIterating(boolean flag) {
		isIterating = flag;
	}
}
