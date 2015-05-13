/**
 * 
 */
package jumpingalien.model.statements;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
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
