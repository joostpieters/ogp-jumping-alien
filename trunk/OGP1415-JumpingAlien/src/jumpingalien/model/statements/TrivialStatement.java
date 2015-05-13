/**
 * 
 */
package jumpingalien.model.statements;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public abstract class TrivialStatement extends Statement {

	/**
	 * @param caller
	 * @param location
	 */
	public TrivialStatement(Program caller, SourceLocation location) {
		super(caller, location);
		setNextStatement(null);
	}



	// case distinction
	@Override
	public void execute() throws ClassCastException {
		getCaller().substractTimeRemaining(0.001);
		getCaller().setCurrentStatement(getNextStatement());
	}

	
	
	
}
