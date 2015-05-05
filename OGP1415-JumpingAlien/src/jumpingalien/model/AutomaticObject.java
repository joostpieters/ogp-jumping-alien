/**
 * 
 */
package jumpingalien.model;

import java.util.Random;

import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of automatic objects as special kinds of game objects involving
 * as additional properties a timer and a goal, both used for organizing
 * random movements.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public abstract class AutomaticObject extends GameObject {

	/**
Initialize this new game object with the given parameters.
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
	 * @effect | super(world, x, y, initialHitPoints, maxHitPoints, sprites,
	 *		   | xInitialVelocity, yInitialVelocity, xVelocityLimit,
	 * 		   | duckedVelocityLimit, xAcceleration, yAcceleration, solid)
	 * @effect | startNewMovement()
	 * @effect | setTimer(0)
	 */
	@Raw
	public AutomaticObject(World world, double x, double y,
			int initialHitPoints, int maxHitPoints, Sprite[] sprites,
			double xInitialVelocity, double yInitialVelocity,
			double xVelocityLimit, double duckedVelocityLimit,
			double xAcceleration, double yAcceleration, boolean solid, String program) {
		super(world, x, y, initialHitPoints, maxHitPoints, sprites,
				xInitialVelocity, yInitialVelocity, xVelocityLimit,
				duckedVelocityLimit, xAcceleration, yAcceleration, solid, program);
		generator = new Random();
		setTimer(0);
		startNewMovement();
	}
	
	/**
	 * Start a new movement of this automatic game object.
	 */
	protected abstract void startNewMovement();

	/**
	 * Get the current sprite of this automatic game object.
	 */
	@Override
	public abstract Sprite getCurrentSprite();
	

	/**
	 * Handle the interaction of this automatic game object with other game objects.
	 */
	@Override
	public abstract void handleInteraction(double duration);
	
	/**
	 * Variable registering a random generator.
	 */
	protected static Random generator;
	
	/**
	 * Get the timer of this automatic game object.
	 */
	@Basic
	public double getTimer() {
		return this.timer;
	}
	
	/**
	 * Set the timer of this automatic game object to the given value.
	 * 
	 * @param time
	 * 		  The new value for the this object's timer.
	 * @post  | new.getTimer() == time
	 */
	@Raw
	protected void setTimer(double time) {
		this.timer = time;
	}
	
	/**
	 * Variable registering the timer of this automatic game object.
	 */
	private double timer;
	
	/**
	 * Get the goal of this automatic game object. The goal reflects the time that has to pass before
	 * an automatic game objects starts a new movement.
	 */
	@Basic
	public double getGoal() {
		return goal;
	}

	/**
	 * Set the goal of this object to the given value.
	 * 
	 * @param goal
	 * 		  The new goal for this automatic game object.
	 * @post  | new.getGoal() == goal
	 */
	protected void setGoal(double goal) {
		this.goal = goal;
	}
	
	/**
	 * Variable registering the goal of this automatic game object.
	 */
	private double goal;

}
