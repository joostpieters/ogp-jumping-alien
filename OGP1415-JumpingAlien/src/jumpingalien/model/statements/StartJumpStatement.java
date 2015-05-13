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
public class StartJumpStatement extends TrivialStatement {


	public StartJumpStatement(Program caller, SourceLocation location) {
		super(caller, location);
		setContainsAction(true);
	}
	
	//voldoet aan Liskov, gooit excpetion minder
	//op deze manier kunnen Slimes wel jumpen, maar aangezien hun 
	//jumping velocity gelijk is aan 0, doet dit toch niets
	public void execute(){
		super.execute();
		try {
			getCaller().getGameObject().startJump();
		}
		catch(JumpingException exc) {}
	}

}
