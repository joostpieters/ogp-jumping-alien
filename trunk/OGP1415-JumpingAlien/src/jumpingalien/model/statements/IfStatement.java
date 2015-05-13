package jumpingalien.model.statements;

import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class IfStatement extends Statement {

	public IfStatement(Program caller, SourceLocation location, Expression expression,
			Statement ifBody, Statement elseBody) {
		super(caller, location);
		if (ifBody != null) {
			if (ifBody.containsAction())
				setContainsAction(true);
			if (ifBody.containsBreakOutsideLoop())
				setContainsBreakOutsideLoop(true);
		}
		if (elseBody != null) {
			if (elseBody.containsAction())
				setContainsAction(true);
			if (ifBody.containsBreakOutsideLoop())
				setContainsBreakOutsideLoop(true);
		}
		EXPRESSION = expression;
		IF_BODY = ifBody;
		ELSE_BODY = elseBody;
	}

	public Expression getExpression() {
		return EXPRESSION;
	}

	public Statement getIfBody() {
		return IF_BODY;
	}

	public Statement getElseBody() {
		return ELSE_BODY;
	}

	private final Expression EXPRESSION;
	private final Statement IF_BODY;
	private final Statement ELSE_BODY;

	@Override
	public void execute() throws ClassCastException {
		getCaller().substractTimeRemaining(0.001);
		if ((boolean)(getExpression().eval()) == true) {
			getCaller().setCurrentStatement(getIfBody());
			//het huidige statement van het programma wordt op de if body gezet
			getIfBody().setNextStatement(getNextStatement());
			//getNextStatement() is het volgende statement van het if-else-geheel
		}
		else if (getElseBody() != null) {
			getCaller().setCurrentStatement(getElseBody());
			getElseBody().setNextStatement(getNextStatement());
			//getNextStatement() is het volgende statement van het if-else-geheel
		}
		else
			getCaller().setCurrentStatement(getNextStatement());
	}

}
