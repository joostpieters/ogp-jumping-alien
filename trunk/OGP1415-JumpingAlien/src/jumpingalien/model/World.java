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
						TerrainType[] terrain, int[][] terrainPositions) {
		
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
	
	private void setObjectAt(int x, int y, GameObject object) {
		assert (object.isValidX(x)) && (object.isValidY(y));
		int width = object.getWidth();
		int height = object.getHeight();
		for (int i = 0; i<width; i++) {
			if (x+i > getXLimit())
				break;
			for (int j = 0; j<height; j++) {
				if (y+j > getYLimit())
					break;
				this.gameObjects[x + i][y + j] = object;
			}
		}
				
	}
	
	private void removeObjectAt(int x, int y) {
		GameObject object = getObjectAt(x,y);
		int[] origin = object.getPosition();
		int width = object.getWidth();
		int height = object.getHeight();
		for (int i = 0; i<width; i++) {
			if (origin[0]+i > getXLimit())
				break;
			for (int j = 0; j<height; j++) {
				if (origin[1]+j > getYLimit())
					break;
				this.gameObjects[origin[0] + i][origin[1] + j] = null;
			}
		}
	}
	
	
	
	private GameObject[][] gameObjects;
	
	//no documentation
	public void advanceTime() {
		Mazub myMazub = (Mazub) getObjectAt(mazubPosition[0],mazubPosition[1]);
		removeObjectAt(mazubPosition[0],mazubPosition[1]);
		myMazub.advanceTime();
		int[] new_pos = myMazub.getPosition();
		setObjectAt(new_pos[0],new_pos[1],myMazub);
		mazubPosition = new_pos;
		
		for(int i = 0; i < getXLimit(); i++) {
			for(int j = 0; j < getYLimit(); j++) {
				if(! (mazubPosition[0] == i && mazubPosition[1] == j)) {
					GameObject myObject = getObjectAt(i,j);
					removeObjectAt(i,j);
					myObject.advanceTime();
					setObjectAt(i,j,myObject);
				}
					
			}
		}
	}
	
	int[] mazubPosition;
	
	public int[] getWindowSize() {
		return this.windowSize;
	}
	
	public void setWindowSize(int width, int height) {
		this.windowSize[0] = width;
		this.windowSize[1] = height;
	}
	
	private int[] windowSize;
	
	public int[] getWindowPosition() {
		return this.windowPosition;
	}
	
	private void setWindowPosition(int x, int y) {
		this.windowPosition[0] = x;
		this.windowPosition[1] = y;
	}
	
	private int[] windowPosition;
	
	public TerrainType getTerrainAt(int x, int y) {
		int[] location = getMatchingTile(x,y);
		return this.tiles[location[0]][location[1]];
	}
	
	public void setTerrainAt(int x, int y, TerrainType terrain) {
		int[] location = getMatchingTile(x,y);
		this.tiles[location[0]][location[1]] = terrain;
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
	
}
