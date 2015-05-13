
package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public class StopJumpStatement extends TrivialStatement {


	public StopJumpStatement(Program caller, SourceLocation location) {
		super(caller, location);
		setContainsAction(true);
	}
	
	//voldoet aan Liskov, gooit excpetion minder
	public void execute(){
		super.execute();
		try {
			getCaller().getGameObject().endJump();
		}
		
		catch(JumpingException exc) {}
	}

}
