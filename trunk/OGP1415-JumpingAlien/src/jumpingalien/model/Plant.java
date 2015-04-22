package jumpingalien.model;

import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of plants as special kinds of automatic game objects.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Plant extends AutomaticObject {

	/**
	 * Initialize this new plant with the given parameters.
	 * 
	 * @param world
	 * 		  The world of this new plant.
	 * @param x
	 *		  The initial x position for this new plant. 
	 * @param y
	 * 		  The initial y position for this new plant. 
	 * @param sprites
	 * 		  The series of initial sprites for this new plant
	 * @effect	| super(world, x, y, 1, 1, sprites, 50, 0, 50, 50, 1, 0, false)
	 */
	public Plant(World world, double x, double y, Sprite[] sprites) {
		super(world, x, y, 1, 1, sprites, 50, 0, 50, 50, 1, 0, false);
	}

	//no documentation needed
	public void advanceTime(double duration) {
		
		if(getTimer()+duration > getGoal()) {
			super.advanceTime(getGoal()-getTimer());
			startNewMovement();
			super.advanceTime(duration-(getGoal()-getTimer()));
			setTimer(getTimer()+duration-getGoal());	
		}
		else {
			super.advanceTime(duration);
			setTimer(getTimer()+duration);
		}	
	}
	
	/**
	 * Handle the interaction of this plant with other game objects.
	 * 
	 * @param 	duration
	 * 			The duration for which the interaction should be handled.	 * 		  
	 */
	public void handleInteraction(double duration) {
		//Handled by Mazub
		return;
	}
	
	/**
	 * Start a new movement of this plant.
	 * 
	 * @effect  | setGoal(0.5)
	 * @effect  | endMove()
	 * @effect  | if(this.getXDirection() == Direction.LEFT)
	 * 			|   this.startMove(Direction.RIGHT)
	 * 			| else
	 * 			|   this.startMove(Direction.LEFT)
	 */
	protected void startNewMovement() {
		setGoal(0.5);
		endMove();
		if(this.getXDirection() == Direction.LEFT)
			this.startMove(Direction.RIGHT);
		else
			this.startMove(Direction.LEFT);
	}

	//Gegokt, want staat niet in opgave
	//no documentation needed
	public Sprite getCurrentSprite() {
		if(getXDirection() == Direction.LEFT)
			return getSprites()[0];
		return getSprites()[1];
	}
	

	@Override
	/**
	 * @param insta
	 * 		  Has no effect.
	 * @effect | super.terminate(true)
	 */
	public void terminate(boolean insta) {
		super.terminate(true);		
	}

}
