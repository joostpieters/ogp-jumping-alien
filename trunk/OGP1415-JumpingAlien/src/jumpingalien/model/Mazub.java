package jumpingalien.model;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;
import static java.lang.Math.pow;

/**
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
		this.setXDirection(RIGHT);
		
		
	}
	
	public Mazub(double x, double y, Sprite[] sprites) {
		this(x,y,sprites,100,300);
	}
	
	
	/**
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
	 * 
	 * @param 	xVelocity
	 * 			The velocity in the x direction to be checked.
	 * @return	True if and only if the given velocity is greater than or equal
	 * 			to the initial velocity in the x direction, and if the given velocity
	 * 			is smaller than or equal to the velocity limit in the x direction.
	 * 		  | result == 
	 * 		  |    ( (xVelocity >= X_INITIAL_VELOCITY)
	 *		  |   && (xVelocity <= getXVelocityLimit()) )
	 */
	public boolean canHaveAsXVelocity(double xVelocity) {
		return (xVelocity >= X_INITIAL_VELOCITY && xVelocity <= getXVelocityLimit());
	}
	
	/**
	 * 
	 * @param	xInitialVelocity
	 * 			The initial velocity in the x direction to be checked.
	 * @return	True if and only if the given initial velocity is smaller than
	 * 			or equal to the velocity limit in the x direction, and if
	 * 			the given initial velocity is greater than or equal to 1.
	 * 		  | result ==
	 * 		  |    ( (xInitialVelocity <= getXVelocityLimit())
	 * 		  |   && (xInitialVelocity >= 1) )
	 */
	public boolean canHaveAsXInitalVelocity(double xInitialVelocity) {
		return (xInitialVelocity <= getXVelocityLimit()) && (xInitialVelocity >= 1);
	}
	
	/**
	 * 
	 * @param 	xAcceleration
	 * 			The acceleration in the x direction to be checked.
	 * @return	True if and only if the given acceleration is 
	 * 			greater than or equal to 0.
	 * 		  | result ==
	 * 		  |    (xAcceleration >= 0)
	 */
	public static boolean isValidXAcceleration(double xAcceleration) {
		return (xAcceleration >= 0) ;
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
	private static final double Y_ACCELERATION = -1000; //in pixels per seconde kwadraat
	
	
	/**
	 * 
	 * @param 	direction
	 * 			The direction in which the Mazub character should move.
	 * @pre		The given direction should be Mazub.LEFT or Mazub.RIGHT.
	 * 		  | (direction == Mazub.LEFT) || (direction == Mazub.RIGHT)
	 */
	public void startMove(int direction) {
		setXDirection(direction);
		setXVelocity(X_INITIAL_VELOCITY);
	}
	
	/**
	 * @pre	The velocity in the x direction should be greater than 0.
	 * 	  | getXVelocity() > 0
	 */
	public void endMove() {
		setXVelocity(0);
	}
	
	/**
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
	 * 
	 * @throws 	JumpingException
	 * 			This Mazub charachter cannot stop jumping because it isn't jumping.
	 * 		  | ! isJumping()
	 */
	public void endJump() throws JumpingException {
		if (!isJumping())
			throw new JumpingException("Not yet jumping!", this);
		setYVelocity(0);
	}
	
	public void startDuck() {
		setPreviousXVelocityLimit(getXVelocity());
		setXVelocityLimit(100) ;
		//  hoogte aanpassen
		setDucked(true);
	}
	
	public void endDuck() {
		setXVelocityLimit(getPreviousXVelocityLimit());
		setDucked(false);
	}
	
	
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
		if(getXVelocity() > 0)
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
		
		if (getXAcceleration() == 0) {
			return;
		}
		
		double xNew;
		double vNew = vCurrent+duration*getXAcceleration();
		
		if(vNew > getXVelocityLimit()) {
			double timeBeforeVelocityLimit = (getXVelocityLimit() - vCurrent)/getXAcceleration();
			moveX(timeBeforeVelocityLimit);
			setXVelocity(getXVelocityLimit());
			vNew = getXVelocityLimit();
			xNew = getX() + getXDirection()*vNew*(duration - timeBeforeVelocityLimit);
			
		}
		else {
			xNew = xCurrent + getXDirection()*
									(getXVelocity()*duration + 0.5*getXAcceleration()*pow(duration,2.0));
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
		this.y = y;
	}
	

	/**
	 * Return the xDirection of this Mazub character.
	 */
	@Basic
	public int getXDirection() {
		return this.xDirection;
	}
	
	/**
	 * 
	 * @param 	xDirection
	 * 			The new xDirection for this Mazub character.
	 * @pre		The given direction should be Mazub.LEFT or Mazub.RIGHT.
	 * 		  | (direction == Mazub.LEFT) || (direction == Mazub.RIGHT)
	 * @post	The new xDirection of this Mazub character is equal to
	 * 			the given xDirection.
	 * 		  | new.getXDirection() == xDirection
	 */
	private void setXDirection(int xDirection) {
		this.xDirection = xDirection;
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
	 */
	@Basic
	private double getXVelocityLimit() {
		return xVelocityLimit;
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
	
	@Basic
	/**
	 * Return the X_ACCELERATION of this Mazub character.
	 */
	public double getXAcceleration() {
		if (getXVelocity() == 0)
			return 0;
		else
			return X_ACCELERATION;
	}
	
	@Basic
	/**
	 * Return the Y_ACCELERATION of this Mazub character.
	 */
	public double getYAcceleration() {
		if (getY() > 0)
			return Y_ACCELERATION;
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
	 * Variable registering the xDirection of this Mazub character.
	 */
	private int xDirection;
	
	/**
	 * Variable registering the value of LEFT that applies to all Mazub characters.
	 */
	public static final int LEFT = -1;
	
	/**
	 * Variable registering the value of RIGHT that applies to all Mazub characters.
	 */
	public static final int RIGHT = 1;
	
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
	
	
	public void setDucked(boolean flag) {
		ducked = flag;
	}
	
	@Basic
	public double getPreviousXVelocityLimit() {
		return previousXVelocityLimit;
	}
	
	public void setPreviousXVelocityLimit(double previousXVelocityLimit) {
		this.previousXVelocityLimit = previousXVelocityLimit;
	}
	
	private double previousXVelocityLimit;
	
	public boolean isJumping() {
		return (getYVelocity() != 0);
	}
	
	public Sprite getCurrentSprite() {
		if ((getXVelocity() == 0) && (getTimeSinceLastMove() > 1)) {
			if (! getDucked())
				return this.getSprites()[0];
			else
				return this.getSprites()[1];
		}
		
		
		
		if ((getXVelocity() == 0) && (getTimeSinceLastMove() <= 1) && (! getDucked())) {
			if (getXDirection() == 1) 
				return this.getSprites()[2];
			else
				return this.getSprites()[3];
		}
			
		
		
		if ((getXVelocity() > 0) && (isJumping()) && (! getDucked())) {
			if (getXDirection() == 1)
				return this.getSprites()[4];
			else
				return this.getSprites()[5];
		}
		
		
		if ((getDucked()) && ((getXVelocity() > 0) || (getTimeSinceLastMove() <= 1))) {
			if (getXDirection() == 1)
				return this.getSprites()[6];
			else
				return this.getSprites()[7];
		}
		
		if (isRunningNormally()) {
			int index = ((int)(getTimeSinceLastRunningImage()/0.075))%(getM()+1);
			if(getXDirection() == 1)
				return getSprites()[8+index];
			else
				return getSprites()[9+getM()+index];
			
		}
		
		return getSprites()[0];
	}
	
	private int getM() {
		return (this.sprites.length-10)/2;
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	Sprite[] sprites;
	
	public boolean isRunningNormally() {
		return ((getDucked()) && (getXVelocity() > 0) && (! isJumping()));
	}
	
	

	/**
	 * @return the timeSinceLastRunningImage
	 */
	private double getTimeSinceLastRunningImage() {
		return timeSinceLastRunningImage;
	}

	/**
	 * @param timeSinceLastRunningImage the timeSinceLastRunningImage to set
	 */
	private void setTimeSinceLastRunningImage(double timeSinceLastRunningImage) {
		this.timeSinceLastRunningImage = timeSinceLastRunningImage;
	}
	
	private double timeSinceLastRunningImage = 0;
	
	
	
}
