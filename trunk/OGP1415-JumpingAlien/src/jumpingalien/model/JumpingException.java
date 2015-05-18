package jumpingalien.model;
import jumpingalien.model.elements.GameObject;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of exceptions signaling illegal jumping behavior.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class JumpingException extends RuntimeException {

	/**
	 * Initialize this new JumpingException with a given amount and a given game object.
	 * 
	 * @param 	message
	 * 			The message for this new JumpingException.
	 * @param	gameObject
	 * 			The game object for this new JumpingException.
	 * @post	The character of this new JumpingException is the same as the given
	 * 			game object.
	 * 			| new.getCharacter() == character;
	 * @effect 	This new JumpingException is further initialized as a new RuntimeException
	 * 			with message as diagnostic message.
	 * 			| super(message)
	 */
	public JumpingException(String message, GameObject gameObject) {
	    super(message);
	    this.CHARACTER = gameObject;
	}
	
	/**
	 * Return the character of this JumpingException.
	 */
	@Basic @Immutable
	public GameObject getCharacter() {
		return this.CHARACTER;
	}
	
	/**
	 * Variable referencing the game object of this JumpingException.
	 */
	private final GameObject CHARACTER;
	
	private static final long serialVersionUID = 1L;

}
