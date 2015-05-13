package jumpingalien.model.statements;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class SkipStatement extends TrivialStatement {

	public SkipStatement(Program caller, SourceLocation location) {
		super(caller, location);
		setContainsAction(true);
	}

}
