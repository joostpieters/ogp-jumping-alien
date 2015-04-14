/**
 * 
 */
package jumpingalien.model;

import java.util.Random;

import jumpingalien.util.Sprite;

/**
 * @author Jonathan
 *
 */
public abstract class AutomaticObject extends GameObject {

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param initialHitPoints
	 * @param maxHitPoints
	 * @param sprites
	 * @param xInitialVelocity
	 * @param yInitialVelocity
	 * @param xVelocityLimit
	 * @param duckedVelocityLimit
	 * @param xAcceleration
	 * @param yAcceleration
	 */
	public AutomaticObject(World world, double x, double y, int z,
			int initialHitPoints, int maxHitPoints, Sprite[] sprites,
			double xInitialVelocity, double yInitialVelocity,
			double xVelocityLimit, double duckedVelocityLimit,
			double xAcceleration, double yAcceleration, boolean solid) {
		super(world, x, y, z, initialHitPoints, maxHitPoints, sprites,
				xInitialVelocity, yInitialVelocity, xVelocityLimit,
				duckedVelocityLimit, xAcceleration, yAcceleration, solid);
		generator = new Random();
		setTimer(0);
		startNewMovement();
	}
	

	
	protected abstract void startNewMovement();

	@Override
	public abstract Sprite getCurrentSprite();
	

	@Override
	public abstract void handleInteraction(double duration);
	
	
	protected static Random generator;
	
	public double getTimer() {
		return this.timer;
	}
	
	protected void setTimer(double time) {
		this.timer = time;
	}
	
	private double timer;
	
	public double getGoal() {
		return goal;
	}

	protected void setGoal(double goal) {
		this.goal = goal;
	}
	
	private double goal;

}
