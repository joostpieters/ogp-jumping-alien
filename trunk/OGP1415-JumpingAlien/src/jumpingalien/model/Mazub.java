package jumpingalien.model;
import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;


/**
 * A class of Mazub characters involving several properties.
 * 
 * @invar	Each Mazub character can have its position as its position.
 * 		  | isValidX(getX()) && isValidY(getY())
 * @invar	Each Mazub character can have its velocity as its velocity.
 * 		  | canHaveAsXVelocity(getXVelocity())
 * @invar	Each Mazub character can have its initial velocity as its initial velocity in the x direction.
 * 			Each Mazub character can have its velocity limit as its velocity limit in the x direction.
 * 		  | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(), getXVelocityLimit())
 * @invar	Each Mazub character can have its acceleration as its acceleration in the x direction.
 * 		  | canHaveAsXAcceleration(getXAcceleration())
 * @invar	Each Mazub character has Direction.LEFT or Direction.RIGHT as its direction.
 * 		  | (getXDirection() == Direction.LEFT) || (getXDirection() == Direction.RIGHT)
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Mazub extends GameObject {
	
	/**
	 * Initialize this new Mazub character with given x position, given y position, given
	 * sprites, given initial velocity in the x direction and given velocity limit in the x direction.
	 * 
	 * @param 	x
	 * 			The initial x position for this new Mazub character.
	 * @param 	y
	 * 			The initial y position for this new Mazub character.
	 * @param	sprites
	 * 			The series of initial sprites for this new Mazub character.
	 * @param 	xInitialVelocity
	 * 			The initial velocity in the x direction for this new Mazub character.
	 * @param 	xVelocityLimit
	 * 			The velocity limit in the x direction for this new Mazub character.
	 * @pre		The given initial x position must be a valid x position for any Mazub character.
	 * 		  | isValidX(x)
	 * @pre		The given initial y position must be a valid y position for any Mazub character.
	 * 		  | isValidY(y)
	 * @pre		The given series of sprites must be effective.
	 * 		  | sprites != null
	 * @pre		This new Mazub character can have the  given xInitialVelocity as its initial velocity in the x direction.
	 * 			This new Mazub character can have the  given xVelocityLimit as its velocity limit in the x direction.
	 * 		  | isValidXInitialVelocityAndXVelocityLimit(xInitialVelocity, xVelocityLimit)
	 * @post	The new x position of this Mazub character is equal to the given x position.
	 * 		  | new.getX() == x
	 * @post	The new y position of this Mazub character is equal to the given y position.
	 * 		  | new.getY() == y
	 * @post	The new series of sprites of this Mazub character is equal to the given sprites.
	 * 		  | new.getSprites() == sprites
	 * @post	The new initial velocity in the x direction of this Mazub character is equal to
	 * 			the given xInitialVelocity.
	 * 		  | new.getXInitialVelocity() == xInitialVelocity
	 * @post	The new velocity limit in the x direction of this Mazub character is equal to
	 * 			the given xVelocityLimit.
	 * 		  | new.getXVelocityLimit() == xVelocityLimit
	 */
	@Raw
	public Mazub(World world, double x, double y, Sprite[] sprites) {
	
		super(world, x, y, 0, 100, 500 ,sprites, 
				100, 800, 300, 100,
								90, 1000);
//		assert isValidX((int) x);
//		assert isValidY((int) y);
//		assert isValidPosition((int) x,(int) y);
		assert sprites != null;

		this.setTimeToBeImmune(0);
		this.setWaterTimer(0);
		this.setMagmaTimer(0.2);
	}
	
	

	
	
	/**
	 * Check whether the given x position is a valid x position for any Mazub character.
	 * 
	 * @param 	x
	 * 			The x position to be checked.
	 * @return 	True if and only if the given x is positive and smaller than 
	 * 			or equal to X_LIMIT.
	 * 		  | result == 
	 * 		  |    ( (x <= X_LIMIT) 
	 * 		  |   && (x >= 0) )
	 */
//	public static boolean isValidX(int x) {
//		return ( (x <= X_LIMIT) && (x >= 0) );
//	}
	
//	/**
//	 * Variable registering the maximum x position that applies to all Mazub characters.
//	 */
//	private static final int X_LIMIT = 1023; //in pixels
	
	/**
	 * Check whether the given y position is a valid y position for any Mazub character.
	 * 
	 * @param 	y
	 * 			The y position to be checked.
	 * @return 	True if and only if the given y is positive and smaller than 
	 * 			or equal to Y_LIMIT.
	 * 		  | result ==
	 * 		  |	   ( (y <= Y_LIMIT) 
	 * 		  |   && (y >= 0) )
	 */
//	public static boolean isValidY(int y) {
//		return (y <= Y_LIMIT) && (y >= 0);
//	}
	
//	/**
//	 * Variable registering the maximum y position that applies to all Mazub characters.
//	 */
//	private static final int Y_LIMIT = 767; //in pixels
	
//	/**
//	 * Return the x position of this Mazub character.
//	 */
//	@Basic
//	public double getX() {
//		return this.x;
//	}
//	
//	/**
//	 * Return the y position of this Mazub character.
//	 */
//	@Basic
//	public double getY() {
//		return this.y;
//	}
//	
//	/**
//	 * Set the x position of this Mazub character to the given x position.
//	 * 
//	 * @param 	x
//	 * 			The new x position for this Mazub character.
//	 * @pre		The given x position must be a valid x position for any Mazub character.
//	 * 		  | isValidX((int) x)
//	 * @post	The new x position of this Mazub character is equal to
//	 * 			the given position.
//	 * 		  | new.getX() == x
//	 */
//	@Raw
//	private void setX(double x) {
//		assert isValidX((int) x);
//		this.x = x;
//	}
//	
//	/**
//	 * Set the y position of this Mazub character to the given y position.
//	 * 
//	 * @param 	y
//	 * 			The new y position for this Mazub character.
//	 * @pre		The given y position must be a valid y position for any Mazub character.
//	 * 		  | isValidY((int) y)
//	 * @post	The new y position of this Mazub character is equal to
//	 * 			the given position.
//	 * 		  | new.getY() == y
//	 */
//	@Raw
//	private void setY(double y) {
//		assert isValidY((int) y);
//		this.y = y;
//	}
//	
//	/**
//	 * Variable registering the x position of this Mazub character.
//	 */
//	private double x;
//	
//	/**
//	 * Variable registering the y position of this Mazub character.
//	 */
//	private double y;
	
	/**
	 * This Mazub character's properties are updated for the given time duration.
	 * 
	 * @param 	duration
	 * 			The duration for which advanceTime should work.
	 * @effect	Move this Mazub character in the x and y direction for the given duration.
	 * 		  | moveX(duration) && moveY(duration)
	 * @post	If the current xVelocity is not equal to 0, then the new timeSinceLastMove is equal to 0.
	 * 		  | if (getXVelocity() != 0)
	 * 		  | 	then new.getTimeSinceLastMove() == 0;
	 * @post	If the current xVelocity is equal to 0, then the new timeSinceLastMove is equal to the current
	 * 			timeSinceLastMove + duration.
	 * 		  | if (getXVelocity() == 0)
	 * 		  |		then new.getTimeSinceLastMove() == (this.getTimeSinceLastMove()+duration)
	 * @post	If this Mazub character is running normally, then the new timeSinceLastRunningImage is equal
	 * 		    to the current timeSinceLastRunningImage + duration
	 * 		  | if (isRunningNormally())
	 * 		  | 	then new.getTimeSinceLastRunningImage() == (this.getTimeSinceLastRunningImage() + duration)
	 * @post	If this Mazub character is not running normally, then the new timeSinceLastRunningImage is equal
	 * 		    to the current timeSinceLastRunningImage.
	 * 		  | if (! isRunningNormally())
	 * 		  | 	then new.getTimeSinceLastRunningImage() == 0
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

	public void handleInteraction(double duration) {
		Plant object0 = (Plant) this.touches(Plant.class);
		if (object0 != null && this.getHitPoints() != this.getMaxHitpoints()) {
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
