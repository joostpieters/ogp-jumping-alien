package jumpingalien.model.statements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jumpingalien.model.Expression;
import jumpingalien.model.Program;
import jumpingalien.model.Type;
import jumpingalien.model.elements.Buzam;
import jumpingalien.model.elements.GameElement;
import jumpingalien.model.elements.Plant;
import jumpingalien.model.elements.Shark;
import jumpingalien.model.elements.Slime;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;
import jumpingalien.part3.programs.SourceLocation;

public class ForEachStatement extends Statement {

	public ForEachStatement(Program caller, SourceLocation location, String variableName,
			Kind variableKind,
			Expression where, Expression sort, SortDirection sortDirection, Statement body) {
		super(caller, location);
		setContainsAction(body.containsAction());
		if (this.containsAction()) {
			caller.setWellFormed(false);
			System.out.println("For each statement at " + location + " is not well formed!");
		}
		VARIABLE_NAME = variableName;
		VARIABLE_KIND = variableKind;
		WHERE = where;
		SORT = sort;
		SORT_DIRECTION = sortDirection;
		BODY = body;
		setIndex(0);
	}
	
	public String getVariableName() {
		return VARIABLE_NAME;
	}

	public Kind getVariableKind() {
		return VARIABLE_KIND;
	}

	public Expression getWhere() {
		return WHERE;
	}

	public Expression getSort() {
		return SORT;
	}

	public SortDirection getSortDirection() {
		return SORT_DIRECTION;
	}

	public Statement getBody() {
		return BODY;
	}

	public List<GameElement> getMyObjects() {
		return myObjects;
	}	
	
	public GameElement getObjectAt(int i) {
		return myObjects.get(i);
	}
	
	public int getNbObjects() {
		return myObjects.size();
	}
	
	public void addAllObjects(Set<GameElement> objects) {
		myObjects.addAll(objects);
	}
	
	protected void setMyObjects(List<GameElement> myObjects) {
		this.myObjects = myObjects;
	}

	public boolean isIterating() {
		return isIterating;
	}
	
	protected void setIterating(boolean flag) {
		isIterating = flag;
	}
	
	
	private final String VARIABLE_NAME;
	private final Kind VARIABLE_KIND;
	private final Expression WHERE;
	private final Expression SORT;
	private final SortDirection SORT_DIRECTION;
	private final Statement BODY;
	private List<GameElement> myObjects;
	private boolean isIterating;
	private int index;
	
	public int getIndex() {
		return index;
	}

	private void setIndex(int index) {
		this.index = index;
	}

	private int sortTest(GameElement obj1, GameElement obj2) {
		getCaller().setVariableValue(getVariableName(), Type.GAME_ELEMENT, obj1);
		double eval1 = (double)getSort().eval();
		getCaller().setVariableValue(getVariableName(), Type.GAME_ELEMENT, obj2);
		double eval2 = (double)getSort().eval();
		return java.lang.Double.compare(eval1, eval2);
	}
	
	private void prepareForIterating() {
		setIterating(true);
		getCaller().substractTimeRemaining(0.001);
		setMyObjects(new ArrayList<GameElement>());
		
		if(getVariableKind() == Kind.MAZUB) {
			getMyObjects().add(getCaller().getGameObject().getMyWorld().getMyMazub());
		}
		else if (getVariableKind() == Kind.TERRAIN) {
			getMyObjects().addAll(getCaller().getGameObject().getMyWorld().getTerrainObjects());
		}
		else {
			getMyObjects().addAll(getCaller().getGameObject().getMyWorld().getGameObjects());
			if(getVariableKind() == Kind.BUZAM)
				setMyObjects(getMyObjects().stream().filter((u -> (u instanceof Buzam))).collect(Collectors.toList()));
			if(getVariableKind() == Kind.PLANT)
				setMyObjects(getMyObjects().stream().filter((u -> (u instanceof Plant))).collect(Collectors.toList()));
			if(getVariableKind() == Kind.SLIME)
				setMyObjects(getMyObjects().stream().filter((u -> (u instanceof Slime))).collect(Collectors.toList()));
			if(getVariableKind() == Kind.SHARK)
				setMyObjects(getMyObjects().stream().filter((u -> (u instanceof Shark))).collect(Collectors.toList()));
		}
		
		List<GameElement >filteredList = new ArrayList<>();
		
		for(GameElement element: getMyObjects()) {
			getCaller().setVariableValue(getVariableName(), Type.GAME_ELEMENT, element);

			if((boolean)getWhere().eval() == true)
				filteredList.add(element);
		}
		
		if(getSortDirection() == SortDirection.ASCENDING)
			Collections.sort(filteredList, (GameElement e1, GameElement e2) -> sortTest(e1,e2));
		else if(getSortDirection() == SortDirection.DESCENDING)
			Collections.sort(filteredList, (GameElement e1, GameElement e2) -> -sortTest(e1,e2));
		
		setMyObjects(filteredList);
		setIndex(0);
	}
	
	@Override
	public void execute() throws ClassCastException {
		if (! isIterating()) {
			prepareForIterating();
			getCaller().addLoop(this);
		}
		
		if (getIndex() < getNbObjects() -1) {
			getCaller().setVariableValue(getVariableName(), Type.GAME_ELEMENT, getObjectAt(getIndex()));
			//
			getBody().setNextStatement(this);
			getCaller().setCurrentStatement(getBody());
			setIndex(getIndex()+1);
		}
		
		else {
			getCaller().setCurrentStatement(getNextStatement());
			setMyObjects(null);
			setIterating(false);
			getCaller().popLoop();
		}
	}

}
