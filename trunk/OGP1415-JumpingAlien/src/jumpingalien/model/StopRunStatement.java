/**
 * 
 */
package jumpingalien.model;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public class StopRunStatement extends TrivialStatement {

	/**
	 * @param caller
	 * @param location
	 */
	public StopRunStatement(Program caller, SourceLocation location, Expression direction) {
		super(caller, location);
		DIRECTION = direction;
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
