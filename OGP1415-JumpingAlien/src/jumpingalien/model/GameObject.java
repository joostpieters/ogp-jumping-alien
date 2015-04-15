/**
 * 
 */
package jumpingalien.model;

import static java.lang.Math.*;
import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of game objects involving several properties.
 * 
 * @invar	Each game object can have its position as its position.
 * 		  | canHaveAsPosition(getPosition()[0],getPosition()[1])
 * @invar	Each game object can have its velocity as its velocity.
 * 		  | canHaveAsXVelocity(getXVelocity())
 * @invar	Each game object can have its initial velocity as its initial velocity in the x direction.
 * 			Each game object can have its velocity limit as its velocity limit in the x direction.
 * 		  | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(), getXVelocityLimit())
 * @invar	Each game object can have its acceleration as its acceleration in the x direction.
 * 		  | canHaveAsXAcceleration(getXAcceleration())
 * @invar	Each game object has Direction.LEFT or Direction.RIGHT as its direction.
 * 		  | (getXDirection() == Direction.LEFT) || (getXDirection() == Direction.RIGHT)
 * @invar	Each game object must have a proper world attached to it.
 * 		  | hasProperWorld();
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public abstract class GameObject {

	/**
	 * Initialize this new game object with the given parameters.
	 * 
	 * @param world
	 * 
	 * @param x
	 *		  The initial x position for this new game object. 
	 * @param y
	 * 		  The initial y position for this new game object. 
	 * @param initialHitPoints
	 * 		  The initial number of hitpoints for this new game object.
	 * @param maxHitPoints
	 * 		  The maximum number of hitpoints this new game object can have.
	 * @param sprites
	 * 		  The series of initial sprites for this new game object.
	 * @param xInitialVelocity
	 * 		  The initial velocity in the x direction for this new game object.
	 * @param yInitialVelocity
	 *		  The initial velocity in the y direction for this new game object.
	 * @param xVelocityLimit
	 * 		  The velocity limit in the x direction for this new game object.
	 * @param duckedVelocityLimit
	 * 		  The velocity limit in the x direction while the game object is ducked for this new game object.
	 * @param xAcceleration
	 * 		  The acceleration in the x directions for this new game object.
	 * @param yAcceleration
	 * 		  The acceleration in the y directions for this new game object.
	 * @param solid
	 * 		  The solid state for this new game object.
	 * 
	 * @pre	  | canHaveAsPosition((int) x, (int) y)
	 * @pre	  | sprites != null
	 * @pre	  | isValidXInitialVelocityAndXVelocityLimit(xInitialVelocity, xVelocityLimit)
	 * 
	 * @effect | setPosition(x,y)
	 * @effect | setXVelocityLimit(double xVelocityLimit)
	 * @effect | setSprites(sprites)
	 * @effect | setHitpoints(initialHitPoints)
	 * @effect | setMyWorld(world)
	 * @post   | new.X_ACCELERATION = xAcceleration
	 * @post   | new.Y_ACCELERATION = yAcceleration
	 * @post   | new.X_INITIAL_VELOCITY = xInitialVelocity
	 * @post   | new.Y_INITIAL_VELOCITY = yInitialVelocity
	 * @post   | new.getXInitialVelocity() == xInitialVelocity
	 * @post   | new.isSolid == solid
	 * @post   | new.getDuckedVelocityLimit() == duckedVelocityLimit
	 * 
	 * 		  
	 */
	@Raw
	public GameObject(World world, double x, double y, 
							int initialHitPoints, int maxHitPoints, Sprite[] sprites,
								double xInitialVelocity, double yInitialVelocity,
									double xVelocityLimit,  double duckedVelocityLimit,
										double xAcceleration, double yAcceleration, boolean solid) {
		
		assert sprites != null;
		assert isValidXInitialVelocityAndXVelocityLimit(xInitialVelocity, xVelocityLimit);
		
		setMyWorld(world);
		setPosition(x,y);
		MAX_HITPOINTS = maxHitPoints;
		setHitPoints(initialHitPoints);
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
		
		this.setTimeToBeImmune(0);
		this.setWaterTimer(0);
		this.setMagmaTimer(0.2);
		this.setAirTimer(0);
		
		this.SOLID = solid;
		
		assert canHaveAsXAcceleration(xAcceleration);
		assert canHaveAsXVelocity(xVelocity);
		if (getMyWorld() != null)
			assert canHaveAsPosition((int) x, (int) y);
	
	}
	
	//geen commentaar nodig
	public abstract Sprite getCurrentSprite();
	
	/**
	 * Handle the interaction of this game object with other game objects.
	 * 
	 * @param 	duration
	 * 			The duration for which the interaction should be handled.
	 */
	public abstract void handleInteraction(double duration);
	
	/**
	 * Variable registering the velocity in the x direction while ducked of this game object.
	 */
	private final double DUCKED_VELOCITY_LIMIT;
	
	/**
	 * Return the ducked velocity limit of this game object.
	 */
	@Basic @Immutable
	public double getDuckedVelocityLimit() {
		return DUCKED_VELOCITY_LIMIT;
	}

	/**
	 * Variable registering the acceleration in the x direction that applies to this game object.
	 */
	private final double X_ACCELERATION; //in pixels per seconde kwadraat
	/**
	 * Variable registering the acceleration in the y direction that applies to this game object.
	 */
	private final double Y_ACCELERATION; //in pixels per seconde kwadraat
	/**
	 * Variable registering the initial velocity in the x direction that applies to this game object.
	 */
	private final double X_INITIAL_VELOCITY; //in pixels per seconde
	
	/**
	 * Variable registering the initial velocity in the y direction that applies to this game object.
	 */
	private final double Y_INITIAL_VELOCITY; //in pixels per seconde
	
	/**
	 * Variable registering the maximum allowed velocity in the x direction that applies to this game object.
	 */
	private double xVelocityLimit; //in pixels per seconde
	
	@Immutable @Basic
	/**
	 * Return the solid state of this game object.
	 */
	public boolean isSolid() {
		return SOLID;
	}
	
	/**
	 * Variable registering the solid state of this game object.
	 */
	private final boolean SOLID;
	
	/**
	 * Return the world that belongs to this game object.
	 */
	@Basic
	public World getMyWorld() {
		return myWorld;
	}
	/**
	 * 
	 * @param myWorld
	 * 		  The new world for this game object.
	 * @pre	  |  getMyWorld() == null
	 * @post  |  new.getMyWorld() == myWorld
	 */
	public void setMyWorld(World myWorld) {
		assert getMyWorld() == null;
		this.myWorld = myWorld;
	}
	/**
	 * Variable registering the world of this game object.
	 */
	private World myWorld;
		
	/**
	 * Variable registering the number of hitpoints of this game object.
	 */
	private int hitPoints;
	/**
	 * Variable registering the maximum number of hitpoints of this game object.
	 */
	private final int MAX_HITPOINTS;
	
	/**
	 * Return the maximum number of hitpoints of this game object.
	 */
	@Immutable @Basic
	public int getMaxHitpoints() {
		return MAX_HITPOINTS;
	}
	
	/**
	 * Return the number of hitpoints of this game object.
	 */
	@Basic
	public int getHitPoints() {
		return this.hitPoints;
	}
	
	/**
	 * Set the number of hitpoints of this game object to the given number of hitpoints.
	 * 
	 * @param hitPoints
	 * 		  The new number of hitpoints.
	 * @post  | if (hitpoints < 0)
	 * 		  |		new.getHitPoints == 0
	 *		  |	else if (hitPoints > getMaxHitpoints())
	 *		  |		new.getHitPoints = getMaxHitpoints();
	 *		  | else
	 *		  |		new.getHitPoints = hitPoints;
	 */
	protected void setHitPoints(int hitPoints) {
		if (hitPoints < 0)
			this.hitPoints = 0;
		else if (hitPoints > getMaxHitpoints())
			this.hitPoints = getMaxHitpoints();
		else
			this.hitPoints = hitPoints;
	}
	
	/**
	 * Add the given number of hitpoints to the current number of hitpoints.
	 * 
	 * @param 	hitPoints
	 * 		  	The number of hitpoints to be added to the current number of hitpoints.
	 * @pre	  	| hitpoints >= 0
	 * @effect 	| setHitpoints(getHitPoints() + hitPoints)
	 */
	protected void addHitPoints(int hitPoints) {
		assert hitPoints >= 0;
		setHitPoints(getHitPoints() + hitPoints);
	}
	
	/**
	 * Subtract the given number of hitpoints of the current number of hitpoints.
	 * @param	hitPoints
	 * 			The number of hitpoints to be subtracted of the current number of hitpoints.
	 * @pre		| hitpoints >= 0
	 * @effect	| setHitPoints(getHitPoints() - hitPoints)
	 */
	protected void substractHitPoints(int hitPoints) {
		assert hitPoints >= 0;
		setHitPoints(getHitPoints() - hitPoints);
	}
	
	/**
	 * Check whether this game object can have the given position as its position.
	 * 
	 * @param x
	 * 		  The x position to be checked.
	 * @param y
	 * 		  The y position to be checked.
	 * @return 
	 */
	public boolean canHaveAsPosition(int x,int y) {
		if (y<0)
			return false;
		if(! isSolid())
			return true;
			
		int width = this.getWidth();
		int height = this.getHeight();
		
		for(GameObject obj : myWorld.getGameObjects()) {
			if(obj != this) {
				if(rectanglesIntersect(obj.getPosition()[0]+1,obj.getPosition()[1]+1,obj.getWidth()-1,obj.getHeight()-1,
					x+1,y+1,this.getWidth()-1,this.getHeight()-1))
						if(obj.isSolid())
							return false;
			}
			
		}
			
		for (int i = 1; i < width-1; i++) {
			if (! (myWorld.getTerrainAt(x + i, y+1).isPassable() && 
				myWorld.getTerrainAt(x + i, y + height - 2).isPassable()))
				return false;
		}

		for (int j = 2; j < height-2; j++) {
			if (! (myWorld.getTerrainAt(x+1, y + j).isPassable() &&
					myWorld.getTerrainAt(x + width - 2, y + j).isPassable()))
					return false;
		}
		
		return true;
	}
	
	
	/**
	 * Check whether two rectangles intersect.
	 * 
	 * @param x1
	 * 		  The x position of the first rectangle
	 * @param y1
	 * 		  The y position of the first rectangle
	 * @param width1
	 * 		  The width of the first rectangle
	 * @param height1
	 * 		  The height of the first rectangle
	 * @param x2
	 * 		  The x position of the second rectangle
	 * @param y2
	 * 		  The y position of the second rectangle
	 * @param width2
	 * 		  The width of the second rectangle
	 * @param height2
	 * 		  The height of the second rectangle
	 * @return 
	 */
	public static boolean rectanglesIntersect(int x1, int y1, int width1, int height1,
										int x2, int y2, int width2, int height2) {
		int[][] corners1 = {{x1,y1},{x1,y1+height1-1},{x1+width1-1,y1+height1-1},{x1+width1-1,y1}};
		int[][] corners2 = {{x2,y2},{x2,y2+height2-2},{x2+width2-2,y2+height2-2},{x2+width2-2,y2}};
		
		for(int i = 0; i < corners1.length; i++) {
			if (pointInRectangle(corners1[i][0], corners1[i][1], x2, y2, width2, height2))
				return true;
			if (pointInRectangle(corners2[i][0], corners2[i][1], x1, y1, width1, height1))
				return true;
		}
		
		return false;
	}
	
	public static boolean pointInRectangle(int x,int y, int RectX, int RectY, int width, int height) {
		if(RectX <= x && x <= RectX+width-1 && RectY <= y && y <= RectY+height-1)
			return true;
		return false;
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

	/**
	 * 
	 * @param x
	 * 		  The new x position of this game object.
	 * @param y
	 * 		  The new y position of this game object.
	 * @post  | new.getX() == x && new.getY() == y
	 */
	protected void setPosition(double x, double y) {
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
		if (isTerminated())
			return;
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
		if(duration > 0.2 || duration < 0) return; //???????????????
		
		handleInteraction(duration);
		
		double v_norm = sqrt(pow(getXVelocity(),2)+pow(getYVelocity(),2))/100;
		double a_norm = sqrt(pow(getXAcceleration(),2)+pow(getYAcceleration(),2))/100;
		double timeSlice;
		if(v_norm != 0 || a_norm != 0)
			timeSlice = 0.01 / (v_norm+a_norm*duration);
		else
			timeSlice = duration;
		double fragments = ceil(duration/timeSlice);
		double adjustedDuration = duration/fragments;
		
		
		
		if(getHitPoints() == 0)
			this.terminate(false);
		if (isTerminated()) {
			setTimeSinceTermination(getTimeSinceTermination() + duration);
			if (getTimeSinceTermination() > 0.6) {
				myWorld.removeObject(this);
				if (this instanceof Mazub)
					myWorld.endGame();	
			}
		}
		
		for(int i = 0; i < fragments; i++) {
			
			moveX(adjustedDuration);
			moveY(adjustedDuration);
		}
		
		
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
		 setJumping(canHaveAsPosition(getPosition()[0],getPosition()[1] - 1));
		 

		setTimeToBeImmune(getTimeToBeImmune()-duration);
		
	}
		
	public GameObject touches(Class<?> className) {
		
		for(GameObject obj : myWorld.getGameObjects()) {
			if(obj != this && className.isInstance(obj)) {
				if(rectanglesIntersect(obj.getPosition()[0],obj.getPosition()[1],obj.getWidth(),obj.getHeight(),
					this.getPosition()[0],this.getPosition()[1],this.getWidth(),this.getHeight()))
							return obj;
			}
			
		}
		return null;
	
//		for(GameObject obj: myWorld.getGameObjects()) {
//			if(className.isInstance(obj) && obj != this) {
//					if(obj.overlapsWith(this))
//						return obj
//				}	
//			}
//			
//		}
			
//		int[] pos = this.getPosition();
//		int x = pos[0];
//		int y = pos[1];
//		int height = this.getHeight();
//		int width = this.getWidth();
//		
//		for (int k = 0; k <= 1; k++) {
//			for (int i = 0; i < width; i++) {
//				if (className.isInstance(myWorld.getObjectAt(x + i, y, k)))
//					return myWorld.getObjectAt(x + i, y, k);
//				if	(className.isInstance(myWorld.getObjectAt(x+i, y+height-1, k)))
//					return myWorld.getObjectAt(x+i, y+height-1, k); 
//			}
//			
//			for (int j = 1; j < height-1; j++) {
//				if (className.isInstance(myWorld.getObjectAt(x,y+j,k)))
//					return myWorld.getObjectAt(x,y+j,k);
//				if (className.isInstance(myWorld.getObjectAt(x+width-1,y+j,k)))
//					return myWorld.getObjectAt(x+width-1,y+j,k);
//			}
//		}
		//return null;
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
	 * Return the sprites of this game object.
	 */
	@Basic
	public Sprite[] getSprites() {
		return sprites;
	}
	
	/**
	 * Set the sprites for this game object to the given sprites.
	 * 
	 * @param 	sprites
	 * 			The new sprites for this game object.
	 * @post	The new sprites of this game object are equal to
	 * 			the given sprites.
	 * 		  | new.getSprites() == sprites
	 */
	@Raw
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	/**
	 * Array registering the sprites of this game object.
	 */
	private Sprite[] sprites;
	
	/**
	 * @return Return the width of this game object's current sprite.
	 * 		 | result == (this.getCurrentSprite().getWidth())
	 */
	public int getWidth() {
		return this.getCurrentSprite().getWidth();
	}
	
	/**
	 * @return Return the height of this game object's currect sprite.
	 * 		 | result == (this.getCurrentSprite().getHeight())
	 */
	public int getHeight() {
		return this.getCurrentSprite().getHeight();
	}
	
	
	//COMMENTAAR AANPASSEN
	
	/**
	 * Return the xDirection of this game object.
	 */
	@Basic
	public Direction getXDirection() {
		return this.xDirection;
	}
	
	/**
	 * Set the xDirection for this game object to the given xDirection.
	 * 
	 * @param 	xDirection
	 * 			The new xDirection for this game object.
	 * @pre		The given direction should be Direction.LEFT or Direction.RIGHT.
	 * 		  | (xDirection == Direction.LEFT) || (xDirection == Direction.RIGHT)
	 * @post	The new xDirection of this game object is equal to
	 * 			the given xDirection.
	 * 		  | new.getXDirection() == xDirection
	 */
	@Raw
	private void setXDirection(Direction xDirection) {
		assert ((xDirection == Direction.LEFT) || (xDirection == Direction.RIGHT));
		this.xDirection = xDirection;
	}

	/**
	 * Variable registering the x direction of this game object. The x direction
	 * is Direction.LEFT or Direction.RIGHT.
	 */
	private Direction xDirection;
	
	/**
	 * An enumeration introducing two directions used to express the x direction 
	 * of a game object.
	 */
	public static enum Direction {
		LEFT, RIGHT;
	}
	
	//MOVEMENT
	
	
	
	/**
	 * Check whether the given xInitialVelocity and xVelocityLimiy are valid for any game object.
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
	 * Return the x velocity limit of this game object. 
	 * This velocity limit is positive if the direction of the game object is Direction.RIGHT
	 * and negative if the direction of the game object is Direcion.LEFT.
	 * 
	 * @return 	Return this game object's velocity limit, adjusted for this game object's direction.
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
	 * Return the initial velocity in the x direction of this game object.
	 */
	@Basic @Immutable
	public double getXInitialVelocity() {
		return X_INITIAL_VELOCITY;
	}
	
	/**
	 * Return the initial velocity in the y direction of this game object.
	 */
	@Basic @Immutable
	public double getYInitialVelocity() {
		return Y_INITIAL_VELOCITY;
	}
	
	
	
	/**
	 * Set the xVelocityLimit of this game object to the given xVelocityLimit.
	 * 
	 * @param 	xVelocityLimit
	 * 			The new xVelocityLimit for this game object.
	 * @pre		The given xVelocityLimit must be a valid xVelocityLimit for this game object.
	 * 		  | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),xVelocityLimit)
	 * @post	The new xVelocityLimit of this game object is equal to
	 * 			the given xVelocityLimit.
	 * 		  | new.getXVelocityLimit() == xVelocityLimit
	 */
	@Raw
	private void setXVelocityLimit(double xVelocityLimit) {
		assert isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),xVelocityLimit);
		this.xVelocityLimit = xVelocityLimit;
	}
	
	
	/**
	 * Check whether this game object can have the given xVelocity as its
	 * velocity in the x direction.
	 * 
	 * @param 	xVelocity
	 * 			The velocity in the x direction to be checked.
	 * @return	True if and only if the given velocity is 0 or if
	 * 			the absolute value of the given velocity is greater 
	 * 			than or equal to the initial velocity in the x direction and the 
	 * 			absolute value of the given velocity is smaller than or equal to the
	 * 			velocity limit in the x direction of this game object.
	 * 		  | result == 
	 * 		  |    (  (xVelocity == 0) 
	 * 		  |   || ( (abs(xVelocity) >= getXInitialVelocity())
	 *		  |        && (abs(xVelocity) <= getXVelocityLimit()) ) )
	 */
	public boolean canHaveAsXVelocity(double xVelocity) {
		return (xVelocity == 0) || (abs(xVelocity) >= abs(getXInitialVelocity())) && (abs(xVelocity) <= abs(getXVelocityLimit()));
	}
	
	/**
	 * Return the xVelocity of this game object.
	 */
	@Basic
	public double getXVelocity() {
		return this.xVelocity;
	}
	
	/**
	 * Return the yVelocity of this game object.
	 */
	@Basic
	public double getYVelocity() {
		return this.yVelocity;
	}
	
	/**
	 * Set the xVelocity of this game object to the given xVelocity.
	 * 
	 * @param 	xVelocity
	 * 			The new xVelocity for this game object.
	 * @pre		The given xVelocity must be a valid xVelocity for this game object.
	 * 		  | canHaveAsXVelocity(xVelocity)
	 * @post	The new xVelocity of this game object is equal to
	 * 			the given xVelocity.
	 * 		  | new.getXVelocity() == xVelocity
	 */
	@Raw
	private void setXVelocity(double xVelocity) {
		assert canHaveAsXVelocity(xVelocity);
		this.xVelocity = xVelocity;
	}
	
	/**
	 * Set the yVelocity of this game object to the given yVelocity.
	 * 
	 * @param 	yVelocity
	 * 			The new yVelocity for this game object.
	 * @post	The new yVelocity of this game object is equal to
	 * 			the given yVelocity.
	 * 		  | new.getYVelocity() == yVelocity
	 */
	@Raw
	protected void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	/**
	 * Variable registering the xVelocity of this game object.
	 */
	private double xVelocity;
	
	/**
	 * Variable registering the yVelocity of this game object.
	 */
	private double yVelocity;
	

	
	/**
	 * Check whether this game object can have the given xAcceleration as
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
		return (signum(xAcceleration) == signum(getXVelocity()) || getXVelocity() == 0) ;
	}
	
	/**
	 * 
	 * Return this game object's current acceleration in the x direction.
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
	 * Return the acceleration in the y direction of this game object.
	 * 
	 * @result 	If this game object is jumping, return the opposite of the value stored as acceleration in the y direction.
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
	 * The given game object starts moving in the given x direction with initial velocity
	 * getXInitialVelocity() and with acceleration getXAcceleration().
	 * 
	 * @param 	direction
	 * 			The direction in which the game object should move.
	 * @pre		The given direction should be Direction.LEFT or Direction.RIGHT.
	 * 		  | (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @post	The new xDirection of this game object is equal to the given direction.
	 * 		  | new.getXDirection() == direction
	 * @post	The new xVelocity of this game object is equal to getXInitialVelocity() if
	 * 			the direction of this game object is Direction.RIGHT or equal to 
	 * 			- getXInitialVelocity() if the direction of this game object is Direction.LEFT.
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
	 * The given game object stops moving in the x direction. 
	 * Its velocity in the x direction is set to 0.
	 * 
	 * @post This game object's velocity in the x direction is 0.
	 * 		| new.getXVelocity() == 0
	 */
	public void endMove() {
		setStillMoving(false);
		setXVelocity(0);
	}
	
	/**
	 * The given game object starts jumping with initial velocity
	 * Y_INITIAL_VELOCITY and with acceleration getYAcelleration().
	 * 
	 * @post	The new yVelocity of this game object is equal to Y_INITIAL_VELOCITY.
	 * 		  | new.getYVelocity() == Y_INITIAL_VELOCITY
	 * @throws 	JumpingException
	 * 			This game object cannot start jumping because it is already jumping.
	 * 		  | isJumping()
	 */
	public void startJump() throws JumpingException {
		if (/*isJumping() || */ ! canJump())
			throw new JumpingException("Already jumping!", this);
		setYVelocity(getYInitialVelocity());
	}
	
	public boolean canJump() {
		return (getPosition()[1] == 0 ||
				( !(canHaveAsPosition(getPosition()[0],getPosition()[1]-1))));
	}
	
	/**
	 * The given game object stops jumping. Its velocity is set to 0 if it was greater than 0.
	 * 
	 * @post	The new yVelocity of this game object is equal to 0 if yVelocity is greater than 0.
	 * 		  | if (getYVelocity() > 0)
	 * 		  |		new.getYVelocity() == 0
	 * @throws 	JumpingException
	 * 			This game object cannot stop jumping because it is not jumping.
	 * 		  | ! isJumping()
	 */
	public void endJump() throws JumpingException {
		if (!isJumping())
			throw new JumpingException("Not yet jumping!", this);
		if (getYVelocity() > 0)
			setYVelocity(0);
	}
	
	/**
	 * Check whether this game object is jumping.
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
	 * The given game object starts ducking. Its velocity limit in the x direction
	 * is set to the ducked velocity limit. Its new ducked state is equal to true.
	 * 
	 * @post	The new previousXVelocityLimit of this game object is equal to the old xVelocityLimit.
	 * 		  | new.getPreviousXVelocityLimit() == abs(this.getXVelocityLimit())
	 * @post 	The new xVelocityLimit of this game object is equal to the ducked velocity limit.
	 * 		  | abs(new.getXVelocityLimit()) == getDuckedVelocityLimit()
	 * @post	If this game object has a velocity in the x direction that is greater than 
	 * 			the new xVelocityLimit, then the new velocity in the x direction is set to the new
	 * 			xVelocityLimit.
	 * 		  | if (abs(this.getXVelocity()) > abs(new.getXVelocityLimit()))
	 * 		  |		new.getXVelocity() == (signum(this.getXVelocity())) * (new.getXVelocityLimit())
	 * @post	The new ducked state of this game object is equal to true.
	 * 		  | new.isDucking() == true
	 * @throws	IllegalStateExpcetion
	 * 			This game object cannot start ducking because it is already ducking.
	 * 		  | isDucking()
	 * 
	 */
	public void startDuck() throws IllegalStateException {
		if (isDucking() && getToEndDuck() == false)
			throw new IllegalStateException("Already ducking!");
		if (getToEndDuck() == false) {
			setPreviousXVelocityLimit(abs(getXVelocityLimit()));
			setXVelocityLimit(getDuckedVelocityLimit());
		}
		if(abs(getXVelocity()) > abs(getXVelocityLimit()))
			setXVelocity(signum(getXVelocity())*abs(getXVelocityLimit()));
		setDucking(true);
		setToEndDuck(false);
	}
	
	/**
	 * The given game object stops ducking. Its velocity limit in the x direction
	 * is reset to the previous xVelocityLimit. 
	 * 
	 * @post	The new xVelocityLimit of this game object is equal to the previousXVelocityLimit.
	 * 		  | new.getXVelocityLimit() == this.getPreviousXVelocityLimit()
	 * @post	The new ducked state of this game object is equal to false.
	 * 		  | new.isDucking() == false
	 * @throws	IllegalStateException
	 * 			This game object cannot stop ducking because it is not ducking.
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
		//this.terminate(true); //Staat hier niks te doen?
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
		if (canHaveAsPosition(getPosition()[0],getPosition()[1])) {
			setDucking(previous);
			return true;
		}
		setDucking(previous);
		return false;
	}
	
	

	/**
	 * Return the ducked state of this game object.
	 */
	@Basic
	public boolean isDucking() {
		return isDucking;
	}
	
	/**
	 * Set the ducked state of this game object to the given state.
	 * 
	 * @param 	state
	 * 			The new state for this game object.
	 * @post	The new ducked state of this game object is equal to
	 * 			the given state.
	 * 		  | new.isDucking() == state
	 */
	@Raw
	private void setDucking(boolean state) {
		isDucking = state;
	}
	
	/**
	 * Return the previousXVelocityLimit of this game object.
	 */
	@Basic
	public double getPreviousXVelocityLimit() {
		return previousXVelocityLimit;
	}
	
	/**
	 * Set the previousXVelocityLimit of this game object to the given
	 * previousXVelocityLimit.
	 * 
	 * @param 	previousXVelocityLimit
	 * 			The new previousXVelocityLimit for this game object.
	 * @pre		The given previousXVelocityLimit should be a valid xVelocityLimit for this game object.
	 * 		  | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),previousXVelocityLimit)
	 * @post	The new previousXVelocityLimit of this game object is equal to
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
	 * Variable registering the ducked state of this game object.
	 */
	private boolean isDucking;
	/**
	 * Variable registering the velocity limit in the x direction while ducking.
	 */

	
	


	/**
	 * Return the timeSinceLastMove of this game object.
	 * The timeSinceLastMove of a game object expresses the time that has
	 * passed since the character made its last horizontal move.
	 */
	@Basic 
	public double getTimeSinceLastMove() {
		return this.timeSinceLastMove;
	}
	
	/**
	 * Set the timeSinceLastMove of this game object to the given timeSinceLastMove.
	 * 
	 * @param	timeSinceLastMove
	 * 			The new timeSinceLastMove for this game object.
	 * @pre		The given timeSinceLastMove must be positive.
	 * @post	The new timeSinceLastMove of this game object is equal to
	 * 			the given timeSinceLastMove.
	 * 		  | new.getTimeSinceLastMove() == timeSinceLastMove
	 */
	@Raw
	private void setTimeSinceLastMove(double timeSinceLastMove) {
		assert timeSinceLastMove >= 0;
		this.timeSinceLastMove = timeSinceLastMove;
	}
	
	/**
	 * Variable registering the time since the last move of this game object.
	 */
	private double timeSinceLastMove;


	/**
	 * Return the timeSinceLastRunningImage of this game object.
	 */
	@Basic
	public double getTimeSinceLastRunningImage() {
		return timeSinceLastRunningImage;
	}

	/**
	 * Set the timeSinceLastRunningImage of this game object to the given timeSinceLastRunningImage.
	 * 
	 * @param 	timeSinceLastRunningImage 
	 * 			The new timeSinceLastRunningImage for this game object.
	 * @pre		The given timeSinceLastRunningImage should be positive.
	 * 		  | timeSinceLastRunningImage >= 0
	 * @post	The new timeSinceLastRunningImage of this game object is equal 
	 * 			to the given timeSinceLastRunningImage.
	 * 		  | new.getTimeSinceLastRunningImage() == timeSinceLastRunningImage	
	 */
	private void setTimeSinceLastRunningImage(double timeSinceLastRunningImage) {
		assert timeSinceLastRunningImage >= 0;
		this.timeSinceLastRunningImage = timeSinceLastRunningImage;
	}
	
	/**
	 * Variable registering the time since the last image for a running motion was used for this game object.
	 */
	private double timeSinceLastRunningImage = 0;
	
	/**
	 * Check whether this game object is running normally. A game object
	 * is running normally if it is ducking nor jumping, and if its velocity is not equal to 0.
	 * 
	 * @return	True if and only if this game object is ducking nor jumping, and if its velocity
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
	 * Move this game object in the x direction for the given duration.
	 * 
	 * @param	duration
	 * 			The duration for which the game object should move in the x direction.
	 * @post	If the direction of this game object is Direction.RIGHT and duration is greater than 0, 
	 * 			then the new x position of this	game object has increased or this game object
	 * 			is positioned at the right border of the screen.
	 * 		  | if getXDirection() == Direction.RIGHT && duration > 0
	 * 		  | 	new.getX() > getX() || getX() == X_LIMIT
	 * @post	If the direction of this game object is Direction.LEFT and duration is greater than 0,
	 * 			then the new x position of this	game object has decreaed or this game object is
	 *			positioned at the left border of the screen.
	 * 		  | if getXDirection() == Direction.LEFT && duration > 0
	 * 		  | 	new.getX() < getX() || getX() == 0
	 * @post	If the direction of this game object is Direction.RIGHT and duration is greater than 0, 
	 * 			then the new xVelocity of this	game object has increased or the xVelocity is equal to
	 * 			xVelocityLimit.
	 * 		  | if getXDirection() == Direction.RIGHT && duration > 0
	 * 		  | 	new.getXVelocity() > getXVelocity() || getXVelocity() == getXVelocityLimit()
	 * @post	If the direction of this game object is Direction.LEFT and duration is greater than 0, 
	 * 			then the new xVelocity of this	game object has decreased or the xVelocity is equal to
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
		
				
		if(! canHaveAsPosition((int) xNew,getPosition()[1])) {
			vNew = 0;
			xNew = xCurrent;
			}
		setPosition(xNew,getY());
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
	 * Move this game object in the y direction for the given duration.
	 * 
	 * @param 	duration
	 * 			The duration for which the game object should move in the y direction.
	 * @post  	If duration > 0, the game object is either not jumping or its new y-position is smaller than or equal to
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
		
		if(! canHaveAsPosition(getPosition()[0],(int) yNew)) {
			yNew = yCurrent;
			vNew = 0;
		}
		setPosition(getX(),yNew);
		setYVelocity(vNew);
	}
	
	
	
	
	private double timeToBeImmune;


	public double getTimeToBeImmune() {
		return timeToBeImmune;
	}

	protected void setTimeToBeImmune(double timeToBeImmune) {
		if (timeToBeImmune < 0)
			this.timeToBeImmune = 0;
		else
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
	
	private double airTimer;
	public double getAirTimer() {
		return airTimer;
	}
	protected void setAirTimer(double airTimer) {
		this.airTimer = airTimer;
	}
	public double getY_ACCELERATION() {
		return Y_ACCELERATION;
	}
	
	public boolean hasProperWorld() {
		return (getMyWorld() != null);
	}
	
	
	
}
