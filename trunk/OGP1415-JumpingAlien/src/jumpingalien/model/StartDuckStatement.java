/**
 * 
 */
package jumpingalien.model;

import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Andreas
 *
 */
public class StartDuckStatement extends TrivialStatement {

	public StartDuckStatement(Program caller, SourceLocation location) {
		super(caller, location);
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
