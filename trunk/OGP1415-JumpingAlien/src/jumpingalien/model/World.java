/**
 * 
 */
package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * @author Jonathan
 *
 */
public class World {
	
	public World(int xLimit, int yLimit, int tileLength, 
					GameObject[] objects, int[][]  objectPositions,
						TerrainType[] terrains, int[][] terrainPositions,
						int[] windowSize) {
		X_LIMIT = xLimit;
		Y_LIMIT = yLimit;
		TILE_LENGTH = tileLength;
		gameObjects = new GameObject[xLimit+1][yLimit+1];
		tiles = new TerrainType[(xLimit+1)/tileLength][(yLimit+1)/tileLength];
		
		for(int i = 0; i < (xLimit+1)/tileLength; i++)
			for(int j = 0; j < (yLimit+1)/tileLength; j++)
				setTerrainAt(i,j,TerrainType.AIR);
		
//		for (int i = 0; i < objects.length; i++) 
//			setObjectAt(objectPositions[i][0],objectPositions[i][1],objects[i]);
		
		for (int i = 0; i < terrains.length; i++)
			setTerrainAt(terrainPositions[i][0],terrainPositions[i][1],terrains[i]);
		
		setWindowSize(windowSize[0],windowSize[1]);
		setWindowPosition(0,0);
		adjustWindow();
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
	
	
	public GameObject getObjectAt(int x, int y) {
		return gameObjects[x][y];
	}
	
	public void setObjectAt(int x, int y, GameObject object) {
		assert (object.isValidPosition(x,y));
		int width = object.getWidth();
		int height = object.getHeight();
		for (int i = 1; i<width-1; i++) {
			if (x+i > getXLimit())
				break;
			for (int j = 1; j<height-1; j++) {
				if (y+j > getYLimit())
					break;
				this.gameObjects[x + i][y + j] = object;
			}
		}	
		if (object instanceof Mazub) {
			int[] pos = {x,y};
			setMazubPosition(pos);
		}
	}
	
	public void removeObjectAt(int x, int y) {
		GameObject object = getObjectAt(x,y);
		if (object == null)
				return;
		int[] origin = object.getPosition();
		int width = object.getWidth();
		int height = object.getHeight();
		for (int i = 1; i<width-1; i++) {
			if (origin[0]+i > getXLimit())
				break;
			for (int j = 1; j<height-1; j++) {
				if (origin[1]+j > getYLimit())
					break;
				this.gameObjects[origin[0] + i][origin[1] + j] = null;
			}
		}
	}
	
	private GameObject[][] gameObjects;
	
	//no documentation
	public void advanceTime(double duration) throws IllegalArgumentException {
		if ((duration < 0) || (duration >= 0.2))
			throw new IllegalArgumentException("Illegal time duration!");
		Mazub myMazub = (Mazub) getObjectAt(getMazubPosition()[0],getMazubPosition()[1]);
		removeObjectAt(getMazubPosition()[0],getMazubPosition()[1]);
		myMazub.advanceTime(duration);
		int[] new_pos1 = myMazub.getPosition();
		setObjectAt(new_pos1[0],new_pos1[1],myMazub);
		setMazubPosition(new_pos1);
		
		adjustWindow();
		
		for(int i = 0; i <= getXLimit(); i++) {
			for(int j = 0; j <= getYLimit(); j++) {
				if(! (getMazubPosition()[0] == i && getMazubPosition()[1] == j)) {
					GameObject myObject = getObjectAt(i,j);
					if (i == myObject.getX() && j == myObject.getY()) {
					removeObjectAt(i,j);
					myObject.advanceTime(duration);
					int[] new_pos2 = myObject.getPosition();
					setObjectAt(new_pos2[0],new_pos2[1],myObject);
					if ( new_pos2[0] < 0 || new_pos2[0] > getXLimit() ||
						 new_pos2[1] < 0 || new_pos2[0] > getYLimit() )
						myObject.terminate(true);
					
					}
				}
					
			}
		}
	}
	
	private int[] mazubPosition = new int[2];
	
	public int[] getMazubPosition() {
		return mazubPosition;
	}

	private void setMazubPosition(int[] mazubPosition) {
		this.mazubPosition = mazubPosition;
	}

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
		return ( getWindowPosition()[0] >= 0 && 
				 getWindowPosition()[0] <= (getXLimit() - getWindowSize()[0]) &&
				 getWindowPosition()[1] >= 0 &&
				 getWindowPosition()[1] <= (getYLimit() - getWindowSize()[1]) );
				
	}
	
	public TerrainType getTerrainAt(int x, int y) {
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
	}

	public void adjustWindow() {
		int[] pos = getMazubPosition();
		if ((pos[0] - getWindowPosition()[0]) < 200)
			if (canHaveAsWindowPosition(pos[0]-200, getWindowPosition()[1]))
				setWindowPosition(pos[0]-200, getWindowPosition()[1]);
			else
				setWindowPosition(0,getWindowPosition()[1]);
		
		if (((getWindowPosition()[0] + getWindowSize()[0]) - pos[0]) < 200)
			if (canHaveAsWindowPosition(pos[0]+200, getWindowPosition()[1]))
				setWindowPosition(pos[0]+200, getWindowPosition()[1]);
			else
				setWindowPosition(getXLimit() - getWindowSize()[0], getWindowPosition()[1]);
		
		if ((pos[1] - getWindowPosition()[1]) < 200)
			if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1]-200))
				setWindowPosition(getWindowPosition()[0], pos[1]-200);
			else
				setWindowPosition(getWindowPosition()[0], 0);
		
		if (((getWindowPosition()[1] + getWindowSize()[1]) - pos[1]) < 200)
			if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1] + 200))
				setWindowPosition(getWindowPosition()[0], pos[1] + 200);
			else
				setWindowPosition(getWindowPosition()[0], getYLimit() - getWindowSize()[1]);
	}
	
	
}
