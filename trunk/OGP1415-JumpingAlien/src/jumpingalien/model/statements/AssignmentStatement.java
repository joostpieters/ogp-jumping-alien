/**
 * 
 */
package jumpingalien.model.statements;

import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.model.Type;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class AssignmentStatement extends TrivialStatement {


	/**
	 * @param caller
	 * @param location
	 */
	public AssignmentStatement(Program caller, SourceLocation location, String stringName, Expression expression,
			Type type) {
		super(caller, location);
		STRING_NAME = stringName;
		EXPRESSION = expression;
		TYPE = type;
	}
	
	
	private final String STRING_NAME;
	public String getStringName() {
		return STRING_NAME;
	}
	
	private final Expression EXPRESSION;
	public Expression getExpression() {
		return EXPRESSION;
	}
	
	private final Type TYPE;
	public Type getType() {
		return TYPE;
	}
	
	public void execute() throws ClassCastException {
		super.execute();
		getCaller().setVariableValue(getStringName(), getType(), getExpression().eval());
	}

}
