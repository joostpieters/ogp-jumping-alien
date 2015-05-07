/**
 * 
 */
package jumpingalien.model;

import java.util.List;
import java.util.Map;

import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;

/**
 * @author Jonathan
 *
 */
@SuppressWarnings(value = {"unchecked" })
public class ProgramFactory<E,S,T,P> implements IProgramFactory<E,S,T,P> {

	
	
	public ProgramFactory() {
		MY_PROGRAM = new Program();
	} 
	
	public Program getMyProgram (){
		return MY_PROGRAM;
	}
	
	private final Program MY_PROGRAM;
	
	
	
	@Override
	public E createReadVariable(String variableName, T variableType,
			SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> getMyProgram().getVariableValue((String) a[0], (Type) a[1]), 
											new Object[] {variableName,variableType}, sourceLocation);
	}

	@Override
	public E createDoubleConstant(double value, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> a[0], new Double[] {new Double(value)}, sourceLocation);
	}

	@Override
	public E createTrue(SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> a[0], new Boolean[] {new Boolean(true)}, sourceLocation);
	}

	@Override
	public E createFalse(SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> a[0], new Boolean[] {new Boolean(false)}, sourceLocation);
	}

	@Override
	public E createNull(SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> a[0], new Object[] {null}, sourceLocation);
	}

	@Override
	public E createSelf(SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> a[0], new Object[] {getMyProgram().getGameObject()}, sourceLocation);
	}

	@Override
	public E createDirectionConstant(
			jumpingalien.part3.programs.IProgramFactory.Direction value,
			SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> a[0], new Direction[] {value}, sourceLocation);
	}

	@Override
	public E createAddition(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) + 
														(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createSubtraction(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) - 
														(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createMultiplication(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) *
														(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createDivision(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) /
														(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createSqrt(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> Math.sqrt((Double)((Expression)a[0]).eval(getMyProgram())),
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createRandom(E maxValue, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)(a[0])).eval(getMyProgram())*Math.random(),
				new Object[] {maxValue}, sourceLocation);
	}

	@Override
	public E createAnd(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Boolean)((Expression)a[0]).eval(getMyProgram()) &&
				(Boolean)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createOr(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Boolean)((Expression)a[0]).eval(getMyProgram()) ||
				(Boolean)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createNot(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ! (Boolean)((Expression)a[0]).eval(getMyProgram()),
								new Object[] {expr}, 
									sourceLocation);
	}

	@Override
	public E createLessThan(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) <
				(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createLessThanOrEqualTo(E left, E right,
			SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) <=
				(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createGreaterThan(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) >
				(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createGreaterThanOrEqualTo(E left, E right,
			SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> (Double)((Expression)a[0]).eval(getMyProgram()) >=
				(Double)((Expression)a[1]).eval(getMyProgram()),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createEquals(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((Double)((Expression)a[0]).eval(getMyProgram())).equals(
				(Double)((Expression)a[1]).eval(getMyProgram())),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createNotEquals(E left, E right, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ! ((Double)((Expression)a[0]).eval(getMyProgram())).equals(
				(Double)((Expression)a[1]).eval(getMyProgram())),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public E createGetX(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).getX(), 
										new Object[] {expr}, sourceLocation);		
	}

	@Override
	public E createGetY(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).getY(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public E createGetWidth(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).getWidth(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public E createGetHeight(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).getHeight(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public E createGetHitPoints(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).getHitPoints(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public E createGetTile(E x, E y, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> getMyProgram().getGameObject().getMyWorld().getTerrainAt
				((int)(((Expression)(a[0])).eval(getMyProgram())),(int)(((Expression)(a[1])).eval(getMyProgram()))), 
				new Object[] {x,y}, sourceLocation);		

	}

	@Override
	public E createSearchObject(E direction, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) ->	(getMyProgram().getGameObject().getSearchObject(
								(Direction)
								((Expression)a[0]).
								eval(getMyProgram())
								)
								)
								,
				new Object[] {direction}, sourceLocation);
	}

	@Override
	public E createIsMazub(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((Expression)a[0]).eval(getMyProgram()) instanceof Mazub, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsShark(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((Expression)a[0]).eval(getMyProgram()) instanceof Shark, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsSlime(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((Expression)a[0]).eval(getMyProgram()) instanceof Slime, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsPlant(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((Expression)a[0]).eval(getMyProgram()) instanceof Plant, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsDead(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).isTerminated(), 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsTerrain(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((Expression)a[0]).eval(getMyProgram()) instanceof World.TerrainType, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsPassable(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((World.TerrainType)((Expression)a[0]).eval(getMyProgram())).isPassable(), 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsWater(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((World.TerrainType)((Expression)a[0]).eval(getMyProgram())) 
										== World.TerrainType.WATER, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsMagma(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((World.TerrainType)((Expression)a[0]).eval(getMyProgram())) 
										== World.TerrainType.MAGMA, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsAir(E expr, SourceLocation sourceLocation) {
		return (E) new Expression(
				(Object[] a) -> ((World.TerrainType)((Expression)a[0]).eval(getMyProgram())) 
										== World.TerrainType.AIR, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public E createIsMoving(E expr, E direction, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> 
									((GameObject)((Expression)a[0]).eval(getMyProgram())).isMoving((Direction) a[1]), 
				new Object[] {expr, direction}, sourceLocation);	
	}

	@Override
	public E createIsDucking(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).isDucking(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public E createIsJumping(E expr, SourceLocation sourceLocation) {
		return (E) new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval(getMyProgram())).isJumping(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public S createAssignment(String variableName, T variableType, E value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createWhile(E condition, S body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			E where,
			E sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			S body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createIf(E condition, S ifBody, S elseBody,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createPrint(E value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStartRun(E direction, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStopRun(E direction, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createWait(E duration, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createSkip(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createSequence(List<S> statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getDoubleType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getBoolType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getGameObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getDirectionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public P createProgram(S mainStatement, Map<String, T> globalVariables) {
		// TODO Auto-generated method stub
		return null;
	}


	




}
