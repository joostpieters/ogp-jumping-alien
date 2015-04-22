package jumpingalien.model;


import jumpingalien.model.World.TerrainType;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of slimes as special kinds of automatic game objects, with an association
 * with the class School.
 * 
 * @invar	| this.getSchool() != null
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Slime extends AutomaticObject {

	/**
	 * Initialize this new slime with the given parameters.
	 * 
	 * @param world
	 * 		  The world of this new slime.
	 * @param x
	 *		  The initial x position for this new slime. 
	 * @param y
	 * 		  The initial y position for this new slime. 
	 * @param sprites
	 * 		  The series of initial sprites for this new slime.
	 * @param school
	 * 		  The school this new slime belongs to.
	 * 
	 * @pre	   | school != null
	 * @effect | super(world, x, y, 100, 100, sprites, 100, 0, 250, 250, 70, 1000, true)
	 * @effect | setSchool(school)
	 */
	@Raw
	public Slime(World world, double x, double y, Sprite[] sprites, School school) {
		super(world, x, y, 100, 100, sprites, 100, 0, 250, 250, 70, 1000, true);
		assert school != null;
		this.setSchool(school);
		startNewMovement();
	}
	
	/**
	 * Return the school associated with this slime.
	 */
	@Basic
	public School getSchool() {
		return school;
	}
	
	/**
	 * Set the school of this slime to the given school.
	 * 
	 * @param school
	 * 		  The new school for this slime
	 * @post  	| new.getSchool() == school
	 * @effect 	| school.addAsSlime(this)
	 */
	@Raw
	private void setSchool(School school) {
		this.school = school;
		school.addAsSlime(this);
	}

	/**
	 * Transfer this slime to the given school. If a slime changes its school, hitpoints of
	 * all accompanying slimes of both school are adjusted.
	 * 
	 * @param school
	 * 		  The school this slime should be transfered to.
	 * @effect  | getSchool().removeAsSlime(this)
	 * @effect  | setSchool(school)
	 * @effect  | school.addAsSlime(this)
	 * @effect  | for each colleague in old.getSchool().getSlimes()
	 * 			| 	if  (colleague != this) {
	 *			|		colleague.addHitPoints(1);
	 *			|		this.substractHitPoints(1, false);
	 *			|	}
	 * @effect  | for each colleague in new.getSchool().getSlimes()
	 * 			| 	if  (colleague != this) {
	 *			|		this.addHitPoints(1);
	 *			|		colleague.substractHitPoints(1, false);
	 *			|	}
	 */
	public void transferToSchool(School school) {
		for(Slime colleague: getSchool().getSlimes()) {
			if (colleague != this) {
				colleague.addHitPoints(1);
				this.substractHitPoints(1, false);
			}
		}
		School oldSchool = this.getSchool();
		this.setSchool(school);
		
		oldSchool.removeAsSlime(this);
		
		for(Slime colleague: getSchool().getSlimes()) {
			if (colleague != this) {
				this.addHitPoints(1);
				colleague.substractHitPoints(1, false);
			}
		}
	}
	
	
	/**
	 * Variable registering the school of this slime.
	 */
	private School school;
	

	/**
	 * Subtract the given number of hitpoints from the current number of hitpoints.
	 * 
	 * @param hitPoints
	 * 		  The number of hitpoints to be subtracted.
	 * @param propagate
	 * 		  True if other colleagues in the same school as this slime should
	 * 		  be affected by the subtraction of this slime's hitpoints.
	 * 		  If other colleagues are affected, they also lose a hitpoint.
	 * @effect | super.subtractHitPoints(hitPoints)
	 * @effect | 	if (propagate == true) {
	 * 		   |		for each colleague in getSchool().getSlimes()
	 * 		   |   			if (colleague != this)
	 *		   |      			colleague.substractHitPoints(1, false);
	 *		   |    }
	 */
	public void substractHitPoints(int hitPoints, boolean propagate) {
		super.substractHitPoints(hitPoints);
		if(propagate == true) {
			for(Slime colleague: getSchool().getSlimes()) {
				if (colleague != this)
					colleague.substractHitPoints(1, false);
			}
		}
	}
	
	/**
	 * Subtract the given number of hitpoints from the current number of hitpoints.
	 * 
	 * @param hitPoints
	 * 		  The number of hitpoints to be subtracted.
	 * @effect  | subtractHitPoints(hitPoints, true)
	 */
	@Override
	public void substractHitPoints(int hitPoints) {
		substractHitPoints(hitPoints, true);
	}


	@Override
	public void advanceTime(double duration) {
		super.advanceTime(duration);
		setTimer(getTimer()+duration);
		//Goals of 5.8 - 6 sec are less likely, but implementation is easier
		if(getTimer() > getGoal())
			startNewMovement();
	}
	
	/**
	 * Start a new movement.
	 * 
	 * @effect | endMove()
	 * @effect | setTimer(0)
	 * @effect | if (generator.nextDouble() < 0.5)
	 * 		   |   startMove(Direction.LEFT)
	 * 		   | else
	 * 		   |   startMove(Direction.RIGHT)
	 * @effect | setGoal(2+(5.8-2)*generator.nextDouble())
	 */
	@Raw
	protected void startNewMovement() {
		endMove();
		setTimer(0);
		if(generator.nextDouble() < 0.5) //nextDouble() returns random double out of [0,1]
			startMove(Direction.LEFT);
		else
			startMove(Direction.RIGHT);
		setGoal(2+(5.8-2)*generator.nextDouble());
	}

	//no documentation needed
	@Override
	public Sprite getCurrentSprite() {
		//Staat niet in opgave
		if (getXDirection() == Direction.LEFT)
			return getSprites()[0];
		else
			return getSprites()[1];
	}

	
	/**
	 * Handle the interaction of this game object with other game objects.
	 * 
	 * @param 	duration
	 * 			The duration for which the interaction should be handled.
	 */
	@Override
	public void handleInteraction(double duration) {
		
		Slime object0 = (Slime) this.touches(Slime.class);
		if (object0 != null && getTimeToBeImmune() == 0) {
			if(this.getSchool() != object0.getSchool()) {
				if(this.getSchool().getNbSlimes() > object0.getSchool().getNbSlimes())
					object0.transferToSchool(this.getSchool());
				else if(this.getSchool().getNbSlimes() < object0.getSchool().getNbSlimes())
					this.transferToSchool(object0.getSchool());
			}	
		}
		
		Mazub object1 = (Mazub) this.touches(Mazub.class);
		if (object1 != null && getTimeToBeImmune() == 0) {
			this.substractHitPoints(50, true);
			this.setTimeToBeImmune(0.6);
		}
		
		
		
		Shark object2 = (Shark) this.touches(Shark.class);
		if (object2 != null && getTimeToBeImmune() == 0) {
			this.substractHitPoints(50, true);
			this.setTimeToBeImmune(0.6);
		}
		
		//Exacte kopie van klasse Mazub, niet echt rendabel om een extra niveau in de klassenhierarchie
		//op te nemen.
		if (this.touches(TerrainType.WATER)) {
			this.setWaterTimer(getWaterTimer() + duration);
			if (getWaterTimer() >= 0.2) {
				this.substractHitPoints(2, true);
				setWaterTimer(getWaterTimer() - 0.2);
			}
		}
		
		else
			setWaterTimer(0);
		
		if (this.touches(TerrainType.MAGMA)) {
			this.setMagmaTimer(getMagmaTimer() + duration);
			if (getMagmaTimer() >= 0.2) {
				this.substractHitPoints(50, true);
				setMagmaTimer(getMagmaTimer() - 0.2);
			}
		}

		else
			setMagmaTimer(0.2);
		
	}

}
