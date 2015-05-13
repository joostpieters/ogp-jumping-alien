/**
 * 
 */
package jumpingalien.model;


import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public class StartRunStatement extends TrivialStatement {

	/**
	 * @param caller
	 * @param location
	 */
	public StartRunStatement(Program caller, SourceLocation location, Expression direction) {
		super(caller, location);
		DIRECTION = direction;
		setContainsAction(true);
	}

	private final Expression DIRECTION;
	public Expression getDirection() {
		return DIRECTION;
	}
	
	public void execute() throws ClassCastException {
		super.execute();
		if (getDirection().eval() == jumpingalien.part3.programs.IProgramFactory.Direction.LEFT)
			getCaller().getGameObject().startMove(GameObject.Direction.LEFT);
		else if(getDirection().eval() == jumpingalien.part3.programs.IProgramFactory.Direction.RIGHT)
			getCaller().getGameObject().startMove(GameObject.Direction.RIGHT);
		else
			throw new ClassCastException(); 

	}
}
