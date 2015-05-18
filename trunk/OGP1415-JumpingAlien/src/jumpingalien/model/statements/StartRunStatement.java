/**
 * 
 */
package jumpingalien.model.statements;


import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.model.elements.GameObject;
import jumpingalien.part3.programs.SourceLocation;

/** 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class StartRunStatement extends TrivialStatement {
	
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
