
package jumpingalien.model.statements;

import jumpingalien.model.JumpingException;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public class StopDuckStatement extends TrivialStatement {

	public StopDuckStatement(Program caller, SourceLocation location) {
		super(caller, location);
		setContainsAction(true);
	}
	
	//voldoet aan Liskov, gooit excpetion minder
	//endDuck op Slimes doet niets
	public void execute(){
		super.execute();
		try {
			getCaller().getGameObject().endDuck();
		}
		catch(JumpingException exc) {}
	}
}
