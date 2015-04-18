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
	 * 		  The world of this game object.
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
	 * @pre	  |  if (getMyWorld() != null)
	 * 		  |	 	canHaveAsPosition((int) x, (int) y)
	 * @pre	  | sprites != null
	 * @pre	  | isValidXInitialVelocityAndXVelocityLimit(xInitialVelocity, xVelocityLimit)
	 * @pre	  | canHaveAsXAcceleration(xAcceleration);
 	 * @pre	  | canHaveAsXVelocity(xVelocity);
	 * 
	 * @effect | setPosition(x,y)
	 * @effect | setXVelocityLimit(double xVelocityLimit)
	 * @effect | setSprites(sprites)
	 * @effect | setHitPoints(initialHitPoints)
	 * @effect | setMyWorld(world)
	 * @effect | setTerminated(false)
	 * @post   | new.X_ACCELERATION = xAcceleration
	 * @post   | new.Y_ACCELERATION = yAcceleration
	 * @post   | new.X_INITIAL_VELOCITY = xInitialVelocity
	 * @post   | new.Y_INITIAL_VELOCITY = yInitialVelocity
	 * @post   | new.getXInitialVelocity() == xInitialVelocity
	 * @post   | new.isSolid() == solid
	 * @post   | new.getDuckedVelocityLimit() == duckedVelocityLimit
	 * 
	 * 		  AANVULLEN
	 * 			Waarom geen @ effect hierboven?
	 */
	@Raw
	public GameObject(World world, double x, double y, 
							int initialHitPoints, int maxHitPoints, Sprite[] sprites,
								double xInitialVelocity, double yInitialVelocity,
									double xVelocityLimit,  double duckedVelocityLimit,
										double xAcceleration, double yAcceleration, boolean solid) {
		
		assert sprites != null;
		assert isValidXInitialVelocityAndXVelocityLimit(xInitialVelocity, xVelocityLimit);
		
		this.setMyWorld(world);
		this.setPosition(x,y);
		this.MAX_HITPOINTS = maxHitPoints;
		this.setHitPoints(initialHitPoints);
		this.setSprites(sprites);
		this.setXDirection(Direction.RIGHT);
		
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
		
		assert this.canHaveAsXAcceleration(xAcceleration);
		assert this.canHaveAsXVelocity(xVelocity);
		if (this.getMyWorld() != null)
			assert this.canHaveAsPosition((int) x, (int) y);
		
		this.setTerminated(false);
		
	
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
		return this.DUCKED_VELOCITY_LIMIT;
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
	
	
	/**
	 * Return the solid state of this game object.
	 */
	@Immutable @Basic
	public boolean isSolid() {
		return this.SOLID;
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
		return this.myWorld;
	}
	/**
	 * Set the world of this game object to the given world.
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
	public int getMaxHitPoints() {
		return this.MAX_HITPOINTS;
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
	 *		  |	else if (hitPoints > getMaxHitPoints())
	 *		  |		new.getHitPoints = getMaxHitPoints();
	 *		  | else
	 *		  |		new.getHitPoints = hitPoints;
	 */
	protected void setHitPoints(int hitPoints) {
		if (hitPoints < 0)
			this.hitPoints = 0;
		else if (hitPoints > getMaxHitPoints())
			this.hitPoints = getMaxHitPoints();
		else
			this.hitPoints = hitPoints;
	}
	
	/**
	 * Add the given number of hitpoints to the current number of hitpoints.
	 * 
	 * @param 	hitPoints
	 * 		  	The number of hitpoints to be added to the current number of hitpoints.
	 * @pre	  	| hitpoints >= 0
	 * @effect 	| setHitPoints(getHitPoints() + hitPoints)
	 */
	protected void addHitPoints(int hitPoints) {
		assert hitPoints >= 0;
		setHitPoints(getHitPoints() + hitPoints);
	}
	
	/**
	 * Subtract the given number of hitpoints from the current number of hitpoints.
	 * 
	 * @param	hitPoints
	 * 			The number of hitpoints to be subtracted from the current number of hitpoints.
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
	 * 	 	  | if (! isSolid())
	 * 		  |	 then result == true
	 * 		  | else if (y<0)
	 * 		  |  then result == false
	 * 		  | else if (for each obj in myWorld.getGameObjects()
	 * 		  | 		 if obj.isSolid()
	 * 		  |			 	if rectanglesCollide(obj.getPosition()[0]+1,obj.getPosition()[1]+1,obj.getWidth()-1,obj.getHeight()-1,
	 *		  |				x+1,y+1,this.getWidth()-1,this.getHeight()-1)))
	 * 		  |   			 then result == false
	 * 		  | else if (for a pixel of the lower border, or upper border, or left border, or right border
	 * 		  |			! (myWorld.getTerrainAt(pixel).isPassable())
	 * 		  |				 then result == false
	 * 		  | else
	 * 		  |	 result == true
	 * 					
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
				if(obj.isSolid())
					if(rectanglesCollide(obj.getPosition()[0]+1,obj.getPosition()[1]+1,obj.getWidth()-1,obj.getHeight()-1,
						x+1,y+1,width-1,height-1))
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
	 * Check whether two rectangles collide.
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
	 * 		  | if (for each corner of each rectangle
	 * 		  |     ! pointInRectangle(cornerX, cornerY, otherrectangleX, otherrectangleY,
	 * 		  |							otherrectangleWidht, otherrectangleHeight))
	 * 		  |		then result == true
	 * 		  | else
	 * 		  | 	result == false
	 */
	public static boolean rectanglesCollide(int x1, int y1, int width1, int height1,
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
	
	
	/**
	 * 
	 * @param x
	 * 		  The x position of the point
	 * @param y
	 * 	      The y position of the point
	 * @param RectX
	 * 		  The x position of the rectangle
	 * @param RectY
	 * 	  	  The y position of the rectangle
	 * @param width
	 * 		  The width of the rectangle
	 * @param height
	 * 		  The height of the rectangle
	 * @return
	 *		  if (RectX <= x && x <= RectX+width-1 && RectY <= y && y <= RectY+height-1)
	 *		       then result == true
	 *		  else
	 *			   result == false 
	 */
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
	 * Set the position of this game object to the given x and the given y.
	 * 
	 * @param x
	 * 		  The new x position of this game object.
	 * @param y
	 * 		  The new y position of this game object.
	 * @pre	  canHaveAsPosition(x,y)
	 * @post  | new.getX() == x && new.getY() == y
	 */
	protected void setPosition(double x, double y) {
//		assert canHaveAsPosition((int) x,(int)y);
//		deze assertion zorgt voor problemen als ge helemaal bovenaan op een slime gaat staan
//		en dan tegen het plafond springt
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
		
	/**
	 * Return the position of this game object.
	 * 
	 * @return  | result == {(int) getX(), (int) getY()}
	 */
	public int[] getPosition() {
		int[] result = {(int) getX(), (int) getY()};
		return result;
	}
	
	/**
	 * Variable registering the termination state of this game object.
	 */
	private boolean isTerminated;
	
	/**
	 * Return the termination state of this game object.
	 */
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Set the termination state of this game object to the given flag.
	 * @param flag
	 * 		  The new termination state for this game object.
	 * @post  | new.isTerminated() == flag
	 */
	private void setTerminated(boolean flag) {
		this.isTerminated = flag;	
	}
	
	
	//insta wil zeggen object meteen verwijderen uit gamewereld in volgende advancetime
	/**
	 * Terminate this game object.
	 * @param insta
	 * 		  determines whether this game object should be 
	 *		  terminated instantly (insta == true) or after a certain amount of time (insta == false).
	 * @post   |  if (isTerminated())
	 *		   | 	result ==
	 * @effect |  if (! isTerminad())
	 * 		   |    setTerminated(true)
	 */
	public void terminate(boolean insta) {
		if (isTerminated())
			return;
		setTerminated(true);
		if (insta)
			setTimeSinceTermination(0.7);
		else
			setTimeSinceTermination(0);	
	}
	
	/**
	 * Variable registering the time since the object is terminated.
	 */
	private double timeSinceTermination;

	/**
	 * Return the time since the object is terminated.
	 */
	@Basic
	public double getTimeSinceTermination() {
		return timeSinceTermination;
	}
	
	/**
	 * Set the time since the object is terminated to the given time.
	 * 
	 * @param timeSinceTermination
	 * 		  The new time since termination
	 * 
	 * @post  | new.getTimeSinceTermination() == timeSinceTermination
	 */
	public void setTimeSinceTermination(double timeSinceTermination) {
		this.timeSinceTermination = timeSinceTermination;
	}
	
	//onduidelijk of er documentatie nodig is voor deze methode
	public void advanceTime(double duration) {
		if(duration > 0.2 || duration < 0) return;
		//Bij het laden van het level loopt het zonder deze regel soms mis (veel te grote duration)
		
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
			setJumping(canHaveAsPosition(getPosition()[0],getPosition()[1] - 1));
			moveY(adjustedDuration);
			setJumping(canHaveAsPosition(getPosition()[0],getPosition()[1] - 1));
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
		 
		 

		setTimeToBeImmune(getTimeToBeImmune()-duration);
		
	}
		
	/**
	 * Return the game object of the given class that touches this game object.
	 * @param className
	 * 		  The class of game objects for which to return a possible touching object.
	 * @return | if (for a certain obj of myWorld.getGameObjects()
	 * 		   |   	(obj != this && className.isInstance(obj)) &&
	 *		   |	(rectanglesCollide(obj.getPosition()[0],obj.getPosition()[1],obj.getWidth(),obj.getHeight(),
	 *         |	this.getPosition()[0],this.getPosition()[1],this.getWidth(),this.getHeight()))
	 *         |    result == obj
	 *         | else 
	 *         |	result == null
	 */
	//wat gebeurt er als er twee objecten tegelijk worden aangeraakt?
	public GameObject touches(Class<?> className) {
		
		for(GameObject obj : myWorld.getGameObjects()) {
			if(obj != this && className.isInstance(obj)) {
				if(rectanglesCollide(obj.getPosition()[0],obj.getPosition()[1],obj.getWidth(),obj.getHeight(),
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
	
	/**
	 * Check whether this game object touches the given type of terrain.
	 * @param terrainType
	 * 		  The terrainType to check.
	 * @return
	 */
	
	//return specifieren
	
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
	 * @post	| new.getSprites() == sprites
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
	 * Return the width of this game object's current sprite.
	 * 
	 * @return | result == (this.getCurrentSprite().getWidth())
	 */
	public int getWidth() {
		return this.getCurrentSprite().getWidth();
	}
	
	/**
	 * Return the height of this game object's currect sprite.
	 * 
	 * @return | result == (this.getCurrentSprite().getHeight())
	 */
	public int getHeight() {
		return this.getCurrentSprite().getHeight();
	}
		
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
	 * @pre	  | (xDirection == Direction.LEFT) || (xDirection == Direction.RIGHT)
	 * @post  | new.getXDirection() == xDirection
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
		
	/**
	 * Check whether the given xInitialVelocity and xVelocityLimiy are valid for any game object.
	 *  
	 * @param	xInitialVelocity
	 * 			The initial velocity in the x direction to be checked.
	 * @param	xVelocityLimit
	 * 			The velocity limit in the x direction to be checked.
	 * @return	| result ==
	 * 		    |    ( (xInitialVelocity <= xVelocityLimit)
	 * 		    |   && (xInitialVelocity >= 1) )
	 */
	public static boolean isValidXInitialVelocityAndXVelocityLimit(double xInitialVelocity, double xVelocityLimit) {
		return (xInitialVelocity <= xVelocityLimit) && (xInitialVelocity >= 1);
	}
	
	/**
	 * Return the x velocity limit of this game object. 
	 * This velocity limit is positive if the direction of the game object is Direction.RIGHT
	 * and negative if the direction of the game object is Direcion.LEFT.
	 * 
	 * @return 	| if (getXDirection() == Direction.RIGHT)
	 *			|	result == xVelocityLimit
	 *			| else
	 *			| 	result == -xVelocityLimit
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
	 * @pre	  | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),xVelocityLimit)
	 * @post  | new.getXVelocityLimit() == xVelocityLimit
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
	 * @return | result == 
	 * 		   |    (  (xVelocity == 0) 
	 * 		   |   || ( (abs(xVelocity) >= getXInitialVelocity())
	 *		   |        && (abs(xVelocity) <= getXVelocityLimit()) ) )
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
	 * @pre	  | canHaveAsXVelocity(xVelocity)
	 * @post  | new.getXVelocity() == xVelocity
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
	 * @post  | new.getYVelocity() == yVelocity
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
	 * @return | result ==
	 * 		   |    (signum(xAcceleration) == signum(getXVelocity() || getXVelocity() == 0)
	 */
	public boolean canHaveAsXAcceleration(double xAcceleration) {
		return (signum(xAcceleration) == signum(getXVelocity()) || getXVelocity() == 0) ;
	}
	
	/**
	 * 
	 * Return this game object's current acceleration in the x direction.
	 * 
	 * @return
	 * 		  | if ( (getXVelocity() == 0) || (getXVelocity() == getXVelocityLimit()) )
	 * 		  | 	then result == 0
	 * 		  | else if (getXDirection() == Direction.RIGHT)
	 * 		  | 	then result == X_ACCELERATION
	 * 		  | else if (getXDirection() == Direction.LEFT)
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
	 * @return 	
	 * 			| if (isJumping())
	 * 			| 	then result == -Y_ACCELERATION
	 * 			| else
	 * 			|	result == 0
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
	 * @pre	  | (direction == Direction.LEFT) || (direction == Direction.RIGHT)
	 * @post  | new.getXDirection() == direction
	 * @post  | abs(new.getXVelocity()) == getXInitialVelocity()
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
	 * Its sill moving state is set to false.2
	 * 
	 * @post | new.getXVelocity() == 0
	 * @post | new.getStillMoving(false)
	 */
	public void endMove() {
		setStillMoving(false);
		setXVelocity(0);
	}
	
	/**
	 * The given game object starts jumping with initial velocity
	 * Y_INITIAL_VELOCITY and with acceleration getYAcelleration().
	 * 
	 * @post	| new.getYVelocity() == Y_INITIAL_VELOCITY
	 * @throws 	JumpingException
	 * 			| ! canJump()
	 */
	public void startJump() throws JumpingException {
		if (! canJump())
			throw new JumpingException("Already jumping!", this);
		setYVelocity(getYInitialVelocity());
	}
	
	/**
	 * Check whether this game object can jump.
	 * 
	 * @return | result == (getPosition()[1] == 0 ||
			   |           ( !(canHaveAsPosition(getPosition()[0],getPosition()[1]-1))))
	 */
	public boolean canJump() {
		return (getPosition()[1] == 0 ||
				( !(canHaveAsPosition(getPosition()[0],getPosition()[1]-1))));
	}
	
	/**
	 * The given game object stops jumping. Its velocity is set to 0 if it was greater than 0.
	 * 
	 * @post  | if (getYVelocity() > 0)
	 * 		  |		new.getYVelocity() == 0
	 * @throws 	JumpingException
	 *		  | ! isJumping()
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
	 * @return  | result == (getY() > 0)
	 */
	public boolean isJumping() {
		return isJumping;
				
	}
	
	/**
	 * Set the jumping state of this game object to the given flag.
	 * @param flag
	 * 		  The new jumping state for this game object.
	 * @post  | new.isJumping() == flag
	 */
	private void setJumping(boolean flag) {
		this.isJumping = flag;
	}
	
	/**
	 * Variable registering the jumping state of this game object.
	 */
	private boolean isJumping;
	
	/**
	 * The given game object starts ducking. Its velocity limit in the x direction
	 * is set to the ducked velocity limit. Its new ducked state is equal to true. 
	 * It's toEndDuck state is set to false.
	 * 
	 * @post  | new.getPreviousXVelocityLimit() == abs(this.getXVelocityLimit())
	 * @post  | abs(new.getXVelocityLimit()) == getDuckedVelocityLimit()
	 * @post  | if (abs(this.getXVelocity()) > abs(new.getXVelocityLimit()))
	 * 		  |		new.getXVelocity() == (signum(this.getXVelocity())) * (new.getXVelocityLimit())
	 * @post  | new.isDucking() == true
	 * @post  | new.getToEndDuck() == false
	 * @throws	IllegalStateExpcetion
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
	 * @post  | if canEndDuck()
	 * 		  | 	new.getXVelocityLimit() == this.getPreviousXVelocityLimit()
	 * 		  |		new.isDucking() == false
	 * 		  |		new.getToEndDuck() == false
	 * 		  | else
	 * 		  |     new.getToEndDuck() == true
	 * @throws	IllegalStateException
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
	
	/**
	 * Variable registering whether this game object needs to end duck.
	 */
	private boolean toEndDuck;
	
	/**
	 * Return the toEndDuck state of this game object.
	 */
	@Basic
	public boolean getToEndDuck() {
		return toEndDuck;
	}

	/**
	 * Set the toEndDuck state of this game object to the given toEndDuck.
	 * @param toEndDuck
	 * 		  The new toEndDuck state of this game object.
	 * @post  new.getToEndDuck() == toEndDuck
	 */
	private void setToEndDuck(boolean toEndDuck) {
		this.toEndDuck = toEndDuck;
	}
	
	/**
	 * Check whether this game object can end its duck.
	 * @return | if (canHaveAsPosition(getPosition()[0],getPosition()[1]) while setDucking(false) 
	 * 		   |   result == true
	 * 		   | else
	 * 		   |   result == false
	 */
	//Hoe komt het dat we Mazub ni zien ontducken bij deze methode?
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
	 * @post  | new.isDucking() == state
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
	 * @pre	 | isValidXInitialVelocityAndXVelocityLimit(getXInitialVelocity(),previousXVelocityLimit)
	 * @post | new.getPreviousXVelocityLimit() == previousXVelocityLimit
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
	//WEG?!

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
	 * @pre		| timeSinceLastMove >= 0
	 * @post	| new.getTimeSinceLastMove() == timeSinceLastMove
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
	 * @pre	  | timeSinceLastRunningImage >= 0
	 * @post  | new.getTimeSinceLastRunningImage() == timeSinceLastRunningImage	
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
	 * @return
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
	 * @post  | if getXDirection() == Direction.RIGHT && duration > 0
	 * 		  | 	new.getX() > getX() || getX() == X_LIMIT
	 * @post  | if getXDirection() == Direction.LEFT && duration > 0
	 * 		  | 	new.getX() < getX() || getX() == 0
	 * @post  | if getXDirection() == Direction.RIGHT && duration > 0
	 * 		  | 	new.getXVelocity() > getXVelocity() || getXVelocity() == getXVelocityLimit()
	 * @post  | if getXDirection() == Direction.LEFT && duration > 0
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
	
	/**
	 * Varible registering whether this game object is still moving.
	 */
	private boolean isStillMoving;

	/**
	 * Return the isStillMoving variable of this game object.
	 */
	@Basic
	public boolean isStillMoving() {
		return isStillMoving;
	}

	/**
	 * Set the isStillMoving variable of this game object to the given isStillMoving.
	 * @param isStillMoving
	 * 		  The new isStillMoving value
	 * @post  new.isStillMoving() == isStillMoving
	 */
	public void setStillMoving(boolean isStillMoving) {
		this.isStillMoving = isStillMoving;
	}

	/**
	 * Move this game object in the y direction for the given duration.
	 * 
	 * @param 	duration
	 * 			The duration for which the game object should move in the y direction.
	 * @post  | if duration > 0
	 * 		  | 	then (new.getY() <= getY()+getYVelocity*duration+0.5*getYAcceleration()*pow(duration,2.0)) 
	 * 		  |				|| (! isJumping())
	 * @post  | if duration > 0
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
	
	/**
	 * Variable registering the time a game object has to be immune.
	 */
	private double timeToBeImmune;

	/**
	 * Return the timeToBeImmune of this game object.
	 */
	@Basic
	public double getTimeToBeImmune() {
		return timeToBeImmune;
	}

	/**
	 * Set the timeToBeImmune of this game object to the given timeToBeImmune.
	 * @param timeToBeImmune
	 * 		  The new timeToBeImmunw
	 * @post  | if (timeToBeImmune < 0)
			  |		new.getTimeToBeImmune() == 0
			  | else
			  | 	new.getTimeToBeImmune == timeToBeImmune
	 */
	protected void setTimeToBeImmune(double timeToBeImmune) {
		if (timeToBeImmune < 0)
			this.timeToBeImmune = 0;
		else
			this.timeToBeImmune = timeToBeImmune;
	}
	
	/**
	 * Variable registering how long this game object is in water.
	 */
	private double waterTimer;
	
	/**
	 * Return the waterTimer of this game object. The waterTimer of a game object identifies
	 * how long the game object has been in the water.
	 */
	@Basic
	public double getWaterTimer() {
		return waterTimer;
	}
	
	/**
	 * Set the waterTimer of this game objecect to the given value.
	 * @param waterTimer
	 * 		  The new value for the watertimer
	 * @post  | new.getWaterTimer() == waterTimer
	 */
	protected void setWaterTimer(double waterTimer) {
		this.waterTimer = waterTimer;
	}
	
	/**
	 * Variable registering how long this game object is in magma.
	 */
	private double magmaTimer;
	
	/**
	 * Return the magmaTimer of this game object. The magmaTimer of a game object identifies
	 * how long the game object has been in the magma.
	 */
	@Basic
	public double getMagmaTimer() {
		return magmaTimer;
	}
	
	/**
	 * Set the magmaTimer of this game objecect to the given value.
	 * @param magmaTimer
	 * 		  The new value for the magmaTimer
	 * @post  | new.getMagmaTimer() == magmaTimer
	 */
	protected void setMagmaTimer(double magmaTimer) {
		this.magmaTimer = magmaTimer;
	}
	
	/**
	 * Variable registering how long this game object is in air.
	 */
	private double airTimer;
	
	/**
	 * Return the airTimer of this game object. The airTimer of a game object identifies
	 * how long the game object has been in the air.
	 */
	@Basic
	public double getAirTimer() {
		return airTimer;
	}
	
	/**
	 * Set the airTimer of this game objecect to the given value.
	 * @param airTimer
	 * 		  The new value for the airTimer
	 * @post  | new.getAirTimer() == airTimer
	 */
	protected void setAirTimer(double airTimer) {
		this.airTimer = airTimer;
	}
	
	/**
	 * Return the acceleration in the y direction of this game object.
	 */
	@Basic
	public double getY_ACCELERATION() {
		return Y_ACCELERATION;
	}
	
	/**
	 * Check whether this game object has a proper world.
	 * @return | result == (getMyWorld() != null)
	 */
	public boolean hasProperWorld() {
		return (getMyWorld() != null);
	}
	
}
