/**
 * 
 */
package jumpingalien.part1.facade;

import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;
import jumpingalien.model.JumpingException;
import jumpingalien.model.World;
import jumpingalien.model.elements.Mazub;

/**
 * @author Andreas Schrvyers & Jonathan Oostvogels
 *
 */
public class Facade implements IFacade {
	
	/**
	 * Create an instance of Mazub.
	 * 
	 * @param pixelLeftX
	 *            The x-location of Mazub's bottom left pixel.
	 * @param pixelBottomY
	 *            The y-location of Mazub's bottom left pixel.
	 * @param sprites
	 *            The array of sprite images for Mazub.
	 * 
	 * @return
	 */
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		int[] windowSize = {1024,768};
		World myWorld = new World(1023,767,1,windowSize,10000,10000);
		Mazub myMazub = new Mazub(myWorld,(double) pixelLeftX, (double) pixelBottomY,sprites);
		return myMazub;
	}

	/**
	 * Return the current location of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the location.
	 * 
	 * @return an array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given alien's bottom left pixel in the world.
	 */
	public int[] getLocation(Mazub alien) {
		return new int[] {(int) alien.getX(), (int) alien.getY()};
	}

	/**
	 * Return the current velocity (in m/s) of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the velocity.
	 * 
	 * @return an array, consisting of 2 doubles {vx, vy}, that represents the
	 *         horizontal and vertical components of the given alien's current
	 *         velocity, in units of m/s.
	 */
	public double[] getVelocity(Mazub alien){
		return new double[] {alien.getXVelocity()/100,alien.getYVelocity()/100};
	}

	/**
	 * Return the current acceleration (in m/s^2) of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the acceleration.
	 * 
	 * @return an array, consisting of 2 doubles {ax, ay}, that represents the
	 *         horizontal and vertical components of the given alien's current
	 *         acceleration, in units of m/s^2.
	 */
	public double[] getAcceleration(Mazub alien) {
		return new double[] {alien.getXAcceleration()/100,alien.getYAcceleration()/100};
	}

	/**
	 * Return the current size of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the size.
	 * 
	 * @return An array, consisting of 2 integers {w, h}, that represents the
	 *         current width and height of the given alien, in number of pixels.
	 */
	public int[] getSize(Mazub alien) {
		return new int[] {alien.getWidth(),alien.getHeight()};
	}

	/**
	 * Return the current sprite image for the given alien.
	 * 
	 * @param alien
	 *            The alien for which to get the current sprite image.
	 * 
	 * @return The current sprite image for the given alien, determined by its
	 *         state as defined in the assignment.
	 */
	public Sprite getCurrentSprite(Mazub alien) {
		return alien.getCurrentSprite();
	}

	/**
	 * Make the given alien jump.
	 * 
	 * @param alien
	 *            The alien that has to start jumping.
	 */
	public void startJump(Mazub alien) throws ModelException {
		try {
			alien.startJump();
		}
		catch (JumpingException exc) {
			//throw new ModelException(exc); //Hide orange bar
		}
		
	}

	/**
	 * End the given alien's jump.
	 * 
	 * @param alien
	 *            The alien that has to stop jumping.
	 */
	public void endJump(Mazub alien) throws ModelException {
		try {
			alien.endJump();
		}
		catch (JumpingException exc) {
			//throw new ModelException(exc); //Hide orange bar
		}
		
	}

	/**
	 * Make the given alien move left.
	 * 
	 * @param alien
	 *            The alien that has to start moving left.
	 */
	public void startMoveLeft(Mazub alien) {
		alien.startMove(Mazub.Direction.LEFT);
	}

	/**
	 * End the given alien's left move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving left.
	 */
	public void endMoveLeft(Mazub alien) {
		if(alien.getXDirection() == Mazub.Direction.LEFT)
			alien.endMove();
	}

	/**
	 * Make the given alien move right.
	 * 
	 * @param alien
	 *            The alien that has to start moving right.
	 */
	public void startMoveRight(Mazub alien) {
		alien.startMove(Mazub.Direction.RIGHT);
	}

	/**
	 * End the given alien's right move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving right.
	 */
	public void endMoveRight(Mazub alien) {
		if(alien.getXDirection() == Mazub.Direction.RIGHT)
			alien.endMove();
	}

	/**
	 * Make the given alien duck.
	 * 
	 * @param alien
	 *            The alien that has to start ducking.
	 */
	public void startDuck(Mazub alien) throws ModelException {
		try {
			alien.startDuck();
		}
		catch(IllegalStateException exc) {
			//throw new ModelException("Already ducking!"); //Hide orange bar
		}
	}

	/**
	 * End the given alien's ducking.
	 * 
	 * @param alien
	 *            The alien that has to stop ducking.
	 */
	public void endDuck(Mazub alien) throws ModelException {
		try {
			alien.endDuck();
		}
		catch(IllegalStateException exc) {
			//throw new ModelException("Not yet ducking!"); //Hide orange bar
		}
	}

	/**
	 * Advance the state of the given alien by the given time period.
	 * 
	 * @param alien
	 *            The alien whose time has to be advanced.
	 * @param dt
	 *            The time interval (in seconds) by which to advance the given
	 *            alien's time.
	 */
	public void advanceTime(Mazub alien, double dt) {
		alien.advanceTime(dt);
	}
}
