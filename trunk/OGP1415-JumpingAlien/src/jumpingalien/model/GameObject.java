/**
 * 
 */
package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * @author Jonathan
 *
 */
public abstract class GameObject {

	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void advanceTime();
	
	public GameObject(World world, double x, double y) {
		this.WORLD = world;
		setX(x);
		setY(y);
	}
	
	private final World WORLD;
	
	public boolean isValidX(int x) {
		return ( (x <= WORLD.getXLimit()) && (x >= 0) );
	}
	
	public boolean isValidY(int y) {
		return (y <= WORLD.getYLimit()) && (y >= 0);
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
	 * Set the x position of this game object to the given x position.
	 * 
	 * @param 	x
	 * 			The new x position for this game object.
	 * @pre		The given x position must be a valid x position for any game object.
	 * 		  | isValidX((int) x)
	 * @post	The new x position of this game object is equal to
	 * 			the given position.
	 * 		  | new.getX() == x
	 */
	@Raw
	protected void setX(double x) {
		assert isValidX((int) x);
		this.x = x;
	}
	
	/**
	 * Set the y position of this game object to the given y position.
	 * 
	 * @param 	y
	 * 			The new y position for this game object.
	 * @pre		The given y position must be a valid y position for any game object.
	 * 		  | isValidY((int) y)
	 * @post	The new y position of this game object is equal to
	 * 			the given position.
	 * 		  | new.getY() == y
	 */
	@Raw
	protected void setY(double y) {
		assert isValidY((int) y);
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
}
