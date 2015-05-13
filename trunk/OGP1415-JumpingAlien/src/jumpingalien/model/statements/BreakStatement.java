package jumpingalien.model.statements;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class BreakStatement extends Statement {

	public BreakStatement(Program caller, SourceLocation location) {
		super(caller, location);
		setContainsBreakOutsideLoop(true);
	}

	@Override
	public void execute() throws ClassCastException {
		getCaller().substractTimeRemaining(0.001);
		Statement a = getCaller().popLoop();
		getCaller().setCurrentStatement(a.getNextStatement());
		//kan ook via interface
		if(a instanceof WhileStatement) {
			((WhileStatement) a).setIterating(false);
		}
		else if (a instanceof ForEachStatement) {
			((ForEachStatement) a).setIterating(false);
			((ForEachStatement) a).setMyObjects(null);
		}
	}
}