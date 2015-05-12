/**
 * 
 */
package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Jonathan
 *
 */
public abstract class Statement {

	/**
	 * 
	 */
	public Statement(Program caller, SourceLocation location) {
		LOCATION = location;
		CALLER = caller;
	}
	
	public Program getCaller() {
		return CALLER;
	}
	
	private final Program CALLER;

	public SourceLocation getLocation() {
		return LOCATION;
	}
	
	private final SourceLocation LOCATION;
	
	public abstract void execute() throws ClassCastException;
	
	public Statement getNextStatement() {
		if(nextStatement == null)
			return getCaller().getMainStatement();
		return nextStatement;
	}
	
	protected void setNextStatement(Statement nextStatement) {
		assert getNextStatement() == null;
		this.nextStatement = nextStatement;
	}
	private Statement nextStatement;
	
}
