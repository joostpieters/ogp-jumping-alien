package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.model.elements.Slime;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of schools with an association with the class Slime.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class School {

	/**
	 * Initialize this new school.
	 * 
	 * @post  | new.getSlimes() != null
	 */
	public School() {
		slimes = new HashSet<Slime>();
	}
	
	/**
	 * Return the slimes that belong to this school.
	 */
	@Basic
	public Set<Slime> getSlimes() {
		return slimes;
	}
	
	/**
	 * Check whether this school contains the given slime.
	 * 
	 * @param slime
	 * 		  The slime to check
	 * @return  | result == getSlimes().contains(slime)
	 */
	public boolean hasAsSlime(Slime slime) {
		return getSlimes().contains(slime);
	}
	
	/**
	 * Add the given slime to this school.
	 * 
	 * @param slime
	 * 		  The slime to add.
	 * @pre	    | canHaveAsSlime(slime)
	 * @pre	    | slime.getSchool() == this
	 * @effect 	| getSlimes().add(slime)
	 * 
	 */
	public void addAsSlime(Slime slime) {
		assert canHaveAsSlime(slime);
		assert slime.getSchool() == this;
		getSlimes().add(slime);
	}
	
	/**
	 * Remove the given slime of this school.
	 * 
	 * @param slime
	 * 		  The slime to be removed.
	 * @pre	    | slime.getSchool() != this
	 * @effect  | getSlimes().remove(slime)
	 */
	public void removeAsSlime(Slime slime) {
		assert slime.getSchool() != this;
		getSlimes().remove(slime);
	}
	
	/**
	 * Return the number of slimes of this school.
	 * 
	 * @return | result == getSlimes().size()
	 */
	public int getNbSlimes() {
		return getSlimes().size();
	}
	
	/**
	 * Check whether this school can have the given slime as one of its slimes.
	 * 
	 * @param slime
	 * 		  The slime to check.
	 * @return	| result == (slime != null)
	 */
	public boolean canHaveAsSlime(Slime slime) {
		return slime != null;
	}
	
	/**
	 * Variable registering the slimes of this school.
	 */
	private Set<Slime> slimes;
}
