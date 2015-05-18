package jumpingalien.model.elements;

import jumpingalien.model.World;

/** 
 * An interface of game elements.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public interface GameElement {
	public abstract double getX();
	public abstract double getY();
	public abstract int getHeight();
	public abstract int getWidth();	
	public abstract World getMyWorld();
}
