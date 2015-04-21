package jumpingalien.model;
import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;


/**
 * A class of Mazub characters as special kinds of game objects.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Mazub extends GameObject {
	
	/**
	 * Initialize this new Mazub character with given x position, given y position, given
	 * sprites and given world.
	 * 
	 * @effect	| super(world, x, y, 100, 500 ,sprites, 100, 800, 300, 100,	90, 1000, true);
	 * 
//	 * @param 	x
//	 * 			The initial x position for this new Mazub character.
//	 * @param 	y
//	 * 			The initial y position for this new Mazub character.
//	 * @param	sprites
//	 * 			The series of initial sprites for this new Mazub character.
// 	 * @param	world
// 	 * 			The world for this new Mazub character.
//	 * @param 	xInitialVelocity
//	 * 			The initial velocity in the x direction for this new Mazub character.
//	 * @param 	xVelocityLimit
//	 * 			The velocity limit in the x direction for this new Mazub character.
//	 * @pre		The given initial x position must be a valid x position for any Mazub character.
//	 * 		  | isValidX(x)
//	 * @pre		The given initial y position must be a valid y position for any Mazub character.
//	 * 		  | isValidY(y)
//	 * @pre		The given series of sprites must be effective.
//	 * 		  | sprites != null
//	 * @pre		This new Mazub character can have the  given xInitialVelocity as its initial velocity in the x direction.
//	 * 			This new Mazub character can have the  given xVelocityLimit as its velocity limit in the x direction.
//	 * 		  | isValidXInitialVelocityAndXVelocityLimit(xInitialVelocity, xVelocityLimit)
//	 * @post	The new x position of this Mazub character is equal to the given x position.
//	 * 		  | new.getX() == x
//	 * @post	The new y position of this Mazub character is equal to the given y position.
//	 * 		  | new.getY() == y
//	 * @post	The new series of sprites of this Mazub character is equal to the given sprites.
//	 * 		  | new.getSprites() == sprites
//	 * @post	The new initial velocity in the x direction of this Mazub character is equal to
//	 * 			the given xInitialVelocity.
//	 * 		  | new.getXInitialVelocity() == xInitialVelocity
//	 * @post	The new velocity limit in the x direction of this Mazub character is equal to
//	 * 			the given xVelocityLimit.
//	 * 		  | new.getXVelocityLimit() == xVelocityLimit
//	 */
	@Raw
	public Mazub(World world, double x, double y, Sprite[] sprites) {
	
		super(world, x, y, 100, 500 ,sprites, 
				100, 800, 300, 100,
								90, 1000, true);

		
	}
	
	/**
	 * This Mazub character's properties are updated for the given time duration.
	 * 
	 * @param 	duration
	 * 			The duration for which advanceTime should work.
	 * @effect	super.advanceTime(duration)
	 * @throws 	IllegalArgumentException
	 * 			The given duration is not a valid duration. A duration is not valid
	 * 			if it is smaller than 0 or greater than or equal to 0.2.
	 * 		  | (duration < 0) || (duration >= 0.2)	
	 */
	public void advanceTime(double duration) throws IllegalArgumentException {
		super.advanceTime(duration);
	}
	

	/**
	 * Return the current sprite of this Mazub character.
	 */
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

	/**
	 * Handle the interaction of this Mazub charachter with other objects for the given time duration.
	 * 
	 * @param duration
	 * 		  The duration for which the interaction should be handled. 
	 */
	//hoe documentatie fixen?
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
