/**
 * 
 */
package jumpingalien.model.elements;

import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.model.Program;
import jumpingalien.model.World;
import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;

/**
 * @author Andreas
 *
 */
public abstract class Alien extends GameObject {

	/**
	 * Initialize this new Alien character with given x position, given y position, given
	 * sprites and given world.
	 * 
	 * @effect	| super(world, x, y, 100, 500 ,sprites, 100, 800, 300, 100,	90, 1000, true);
	 * 
	 * @param 	x
	 * 			The initial x position for this new Alien character.
	 * @param 	y
	 * 			The initial y position for this new Alien character.
	 * @param	sprites
	 * 			The series of initial sprites for this new Alien character.
 	 * @param	world
 	 * 			The world for this new Alien character.
	 */
	@Raw
	public Alien(World world, double x, double y,int maxHitPoints, Sprite[] sprites, Program program) {
		super(world, x, y, maxHitPoints, 500 ,sprites, 
				100, 800, 300, 100,
								90, 1000, true, program);		
	}
	
	
	/**
	 * Return the current sprite of this Alien character.
	 */
	@Override
	public Sprite getCurrentSprite() {
		int index = ((int)(getTimeSinceLastRunningImage()/0.075))%(getM()+1);
		if ((getXVelocity() == 0) && (getTimeSinceLastMove() > 1)) {
			if (! isDucking())
				return this.getSprites()[0];
			else
				return this.getSprites()[1];
		}
		
		if ((getXVelocity() == 0) && (getTimeSinceLastMove() <= 1) && (! isDucking())) {
			if (getXDirection() == Direction.RIGHT) 
				return this.getSprites()[2];
			else
				return this.getSprites()[3];
		}
			
		if ((getXVelocity() != 0) && (isJumping()) && (! isDucking())) {
			if (getXDirection() == Direction.RIGHT)
				return this.getSprites()[4];
			else
				return this.getSprites()[5];
		}
		
		if ((isDucking()) && ((getXVelocity() != 0) || (getTimeSinceLastMove() <= 1))) {
			if (getXDirection() == Direction.RIGHT)
				return this.getSprites()[6];
			else
				return this.getSprites()[7];
		}
		
		if (isRunningNormally()) {
			if(getXDirection() == Direction.RIGHT)
				return getSprites()[8+index];
				
		}
			//else
		return getSprites()[9+getM()+index];
	}
	
	/**
	 * @return	The number of different images for the running motion.
	 * 		  | result == (this.sprites.length-10)/2
	 */
	public int getM() {
		return (this.getSprites().length-10)/2;
	}

//	@Override
//	public abstract void handleInteraction(double duration);
	
	/**
	 * Handle the interaction of this Alien character with other objects for the given time duration.
	 * 
	 * @param duration
	 * 		  The duration for which the interaction should be handled. 
	 */
	public void handleInteraction(double duration) {
		Plant object0 = (Plant) this.touches(Plant.class);
		if (object0 != null && this.getHitPoints() != this.getMaxHitPoints()) {
			this.addHitPoints(50);
			object0.substractHitPoints(1);
		}
		
		Slime object1 = (Slime) this.touches(Slime.class);
		if (object1 != null && getTimeToBeImmune() == 0) {
			this.substractHitPoints(50);
			this.setTimeToBeImmune(0.6);
		}
		
		Shark object2 = (Shark) this.touches(Shark.class);
		if (object2 != null && getTimeToBeImmune() == 0) {
			this.substractHitPoints(50);
			this.setTimeToBeImmune(0.6);
		}
		
		if (this.touches(TerrainType.WATER)) {
			this.setWaterTimer(getWaterTimer() + duration);
			if (getWaterTimer() >= 0.2) {
				this.substractHitPoints(2);
				setWaterTimer(getWaterTimer() - 0.2);
			}
		}
		
		else
			setWaterTimer(0);
		
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
