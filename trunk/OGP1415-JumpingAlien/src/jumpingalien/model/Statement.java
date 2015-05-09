/**
 * 
 */
package jumpingalien.model;

/**
 * @author Jonathan
 *
 */
public class Statement {

	/**
	 * 
	 */
	public Statement(Program caller) {
		CALLER = caller;
	}
	
	public Program getCaller() {
		return CALLER;
	}
	
	private final Program CALLER;
	private Expression condition;
	private Statement nextIfTrue;
	private Statement nextIfFalse;
	private Statement parent;
	
	public void execute() {
		return;
	}
	
}
