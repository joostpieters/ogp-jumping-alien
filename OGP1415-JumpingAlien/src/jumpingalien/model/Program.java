package jumpingalien.model;
import be.kuleuven.cs.som.annotate.*;

public class Program {

	public Program(String program, GameObject gameObject) {
		this.GAME_OBJECT = gameObject;
		this.STRING = program;
	}
	
	public final GameObject GAME_OBJECT;
	
	@Immutable
	public GameObject getGameObject() {
		return GAME_OBJECT;
	}

	public final String STRING;
	
	@Immutable
	public String getString() {
		return STRING;
	}
	
	public void advanceTime(double duration) {
		return;
	}

}
