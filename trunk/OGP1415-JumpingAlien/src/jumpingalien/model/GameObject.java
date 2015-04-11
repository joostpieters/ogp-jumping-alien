/**
 * 
 */
package jumpingalien.model;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * @author Jonathan
 *
 */
public abstract class GameObject {

	public abstract Sprite getCurrentSprite();
	public abstract void handleInteraction(double duration);

	
	public GameObject(World world, double x, double y, int z, 
							int initialHitPoints, int maxHitPoints, Sprite[] sprites,
								double xInitialVelocity, double yInitialVelocity,
									double xVelocityLimit,  double duckedVelocityLimit,
										double xAcceleration, double yAcceleration) {
		setMyWorld(world);
		setPosition(x,y);
		MAX_HITPOINTS = maxHitPoints;
		setHitPoints(initialHitPoints);
		this.Z = z;
		this.setSprites(sprites);
		setXDirection(Direction.RIGHT);
		
		this.X_INITIAL_VELOCITY = xInitialVelocity;
		this.Y_INITIAL_VELOCITY = yInitialVelocity;
		this.X_ACCELERATION = xAcceleration;
		this.Y_ACCELERATION = yAcceleration;
		this.DUCKED_VELOCITY_LIMIT = duckedVelocityLimit;
		this.setXVelocityLimit(xVelocityLimit);
		this.setPreviousXVelocityLimit(xVelocityLimit);
		this.setXVelocity(0);
		this.setYVelocity(0);
		this.setDucking(false);
		this.setTimeSinceLastMove(2); // > 1
		this.setToEndDuck(false);
		this.setStillMoving(false);
		
		setTimer(0);
		
		this.setTimeToBeImmune(0);
		this.setWaterTimer(0);
		this.setMagmaTimer(0.2);
	
	}
	
	
	private final double DUCKED_VELOCITY_LIMIT;
	/**
	 * Variable registering the acceleration in the x direction that applies to all Mazub characters.
	 */
	private final double X_ACCELERATION; //in pixels per seconde kwadraat
	
	/**
	 * Variable registering the acceleration in the y direction that applies to all Mazub characters.
	 */
	private final double Y_ACCELERATION; //in pixels per seconde kwadraat
	/**
	 * Variable registering the initial velocity in the x direction of this Mazub charachter.
	 */
	private final double X_INITIAL_VELOCITY; //in pixels per seconde
	
	/**
	 * Variable registering the initial velocity in the y direction that applies to all Mazub characters.
	 */
	private final double Y_INITIAL_VELOCITY; //in pixels per seconde
	
	/**
	 * Variable registering the maximum allowed velocity in the x direction of this Mazub character.
	 */
	private double xVelocityLimit; //in pixels per seconde
	
	
	public World getMyWorld() {
		return myWorld;
	}
	public void setMyWorld(World myWorld) {
		assert getMyWorld() == null;
		this.myWorld = myWorld;
	}
	private World myWorld;

	public double getTimer() {
		return this.timer;
	}
	
	protected void setTimer(double time) {
		this.timer = time;
	}
	
	private double timer;
	
	
	private int hitPoints;
	private final int MAX_HITPOINTS;
	
	public int getMaxHitpoints() {
		return MAX_HITPOINTS;
	}
	
	public int getHitPoints() {
		return this.hitPoints;
	}
	
	protected void setHitPoints(int hitPoints) {
		if (hitPoints < 0)
			this.hitPoints = 0;
		else if (hitPoints > MAX_HITPOINTS)
			this.hitPoints = MAX_HITPOINTS;
		else
			this.hitPoints = hitPoints;
	}
	
	protected void addHitPoints(int hitPoints) {
		assert hitPoints >= 0;
		setHitPoints(getHitPoints() + hitPoints);
	}
	
	protected void substractHitPoints(int hitPoints) {
		assert hitPoints >= 0;
		setHitPoints(getHitPoints() - hitPoints);
	}
	
	public boolean isValidPosition(int x,int y) {
		//if (!( (x <= myWorld.getXLimit()) && (x >= 0) ))
			//return false;
		
		//if(! ((y <= myWorld.getYLimit()) && (y >= 0)))
			//return false;
		if (y<0)
			return false;
			
		int width = this.getWidth();
		int height = this.getHeight();
		for (int i = 1; i<width-1; i++) {
			if (x+i > myWorld.getXLimit())
				break;
			for (int j = 1; j<height-1; j++) {
				if (y+j > myWorld.getYLimit())
					break;
				if (( (myWorld.getObjectAt(x+i, y+j,getZ()) != this) && (myWorld.getObjectAt(x+i, y+j, getZ()) != null))
						|| (! (myWorld.getTerrainAt(x+i,y+j).isPassable())))
					return false;
			}
		}
		return true;
	}
	

	
	/**
	 * Return the x position of this game object.
	 */
	@Basic
	public double getX() {
		return this.x;
	}
	
	/**
	 * Return the y position of this game object.
	 */
	@Basic
	public double getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.Z;
	}
	

	
	protected void setPosition(double x, double y) {
		assert isValidPosition((int) x,(int) y);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Variable registering the x position of this game object.
	 */
	private double x;
	
	/**
	 * Variable registering the y position of this game object.
	 */
	private double y;
	
	private final int Z;
	
	public int[] getPosition() {
		int[] result = {(int) getX(), (int) getY()};
		return result;
	}
	
	private boolean isTerminated;
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	
	//insta wil zeggen object meteen verwijderen uit gamewereld in volgende advancetime
	public void terminate(boolean insta) {
		this.isTerminated = true;
		if (insta)
			setTimeSinceTermination(0.7);
		else
			setTimeSinceTermination(0);
		
	}
	
	private double timeSinceTermination;

	public double getTimeSinceTermination() {
		return timeSinceTermination;
	}
	public void setTimeSinceTermination(double timeSinceTermination) {
		this.timeSinceTermination = timeSinceTermination;
	}
	
	public void advanceTime(double duration) {
		handleInteraction(duration);
		if(getHitPoints() == 0)
			this.terminate(false);
		if (isTerminated()) {
			setTimeSinceTermination(getTimeSinceTermination() + duration);
			if (getTimeSinceTermination() > 0.6) {
				myWorld.removeObjectAt(getPosition()[0],getPosition()[1],getZ());
				if (this instanceof Mazub)
					myWorld.endGame();	
			}
		}
		
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

		 
		 if (getToEndDuck() == true)
			 endDuck();
		 setJumping(isValidPosition(getPosition()[0],getPosition()[1] - 1));
		 

		setTimeToBeImmune(getTimeToBeImmune()-duration);
	}
		
	public GameObject touches(Class className) {
		int[] pos = this.getPosition();
		int x = pos[0];
		int y = pos[1];
		int height = this.getHeight();
		int width = this.getWidth();
		
		for (int k = 0; k <= 1; k++) {
			for (int i = 0; i < width; i++) {
				if (className.isInstance(myWorld.getObjectAt(x + i, y, k)))
					return myWorld.getObjectAt(x + i, y, k);
				if	(className.isInstance(myWorld.getObjectAt(x+i, y+height-1, k)))
					return myWorld.getObjectAt(x+i, y+height-1, k); 
			}
			
			for (int j = 1; j < height-1; j++) {
				if (className.isInstance(myWorld.getObjectAt(x,y+j,k)))
					return myWorld.getObjectAt(x,y+j,k);
				if (className.isInstance(myWorld.getObjectAt(x+width-1,y+j,k)))
					return myWorld.getObjectAt(x+width-1,y+j,k);
			}
		}
		return null;
	}
	
	public boolean touches(TerrainType terrainType) {
		int[] pos = this.getPosition();
		int x = pos[0];
		int y = pos[1];
		int height = this.getHeight();
		int width = this.getWidth();
		for (int i = 0; i < width; i++) {
			if ((myWorld.getTerrainAt(x + i, y) == terrainType) || 
				(myWorld.getTerrainAt(x + i, y + height -1)) == terrainType)
				return true;
		}

		for (int j = 1; j < height-1; j++) {
			if ((myWorld.getTerrainAt(x, y + j) == terrainType) || 
					(myWorld.getTerrainAt(x + width -1, y + j)) == terrainType)
					return true;
		}
		return false;
		
	}
	
	//SPRITES
	
	
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
	 * @post	The new sprites of this Mazub character are equal to
	 * 			the given sprites.
	 * 		  | new.getSprites() == sprites
	 */
	@Raw
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	/**
	 * Array registering the sprites of this Mazub character.
	 */
	private Sprite[] sprites;
	
	/**
	 * @return Return the width of this Mazub character's current sprite.
	 * 		 | result == (this.getCurrentSprite().getWidth())
	 */
	public int getWidth() {
		return this.getCurrentSprite().getWidth();
	}
	
	/**
	 * @return Return the height of this Mazub charachter's currect sprite.
	 * 		 | result == (this.getCurrentSprite().getHeight())
	 */
	public int getHeight() {
		return this.getCurrentSprite().getHeight();
	}
	
	
	//COMMENTAAR AANPASSEN
	
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
	
	//MOVEMENT
	
	
	
	/**
	 * Check whether the given xInitialVelocity and xVelocityLimiy are valid for any Mazub character.
	 *  
	 * @param	xInitialVelocity
	 * 			The initial velocity in the x direction to be checked.
	 * @param	xVelocityLimit
	 * 			The velocity limit in the x direction to be checked.
	 * @return	True if and only if the given initial velocity is smaller than or equal to 
	 * 			the velocity limit in the x direction, and if
	 * 			the the given initial velocity is greater than or equal to 1.
	 * 		  | result ==
	 * 		  |    ( (xInitialVelocity <= xVelocityLimit)
	 * 		  |   && (xInitialVelocity >= 1) )
	 */
	public static boolean isValidXInitialVelocityAndXVelocityLimit(double xInitialVelocity, double xVelocityLimit) {
		return (xInitialVelocity <= xVelocityLimit) && (xInitialVelocity >= 1);
	}
	
	/**
	 * Return the x velocity limit of this Mazub character. 
	 * This velocity limit is positive if the direction of the Mazub is Direction.RIGHT
	 * and negative if the direction of the Mazub is Direcion.LEFT.
	 * 
	 * @return 	Return this Mazub character's velocity limit, adjusted for this Mazub's direction.
	 * 			| if (getXDirection() == Direction.RIGHT)
	 *			|	result == xVelocityLimit
	 *			| else
	 *			| 	result == -xVelocityLimit
	 * 
	 */
	public double getXVelocityLimit() {
		if (getXDirection() == Direction.RIGHT)
			return xVelocityLimit;
		else
			return -xVelocityLimit;
	}
	
	/**
	 * Return the initial velocity in the x direction of this Mazub character.
	 */
	@Basic @Immutable
	public double getXInitialVelocity() {
		return X_INITIAL_VELOCITY;
	}
	
	/**
	 * Return the initial velocity in the y direction of this Mazub character.
	 */
	@Basic @Immutable
	public double getYInitialVelocity() {
		return Y_INITIAL_VELOCITY;
	}
	
	/**
	 * Set the xVelocityLimit of this Mazub character to the given xVelocityLimit.
	 * 
	 * @param 	xVelocityLimit
	 * 			The new xVelocityLimit for this Mazub character.
	 * @pre		The given xVelocityLimit must be a valid xVelocityLimit for this Mazub character.
	 * 		  | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),xVelocityLimit)
	 * @post	The new xVelocityLimit of this Mazub character is equal to
	 * 			the given xVelocityLimit.
	 * 		  | new.getXVelocityLimit() == xVelocityLimit
	 */
	@Raw
	private void setXVelocityLimit(double xVelocityLimit) {
		assert isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),xVelocityLimit);
		this.xVelocityLimit = xVelocityLimit;
	}
	
	
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
	 * Variable registering the xVelocity of this Mazub character.
	 */
	private double xVelocity;
	
	/**
	 * Variable registering the yVelocity of this Mazub character.
	 */
	private double yVelocity;
	

	
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
	 * 
	 * Return this Mazub character's current acceleration in the x direction.
	 * 
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
	 * 
	 * @result 	If this Mazub character is jumping, return the opposite of the value stored as acceleration in the y direction.
	 * 			Otherwise, return 0.
	 * 			| if (isJumping())
	 * 			| 	then result == -Y_ACCELERATION
	 * 			| else
	 * 				result == 0
	 */
	public double getYAcceleration() {
		if (isJumping())
			return -Y_ACCELERATION;
		else
			return 0;
	}

	
	
	/**
	 * The given Mazub starts moving in the given x direction with initial velocity
	 * getXInitialVelocity() and with acceleration getXAcceleration().
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
		setStillMoving(true);
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
	 * @post This Mazub character's velocity in the x direction is 0.
	 * 		| new.getXVelocity() == 0
	 */
	public void endMove() {
		setStillMoving(false);
		setXVelocity(0);
	}
	
	/**
	 * The given Mazub character starts jumping with initial velocity
	 * Y_INITIAL_VELOCITY and with acceleration getYAcelleration().
	 * 
	 * @post	The new yVelocity of this mazub character is equal to Y_INITIAL_VELOCITY.
	 * 		  | new.getYVelocity() == Y_INITIAL_VELOCITY
	 * @throws 	JumpingException
	 * 			This Mazub character cannot start jumping because it is already jumping.
	 * 		  | isJumping()
	 */
	public void startJump() throws JumpingException {
		if (isJumping() || ! canJump())
			throw new JumpingException("Already jumping!", this);
		setYVelocity(getYInitialVelocity());
	}
	
	public boolean canJump() {
		return (getPosition()[1] == 0 ||
				( !(isValidPosition(getPosition()[0],getPosition()[1]-1))));
	}
	
	/**
	 * The given Mazub stops jumping. Its velocity is set to 0 if it was greater than 0.
	 * 
	 * @post	The new yVelocity of this Mazub character is equal to 0 if yVelocity is greater than 0.
	 * 		  | if (getYVelocity() > 0)
	 * 		  |		new.getYVelocity() == 0
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
	 * Check whether this Mazub character is jumping.
	 * 
	 * @return 	True if and only if the current y position is greater than 0.
	 * 		  | result == (getY() > 0)
	 */
	public boolean isJumping() {
		return isJumping;
				
	}
	
	private void setJumping(boolean status) {
		this.isJumping = status;
	}
	
	private boolean isJumping;
	
	/**
	 * The given Mazub character starts ducking. Its velocity limit in the x direction
	 * is set to DUCKED_VELOCITY_LIMIT. Its new ducked state is equal to True.
	 * 
	 * @post	The new previousXVelocityLimit of this Mazub character is equal to the old xVelocityLimit.
	 * 		  | new.getPreviousXVelocityLimit() == abs(this.getXVelocityLimit())
	 * @post 	The new xVelocityLimit of this Mazub character is equal to DUCKED_VELOCITY_LIMIT.
	 * 		  | abs(new.getXVelocityLimit()) == DUCKED_VELOCITY_LIMIT
	 * @post	If this Mazub character has a velocity in the x direction that is greater than 
	 * 			the new xVelocityLimit, then the new velocity in the x direction is set to the new
	 * 			xVelocityLimit.
	 * 		  | if (abs(this.getXVelocity()) > abs(new.getXVelocityLimit()))
	 * 		  |		new.getXVelocity() == (signum(this.getXVelocity())) * (new.getXVelocityLimit())
	 * @post	The new ducked state of this Mazub character is equal to true.
	 * 		  | new.isDucking() == true
	 * @throws	IllegalStateExpcetion
	 * 			This Mazub character cannot start ducking because it is already ducking.
	 * 		  | isDucking()
	 * 
	 */
	public void startDuck() throws IllegalStateException {
		if (isDucking() && getToEndDuck() == false)
			throw new IllegalStateException("Already ducking!");
		if (getToEndDuck() == false) {
			setPreviousXVelocityLimit(abs(getXVelocityLimit()));
			setXVelocityLimit(DUCKED_VELOCITY_LIMIT);
		}
		if(abs(getXVelocity()) > abs(getXVelocityLimit()))
			setXVelocity(signum(getXVelocity())*abs(getXVelocityLimit()));
		setDucking(true);
		setToEndDuck(false);
	}
	
	/**
	 * The given Mazub character stops ducking. Its velocity limit in the x direction
	 * is reset to the previous xVelocityLimit. 
	 * 
	 * @post	The new xVelocityLimit of this Mazub character is equal to the previousXVelocityLimit.
	 * 		  | new.getXVelocityLimit() == this.getPreviousXVelocityLimit()
	 * @post	The new ducked state of this Mazub character is equal to false.
	 * 		  | new.isDucking() == false
	 * @throws	IllegalStateException
	 * 			This Mazub character cannot stop ducking because it is not ducking.
	 * 		  | ! isDucking()
	 */
	public void endDuck() throws IllegalStateException {
		if (! isDucking())
			throw new IllegalStateException("Not yet ducking!");
		if (canEndDuck()) {
			setXVelocityLimit(getPreviousXVelocityLimit());
			setDucking(false);
			setToEndDuck(false);
		}
		else {
			setToEndDuck(true);
		}	
		this.terminate(true);
	}
	
	private boolean toEndDuck;
	
	
	public boolean getToEndDuck() {
		return toEndDuck;
	}

	private void setToEndDuck(boolean toEndDuck) {
		this.toEndDuck = toEndDuck;
	}
	
	public boolean canEndDuck() {
		boolean previous = isDucking();
		setDucking(false);
		if (isValidPosition(getPosition()[0],getPosition()[1])) {
			setDucking(previous);
			return true;
		}
		setDucking(previous);
		return false;
	}
	
	

	/**
	 * Return the ducked state of this Mazub character.
	 */
	@Basic
	public boolean isDucking() {
		return isDucking;
	}
	
	/**
	 * Set the ducked state of this Mazub character to the given state.
	 * 
	 * @param 	state
	 * 			The new state for this Mazub character.
	 * @post	The new ducked state of this Mazub character is equal to
	 * 			the given state.
	 * 		  | new.isDucking() == state
	 */
	@Raw
	private void setDucking(boolean state) {
		isDucking = state;
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
	 * 
	 * @param 	previousXVelocityLimit
	 * 			The new previousXVelocityLimit for this Mazub character.
	 * @pre		The given previousXVelocityLimit should be a valid xVelocityLimit for this Mazub character.
	 * 		  | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),previousXVelocityLimit)
	 * @post	The new previousXVelocityLimit of this Mazub character is equal to
	 * 			the given previousXVelocityLimit.
	 * 		  | new.getPreviousXVelocityLimit() == previousXVelocityLimit
	 */
	@Raw
	private void setPreviousXVelocityLimit(double previousXVelocityLimit) {
		assert isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),previousXVelocityLimit);
		this.previousXVelocityLimit = previousXVelocityLimit;
	}
	
	/**
	 * Variable registering the previous velocity limit in the x direction.
	 */
	private double previousXVelocityLimit;
	/**
	 * Variable registering the ducked state of this Mazub character.
	 */
	private boolean isDucking;
	/**
	 * Variable registering the velocity limit in the x direction while ducking.
	 */

	
	


	/**
	 * Return the timeSinceLastMove of this Mazub character.
	 * The timeSinceLastMove of a Mazub character expresses the time that has
	 * passed since the character made its last horizontal move.
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
	private void setTimeSinceLastMove(double timeSinceLastMove) {
		assert timeSinceLastMove >= 0;
		this.timeSinceLastMove = timeSinceLastMove;
	}
	
	/**
	 * Variable registering the time since the last move of this Mazub character.
	 */
	private double timeSinceLastMove;


	/**
	 * Return the timeSinceLastRunningImage of this Mazub character.
	 */
	@Basic
	public double getTimeSinceLastRunningImage() {
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
	 * Check whether this Mazub character is running normally. A Mazub character
	 * is running normally if it is ducking nor jumping, and if its velocity is not equal to 0.
	 * 
	 * @return	True if and only if this Mazub character is ducking nor jumping, and if its velocity
	 * 			is not equal to 0.
	 * 		  | result ==
	 * 		  |    ( ! isDucking())
	 * 		  |   && (! isJumping())
	 * 		  |   && (abs(getXVelocity()) > 0) )
	 */
	public boolean isRunningNormally() {
		return ( (! isDucking()) && (! isJumping()) && (abs(getXVelocity()) > 0));
	}

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
		if (isStillMoving() && getXVelocity() == 0)
			startMove(getXDirection());
		double xCurrent = getX();
		double vCurrent = getXVelocity();
		double xNew = xCurrent;
		double vNew = vCurrent+duration*getXAcceleration();
		if(abs(vNew) > abs(getXVelocityLimit())) {
			double timeBeforeVelocityLimit = (getXVelocityLimit() - vCurrent)/getXAcceleration();
			xNew += getXVelocity()*timeBeforeVelocityLimit+0.5*getXAcceleration()*pow(duration,2);
			vNew = getXVelocityLimit();
			xNew += vNew*(duration - timeBeforeVelocityLimit);
			
		}
		else {
			xNew = xCurrent + (getXVelocity()*duration + 0.5*getXAcceleration()*pow(duration,2));
		}
				
		if(! isValidPosition((int) xNew,getPosition()[1])) {
			vNew = 0;
			xNew = xCurrent;
			}
		setPosition(xNew,getPosition()[1]);
		setXVelocity(vNew);
	}
	
	
	private boolean isStillMoving;

	public boolean isStillMoving() {
		return isStillMoving;
	}

	public void setStillMoving(boolean isStillMoving) {
		this.isStillMoving = isStillMoving;
	}

	/**
	 * Move this Mazub character in the y direction for the given duration.
	 * 
	 * @param 	duration
	 * 			The duration for which the Mazub character should move in the y direction.
	 * @post  	If duration > 0, the Mazub character is either not jumping or its new y-position is smaller than or equal to
	 * 		  	getY()+getYVelocity*duration+0.5*getYAcceleration()*pow(duration,2.0).
	 * 		  | if duration > 0
	 * 		  | 	then (new.getY() <= getY()+getYVelocity*duration+0.5*getYAcceleration()*pow(duration,2.0)) 
	 * 		  |				|| (! isJumping())
	 * @post	If duration > 0, the yVelocity has decreased or the character is not jumping.
	 * 		  | if duration > 0
	 * 		  | 	then new.getYVelocity() < getYVelocity() || (! isJumping()) 
	 */
	private void moveY(double duration) {
		if (duration <= 0)
			return;
		double yCurrent = getY();
		double vCurrent = getYVelocity();
		double yNew;
		
		double vNew = vCurrent + duration*getYAcceleration();
		
		yNew = yCurrent + vCurrent*duration + 0.5*getYAcceleration()*pow(duration,2.0);
		
		if(! isValidPosition(getPosition()[0],(int) yNew)) {
			yNew = yCurrent;
			vNew = 0;
		}
		setPosition(getPosition()[0],yNew);
		setYVelocity(vNew);
	}
	
	
	
	
	private double timeToBeImmune;


	public double getTimeToBeImmune() {
		return timeToBeImmune;
	}

	protected void setTimeToBeImmune(double timeToBeImmune) {
		if (timeToBeImmune < 0)
			this.timeToBeImmune = 0;
		this.timeToBeImmune = timeToBeImmune;
		
	}


	private double waterTimer;
	public double getWaterTimer() {
		return waterTimer;
	}
	protected void setWaterTimer(double waterTimer) {
		this.waterTimer = waterTimer;
	}
	
	private double magmaTimer;
	public double getMagmaTimer() {
		return magmaTimer;
	}
	protected void setMagmaTimer(double magmaTimer) {
		this.magmaTimer = magmaTimer;
	}
	
	
	
	
}
