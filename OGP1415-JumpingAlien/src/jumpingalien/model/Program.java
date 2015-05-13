package jumpingalien.model;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import jumpingalien.part3.programs.IProgramFactory.Direction;
import be.kuleuven.cs.som.annotate.*;

public class Program {
	
	

	public Program() {
		GAME_OBJECT = null;
		MAIN_STATEMENT = null;
		objMap = new HashMap<String, GameElement>();
		dirMap = new HashMap<String, Direction>();
		doubleMap = new HashMap<String, Double>();
		boolMap = new HashMap<String, Boolean>();
		setTimeRemaining(0);
		setWaitTime(0);
		setContainsError(false);
		loopStack = new Stack<Statement>();
		setWellFormed(true);
	}

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
		setCurrentStatement(statement);
		if (statement.containsBreakOutsideLoop()) {
			setWellFormed(false);
			System.out.println("Program " + this +  " is not well formed!");
		}
	}
	
	private GameObject GAME_OBJECT;
	private Statement MAIN_STATEMENT;
	
	private Map<String, GameElement> objMap;
	private Map<String, Direction> dirMap;
	private Map<String, Double>	doubleMap;
	private Map<String, Boolean> boolMap;
	
	public Statement getCurrentStatement() {
		return currentStatement;
	}
	
	public void setCurrentStatement(Statement statement) {
		this.currentStatement = statement;
	}
	
	private Statement currentStatement;
	
	@Basic
	public double getWaitTime() {
		return waitTime;
	}
	
	public void addWaitTime(double time) {
		setWaitTime(getWaitTime() + time);
	}
	
	public void substractWaitTime(double time) {
		setWaitTime(getWaitTime() - time);
	}
	
	private void setWaitTime(double time) {
		waitTime = time;
		if(time < 0) {
			waitTime = 0;
		}
	}
	
	private double waitTime;
	
	@Basic
	public double getTimeRemaining() {
		return timeRemaining;
	}
	
	public void addTimeRemaining(double time) {
		setTimeRemaining(getTimeRemaining() + time);
	}
	
	public void substractTimeRemaining(double time) {
		setTimeRemaining(getTimeRemaining() - time);
	}
	
	private void setTimeRemaining(double time) {
		timeRemaining = time;
		if(time < 0) {
			timeRemaining = 0;
		}
	}
	
	private double timeRemaining;
	
	
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
	
	public boolean getContainsError() {
		return containsError;
	}

	void setContainsError(boolean containsError) {
		this.containsError = containsError;
	}
	
	private boolean containsError;

	public void advanceTime(double duration) {
		if (getContainsError())
			return;
		double dt = duration;		
		addTimeRemaining(dt);
		while(getTimeRemaining() > 0) {
			double a = getWaitTime();
			substractWaitTime(getTimeRemaining());
			substractTimeRemaining(a);
			if (getWaitTime() == 0 && getTimeRemaining() > 0) {
				Statement temp = getCurrentStatement();
				 try {
					 getCurrentStatement().execute();
				 }
				 catch (ClassCastException exc) {
					 this.setContainsError(true);
					 System.out.println("Runtime Type Error in program for");
					 System.out.println(getGameObject());
					 System.out.println(temp.getLocation());
					 getGameObject().endMove();
					 return;
				 }
			}
		}
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
			objMap.put(param,(GameElement) value);
		if (type == Type.DIRECTION)
			dirMap.put(param,(Direction) value);
		if (type == Type.DOUBLE)
			doubleMap.put(param,(Double) value);
		if (type == Type.BOOLEAN)
			boolMap.put(param,(Boolean) value);
	}
	
	private Stack<Statement> loopStack;

	private Stack<Statement> getLoopStack() {
		return loopStack;
	}

	protected void addLoop(Statement loop) {
		loopStack.push(loop);
	}
	
	protected Statement popLoop() {
		if (loopStack.empty())
			return null;
		return getLoopStack().pop();
	}
	
	private boolean isWellFormed;

	public boolean isWellFormed() {
		return isWellFormed;
	}

	public void setWellFormed(boolean isWellFormed) {
		if (! isWellFormed)
			setContainsError(true);
		this.isWellFormed = isWellFormed;
	}
}
