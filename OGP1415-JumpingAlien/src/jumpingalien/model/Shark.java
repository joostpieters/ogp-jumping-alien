/**
 * 
 */
package jumpingalien.model;

import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;

/**
 * @author Andreas
 *
 */
public class Shark extends AutomaticObject {

	/**
	 * @param world
	 * @param x
	 * @param y
	 */
	public Shark(World world, double x, double y, Sprite[] sprites) {
		super(world, x, y, 0, 100, 100, sprites, 100, 200, 400, 400, 150, 1000, true);
		// TODO Auto-generated constructor stub
	}
	

	
	@Override
	public void advanceTime(double duration) {
		System.out.println(getHitPoints());
		super.advanceTime(duration);
		setTimer(getTimer()+duration);
		if(getTimer() > getGoal()) {
			startNewMovement();
		}
	}
	
	@Override
	public void startNewMovement() {
		setTimer(0);
		endMove();
		//4 seconds is less likely, but still possible when duration = 0.2
		setGoal(1+generator.nextDouble()*(3.8-1));
		if(generator.nextDouble() < 0.5)
			startMove(Direction.LEFT);
		else
			startMove(Direction.RIGHT);
		
	}
	
	@Override
	public Sprite getCurrentSprite() {
		if (getXDirection() == Direction.LEFT)
			return getSprites()[0];
		return getSprites()[1];
	}

	@Override
	public void handleInteraction(double duration) {
		
		if (this.touches(TerrainType.AIR)) {
			this.setAirTimer(getAirTimer() + duration);
			if (getAirTimer() >= 0.2) {
				this.substractHitPoints(6);
				setAirTimer(getAirTimer() - 0.2);
			}
		}
		else {
			setAirTimer(0);
		}
		
		if (this.touches(TerrainType.MAGMA)) {
			this.setMagmaTimer(getMagmaTimer() + duration);
			if (getMagmaTimer() >= 0.2) {
				this.substractHitPoints(50);
				setMagmaTimer(getMagmaTimer() - 0.2);
			}
		}

		else {
			setMagmaTimer(0.2);
		}
	}


}
