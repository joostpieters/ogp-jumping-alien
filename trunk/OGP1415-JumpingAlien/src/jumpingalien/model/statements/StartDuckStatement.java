/**
 * 
 */
package jumpingalien.model.statements;

import jumpingalien.model.JumpingException;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public class StartDuckStatement extends TrivialStatement {

	public StartDuckStatement(Program caller, SourceLocation location) {
		super(caller, location);
		setContainsAction(true);
	}
	
	//voldoet aan Liskov, gooit excpetion minder
	//startDuck op Slimes doet niets
	public void execute(){
		super.execute();
		try {
			getCaller().getGameObject().startDuck();
		}
		catch(JumpingException exc) {}
	}
}
