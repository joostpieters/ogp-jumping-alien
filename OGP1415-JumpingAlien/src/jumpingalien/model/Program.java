package jumpingalien.model;
import java.util.HashMap;
import java.util.Map;

import jumpingalien.part3.programs.IProgramFactory.Direction;
import be.kuleuven.cs.som.annotate.*;

public class Program {
	
	private Map<String, GameObject> objMap;
	private Map<String, Direction> dirMap;
	private Map<String, Double>	doubleMap;
	private Map<String, Boolean> boolMap;

	public Program() {
		GAME_OBJECT = null;
		objMap = new HashMap<String, GameObject>();
		dirMap = new HashMap<String, Direction>();
		doubleMap = new HashMap<String, Double>();
		boolMap = new HashMap<String, Boolean>();
	}
	
	private GameObject GAME_OBJECT;
	private Statement MAIN_STATEMENT;
	
	
	
	public GameObject getGameObject() {
		return GAME_OBJECT;
	}
	
	public void setGameObject(GameObject gameObject) {
		assert GAME_OBJECT == null;
		GAME_OBJECT = gameObject;
	}
	
	public Statement getMainStatement() {
		return MAIN_STATEMENT;
	}
	
	public void setMainStatement(Statement statement) {
		assert MAIN_STATEMENT == null;
		MAIN_STATEMENT = statement;
	}
	
	public void initialiseVariables(Map<String, Type> globalVariables) {
		for (String key : globalVariables.keySet()) {
			if (globalVariables.get(key) == Type.GAME_OBJECT)
				objMap.put(key, null);
			else if (globalVariables.get(key) == Type.BOOLEAN)
				boolMap.put(key, false);
			else if (globalVariables.get(key) == Type.DIRECTION)
				dirMap.put(key, null);
			else if (globalVariables.get(key) == Type.DOUBLE)
				doubleMap.put(key, 0.0);
		}
	}


	public void advanceTime(double duration) {
		return;
	}

	public Object getVariableValue(String param, Type type) {
		if (type == Type.GAME_OBJECT)
			return objMap.get(param);
		if (type == Type.DIRECTION)
			return dirMap.get(param);
		if (type == Type.DOUBLE)
			return doubleMap.get(param);
		if (type == Type.BOOLEAN)
			return boolMap.get(param);
		return null;
	}
	
	public void setVariableValue(String param, Type type, Object value) {
		if (type == Type.GAME_OBJECT)
			objMap.put(param,(GameObject) value);
		if (type == Type.DIRECTION)
			dirMap.put(param,(Direction) value);
		if (type == Type.DOUBLE)
			doubleMap.put(param,(Double) value);
		if (type == Type.BOOLEAN)
			boolMap.put(param,(Boolean) value);
	}

}
