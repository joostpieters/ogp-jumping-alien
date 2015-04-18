/**
 * 
 */
package jumpingalien.model;

import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Shark extends AutomaticObject {

	/**
	 * @param world
	 * @param x
	 * @param y
	 */
	public Shark(World world, double x, double y, Sprite[] sprites) {
		super(world, x, y, 100, 100, sprites, 100, 200, 400, 400, 150, 1000, true);
		setCounter(0);
	}
	

	/**
	 * @post |getTimer() <= getGoal()
	 */
	@Override
	public void advanceTime(double duration) {
		super.advanceTime(duration);
		setTimer(getTimer()+duration);
		if(getTimer() > getGoal()) {
			startNewMovement();
		}
		if (getCounter() == 1 && getYVelocity() < 0 && isSubmerged()) {
			setYVelocity(0);
		}
	}
	
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
	
	@Override
	public boolean canJump() {
		boolean touchesWater = false;
		for(int i = 0; i < getWidth(); i++) {
			if (getMyWorld().getTerrainAt(getPosition()[0] + i, getPosition()[1]) == TerrainType.WATER)
				touchesWater = true;
		}
		return ((super.canJump()) || touchesWater);		
	}



	public int getCounter() {
		return counter;
	}
	
	private void setCounter(int counter) {
		this.counter = counter;
	}
	
	private int counter;
	
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

	public boolean isSubmerged() {
		for(int i = 0; i < getWidth(); i++) {
			if (getMyWorld().getTerrainAt(getPosition()[0] + i, getPosition()[1] + getHeight() -1) == TerrainType.WATER)
				return true;
		}
		return false;
	}
	
	private double divingAcceleration;

	public double getDivingAcceleration() {
		return divingAcceleration;
	}

	private void setDivingAcceleration(double divingAcceleration) {
		this.divingAcceleration = divingAcceleration;
	}
}
