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
public class Shark extends GameObject {

	/**
	 * @param world
	 * @param x
	 * @param y
	 */
	public Shark(World world, double x, double y, Sprite[] sprites) {
		super(world, x, y, 0, 100, 100, sprites, 100, 200, 400, 400, 150, 1000);
		// TODO Auto-generated constructor stub
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

		else
			setMagmaTimer(0.2);
	}


}
