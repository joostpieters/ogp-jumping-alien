/**
 * 
 */
package jumpingalien.model.elements;

import jumpingalien.model.Program;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

/** 
 * A class of Buzam characters as special kinds of aliens.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Buzam extends Alien {

	public Buzam(World world, double x, double y, Sprite[] sprites, Program program) {
		super(world, x, y, 500, sprites, program);
	}

	@Override
	public void handleInteraction(double duration) {
		super.handleInteraction(duration);
		Mazub object1 = (Mazub) this.touches(Mazub.class);
		if (object1 != null && getTimeToBeImmune() == 0) {
			this.substractHitPoints(50);
			this.setTimeToBeImmune(0.6);
		}
	}
	
	@Override
	public void advanceTime(double duration) {
		super.advanceTime(duration);
		if (getProgram() != null) {
			getProgram().advanceTime(duration);
		}
	}
}
