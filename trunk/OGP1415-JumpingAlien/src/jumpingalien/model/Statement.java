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
		setContainsAction(false);
		setContainsBreakOutsideLoop(false);
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
		this.nextStatement = nextStatement;
	}
	private Statement nextStatement;
	
	
	private boolean containsAction;

	public boolean containsAction() {
		return containsAction;
	}

	protected void setContainsAction(boolean containsAction) {
		this.containsAction = containsAction;
	}
	
	private boolean containsBreakOutsideLoop;

	public boolean containsBreakOutsideLoop() {
		return containsBreakOutsideLoop;
	}

	protected void setContainsBreakOutsideLoop(boolean containsBreakOutsideLoop) {
		this.containsBreakOutsideLoop = containsBreakOutsideLoop;
	}
}
