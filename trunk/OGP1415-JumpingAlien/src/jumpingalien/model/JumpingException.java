package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * A class of exceptions signaling illegal jumping behavior.
 * 
 * @author Andreas Schryvers & Jonathan Oostvogels
 *
 */
public class JumpingException extends RuntimeException {
	/**
	 * Initialize this new JumpingException with a given amount and a given Mazub character.
	 * 
	 * @param 	message
	 * 			The message for this new JumpingException.
	 * @param	gameObject
	 * 			The Mazub character for this new JumpingException.
	 * @post	The character of this new JumpingException is the same as the given
	 * 			Mazub character.
	 * 			| new.getCharacter() == character;
	 * @effect 	This new JumpingException is further initialized as a new RuntimeException
	 * 			with message as diagnostic message.
	 * 			| super(message)
	 */
	public JumpingException(String message, GameObject gameObject) {
	    super(message);
	    this.character = gameObject;
	}
	
	/**
	 * Return the character of this JumpingException.
	 * @return result == this.character
	 */
	@Basic @Immutable
	public GameObject getCharacter() {
		return this.character;
	}
	
	
	/**
	 * Variable referencing the Mazub character of this JumpingException.
	 */
	private final GameObject character;
}
