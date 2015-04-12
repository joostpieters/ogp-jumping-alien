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
		gameObjects = new GameObject[xLimit+1][yLimit+1][2];
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
		int x = obj.getPosition()[0];
		int y = obj.getPosition()[1];
		setObjectAt(x,y,obj.getZ(),obj);
	}
	
	public Set getAllInstancesOf(Class c) { //Set<GameObject> mag niet van de facade
		Set<GameObject> result = new HashSet<>();
		for(int k = 0; k<=1; k++) {
			for(int i = 0; i <= getXLimit(); i++) {
				for(int j = 0; j <= getYLimit(); j++) {
					if(c.isInstance(getObjectAt(i,j,k)))
							result.add(getObjectAt(i,j,k));
				}
			}	
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
	
	
	public GameObject getObjectAt(int x, int y, int z) {
		try {
		return gameObjects[x+1][y+1][z]; //DE RAND STAAT _NIET_ GEMARKEERD!
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void setObjectAt(int x, int y, int z, GameObject object) {
		assert (object.isValidPosition(x,y));
		int width = object.getWidth();
		int height = object.getHeight();
		for (int i = 1; i<width-1; i++) {
			if ((x+i > getXLimit()) || (x+i < 0))
				break;
			for (int j = 1; j<height-1; j++) {
				if ((y+j > getYLimit()) || (y+j < 0))
					break;
				
				this.gameObjects[x + i][y + j][z] = object;
			}
		}	
		if (object instanceof Mazub) {
			int[] pos = {x,y};
			setMazubPosition(pos);
		}
	}
	
	public void removeObjectAt(int x, int y, int z) {
		GameObject object = getObjectAt(x,y,z);
		if (object == null)
				return;
		int[] origin = object.getPosition();
		int width = object.getWidth();
		int height = object.getHeight();
		for (int i = 1; i<width-1; i++) {
			if ((origin[0]+i > getXLimit()) || (origin[0]+i < 0))
				break;
			for (int j = 1; j<height-1; j++) {
				if ((origin[1]+j > getYLimit()) || (origin[1]+j<0))
					break;
				this.gameObjects[origin[0] + i][origin[1] + j][z] = null;
			}
		}
	}
	
	private GameObject[][][] gameObjects;
	
	//no documentation
	public void advanceTime(double duration) throws IllegalArgumentException {
		//if ((duration < 0) || (duration >= 0.2))
			//throw new IllegalArgumentException("Illegal time duration!");
		Mazub myMazub = (Mazub) getObjectAt(getMazubPosition()[0],getMazubPosition()[1],0);
		removeObjectAt(getMazubPosition()[0],getMazubPosition()[1],0);
		myMazub.advanceTime(duration);
		int[] new_pos1 = myMazub.getPosition();
		setObjectAt(new_pos1[0],new_pos1[1],0,myMazub);
		setMazubPosition(new_pos1);
		if ( !((new_pos1[0] <= getXLimit()) && (new_pos1[0] >= 0) ) ||
				(! ((new_pos1[1] <= getYLimit()) && (new_pos1[1] >= 0))))
			myMazub.terminate(true);

		int[] tile = getMatchingTile(new_pos1[0],new_pos1[1]);
		if (tile[0] == getTargetTileX() && tile[1] == getTargetTileY()) {
			myMazub.terminate(true);
			setDidPlayerWin(true);
		}
		
		adjustWindow();
		assert (5 < 0.2);
		int cnt = 0; //
		for(int k = 0; k<=1; k++) {

			for(int i = 0; i <= getXLimit(); i++) {
				for(int j = 0; j <= getYLimit(); j++) {
					if(! (getMazubPosition()[0] == i && getMazubPosition()[1] == j)) {
						GameObject myObject = getObjectAt(i,j,k);
						if(myObject == null)
							continue;
						if (i == myObject.getPosition()[0] && j == myObject.getPosition()[1]) {
						removeObjectAt(i,j,k);
						if(myObject instanceof Plant) cnt++;
						myObject.advanceTime(duration);
						assert duration < 0.2;
						int[] new_pos2 = myObject.getPosition();
						setObjectAt(new_pos2[0],new_pos2[1],k,myObject);
						if ( new_pos2[0] < 0 || new_pos2[0] > getXLimit() ||
							 new_pos2[1] < 0 || new_pos2[0] > getYLimit() ) { 
							myObject.terminate(true);
							}
						
						}
					}		
				}
			}
		}
		System.out.println(cnt);
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
		int[] pos = getMazubPosition();
		if ((pos[0] - getWindowPosition()[0]) < 200) {
			if (canHaveAsWindowPosition(pos[0]-200, getWindowPosition()[1]))
				setWindowPosition(pos[0]-200, getWindowPosition()[1]);
			else
				setWindowPosition(0,getWindowPosition()[1]);
			
		}
		
		else if (((getWindowPosition()[0] + getWindowSize()[0]) - pos[0]) < 200) {
			if (canHaveAsWindowPosition(pos[0]-getWindowSize()[0]+200, getWindowPosition()[1]))
				setWindowPosition(pos[0]-getWindowSize()[0]+200, getWindowPosition()[1]);
			else
				setWindowPosition(getXLimit() - getWindowSize()[0], getWindowPosition()[1]);
		}
		if ((pos[1] - getWindowPosition()[1]) < 200) {
			if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1]-200))
				setWindowPosition(getWindowPosition()[0], pos[1]-200);
			else
				setWindowPosition(getWindowPosition()[0], 0);
		}
		
		else if (((getWindowPosition()[1] + getWindowSize()[1]) - pos[1]) < 200) {
			if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1]-getWindowSize()[1] + 200))
				setWindowPosition(getWindowPosition()[0],  pos[1]-getWindowSize()[1] + 200);
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
