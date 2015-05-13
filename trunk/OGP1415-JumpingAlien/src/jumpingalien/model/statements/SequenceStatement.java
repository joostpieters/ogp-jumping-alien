package jumpingalien.model.statements;

import java.util.List;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class SequenceStatement extends Statement {


	public SequenceStatement(Program caller, SourceLocation location, List<Statement> statements) {
		super(caller, location);
		this.statements = statements;
		for (int i = 0; i < getNbStatements()-1; i++) {
			if (getStatementAt(i).containsAction())
				this.setContainsAction(true);
			if (getStatementAt(i).containsBreakOutsideLoop())
				this.setContainsBreakOutsideLoop(true);
			getStatementAt(i).setNextStatement(getStatementAt(i+1));
		}
		if (getStatementAt(getNbStatements()-1).containsAction())
			this.setContainsAction(true);
		if (getStatementAt(getNbStatements()-1).containsBreakOutsideLoop())
			this.setContainsBreakOutsideLoop(true);
		
	}

	private List<Statement> statements;

	public Statement getStatementAt(int i) {
		return this.statements.get(i);
	}
	
	public int getNbStatements() {
		return this.statements.size();
	}
	
	
	@Override
	public void execute() throws ClassCastException {
		getCaller().setCurrentStatement(getStatementAt(0));
		getStatementAt(getNbStatements()-1).setNextStatement(getNextStatement());	
	}
}
