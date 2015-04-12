/**
 * 
 */
package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 * @author Jonathan
 *
 */
public class World {
	
	public World(int xLimit, int yLimit, int tileLength,
						int[] windowSize, int targetTileX, int targetTileY) {
		X_LIMIT = xLimit;
		Y_LIMIT = yLimit;
		TILE_LENGTH = tileLength;
		gameObjects = new HashSet<GameObject>();
		tiles = new TerrainType[(xLimit+1)/tileLength][(yLimit+1)/tileLength];
		
		for(int i = 0; i < (xLimit+1)/tileLength; i++)
			for(int j = 0; j < (yLimit+1)/tileLength; j++)
				setTerrainAt(i,j,TerrainType.AIR);

		
		setWindowSize(windowSize[0],windowSize[1]);
		setWindowPosition(0,0);
		adjustWindow();
		setTargetTileX(targetTileX);
		setTargetTileY(targetTileY);
		setGameOver(false);
		setDidPlayerWin(false);
	}
	
	public void addObject(GameObject obj) {
		obj.setMyWorld(this);
		getGameObjects().add(obj);
		if(obj instanceof Mazub)
			setMyMazub((Mazub) obj);
			
	}
	
	public Set getAllInstancesOf(Class c) { //Set<GameObject> mag niet van de facade
		Set<GameObject> result = new HashSet<>();
		for(GameObject obj : getGameObjects()) {
			if(c.isInstance(obj))
				result.add(obj);
		}
		return result;
	}

	@Immutable
	@Basic
	public int getXLimit() {
		return X_LIMIT;
	}
	
	private final int X_LIMIT ; //1023 in pixels
	
	@Immutable
	@Basic
	public int getYLimit() {
		return Y_LIMIT;
	}
	
	private final int Y_LIMIT; //767 in pixels
	
	@Immutable
	@Basic
	public int getTileLength() {
		return TILE_LENGTH;
	}
	
	private final int TILE_LENGTH;
	

	
	public void removeObject(GameObject obj) {
		getGameObjects().remove(obj);
	}
	
	public Set<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	private Set<GameObject> gameObjects;
	
	//no documentation
	public void advanceTime(double duration) throws IllegalArgumentException {
		//if ((duration < 0) || (duration >= 0.2))
			//throw new IllegalArgumentException("Illegal time duration!");
		getMyMazub().advanceTime(duration);
		int[] new_pos1 = myMazub.getPosition();
		if ( !((new_pos1[0] <= getXLimit()) && (new_pos1[0] >= 0) ) ||
				(! ((new_pos1[1] <= getYLimit()) && (new_pos1[1] >= 0))))
			myMazub.terminate(true);

		int[] tile = getMatchingTile(new_pos1[0],new_pos1[1]);
		if (tile[0] == getTargetTileX() && tile[1] == getTargetTileY()) {
			myMazub.terminate(true);
			setDidPlayerWin(true);
		}
		
		adjustWindow();

		for(GameObject obj: getGameObjects()) {
						if(! (obj instanceof Mazub))  {
							obj.advanceTime(duration);
							int[] new_pos2 = obj.getPosition();
							if ( new_pos2[0] < 0 || new_pos2[0] > getXLimit() ||
									new_pos2[1] < 0 || new_pos2[0] > getYLimit() );
								//obj.terminate(true);
						}
		}

	}
	
	

	public Mazub getMyMazub() {
		return myMazub;
	}

	private void setMyMazub(Mazub myMazub) {
		this.myMazub = myMazub;
	}

	private Mazub myMazub;
	
	public int[] getWindowSize() {
		return this.windowSize;
	}
	
	public void setWindowSize(int width, int height) {
		this.windowSize[0] = width;
		this.windowSize[1] = height;
	}
	
	private int[] windowSize = new int[2];
	
	public int[] getWindowPosition() {
		return this.windowPosition;
	}
	
	private void setWindowPosition(int x, int y) {
		this.windowPosition[0] = x;
		this.windowPosition[1] = y;
	}
	
	private int[] windowPosition = new int[2];
	
	public boolean canHaveAsWindowPosition(int x, int y) {
		return ( x >= 0 && 
				 x <= (getXLimit() - getWindowSize()[0]) &&
				 y >= 0 &&
				 y <= (getYLimit() - getWindowSize()[1]) );
				
	}
	
	public TerrainType getTerrainAt(int x, int y) {
		if( (x > getXLimit()) || (x < 0) || (y > getYLimit()) || (y < 0))
			return TerrainType.AIR;
		int[] location = getMatchingTile(x,y);
		return this.tiles[location[0]][location[1]];
	}
	
	public void setTerrainAt(int x, int y, TerrainType terrain) {
//		int[] location = getMatchingTile(x,y);
		this.tiles[x][y] = terrain;
	}
	
	public int[] getMatchingTile(int x, int y) {
		int[] result = {x/getTileLength(),y/getTileLength()};
		return result;
	}

	private TerrainType[][] tiles;
	
	
	@Value
	public enum TerrainType {
		AIR(true), WATER(true), MAGMA(true), SOLID_GROUND(false);
		
		private TerrainType(boolean isPassable) {
			this.passable = isPassable;
		}
		
		public boolean isPassable() {
			return this.passable;
		}
		
		private final boolean passable;
	}
	
	
	public void endGame() {
		setGameOver(true);
	}

	public void adjustWindow() {
		if(getMyMazub() == null) return;
		int[] pos = getMyMazub().getPosition();
		if ((pos[0] - getWindowPosition()[0]) < 200) {
			if (canHaveAsWindowPosition(pos[0]-200, getWindowPosition()[1]))
				setWindowPosition(pos[0]-200, getWindowPosition()[1]);
			else
				setWindowPosition(0,getWindowPosition()[1]);
			
		}
		
		else if (((getWindowPosition()[0] + getWindowSize()[0]) - pos[0]) < 200+getMyMazub().getWidth()) {
			if (canHaveAsWindowPosition(pos[0]-getWindowSize()[0]+200+getMyMazub().getWidth(), getWindowPosition()[1]))
				setWindowPosition(pos[0]-getWindowSize()[0]+200+getMyMazub().getWidth(), getWindowPosition()[1]);
			else
				setWindowPosition(getXLimit() - getWindowSize()[0], getWindowPosition()[1]);
		}
		if ((pos[1] - getWindowPosition()[1]) < 200) {
			if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1]-200))
				setWindowPosition(getWindowPosition()[0], pos[1]-200);
			else
				setWindowPosition(getWindowPosition()[0], 0);
		}
		
		else if (((getWindowPosition()[1] + getWindowSize()[1]) - pos[1]) < 200+getMyMazub().getHeight()) {
			if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1]-getWindowSize()[1] + 200+getMyMazub().getHeight()))
				setWindowPosition(getWindowPosition()[0],  pos[1]-getWindowSize()[1] + 200+getMyMazub().getHeight());
			else
				setWindowPosition(getWindowPosition()[0], getYLimit() - getWindowSize()[1]);
		}
	}
	
	private int targetTileX;
	public int getTargetTileX() {
		return targetTileX;
	}
	public void setTargetTileX(int targetTileX) {
		this.targetTileX = targetTileX;
	}

	private int targetTileY;
	public int getTargetTileY() {
		return targetTileY;
	}
	public void setTargetTileY(int targetTileY) {
		this.targetTileY = targetTileY;
	}

	private boolean gameOver;

	public boolean isGameOver() {
		return gameOver;
	}

	private void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	private boolean didPlayerWin;

	public boolean getDidPlayerWin() {
		return didPlayerWin;
	}

	private void setDidPlayerWin(boolean didPlayerWin) {
		this.didPlayerWin = didPlayerWin;
	}
	
}
