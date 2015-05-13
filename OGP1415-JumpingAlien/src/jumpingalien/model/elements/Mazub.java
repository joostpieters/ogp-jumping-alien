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
}