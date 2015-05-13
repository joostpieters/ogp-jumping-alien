/**
 * 
 */
package jumpingalien.model.elements;

import jumpingalien.model.Program;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

public class Buzam extends Alien {

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param sprites
	 */
	public Buzam(World world, double x, double y, Sprite[] sprites, Program program) {
		super(world, x, y, 500, sprites, program);
	}

	/* (non-Javadoc)
	 * @see jumpingalien.model.Alien#handleInteraction(double)
	 */
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
