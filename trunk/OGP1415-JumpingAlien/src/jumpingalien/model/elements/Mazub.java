package jumpingalien.model.elements;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;


/**
 * A class of Mazub characters as special kinds of aliens.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class Mazub extends Alien {
	
	/**
	 * Initialize this new Mazub character with given x position, given y position, given
	 * sprites and given world.
	 * 
	 * @effect	| super(world, x, y, 100, 500 ,sprites, 100, 800, 300, 100,	90, 1000, true);
	 * 
	 * @param 	x
	 * 			The initial x position for this new Mazub character.
	 * @param 	y
	 * 			The initial y position for this new Mazub character.
	 * @param	sprites
	 * 			The series of initial sprites for this new Mazub character.
 	 * @param	world
 	 * 			The world for this new Mazub character.
	 */
	@Raw
	public Mazub(World world, double x, double y, Sprite[] sprites) {
		super(world, x, y, 100, sprites, null);		
	}
	
//	/**
//	 * This Mazub character's properties are updated for the given time duration.
//	 * 
//	 * @param 	duration
//	 * 			The duration for which advanceTime should work.
//	 * @effect	| super.advanceTime(duration)
//	 * @throws 	IllegalArgumentException
//	 * 			The given duration is not a valid duration. A duration is not valid
//	 * 			if it is smaller than 0 or greater than or equal to 0.2.
//	 * 		    | (duration < 0) || (duration >= 0.2)	
//	 */
//	public void advanceTime(double duration) throws IllegalArgumentException {
//		super.advanceTime(duration);
//	}
	
}