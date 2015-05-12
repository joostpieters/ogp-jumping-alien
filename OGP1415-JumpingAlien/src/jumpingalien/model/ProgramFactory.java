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

public class ProgramFactory implements IProgramFactory<Expression, Statement, Type, Program> {

	
	
	public ProgramFactory() {
		MY_PROGRAM = new Program();
	} 
	
	public Program getMyProgram (){
		return MY_PROGRAM;
	}
	
	public final Program MY_PROGRAM;
	
	
	
	@Override
	public Expression createReadVariable(String variableName, Type variableType,
			SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> getMyProgram().getVariableValue((String) a[0], (Type) a[1]), 
											new Object[] {variableName,variableType}, sourceLocation);
	}

	@Override
	public Expression createDoubleConstant(double value, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> a[0], new Double[] {new Double(value)}, sourceLocation);
	}

	@Override
	public Expression createTrue(SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> a[0], new Boolean[] {new Boolean(true)}, sourceLocation);
	}

	@Override
	public Expression createFalse(SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> a[0], new Boolean[] {new Boolean(false)}, sourceLocation);
	}

	@Override
	public Expression createNull(SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> a[0], new Object[] {null}, sourceLocation);
	}

	@Override
	public Expression createSelf(SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((Program)a[0]).getGameObject(), new Object[] {getMyProgram()}, sourceLocation);
	}

	@Override
	public Expression createDirectionConstant(
			jumpingalien.part3.programs.IProgramFactory.Direction value,
			SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> a[0], new Direction[] {value}, sourceLocation);
	}

	@Override
	public Expression createAddition(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() + 
														(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createSubtraction(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() - 
														(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createMultiplication(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() *
														(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createDivision(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() /
														(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createSqrt(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> Math.sqrt((Double)((Expression)a[0]).eval()),
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createRandom(Expression maxValue, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)(a[0])).eval()*Math.random(),
				new Object[] {maxValue}, sourceLocation);
	}

	@Override
	public Expression createAnd(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Boolean)((Expression)a[0]).eval() &&
				(Boolean)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createOr(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Boolean)((Expression)a[0]).eval() ||
				(Boolean)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createNot(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ! (Boolean)((Expression)a[0]).eval(),
								new Object[] {expr}, 
									sourceLocation);
	}

	@Override
	public Expression createLessThan(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() <
				(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createLessThanOrEqualTo(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() <=
				(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createGreaterThan(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() >
				(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createGreaterThanOrEqualTo(Expression left, Expression right,
			SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> (Double)((Expression)a[0]).eval() >=
				(Double)((Expression)a[1]).eval(),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createEquals(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((((Expression)a[0]).eval() == null && ((Expression)a[1]).eval() == null)) ||
				((((Expression)a[0]).eval() != null) && 
				((Object)((Expression)a[0]).eval()).equals(
				(Object)((Expression)a[1]).eval())),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createNotEquals(Expression left, Expression right, SourceLocation sourceLocation) {
		return new Expression((Object[] a) ->  ((((Expression)a[0]).eval() == null && ((Expression)a[1]).eval() != null)) ||
				((((Expression)a[0]).eval() != null) && 
				!((Object)((Expression)a[0]).eval()).equals(
				(Object)((Expression)a[1]).eval())),
								new Object[] {left, right}, 
									sourceLocation);
	}

	@Override
	public Expression createGetX(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((GameElement)((Expression)a[0]).eval()).getX(), 
										new Object[] {expr}, sourceLocation);		
	}

	@Override
	public Expression createGetY(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((GameElement)((Expression)a[0]).eval()).getY(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public Expression createGetWidth(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((GameElement)((Expression)a[0]).eval()).getWidth(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public Expression createGetHeight(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((GameElement)((Expression)a[0]).eval()).getHeight(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public Expression createGetHitPoints(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval()).getHitPoints(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public Expression createGetTile(Expression x, Expression y, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> getMyProgram().getGameObject().getMyWorld().getTerrainObjectAt
				(((Double)(((Expression)(a[0])).eval())).intValue(),(((Double)(((Expression)(a[1])).eval()))).intValue()), 
				new Object[] {x,y}, sourceLocation);		

	}

	@Override
	public Expression createSearchObject(Expression direction, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) ->	(getMyProgram().getGameObject().getSearchObject(
								(Direction)
								((Expression)a[0]).
								eval()
								)
								)
								,
				new Object[] {direction}, sourceLocation);
	}

	@Override
	public Expression createIsMazub(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((Expression)a[0]).eval() instanceof Mazub, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsShark(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((Expression)a[0]).eval() instanceof Shark, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsSlime(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((Expression)a[0]).eval() instanceof Slime, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsPlant(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((Expression)a[0]).eval() instanceof Plant, 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsDead(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((GameObject)((Expression)a[0]).eval()).isTerminated(), 
					new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsTerrain(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((Expression)a[0]).eval() instanceof World.TerrainType, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsPassable(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> (((GameTile)(((Expression)a[0]).eval())).getTerrainType()).isPassable(), 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsWater(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((World.TerrainType)((Expression)a[0]).eval()) 
										== World.TerrainType.WATER, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsMagma(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((World.TerrainType)((Expression)a[0]).eval()) 
										== World.TerrainType.MAGMA, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsAir(Expression expr, SourceLocation sourceLocation) {
		return new Expression(
				(Object[] a) -> ((World.TerrainType)((Expression)a[0]).eval()) 
										== World.TerrainType.AIR, 
				new Object[] {expr}, sourceLocation);
	}

	@Override
	public Expression createIsMoving(Expression expr, Expression direction, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> 
									((GameObject)((Expression)a[0]).eval()).
									isMoving((Direction)((Expression)(a[1])).
											eval()), 
				new Object[] {expr, direction}, sourceLocation);	
	}

	@Override
	public Expression createIsDucking(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval()).isDucking(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public Expression createIsJumping(Expression expr, SourceLocation sourceLocation) {
		return new Expression((Object[] a) -> ((GameObject)((Expression)a[0]).eval()).isJumping(), 
				new Object[] {expr}, sourceLocation);	
	}

	@Override
	public Statement createAssignment(String variableName, Type variableType, Expression value,
			SourceLocation sourceLocation) {
		return new AssignmentStatement(getMyProgram(), sourceLocation, variableName, value, variableType);
	}

	@Override
	public Statement createWhile(Expression condition, Statement body, SourceLocation sourceLocation) {
		return new WhileStatement(getMyProgram(), sourceLocation, condition, body);
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression where,
			Expression sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		return new ForEachStatement(getMyProgram(), sourceLocation, variableName, variableKind, where, sort, sortDirection, body);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		return new BreakStatement(getMyProgram(), sourceLocation);
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		return new IfStatement(getMyProgram(), sourceLocation, condition, ifBody, elseBody);
	}

	@Override
	public Statement createPrint(Expression value, SourceLocation sourceLocation) {
		return new PrintStatement(getMyProgram(), sourceLocation, value);
	}

	@Override
	public Statement createStartRun(Expression direction, SourceLocation sourceLocation) {
		return new StartRunStatement(getMyProgram(), sourceLocation, direction);
	}

	@Override
	public Statement createStopRun(Expression direction, SourceLocation sourceLocation) {
		return new StopRunStatement(getMyProgram(), sourceLocation, direction);
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		return new StartJumpStatement(getMyProgram(),sourceLocation);
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		return new StopJumpStatement(getMyProgram(),sourceLocation);
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		return new StartDuckStatement(getMyProgram(), sourceLocation);
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		return new StopDuckStatement(getMyProgram(), sourceLocation);
	}

	@Override
	public Statement createWait(Expression duration, SourceLocation sourceLocation) {
		return new WaitStatement(getMyProgram(), sourceLocation, duration);
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		return new TrivialStatement(getMyProgram(), sourceLocation);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		return new SequenceStatement(getMyProgram(), sourceLocation, statements);
	}

	@Override
	public Type getDoubleType() {
		return Type.DOUBLE;
	}

	@Override
	public Type getBoolType() {
		return Type.BOOLEAN;
	}

	@Override
	public Type getGameObjectType() {
		return Type.GAME_OBJECT;
	}

	@Override
	public Type getDirectionType() {
		return Type.DIRECTION;
	}

	@Override
	public Program createProgram(Statement mainStatement, Map<String, Type> globalVariables) {
		getMyProgram().setMainStatement((Statement) mainStatement);
		getMyProgram().initialiseVariables((Map<String, Type>) globalVariables);
		return MY_PROGRAM;
	}
}
