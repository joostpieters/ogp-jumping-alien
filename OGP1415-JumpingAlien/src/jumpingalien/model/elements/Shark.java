/**
 * 
 */
package jumpingalien.model.elements;

import jumpingalien.model.Program;
import jumpingalien.model.World;
import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of sharks as special kinds of automatic game objects.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Shark extends AutomaticObject {


	/**
	 * Initialize this new shark with the given parameters.
	 * 
	 * @param world
	 * 		  The world of this new shark.
	 * @param x
	 *		  The initial x position for this new shark. 
	 * @param y
	 * 		  The initial y position for this new shark. 
	 * @param sprites
	 * 		  The series of initial sprites for this new shark.
 	 * @param program
 	 * 		  The program for this new shark.
	 * 
	 * @effect  | super(world, x, y, 100, 100, sprites, 100, 200, 400, 400, 150, 1000, true, program)
	 * @effect  | setCounter(0)
	 */
	public Shark(World world, double x, double y, Sprite[] sprites, Program program) {
		super(world, x, y, 100, 100, sprites, 100, 200, 400, 400, 150, 1000, true, program);
		setCounter(0);
		LIVES_ON_LAND = false;
	}
	

	@Override
	public void advanceTime(double duration) {
		super.advanceTime(duration);
		
		if (getProgram() != null) {
			getProgram().advanceTime(duration);
		}
		
		else {
			setTimer(getTimer()+duration);
			if(getTimer() > getGoal()) {
				startNewMovement();
			}
			if (getCounter() == 1 && getYVelocity() < 0 && isSubmerged()) {
				setYVelocity(0);
			}
		}
	}

	/**
	 * Start a new movement of this shark.
	 * 
	 * @effect	| setTimer(0)
	 * @effect	| endMove()
	 * @effect  | if isJumping()
	 * 			|    endJump()
	 * @effect	| setGoal(1+generator.nextDouble()*(3.8-1))
	 * @effect  | if(generator.nextDouble() < 0.5)
	 * 			|	 startMove(Direction.LEFT)
	 * 			| else
	 * 			|   startMove(Direction.RIGHT)
	 * @effect  | if (getCounter() == 0)
	 * 			|     if (canJump())
	 * 			|		startJump()
	 * @effect  | setDivingAcceleration(100*(-0.2+0.4*generator.nextDouble()))
	 * @effect  | setCounter((getCounter() + 1)%5)
	 */
	@Override
	protected void startNewMovement() {
		setTimer(0);
		endMove();
		try {
			endJump();
		}
		catch (Exception exc) {
		}
		
		//4 seconds is less likely, but still possible when duration = (0.2
		setGoal(1+generator.nextDouble()*(3.8-1));
		if(generator.nextDouble() < 0.5)
			startMove(Direction.LEFT);
		else
			startMove(Direction.RIGHT);
		
		if (getCounter() == 0) {
			try {
				startJump();
			}
			catch (Exception exc) {
			}
		}
		setDivingAcceleration(100*(-0.2+0.4*generator.nextDouble()));
		setCounter((getCounter() + 1)%5); 
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

		else {
			setMagmaTimer(0.2);
		}
		
		Mazub object1 = (Mazub) this.touches(Mazub.class);
		if (object1 != null && getTimeToBeImmune() == 0) {
			this.substractHitPoints(50);
			this.setTimeToBeImmune(0.6);
		}
		
		Slime object2 = (Slime) this.touches(Slime.class);
		if (object2 != null && getTimeToBeImmune() == 0) {
			this.substractHitPoints(50);
			this.setTimeToBeImmune(0.6);
		}
	}
	
	/**
	 * Check whether this shark can jump.
	 * 
	 * @return | if for some bottom pixel (x,y) of this shark 
	 * 		   |    getMyWorld().getTerrainAt(x,y) == TerrainType.WATER && 
	 * 		   |    	result == true
	 * 		   | if super.canJump()
	 * 		   | 	result == true
	 * 		   | else
	 * 		   |	result == false
	 */
	@Override
	public boolean canJump() {
		boolean touchesWater = false;
		for(int i = 0; i < getWidth(); i++) {
			if (getMyWorld().getTerrainAt(getPosition()[0] + i, getPosition()[1]) == TerrainType.WATER)
				touchesWater = true;
				//zorgt ervoor dat er ook kan gesprongen worden als er geen solid tile onder het object is
		}
		return ((super.canJump()) || touchesWater);		
	}

	/**
	 * Return the counter of this shark.
	 */
	@Basic
	public int getCounter() {
		return counter;
	}
	
	/**
	 * Set the counter of this shark to the given value.
	 * 
	 * @param counter
	 * 		  The new counter for this shark.
	 * @post  | new.getCounter() == counter
	 */
	@Raw
	private void setCounter(int counter) {
		this.counter = counter;
	}
	
	/**
	 * Variable registering the counter of this shark.
	 */
	private int counter;
	
	/**
	 * Return the acceleration in the y direction of this shark.
	 * 
	 * @return  | if (getCounter() == 0) {
	 *			|	if (isSubmerged())
	 *			|		result == 0
	 *			|	else
	 *			|		result == -getY_ACCELERATION()
	 *			| }
	 *			| else if (isSubmerged())
	 *			|   result == getDivingAcceleration()
	 *		    | else
	 *		    |   result == -getY_ACCELERATION()
	 *
	 * 
	 */
	@Override
	public double getYAcceleration() {
		if (getCounter() == 0) {
			if (isSubmerged())
				return 0;
			return -getY_ACCELERATION();
		}
			
		if (isSubmerged())
			return getDivingAcceleration();
		return -getY_ACCELERATION();
	}

	/**
	 * Check whether this shark is submerged in water.
	 * 
	 * @return  | if for some top border pixel (x,y) of this shark
	 * 			|   getMyWorld().getTerrainAt(x,y) == TerrainType.WATER
	 * 		    |	  result == true
	 * 			| else
	 * 			|     result == false
	 */
	public boolean isSubmerged() {
		for(int i = 0; i < getWidth(); i++) {
			if (getMyWorld().getTerrainAt(getPosition()[0] + i, getPosition()[1] + getHeight() -1) == TerrainType.WATER)
				return true;
		}
		return false;
	}
	
	/**
	 * Variable registering the y acceleration when a shark is diving (i.e. submerged in water).
	 */
	private double divingAcceleration;

	/**
	 * Return the diving acceleration of this shark.
	 */
	@Basic
	public double getDivingAcceleration() {
		return divingAcceleration;
	}

	/**
	 * Set the diving acceleration of this shark to the given value.
	 * 
	 * @param divingAcceleration
	 * 		  The new diving acceleration for this shark.
	 * @post  | new.getDivingAcceleration() == divingAccleration
	 */
	private void setDivingAcceleration(double divingAcceleration) {
		this.divingAcceleration = divingAcceleration;
	}
}
