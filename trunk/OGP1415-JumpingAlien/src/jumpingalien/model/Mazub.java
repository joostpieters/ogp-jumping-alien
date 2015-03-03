package jumpingalien.model;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.signum;
//precondities canHaveAs bij constructor (xInitialVelocity)


/**
 * A class of Mazub characters involving several properties.
 * 
 * @invar	The position that applies to all Mazub characters must be 
 * 			a valid position.
 * 		  | isValidX(getX()) && isValidY(getY())
 * @invar	Each Mazub character can have its velocity as its velocity.
 * 		  | canHaveAsXVelocity(getXVelocity())
 * @invar	Each Mazub character can have its initial velocity in the 
 *			x direction as its initial velocity in the x direction.
 *		  | canHaveAsXInitialVelocity(getXInitialVelocity())
 * @invar	Each Mazub character can have its acceleration as its acceleration.
 * 		  | canHaveAsXAcceleration(getXAcceleration())
 * @invar	Each Mazub character can have its velocity limit as its velocity limit.
 * 		  | canHaveAsXVelocityLimit(getXVelocityLimit())
 * @invar	Each Mazub character has Direction.LEFT or Direction.RIGHT as its direction.
 * 		  | (getXDirection() == Direction.LEFT) || (getXDirection() == Direction.RIGHT)
 * 
 * @author Andreas Schryvers & Jonathan Oostvogels
 */

public class Mazub {
	
	/**
	 * Initialize this new Mazub character with given x position, given y position, given
	 * sprites, given initial velocity in the x direction and given velocity limit in the x direction.
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
	 * 		  | canHaveAsXInitialVelocity(xInitialVelocity)
	 * @pre		This new Mazub character can have the  given xVelocityLimit as its velocity limit in the x direction.
	 * 		  | canHaveAsXVelocityLimit(xVelocityLimit)
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
	public Mazub(double x, double y, Sprite[] sprites,
			double xInitialVelocity, double xVelocityLimit) {
	
		assert isValidX((int) x);
		assert isValidY((int) y);
		assert sprites != null;
				
		
		this.setX(x);
		this.setY(y);
		
		this.setSprites(sprites);
		this.X_INITIAL_VELOCITY = xInitialVelocity;
		this.setXVelocityLimit(xVelocityLimit);
		this.setPreviousXVelocityLimit(xVelocityLimit);
		this.setXVelocity(0);
		this.setYVelocity(0);
		this.setDucked(false);
		this.setTimeSinceLastMove(2); // > 1
		this.setXDirection(Direction.RIGHT);
	}
	
	/**
	 * Initialize this new Mazub character as a Mazub character with default values for the initial velocity in 
	 * the x direction and for the velocity limit in the x direction, with given x and y position and with given sprites.
	 * 
	 * @param 	x
	 * 			The initial x position for this new Mazub character. 
	 * @param 	y
	 * 			The initial y position for this new Mazub character.
	 * @param 	sprites
	 * 			The series of initial sprites for this new Mazub character.
	 * @effect	This new Mazub character is initialized with the given x as its x position, the given
	 * 			y as its y position and the given sprites as its sprites.
	 * 		  | this(x,y,sprites,X_INITIAL_VELOCITY,xVelocityLimit);
	 */
	@Raw
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
		return ( (x <= X_LIMIT) && (x >= 0) );
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
	 * @return	True if and only if the given velocity is 0 or if
	 * 			the absolute value of the given velocity is greater 
	 * 			than or equal to the initial velocity in the x direction and the 
	 * 			absolute value of the given velocity is smaller than or equal to the
	 * 			velocity limit in the x direction of this Mazub character.
	 * 		  | result == 
	 * 		  |    (  (xVelocity == 0) 
	 * 		  |   || ( (abs(xVelocity) >= getXInitialVelocity())
	 *		  |        && (abs(xVelocity) <= getXVelocityLimit()) ) )
	 */
	public boolean canHaveAsXVelocity(double xVelocity) {
		return (xVelocity == 0) || (abs(xVelocity) >= abs(getXInitialVelocity())) && (abs(xVelocity) <= abs(getXVelocityLimit()));
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
	public boolean canHaveAsXInitialVelocity(double xInitialVelocity) {
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
	 * Check whether this Mazub character can have the given xVelocityLimit as its xVelocityLimit.
	 * 
	 * @param 	xVelocityLimit
	 * 			The xVelocityLimit to be checked.
	 * @return	True if and only if this Mazub character can have the given xVelocityLimit as its xVelocity
	 * 			and if the given xVelocityLimit is positive.
	 * 		  | result == ( (canHaveAsXVelocity(xVelocityLimit)) && (xVelocityLimit >= 0) )
	 */
	private boolean canHaveAsXVelocityLimit(double xVelocityLimit) {
		return ((canHaveAsXVelocity(xVelocityLimit)) && (xVelocityLimit >= 0) );
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
	 * getXInitialVelocity() and with acceleration getXAcelleration().
	 * 
	 * @param 	direction
	 * 			The direction in which the Mazub character should move.
	 * @pre		The given direction should be Direction.LEFT or Direction.RIGHT.
	 * 		  | (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @post	The new xDirection of this Mazub character is equal to the given direction.
	 * 		  | new.getXDirection() == direction
	 * @post	The new xVelocity of this Mazub character is equal to getXInitialVelocity() if
	 * 			the direction of this Mazub character is Direction.RIGHT or equal to 
	 * 			- getXInitialVelocity() if the direction of this Mazub character is Direction.LEFT.
	 * 		  | abs(new.getXVelocity()) == getXInitialVelocity()
	 */
	public void startMove(Direction direction) {
		assert (direction == Direction.LEFT) || (direction == Direction.RIGHT);
		setXDirection(direction);
		if (getXDirection() == Direction.RIGHT)
			setXVelocity(getXInitialVelocity());
		else
			setXVelocity(-getXInitialVelocity());
	}
	
	/**
	 * The given Mazub character stops moving in the x direction. 
	 * Its velocity in the x direction is set to 0.
	 * 
	 */
	public void endMove() {
		setXVelocity(0);
	}
	
	/**
	 * The given Mazub character starts jumping with initial velocity
	 * Y_INITIAL_VELOCITY and with acceleration getYAcelleration().
	 * 
	 * @post	The new yVelocity of this mazub character is equal to Y_INITIAL_VELOCITY.
	 * 		  | new.getYVelocity() == Y_INITIAL_VELOCITY
	 * 
	 * @throws 	JumpingException
	 * 			This Mazub character cannot start jumping because it is already jumping.
	 * 		  | isJumping()
	 */
	public void startJump() throws JumpingException {
		if (isJumping())
			throw new JumpingException("Already jumping!", this);
		setYVelocity(getYInitialVelocity());
	}
	
	/**
	 * The given Mazub stops jumping. Its velocity is set to 0 if it was greater than 0.
	 * 
	 * @post	The new yVelocity of this Mazub character is equal to 0 if yVelocity is greater than 0.
	 * 		  | if (getYVelocity() > 0)
	 * 		  |		new.getYVelocity() == 0
	 * 
	 * @throws 	JumpingException
	 * 			This Mazub charachter cannot stop jumping because it is not jumping.
	 * 		  | ! isJumping()
	 */
	public void endJump() throws JumpingException {
		if (!isJumping())
			throw new JumpingException("Not yet jumping!", this);
		if (getYVelocity() > 0)
			setYVelocity(0);
	}
	
	/**
	 * The given Mazub character starts ducking. Its velocity limit in the x direction
	 * is set to DUCKED_VELOCITY_LIMIT. Its new ducked state is equal to True.
	 * 
	 * @post	The new previousXVelocityLimit of this Mazub character is equal to the old xVelocityLimit.
	 * 		  | new.getPreviousXVelocityLimit() == this.getXVelocityLimit()
	 * @post 	The new xVelocityLimit of this Mazub character is equal to DUCKED_VELOCITY_LIMIT.
	 * 		  | new.getXVelocityLimit() == DUCKED_VELOCITY_LIMIT
	 * @post	If this Mazub character has a velocity in the x direction that is greater than 
	 * 			the new xVelocityLimit, then the new velocity in the x direction is set to the new
	 * 			xVelocityLimit.
	 * 		  | if (abs(this.getXVelocity()) > abs(new.getXVelocityLimit()))
	 * 		  |		new.getXVelocity() == (signum(this.getXVelocity())) * (new.getXVelocityLimit())
	 * @post	The new ducked state of this Mazub character is equal to true.
	 * 		  | new.getDucked() == true
	 * @throws	IllegalStateExpcetion
	 * 			This Mazub character cannot start ducking because it is already ducking.
	 * 		  | getDucked()
	 * 
	 */
	public void startDuck() throws IllegalStateException {
		if (getDucked())
			throw new IllegalStateException("Already ducking!");
		setPreviousXVelocityLimit(abs(getXVelocityLimit()));
		setXVelocityLimit(DUCKED_VELOCITY_LIMIT);
		if(abs(getXVelocity()) > abs(getXVelocityLimit()))
			setXVelocity(signum(getXVelocity())*abs(getXVelocityLimit()));
		setDucked(true);
	}
	
	/**
	 * The given Mazub character stops ducking. Its velocity limit in the x direction
	 * is reset to the previous xVelocityLimit. 
	 * 
	 * @post	The new xVelocityLimit of this Mazub character is equal to the previousXVelocityLimit.
	 * 		  | new.getXVelocityLimit() == this.getPreviousXVelocityLimit()
	 * @post	The new ducked state of this Mazub character is equal to false.
	 * 		  | new.getDucked() == false
	 * @throws	IllegalStateException
	 * 			This Mazub character cannot stop ducking because it is not ducking.
	 * 		  | ! getDucked()
	 */
	public void endDuck() throws IllegalStateException {
		if (! getDucked())
			throw new IllegalStateException("Not yet ducking!");
		setXVelocityLimit(getPreviousXVelocityLimit());
		setDucked(false);
	}
	
	/**
	 * Variable registering the velocity limit in the x direction while ducking.
	 */
	private final int DUCKED_VELOCITY_LIMIT = 100;
	
	
	/**
	 * This Mazub character's properties are updated for the given time duration.
	 * 
	 * @param 	duration
	 * 			The duration for which advanceTime should work.
	 * @effect	Move this Mazub character in the x and y direction for the given duration.
	 * 		  | moveX(duration) && moveY(duration)
	 * @post	If the current xVelocity is not equal to 0, then the new timeSinceLastMove is equal to 0.
	 * 		  | if (getXVelocity() != 0)
	 * 		  | 	new.getTimeSinceLastMove() == 0;
	 * @post	If the current xVelocity is equal to 0, then the new timeSinceLastMove is equal to the current
	 * 			timeSinceLastMove + duration.
	 * 		  | if (getXVelocity() == 0)
	 * 		  |		new.getTimeSinceLastMove() == (this.getTimeSinceLastMove()+duration)
	 * @post	If this Mazub character is running normally, then the new timeSinceLastRunningImage is equal
	 * 		    to the current timeSinceLastRunningImage + duration
	 * 		  | if (isRunningNormally())
	 * 		  | 	new.getTimeSinceLastRunningImage() == (this.getTimeSinceLastRunningImage() + duration)
	 * @post	If this Mazub character is not running normally, then the new timeSinceLastRunningImage is equal
	 * 		    to the current timeSinceLastRunningImage.
	 * 		  | if (! isRunningNormally())
	 * 		  | 	new.getTimeSinceLastRunningImage() == this.getTimeSinceLastRunningImage()
	 * @throws 	IllegalArgumentException
	 * 			The given duration is not a valid duration. A duration is not valid
	 * 			if it is smaller than 0 or greater than or equal to 0.2.
	 * 		  | (duration < 0) || (duration >= 0.2)	
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
	 * @pre		The given timeSinceLastMove must be positive.
	 * @post	The new timeSinceLastMove of this Mazub character is equal to
	 * 			the given timeSinceLastMove.
	 * 		  | new.getTimeSinceLastMove() == timeSinceLastMove
	 */
	@Raw
	public void setTimeSinceLastMove(double timeSinceLastMove) {
		assert timeSinceLastMove >= 0;
		this.timeSinceLastMove = timeSinceLastMove;
	}
	
	/**
	 * Variable registering the time since the last move of this Mazub character.
	 */
	private double timeSinceLastMove = 0;

	/**
	 * Move this Mazub character in the x direction for the given duration.
	 * 
	 * @param	duration
	 * 			The duration for which the Mazub character should move in the x direction.
	 * @post	If the direction of this Mazub character is Direction.RIGHT and duration is greater than 0, 
	 * 			then the new x position of this	Mazub character has increased or this Mazub character
	 * 			is positioned at the right border of the screen.
	 * 		  | if getXDirection() == Direction.RIGHT && duration > 0
	 * 		  | 	new.getX() > getX() || getX() == X_LIMIT
	 * @post	If the direction of this Mazub character is Direction.LEFT and duration is greater than 0,
	 * 			then the new x position of this	Mazub character has decreaed or this Mazub character is
	 *			positioned at the left border of the screen.
	 * 		  | if getXDirection() == Direction.LEFT && duration > 0
	 * 		  | 	new.getX() < getX() || getX() == 0
	 * @post	If the direction of this Mazub character is Direction.RIGHT and duration is greater than 0, 
	 * 			then the new xVelocity of this	Mazub character has increased or the xVelocity is equal to
	 * 			xVelocityLimit.
	 * 		  | if getXDirection() == Direction.RIGHT && duration > 0
	 * 		  | 	new.getXVelocity() > getXVelocity() || getXVelocity() == getXVelocityLimit()
	 * @post	If the direction of this Mazub character is Direction.LEFT and duration is greater than 0, 
	 * 			then the new xVelocity of this	Mazub character has decreased or the xVelocity is equal to
	 * 			- xVelocityLimit.
	 * 		  | if getXDirection() == Direction.LEFT && duration > 0
	 * 		  | 	new.getXVelocity() > getXVelocity() || getXVelocity() == getXVelocityLimit()
	 */
	private void moveX(double duration) {
		if (duration <= 0)
			return;
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
	 * Move this Mazub character in the y direction for the given duration.
	 * 
	 * @param 	duration
	 * 			The duration for which the Mazub character should move in the y direction.
	 * @post	If duration > 0 and if the Mazub character is jumping, then the absolute value of the
	 * 			adjustement for it's y position is smaller than or equal to abs(getYVelocity()*duration
	 * 			 + 0.5*getYAcceleration()*pow(duration,2.0))
	 * 		  | if duration > 0
	 * 		  |		 abs(new.getY() - getY()) <= abs(getYVelocity()*duration
	 * 		  |			 + 0.5*getYAcceleration()*pow(duration,2.0))
	 * @post	If duration > 0, the yVelocity has decreased or the character is not jumping.
	 * 		  | if duration > 0
	 * 		  | 	new.getYVelocity() < getYVelocity() || (! isJumping()) 
	 */
	private void moveY(double duration) {
		if (duration <= 0)
			return;
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
	@Raw
	private void setX(double x) {
		assert isValidX((int) x);
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
	@Raw
	private void setY(double y) {
		assert isValidY((int) y);
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
	@Raw
	private void setXVelocity(double xVelocity) {
		assert canHaveAsXVelocity(xVelocity);
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
	@Raw
	private void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	/**
	 * Return the xVelocityLimit of this Mazub character. 
	 * The xVelocityLimit is positive if the direction of the Mazub is Direction.RIGHT
	 * and negative if the direction of the Mazub is Direcion.LEFT.
	 */
	public double getXVelocityLimit() {
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
	 * @pre		The given xVelocityLimit must be a valid xVelocityLimit for this Mazub character.
	 * 		  | canHaveAsXVelocityLimit(xVelocityLimit)
	 * @post	The new xVelocityLimit of this Mazub character is equal to
	 * 			the given xVelocityLimit.
	 * 		  | new.getXVelocityLimit() == xVelocityLimit
	 */
	@Raw
	private void setXVelocityLimit(double xVelocityLimit) {
//		assert canHaveAsXVelocityLimit(xVelocityLimit);
		this.xVelocityLimit = xVelocityLimit;
	}
	

	
	/**
	 * @return	0 if xVelocity is 0 or if xVelocity is equal to xVelocityLimit
	 * 		  | if ( (getXVelocity() == 0) || (getXVelocity() == getXVelocityLimit()) )
	 * 		  | 	then result == 0
	 * 			Otherwise, X_ACCELERTATION if xDirection is equal to Direction.RIGHT
	 * 		  | if (getXDirection() == Direction.RIGHT)
	 * 		  | 	then result == X_ACCELERATION
	 * 			Otherwise, -X_ACCELERTATION if xDirection is equal to Direction.LEFT
	 * 		  | if (getXDirection() == Direction.LEFT)
	 * 		  | 	then result == -X_ACCELERATION
	 *
	 * 
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
		if (isJumping())
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
	 * @return 	The width of this Mazub character's current sprite.
	 * 		  | result == (this.getCurrentSprite().getWidth())
	 */
	public int getWidth() {
		return this.getCurrentSprite().getWidth();
	}
	
	/**
	 * @return 	The height of this Mazub charachter's currect sprite.
	 * 		  | result == (this.getCurrentSprite().getHeight())
	 */
	public int getHeight() {
		return this.getCurrentSprite().getHeight();
	}

	/**
	 * Return the ducked state of this Mazub character.
	 */
	@Basic
	public boolean getDucked() {
		return ducked;
	}
	
	
	/**
	 * Set the ducked state of this Mazub character to the given state.
	 * 
	 * @param 	state
	 * 			The new state for this Mazub character.
	 * @post	The new ducked state of this Mazub character is equal to
	 * 			the given state.
	 * 		  | new.getDucked() == state
	 */
	@Raw
	private void setDucked(boolean state) {
		ducked = state;
	}
	
	/**
	 * Return the previousXVelocityLimit of this Mazub character.
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
	 * @pre		The given previousXVelocityLimit should be a valid xVelocityLimit for this Mazub character.
	 * 		  | canHaveAsXVelocityLimit(previousXVelocityLimit)
	 * @post	The new previousXVelocityLimit of this Mazub character is equal to
	 * 			the given previousXVelocityLimit.
	 * 		  | new.getPreviousXVelocityLimit() == previousXVelocityLimit
	 */
	@Raw
	private void setPreviousXVelocityLimit(double previousXVelocityLimit) {
		this.previousXVelocityLimit = previousXVelocityLimit;
	}
	
	/**
	 * Variable registering the previous velocity limit in the x direction.
	 */
	private double previousXVelocityLimit;
	
	/**
	 * Check whether this Mazub charcter is jumping.
	 * 
	 * @return 	True if and only if the current y position is greater than 0.
	 * 		  | result == (getY() > 0)
	 */
	public boolean isJumping() {
		return (getY() > 0);
	}
	
	/**
	 * Return the current sprite of this Mazub character.
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
	 * @return	The number of different images for the running motion.
	 * 		  | result == (this.sprites.length-10)/2
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
	 * 
	 * @param 	sprites
	 * 			The new sprites for this Mazub character.
	 * @post	The new sprites of this Mazub character is equal to
	 * 			the given sprties.
	 * 		  | new.getSprites() == sprites
	 */
	@Raw
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	/**
	 * Variable registering the sprites of this Mazub character.
	 */
	private Sprite[] sprites;
	
	/**
	 * Check whether this Mazub character is running normally. A Mazub character
	 * is running normally if it is ducking nor jumping, and if its velocity is not equal to 0.
	 * 
	 * @return	True if and only if this Mazub character is ducking nor jumping, and if its velocity
	 * 			is not equal to 0.
	 * 		  | result ==
	 * 		  |    ( ! getDucked())
	 * 		  |   && (! isJumping())
	 * 		  |   && (abs(getXVelocity()) > 0) )
	 */
	public boolean isRunningNormally() {
		return ( (! getDucked()) && (! isJumping()) && (abs(getXVelocity()) > 0) );
	}
	

	/**
	 * Return the timeSinceLastRunningImage of this Mazub character.
	 */
	@Basic
	private double getTimeSinceLastRunningImage() {
		return timeSinceLastRunningImage;
	}

	/**
	 * Set the timeSinceLastRunningImage of this Mazub character to the given timeSinceLastRunningImage.
	 * 
	 * @param 	timeSinceLastRunningImage 
	 * 			The new timeSinceLastRunningImage for this Mazub character.
	 * @pre		The given timeSinceLastRunningImage should be positive.
	 * 		  | timeSinceLastRunningImage >= 0
	 * @post	The new timeSinceLastRunningImage of this Mazub character is equal 
	 * 			to the given timeSinceLastRunningImage.
	 * 		  | new.getTimeSinceLastRunningImage() == timeSinceLastRunningImage	
	 */
	private void setTimeSinceLastRunningImage(double timeSinceLastRunningImage) {
		assert timeSinceLastRunningImage >= 0;
		this.timeSinceLastRunningImage = timeSinceLastRunningImage;
	}
	
	/**
	 * Variable registering the time since the last image for a running motion was used for this Mazub character.
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
	 * Set the xDirection for this Mazub character to the given xDirection.
	 * 
	 * @param 	xDirection
	 * 			The new xDirection for this Mazub character.
	 * @pre		The given direction should be Direction.LEFT or Direction.RIGHT.
	 * 		  | (xDirection == Direction.LEFT) || (xDirection == Direction.RIGHT)
	 * @post	The new xDirection of this Mazub character is equal to
	 * 			the given xDirection.
	 * 		  | new.getXDirection() == xDirection
	 */
	@Raw
	private void setXDirection(Direction xDirection) {
		assert ((xDirection == Direction.LEFT) || (xDirection == Direction.RIGHT));
		this.xDirection = xDirection;
	}

	/**
	 * Variable registering the x direction of this Mazub character. The x direction
	 * is Direction.LEFT or Direction.RIGHT.
	 */
	private Direction xDirection;
	
	/**
	 * An enumeration introducing two directions used to express the x direction 
	 * of a Mazub character.
	 */
	public static enum Direction {
		LEFT, RIGHT;
	}
	
	/**
	 * Return the initial velocity in the x direction of this Mazub character.
	 */
	@Basic @Immutable
	private double getXInitialVelocity() {
		return X_INITIAL_VELOCITY;
	}
	
	@Basic @Immutable
	private static double getYInitialVelocity() {
		return Y_INITIAL_VELOCITY;
	}
	
}
