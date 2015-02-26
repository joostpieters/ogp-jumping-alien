package jumpingalien.model;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.signum;

/**
 * TAKEN
 * 
 * StackOverflow
 * JumpingExcepton
 * Teleportatie (floating point)
 * Tests
 * Documentatie
 * Totaal/nominaal/defensief
 * Opmaak
 * 
 * 
 */

/**
 * A class of Mazub characters involving a position, a position limit, a velocity, 
 * a velocity limit, an acceleration and an acceleration limit.
 * 
 * @invar	The position that applies to all Mazub characters must be 
 * 			a valid position.
 * 			| isValidX(getX()) && isValidY(getY())
 * @invar	Each Mazub character can have its velocity as its velocity.
 * 			| canHaveAsXVelocity(getXVelocity())
 * @invar	Each Mazub character can have its acceleration as its acceleration.
 * 			| canHaveAsXAcceleration(getXAcceleration())
 * @invar	Each Mazub character can have its velocity limit as its velocity limit.
 * 			| canHaveAsXVelocityLimit(getXVelocityLimit())
 * @invar	Each Mazub character has Mazub.LEFT or Mazub.RIGHT as its direction.
 * 			| (getXDirection() == Mazub.LEFT) || (getXDirection() == Mazub.RIGHT)
 * 

 * @author Andreas Schryvers & Jonathan Oostvogels
 * 
 * 
 */
public class Mazub {
	
	//Misschien Raw?
	//Nominal
	public Mazub(double x, double y, Sprite[] sprites,
			double xInitialVelocity, double xVelocityLimit) {
	
		this.setX(x);
		this.setY(y);
		
		this.setSprites(sprites);
		this.X_INITIAL_VELOCITY = xInitialVelocity;
		this.xVelocityLimit = xVelocityLimit;
		this.setPreviousXVelocityLimit(xVelocityLimit);
		
		this.setXVelocity(0);
		this.setYVelocity(0);
		this.setDucked(false);
		this.setTimeSinceLastMove(2); // > 1
		this.setXDirection(Direction.RIGHT);
	}
	
	public Mazub(double x, double y, Sprite[] sprites) {
		this(x,y,sprites,100,300);
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
	public static boolean isValidX(int x) {
		return
				( (x <= X_LIMIT) && (x >= 0) );
	}
	
	/**
	 * Variable registering the maximum x position that applies to all Mazub characters.
	 */
	private static final int X_LIMIT = 1023; //in pixels

	/**
	 * Check whether this Mazub character can have the given xVelocity as its
	 * velocity in the x direction.
	 * 
	 * @param 	xVelocity
	 * 			The velocity in the x direction to be checked.
	 * @return	True if and only if the absolute value of the given velocity is greater 
	 * 			than or equal to the initial velocity in the x direction, and if the 
	 * 			absolute value of the given velocity is smaller than or equal to the
	 * 			velocity limit in the x direction.
	 * 		  | result == 
	 * 		  |    ( (abs(xVelocity) >= X_INITIAL_VELOCITY)
	 *		  |   && (abs(xVelocity) <= getXVelocityLimit()) )
	 */
	public boolean canHaveAsXVelocity(double xVelocity) {
		return (abs(xVelocity) >= X_INITIAL_VELOCITY && abs(xVelocity) <= getXVelocityLimit());
	}
	
	/**
	 * Check whether this Mazub character can have the given xInitialVelocity as
	 * its initial velocity in the x direction.
	 *  
	 * @param	xInitialVelocity
	 * 			The initial velocity in the x direction to be checked.
	 * @return	True if and only if the absolute value of the given initial velocity is 
	 * 			smaller than or equal to the velocity limit in the x direction, and if
	 * 			the absolute value of the given initial velocity is greater than or equal to 1.
	 * 		  | result ==
	 * 		  |    ( (abs(xInitialVelocity) <= getXVelocityLimit())
	 * 		  |   && (abs(xInitialVelocity) >= 1) )
	 */
	public boolean canHaveAsXInitalVelocity(double xInitialVelocity) {
		return (abs(xInitialVelocity) <= getXVelocityLimit()) && (abs(xInitialVelocity) >= 1);
	}
	
	/**
	 * Check whether this Mazub character can have the given xAcceleration as
	 * its acceleration in the x direction.
	 * 
	 * @param 	xAcceleration
	 * 			The acceleration in the x direction to be checked.
	 * @return	True if and only if the given acceleration in the x direction
	 * 			has the same sign as the velocity in the x direction.
	 * 		  | result ==
	 * 		  |    (signum(xAcceleration) == signum(getXVelocity())
	 */
	public boolean canHaveAsXAcceleration(double xAcceleration) {
		return (signum(xAcceleration) == signum(getXVelocity())) ;
	}
	
	/**
	 * Variable registering the initial velocity in the x direction of this Mazub charachter.
	 */
	private final double X_INITIAL_VELOCITY; //in pixels per seconde
	
	/**
	 * Variable registering the maximum allowed velocity in the x direction of this Mazub character.
	 */
	private double xVelocityLimit; //in pixels per seconde
	
	/**
	 * Variable registering the acceleration in the x direction that applies to all Mazub characters.
	 */
	private static final double X_ACCELERATION = 90; //in pixels per seconde kwadraat
	
	
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
	public static boolean isValidY(int y) {
		return (y <= Y_LIMIT) && (y >= 0);
	}
	
	/**
	 * Variable registering the maximum y position that applies to all Mazub characters.
	 */
	private static final int Y_LIMIT = 767; //in pixels
	
	/**
	 * Variable registering the initial velocity in the y direction that applies to all Mazub characters.
	 */
	private static final double Y_INITIAL_VELOCITY = 800; //in pixels per seconde
	
	/**
	 * Variable registering the acceleration in the y direction that applies to all Mazub characters.
	 */
	private static final double Y_ACCELERATION = 1000; //in pixels per seconde kwadraat
	
	
	/**
	 * The given Mazub starts moving in the given x direction with initial velocity
	 * X_INITIAL_VELOCITY and with acceleration getXAcelleration().
	 * 
	 * @param 	direction
	 * 			The direction in which the Mazub character should move.
	 * @pre		The given direction should be Direction.LEFT or Direction.RIGHT.
	 * 		  | (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 */
	public void startMove(Direction direction) {
//		assert (direction == Direction.LEFT) || (direction == Direction.RIGHT);
		setXDirection(direction);
		if (direction == Direction.RIGHT)
			setXVelocity(X_INITIAL_VELOCITY);
		else
			setXVelocity(-X_INITIAL_VELOCITY);
	}
	
	/**
	 * The given Mazub stops moving in the x direction. Its velocity in the 
	 * x direction is set to 0.
	 * 
	 * @pre	The velocity in the x direction cannot be 0.
	 * 	  | getXVelocity() != 0
	 */
	public void endMove() {
//		assert getXVelocity() != 0;
		setXVelocity(0);
	}
	
	/**kan ook met illegalstateexception
	 * The given Mazub starts jumping with initial velocity
	 * Y_INITIAL_VELOCITY and with acceleration getYAcelleration().
	 * 
	 * @throws 	JumpingException
	 * 			This Mazub character cannot start jumping because it is already jumping.
	 * 		  | isJumping()
	 */
	public void startJump() throws JumpingException {
		if (isJumping())
			throw new JumpingException("Already jumping!", this);
		setYVelocity(Y_INITIAL_VELOCITY);
	}
	
	/**
	 * The given Mazub stops jumping. Its velocity is set to 0 if it wasn't already 0.
	 * 
	 * @throws 	JumpingException
	 * 			This Mazub charachter cannot stop jumping because it isn't jumping.
	 * 		  | ! isJumping()
	 */
	public void endJump() throws JumpingException {
		if (!isJumping())
			throw new JumpingException("Not yet jumping!", this);
		if (getYVelocity() > 0)
			setYVelocity(0);
	}
	
	/**
	 * The given Mazub starts ducking. Its velocity limit in the x direction
	 * is set to DUCKED_VELOCITY_LIMIT.
	 */
	public void startDuck() {
		setPreviousXVelocityLimit(abs(getXVelocityLimit())); //ABS, want getXVelocityLimit > 0 (!)
		setXVelocityLimit(DUCKED_VELOCITY_LIMIT) ;
		if(abs(getXVelocity()) > abs(DUCKED_VELOCITY_LIMIT))
			setXVelocity(signum(getXVelocity())*DUCKED_VELOCITY_LIMIT);
		setDucked(true);
	}
	
	/**
	 * The given Mazub stops ducking. Its velocity limit in the x direction
	 * is reset to the previous xVelocityLimit.
	 */
	public void endDuck() {
		setXVelocityLimit(getPreviousXVelocityLimit());
		setDucked(false);
	}
	
	/**
	 * Variable registering the velocity limiy in the x direction while ducking.
	 */
	private final int DUCKED_VELOCITY_LIMIT = 100;
	
	
	/**
	 * 
	 * @param 	duration
	 * 			The duration for which advanceTime should work.
	 * @throws 	IllegalArgumentException
	 * 			The given duration is not a valid duration. A duration is not valid
	 * 			if it is smaller than 0 or greater than or equal to 0.2.
	 * 		  | (duration < 0) || (duration >= 0.2)
	 * @effect	
	 */
	public void advanceTime(double duration) throws IllegalArgumentException {
		if ((duration < 0) || (duration >= 0.2))
			throw new IllegalArgumentException("Illegal time duration!");
		moveX(duration);
		moveY(duration);
		if(getXVelocity() != 0)
			setTimeSinceLastMove(0);
		else
			setTimeSinceLastMove(getTimeSinceLastMove()+duration);
		if (isRunningNormally())
			setTimeSinceLastRunningImage(getTimeSinceLastRunningImage()+duration);
		else
			setTimeSinceLastRunningImage(0);
	}

	/**
	 * Return the timeSinceLastMove of this Mazub character.
	 * The timeSinceLastMove of a Mazub character expresses the time that has
	 * passed since the character made its last move.
	 */
	@Basic 
	public double getTimeSinceLastMove() {
		return this.timeSinceLastMove;
	}
	
	/**
	 * Set the timeSinceLastMove of this Mazub character to the given timeSinceLastMove.
	 * 
	 * @param	timeSinceLastMove
	 * 			The new timeSinceLastMove for this Mazub character.
	 * @post	The new timeSinceLastMove of this Mazub character is equal to
	 * 			the given timeSinceLastMove.
	 * 		  | new.getTimeSinceLastMove() == timeSinceLastMove
	 */
	public void setTimeSinceLastMove(double timeSinceLastMove) {
		this.timeSinceLastMove = timeSinceLastMove;
	}
	
	/**
	 * Variable registering the time since the last move of this Mazub character.
	 */
	private double timeSinceLastMove = 0;

	/**
	 * 
	 * @param	duration
	 * 			The duration for which the Mazub character should move in the x direction.
	 */
	private void moveX(double duration) {
		double xCurrent = getX();
		double vCurrent = getXVelocity();
		
		double xNew = xCurrent;
		double vNew = vCurrent+duration*getXAcceleration();
		if(abs(vNew) > abs(getXVelocityLimit())) {
			double timeBeforeVelocityLimit = (getXVelocityLimit()/getXAcceleration() - vCurrent/getXAcceleration());
			//assert (timeBeforeVelocityLimit >= 0);
			//moveX(timeBeforeVelocityLimit); //Stack Overflow
			xNew += getXVelocity()*timeBeforeVelocityLimit+0.5*getXAcceleration()*pow(duration,2);
			//setXVelocity(getXVelocityLimit()); overbodig
			vNew = getXVelocityLimit();
			xNew += vNew*(duration - timeBeforeVelocityLimit);
			
		}
		else {
			xNew = xCurrent + (getXVelocity()*duration + 0.5*getXAcceleration()*pow(duration,2));
		}
		
		if(! isValidX((int) xNew)) {
			vNew = 0;
			if (xNew < 0)
				xNew = 0;
			else
				xNew = X_LIMIT;
			}
		setX(xNew);
		setXVelocity(vNew);
	}
	
	/**
	 * 
	 * @param 	duration
	 * 			The duration for which the Mazub character should move in the y direction.
	 */
	private void moveY(double duration) {
		double yCurrent = getY();
		double vCurrent = getYVelocity();
		double yNew;
		
		double vNew = vCurrent + duration*getYAcceleration();
		
		yNew = yCurrent + vCurrent*duration + 0.5*getYAcceleration()*pow(duration,2.0);
		
		if(! isValidY((int) yNew)) {
			if (yNew < 0) {
				yNew = 0;
				vNew = 0;
			}
			if (yNew > Y_LIMIT) {
				yNew = Y_LIMIT;
				vNew = 0;
			}
		}
		setY(yNew);
		setYVelocity(vNew);
	}
	
	/**
	 * Return the x position of this Mazub character.
	 */
	@Basic
	public double getX() {
		return this.x;
	}
	
	/**
	 * Return the y position of this Mazub character.
	 */
	@Basic
	public double getY() {
		return this.y;
	}
	
	/**
	 * Set the x position of this Mazub character to the given x position.
	 * 
	 * @param 	x
	 * 			The new x position for this Mazub character.
	 * @pre		The given x position must be a valid x position for any Mazub character.
	 * 		  | isValidX((int) x)
	 * @post	The new x position of this Mazub character is equal to
	 * 			the given position.
	 * 		  | new.getX() == x
	 */
	private void setX(double x) {
//		assert isValidX((int) x);
		this.x = x;
	}
	
	/**
	 * Set the y position of this Mazub character to the given y position.
	 * 
	 * @param 	y
	 * 			The new y position for this Mazub character.
	 * @pre		The given y position must be a valid y position for any Mazub character.
	 * 		  | isValidY((int) y)
	 * @post	The new y position of this Mazub character is equal to
	 * 			the given position.
	 * 		  | new.getY() == y
	 */
	private void setY(double y) {
//		assert isValidY((int) y);
		this.y = y;
	}
	
	/**
	 * Return the xVelocity of this Mazub character.
	 */
	@Basic
	public double getXVelocity() {
		return this.xVelocity;
	}
	
	/**
	 * Return the yVelocity of this Mazub character.
	 */
	@Basic
	public double getYVelocity() {
		return this.yVelocity;
	}
	
	/**
	 * Set the xVelocity of this Mazub character to the given xVelocity.
	 * 
	 * @param 	xVelocity
	 * 			The new xVelocity for this Mazub character.
	 * @pre		The given xVelocity must be a valid xVelocity for this Mazub character.
	 * 		  | canHaveAsXVelocity(xVelocity)
	 * @post	The new xVelocity of this Mazub character is equal to
	 * 			the given xVelocity.
	 * 		  | new.getXVelocity() == xVelocity
	 */
	private void setXVelocity(double xVelocity) {
//		assert canHaveAsXVelocity(xVelocity);
		this.xVelocity = xVelocity;
	}
	
	/**
	 * Set the yVelocity of this Mazub character to the given yVelocity.
	 * 
	 * @param 	yVelocity
	 * 			The new yVelocity for this Mazub character.
	 * @post	The new yVelocity of this Mazub character is equal to
	 * 			the given yVelocity.
	 * 		  | new.getYVelocity() == yVelocity
	 */
	private void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	/**
	 * Return the XVelocityLimit of this Mazub character. 
	 * The XVelocityLimit is positive if the direction of the Mazub is Direction.RIGHT
	 * and negative if the direction of the Mazub is Direcion.LEFT.
	 */
	@Basic
	private double getXVelocityLimit() {
		if (getXDirection() == Direction.RIGHT)
			return xVelocityLimit;
		else
			return -xVelocityLimit;
	}
	
	/**
	 * Set the xVelocityLimit of this Mazub character to the given xVelocityLimit.
	 * 
	 * @param 	xVelocityLimit
	 * 			The new xVelocityLimit for this Mazub character.
	 * @pre		The given xVelocityLimit must be a valid xVelocity for this Mazub character.
	 * 		  | canHaveAsXVelocity(xVelocityLimit)
	 * @post	The new xVelocityLimit of this Mazub character is equal to
	 * 			the given xVelocityLimit.
	 * 		  | new.getXVelocityLimit() == xVelocityLimit
	 */
	private void setXVelocityLimit(double xVelocityLimit) {
//		assert canHaveAsXVelocity(xVelocityLimit);
		this.xVelocityLimit = xVelocityLimit;
	}
	
	/**
	 * Return the acceleration in the x direction of this Mazub character.
	 */
	public double getXAcceleration() {
		if ( (getXVelocity() == 0) || (getXVelocity() == getXVelocityLimit()) )
			return 0;
		else {
			if (getXDirection() == Direction.RIGHT)
				return X_ACCELERATION;
			else
				return -X_ACCELERATION;
		}
	}
	
	/**
	 * Return the acceleration in the y direction of this Mazub character.
	 */
	public double getYAcceleration() {
		if (getY() > 0)
			return -Y_ACCELERATION;
		else
			return 0;
	}
	
	/**
	 * Variable registering the x position of this Mazub character.
	 */
	private double x;
	
	/**
	 * Variable registering the y position of this Mazub character.
	 */
	private double y;
	
	/**
	 * Variable registering the xVelocity of this Mazub character.
	 */
	private double xVelocity;
	
	/**
	 * Variable registering the yVelocity of this Mazub character.
	 */
	private double yVelocity;

	/**
	 * Variable registering the ducked state of this Mazub character.
	 */
	private boolean ducked;
	
	/**
	 * Return the width of this Mazub character.
	 */
	public int getWidth() {
		return this.getCurrentSprite().getWidth();
	}
	
	/**
	 * Return the height of this Mazub charachter.
	 */
	public int getHeight() {
		return this.getCurrentSprite().getHeight();
	}

	/**
	 * Return the ducked states of this Mazub character.
	 */
	public boolean getDucked() {
		return ducked;
	}
	
	
	/**
	 * Set the ducked state of this Mazub character to the given state.
	 * @param 	state
	 * 			The new state for this Mazub character.
	 * @post	The new ducked state of this Mazub character is equal to
	 * 			the given state.
	 * 		  | new.getDucked() == state
	 * 			
	 */
	public void setDucked(boolean state) {
		ducked = state;
	}
	
	/**
	 * Return the PreviousXVelocityLimit of this Mazub character.
	 */
	@Basic
	public double getPreviousXVelocityLimit() {
		return previousXVelocityLimit;
	}
	
	/**
	 * Set the previousXVelocityLimit of this Mazub character to the given
	 * previousXVelocityLimit.
	 * @param 	previousXVelocityLimit
	 * 			The new previousXVelocityLimit for this Mazub character.
	 * @post	The new previousXVelocityLimit of this Mazub character is equal to
	 * 			the given previousXVelocityLimit.
	 * 		  | new.getPreviousXVelocityLimit() == previousXVelocityLimit
	 */
	public void setPreviousXVelocityLimit(double previousXVelocityLimit) {
		this.previousXVelocityLimit = previousXVelocityLimit;
	}
	
	/**
	 * Variable registering the previous velocity limit in the x direction.
	 */
	private double previousXVelocityLimit;
	
	/**
	 * Check whether this Mazub charcter is jumping.
	 * @return True if and only if the velocity in the y direction is not 0.
	 */
	public boolean isJumping() {
		return (getYVelocity() != 0);
	}
	
	/**
	 * Return the current sprite of this Mazub character.
	 * @return
	 */
	public Sprite getCurrentSprite() {
		if ((getXVelocity() == 0) && (getTimeSinceLastMove() > 1)) {
			if (! getDucked())
				return this.getSprites()[0];
			else
				return this.getSprites()[1];
		}
		
		if ((getXVelocity() == 0) && (getTimeSinceLastMove() <= 1) && (! getDucked())) {
			if (getXDirection() == Direction.RIGHT) 
				return this.getSprites()[2];
			else
				return this.getSprites()[3];
		}
			
		if ((getXVelocity() != 0) && (isJumping()) && (! getDucked())) {
			if (getXDirection() == Direction.RIGHT)
				return this.getSprites()[4];
			else
				return this.getSprites()[5];
		}
		
		if ((getDucked()) && ((getXVelocity() > 0) || (getTimeSinceLastMove() <= 1))) {
			if (getXDirection() == Direction.RIGHT)
				return this.getSprites()[6];
			else
				return this.getSprites()[7];
		}
		
		if (isRunningNormally()) {
			int index = ((int)(getTimeSinceLastRunningImage()/0.075))%(getM()+1);
			if(getXDirection() == Direction.RIGHT)
				return getSprites()[8+index];
			else
				return getSprites()[9+getM()+index];	
		}
		return getSprites()[0];
	}
	
	/**
	 * Return the number of different images for the running motion.
	 * @return (this.sprites.length-10)/2
	 */
	private int getM() {
		return (this.sprites.length-10)/2;
	}
	
	/**
	 * Return the sprites of this Mazub character.
	 */
	@Basic
	public Sprite[] getSprites() {
		return sprites;
	}
	
	/**
	 * Set the sprites for this Mazub charaacter to the given sprites.
	 * @param 	sprites
	 * 			The new sprites for this Mazub character.
	 * @post	The new sprites of this Mazub character is equal to
	 * 			the given sprties.
	 * 		  | new.getSprites() == sprites
	 */
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	/**
	 * Variable registering the sprites of this Mazub character.
	 */
	Sprite[] sprites;
	
	/**
	 * Check whether this Mazub character is running normally. A mazub character
	 * is running normally if it is not ducking, and if its velocity is not equal to 0.
	 * and if it is not jumping.
	 * 
	 * @return
	 */
	public boolean isRunningNormally() {
		return ((! getDucked()) && (abs(getXVelocity()) > 0) && (! isJumping()));
	}
	

	/**
	 * Return the timeSinceLastRunningImage of this Mazub character.
	 */
	@Basic
	private double getTimeSinceLastRunningImage() {
		return timeSinceLastRunningImage;
	}

	/**
	 * @param 	timeSinceLastRunningImage 
	 * 			The new timeSinceLastRunningImage for this Mazub character.
	 * @post	The new timeSinceLastRunningImage of this Mazub character is equal 
	 * 			to the given timeSinceLastRunningImage.
	 * 		  | new.getTimeSinceLastRunningImage() == timeSinceLastRunningImage	
	 */
	private void setTimeSinceLastRunningImage(double timeSinceLastRunningImage) {
		this.timeSinceLastRunningImage = timeSinceLastRunningImage;
	}
	
	/**
	 * Variable registering the time since the last image for a running motion was used.
	 */
	private double timeSinceLastRunningImage = 0;

	/**
	 * Return the xDirection of this Mazub character.
	 */
	@Basic
	public Direction getXDirection() {
		return this.xDirection;
	}
	
	/**
	 * 
	 * @param 	xDirection
	 * 			The new xDirection for this Mazub character.
	 * @pre		The given direction should be Direction.LEFT or Direction.RIGHT.
	 * 		  | (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @post	The new xDirection of this Mazub character is equal to
	 * 			the given xDirection.
	 * 		  | new.getXDirection() == xDirection
	 */
	private void setXDirection(Direction xDirection) {
//		assert (direction == Direction.LEFT) || (direction == Direction.RIGHT);
		this.xDirection = xDirection;
	}

	/**
	 * Variable registering the x direction of this Mazub character. The x direction
	 * is Direction.LEFT or Direction.RIGHT.
	 */
	private Direction xDirection;
	
	/**
	 * An enumeration introducing two directions used to express the direction 
	 * of a Mazub character.
	 */
	public static enum Direction {
		LEFT, RIGHT;
	}
	
	
}
