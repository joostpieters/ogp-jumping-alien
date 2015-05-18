package jumpingalien.model.statements;
import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class StopRunStatement extends TrivialStatement {
	
	public StopRunStatement(Program caller, SourceLocation location, Expression direction) {
		super(caller, location);
		DIRECTION = direction;
		setContainsAction(true);
	}

	private final Expression DIRECTION;
	public Expression getDirection() {
		return DIRECTION;
	}
	
	public void execute() throws ClassCastException {
		if (getDirection().eval() == Direction.LEFT || getDirection().eval() == Direction.RIGHT) {
			super.execute();
			getCaller().getGameObject().endMove();
		}
		else
			throw new ClassCastException();
	}
}