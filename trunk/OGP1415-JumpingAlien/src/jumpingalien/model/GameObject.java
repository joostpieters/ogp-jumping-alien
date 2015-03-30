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

	
	public GameObject(World world, double x, double y) {
		this.WORLD = world;
		setPosition(x,y);
	}
	
	private final World WORLD;
	
	public boolean isValidPosition(int x,int y) {
		if (!( (x <= WORLD.getXLimit()) && (x >= 0) ))
			return false;
		
		if(! ((y <= WORLD.getYLimit()) && (y >= 0)))
			return false;
			
		int width = this.getWidth();
		int height = this.getHeight();
		for (int i = 1; i<width-1; i++) {
			if (x+i > WORLD.getXLimit())
				break;
			for (int j = 1; j<height-1; j++) {
				if (y+j > WORLD.getYLimit())
					break;
				if (( (WORLD.getObjectAt(x+i, y+j) != this) && (WORLD.getObjectAt(x+i, y+j) != null))
						|| (! (WORLD.getTerrainAt(x+i,y+j).isPassable())))
					return false;
			}
		}
		return true;
	}
	
//	public boolean isValidX(int x) {
//		if (!( (x <= WORLD.getXLimit()) && (x >= 0) ))
//			return false;
//			
//		int width = this.getWidth();
//		int height = this.getHeight();
//		int y = this.getPosition()[1];
//		for (int i = 1; i<width-1; i++) {
//			if (x+i > WORLD.getXLimit())
//				break;
//			for (int j = 1; j<height-1; j++) {
//				if (y+j > WORLD.getYLimit())
//					break;
//				if ((WORLD.getObjectAt(x+i, y+j) != null)
//						|| (! (WORLD.getTerrainAt(x+i,y+j).isPassable())))
//					return false;
//			}
//		}
//		return true;
//	}
//	
//	public boolean isValidY(int y) {
//		if(! ((y <= WORLD.getYLimit()) && (y >= 0)))
//			return false;
//		
//		int width = this.getWidth();
//		int height = this.getHeight();
//		int x = this.getPosition()[0];
//		for (int i = 1; i<width-1; i++) {
//			if (x+i > WORLD.getXLimit())
//				break;
//			for (int j = 1; j<height-1; j++) {
//				if (y+j > WORLD.getYLimit())
//					break;
//				if ((WORLD.getObjectAt(x+i, y+j) != null)
//						|| (! (WORLD.getTerrainAt(x+i,y+j).isPassable())))
//					return false;
//			}
//		}
//		return true;
//	}
	
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
	
//	/**
//	 * Set the x position of this game object to the given x position.
//	 * 
//	 * @param 	x
//	 * 			The new x position for this game object.
//	 * @pre		The given x position must be a valid x position for any game object.
//	 * 		  | isValidX((int) x)
//	 * @post	The new x position of this game object is equal to
//	 * 			the given position.
//	 * 		  | new.getX() == x
//	 */
//	@Raw
//	protected void setX(double x) {
//		assert isValidPosition((int) x,);
//		this.x = x;
//	}
//	
//	/**
//	 * Set the y position of this game object to the given y position.
//	 * 
//	 * @param 	y
//	 * 			The new y position for this game object.
//	 * @pre		The given y position must be a valid y position for any game object.
//	 * 		  | isValidY((int) y)
//	 * @post	The new y position of this game object is equal to
//	 * 			the given position.
//	 * 		  | new.getY() == y
//	 */
//	@Raw
//	protected void setY(double y) {
//		assert isValidY((int) y);
//		this.y = y;
//	}
	
	
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
		if (isTerminated()) {
			setTimeSinceTermination(getTimeSinceTermination() + duration);
			if (getTimeSinceTermination() > 0.6) {
				WORLD.removeObjectAt(getPosition()[0],getPosition()[1]);
				if (this instanceof Mazub)
					WORLD.endGame();			
			}
		}
	
		
	}
	
}
