/**
 * 
 */
package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import jumpingalien.part3.programs.IProgramFactory.Direction;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worlds involving several properties. 
 * This class has an association with the class of game objects.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class World {
	
	/**
	 * Initialize this new world with the given parameters.
	 * 
	 * @param xLimit
	 * 		  The maximum pixel position in the x direction.
	 * @param yLimit
	 * 		  The maximum pixel position in the y direction.
	 * @param tileLength
	 * 		  The length of a square tile.
	 * @param windowSize
	 * 		  The size of the window the player can see.
	 * @param targetTileX
	 * 		  The x position of the target tile.
	 * @param targetTileY
	 * 		  The y position of the target tile.
	 * 
	 * @pre		| xLimit > 0;
	 * @pre		| yLimit > 0;
	 * 
	 * @post	| new.getXLimit() == xLimit
	 * @post	| new.getYLimit() == yLimit
	 * @post	| new.getTileLength() == tileLength
	 * @post	| new.getTargetTileX() == targetTileX
	 * @post	| new.getTargetTileY() == targetTileY
	 * @post	| new.getGameObjects() == HashSet<GameObject>()
	 * @post	| for all pixels in this world with coordinates x and y
	 * 			| 	getTerrainAt(x,y) == TerrainType.AIR
	 * @effect  | setWindowSize(windowSize[0],windowSize[1])
	 * @effect 	| setWindowPosition(0,0)
	 * @effect 	| adjustWindow()
	 * @effect 	| setGameOver(false)
	 * @effect 	| setDidPlayerWin(false)
	 */
	public World(int xLimit, int yLimit, int tileLength,
						int[] windowSize, int targetTileX, int targetTileY) {
		assert xLimit > 0;
		assert yLimit > 0;
		
		X_LIMIT = xLimit;
		Y_LIMIT = yLimit;
		TILE_LENGTH = tileLength;
		gameObjects = new HashSet<GameObject>();
		tiles = new GameTile[(xLimit+1)/tileLength][(yLimit+1)/tileLength];
		TARGET_TILE_X = targetTileX;
		TARGET_TILE_Y = targetTileY;
		terrainObjectSet = new HashSet<>();
		
		for(int i = 0; i < (xLimit+1)/tileLength; i++) {
			for(int j = 0; j < (yLimit+1)/tileLength; j++) {
				tiles[i][j] = new GameTile((double)i*tileLength, (double) j*tileLength, this, TerrainType.AIR);
				terrainObjectSet.add(tiles[i][j]);
			}
		}
		setWindowSize(windowSize[0],windowSize[1]);
		setWindowPosition(0,0);
		adjustWindow();
		setGameOver(false);
		setDidPlayerWin(false);
	}
	
	/**
	 * Associate the given object with this world.
	 * 
	 * @param obj
	 * 		  The object that should be associated with this world.
	 * @effect  | obj.setMyWorld(this)
	 * @effect  | getGameObjects().add(obj)
	 * @effect	| if (obj instanceof Mazub)
	 * 			|	setMyMazub((Mazub) obj)
	 */
	public void addObject(GameObject obj) {
		obj.setMyWorld(this);
		getGameObjects().add(obj);
		if(obj instanceof Mazub)
			setMyMazub((Mazub) obj);
	}
	
	/**
	 * Return a set of all the game objects of a certain class.
	 * 
	 * @param c
	 * 		  The class from which to retrieve the game objects.
	 * @return | for each obj in getGameObjects()
	 * 		   |    if(c.isInstance(obj))
	 * 		   | 	   result.contains(obj)
	 */
	public <T> Set<T> getAllInstancesOf(Class<T> c) { 
		Set<T> result = new HashSet<>();
		for(GameObject obj : getGameObjects()) {
			if(c.isInstance(obj))
				result.add((T) obj);
		}
		return result;
	}

	
	/**
	 * Return the x limit of this world.
	 */
	@Basic @Immutable
	public int getXLimit() {
		return X_LIMIT;
	}
	
	/**
	 * Variable registering the x limit of this world.
	 */
	private final int X_LIMIT;
	
	
	/**
	 * Return the y limit of this world.
	 */
	@Basic @Immutable
	public int getYLimit() {
		return Y_LIMIT;
	}
	
	/**
	 * Variable registering the y limit of this world.
	 */
	private final int Y_LIMIT; 
	
	/**
	 * Return the tile length of this world.
	 */
	@Basic @Immutable
	public int getTileLength() {
		return TILE_LENGTH;
	}
	
	/**
	 * Variable registering the tile length of this world.
	 */
	private final int TILE_LENGTH;
	
	/**
	 * Remove the given object from this world.
	 * 
	 * @param obj
	 * 		  The object to remove
	 * @effect | getGameObjects().remove(obj)
	 */
	public void removeObject(GameObject obj) {
		getGameObjects().remove(obj);
	}
	
	/**
	 * Return the set of game objects that is associated with this world.
	 */
	@Basic
	public Set<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	/**
	 * Variable registering the game objects that are associated with this world.
	 */
	private Set<GameObject> gameObjects;
	
    //no documentation needed
    public void advanceTime(double duration) throws IllegalArgumentException {

            //if ((duration < 0) || (duration >= 0.2))
                    //throw new IllegalArgumentException("Illegal time duration!");
//    		
            getMyMazub().advanceTime(duration);
//            GameElement a = getMyMazub().getSearchObject(Direction.RIGHT);
//            if(a instanceof GameTile)
//            	System.out.println(((GameTile) a).getTerrainType());
//            else
//            	System.out.println(a);
            
            int[] new_pos1 = myMazub.getPosition();
            if ( !((new_pos1[0] <= getXLimit()) && (new_pos1[0] >= 0) ) ||
                            (! ((new_pos1[1] <= getYLimit()) && (new_pos1[1] >= 0))))
                    myMazub.terminate(true);


            if(GameObject.rectanglesCollide(new_pos1[0],new_pos1[1],getMyMazub().getWidth(),getMyMazub().getHeight(),
                            getTargetTileX()*getTileLength(),getTargetTileY()*getTileLength(),(getTargetTileX() + 1)*getTileLength(),(getTargetTileY() + 1)*getTileLength())) {
                    myMazub.terminate(true);
                    setDidPlayerWin(true);
            }
                    
            adjustWindow();

            Set<GameObject> copySet = new HashSet<>();
            copySet.addAll(getGameObjects());
            for(GameObject obj: copySet) {
                                            if(! (obj instanceof Mazub))  {
                                                    obj.advanceTime(duration);
                                                    int[] new_pos2 = obj.getPosition();
                                                    if ( new_pos2[0] < 0 || new_pos2[0] > getXLimit() ||
                                                                    new_pos2[1] < 0 || new_pos2[1] > getYLimit() )
                                                            obj.terminate(true);
                                            }
            }
    }
	
	/**
	 * Return the Mazub that is associated with this world.
	 */
	@Basic
	public Mazub getMyMazub() {
		return myMazub;
	}

	/**
	 * Set the Mazub that is associated with this world to the given Mazub.
	 * 
	 * @param myMazub
	 * 		  The new Mazub for this world.
	 * @post  | new.getMyMazub() == myMazub
	 */
	private void setMyMazub(Mazub myMazub) {
		this.myMazub = myMazub;
	}

	/**
	 * Variable registering the Mazub that is associated with this world.
	 */
	private Mazub myMazub;
	
	/**
	 * Return the window size of this world as an array of width and height.
	 */
	@Basic
	public int[] getWindowSize() {
		return this.windowSize;
	}
	
	/**
	 * Set the window size of this world to the given width and height.
	 * 
	 * @param width
	 * 		  The new width for this world.
	 * @param height
	 * 		  The new height for this world.
	 * @post  | new.getWindowSize() == {width,height}
	 */
	public void setWindowSize(int width, int height) {
		this.windowSize[0] = width;
		this.windowSize[1] = height;
	}
	
	/**
	 * Variable registering the window size of this world.
	 */
	private int[] windowSize = new int[2];
	
	/**
	 * Return the window position of this world.
	 */
	public int[] getWindowPosition() {
		return this.windowPosition;
	}
	
	/**
	 * Set the window position of this world to the given x and y.
	 * 
	 * @param x
	 * 		  The new x position of the windows size for this world.
	 * @param y
	 * 		  The new y position of the windows size for this world
	 * @pre	  | canHaveAsWindowPosition(x, y)
	 * @post  | new.getWindowPosition()[0] == x
	 * 		  | new.getWindowPosition()[1] == y
	 */
	@Raw
	private void setWindowPosition(int x, int y) {
		assert canHaveAsWindowPosition(x, y);
		this.windowPosition[0] = x;
		this.windowPosition[1] = y;
	}
	
	/**
	 * Variable registering the window position of this world. The window position is an array consisting
	 * of the left bottom pixel's coordinates.
	 */
	private int[] windowPosition = new int[2];
	
	/**
	 * Check whether this world can have the given x and y as its window position.
	 * 
	 * @param x
	 * 		  The x position to check
	 * @param y
	 * 		  The y position to check
	 * @return  | result == ( x >= 0 && 
	 *			| 			x <= (getXLimit() - getWindowSize()[0]) &&
	 *			| 			y >= 0 &&
	 *		 	|			y <= (getYLimit() - getWindowSize()[1]) )
	 */
	public boolean canHaveAsWindowPosition(int x, int y) {
		return ( x >= 0 && 
				 x <= (getXLimit() - getWindowSize()[0] + 1) &&
				 y >= 0 &&
				 y <= (getYLimit() - getWindowSize()[1] + 1) );
	}
	
	/**
	 * Get the terrain type of the given position.
	 * 
	 * @param x
	 * 		  The x coordinate of the position
	 * @param y
	 * 		  The y coordinate of the position
	 * @return  | if (! isInsideBoundaries(x,y))
	 * 			|	 then result == TerrainType.AIR
	 * 			| else
	 * 			|    result == tiles.[getMatchingTile(x,y)[0]][getMatchingTile(x,y)[1]]
	 */
	public TerrainType getTerrainAt(int x, int y) {
		if (! isInsideBoundaries(x,y))
			return TerrainType.AIR;
		int[] location = getMatchingTile(x,y);
		return this.tiles[location[0]][location[1]].getTerrainType();
	}
	
	public GameTile getTerrainObjectAt(int x, int y) {
		//if (! isInsideBoundaries(x,y))
			//return null;
		int[] location = getMatchingTile(x,y);
		return this.tiles[location[0]][location[1]];
	}
	
	public Set<GameTile> getTerrainObjects() {
		return terrainObjectSet;
	}
	
	private Set<GameTile> terrainObjectSet;
	
	/**
	 * Set the terrain at the given position to the given terrain type.
	 * 
	 * @param x
	 * 		  The x position for the new terrain type
	 * @param y
	 * 		  The y position for the new terrain type
	 * @param terrain
	 * 		  The new terrain type for this position
	 * @pre	  | isInsideBoundaries(x*getTileLength(),y*getTileLength())
	 * @post  | new.getTerrainAt(x*getTileLength(),y*getTileLength()) == terrain
	 */
	public void setTerrainAt(int x, int y, TerrainType terrain) {
		assert isInsideBoundaries(x*getTileLength(),y*getTileLength());
		this.tiles[x][y].setTerrainType(terrain);
	}
	
	/**
	 * Get the tile that belongs to the given position.
	 * 
	 * @param x
	 * 		  The x position from where to retrieve the tile
	 * @param y
	 * 		  The y position from where to retrieve the tile
	 * @pre	   | isInsideBoundaries(x, y)
	 * @return | result == {x/getTileLength(),y/getTileLength()}
	 */
	public int[] getMatchingTile(int x, int y) {
		assert isInsideBoundaries(x, y);
		int[] result = {x/getTileLength(),y/getTileLength()};
		return result;
	}

	/**
	 * Variable registering the terrain type on tiles.
	 */
	private GameTile[][] tiles;
	
	/**
	 * An enumeration introducing different terrain types used to express the terrain of a tile.
	 */
	@Value
	public static enum TerrainType {
		AIR(true), WATER(true), MAGMA(true), SOLID_GROUND(false);
		
		/**
		 * Set the passable state of this terrain type to the given flag.
		 * 
		 * @param flag
		 * 		  The passable state for this terrain type
		 * @post  | new.isPassable() == flag
		 */
		private TerrainType(boolean flag) {
			this.passable = flag;
		}
		
		/**
		 * Return the passable state of this terrain type.
		 */
		public boolean isPassable() {
			return this.passable;
		}
		
		/**
		 * Variable registering whether a certain terrain type is passable.
		 */
		private final boolean passable;
	}
	
	/**
	 * End the game.
	 * 
	 * @effect  | setGameOver(true)
	 */
	public void endGame() {
		setGameOver(true);
	}

	/**
	 * Adjust the window position to the position of Mazub.
	 * 
//	 * @effect	| if (getMyMazub() == null)
//	 * 			|    return;
//	 * 			| else
//	 * 			|   int[] pos = getMyMazub().getPosition()
//	 * 			| 	if ((pos[0] - getWindowPosition()[0]) < 200)
//	 * 			|   	if (canHaveAsWindowPosition(pos[0]-200, getWindowPosition()[1]))
//	 * 			| 			setWindowPosition(pos[0]-200, getWindowPosition()[1])
//	 * 			|		else
//	 * 			|			setWindowPosition(0,getWindowPosition()[1])
//	 * 			|  else if (((getWindowPosition()[0] + getWindowSize()[0]) - pos[0]) < 200+getMyMazub().getWidth())
//	 * 			|	   if (canHaveAsWindowPosition(pos[0]-getWindowSize()[0]+200+getMyMazub().getWidth(), getWindowPosition()[1]))
//	 * 			|			setWindowPosition(pos[0]-getWindowSize()[0]+200+getMyMazub().getWidth(), getWindowPosition()[1])
//	 * 			|		else
//	 * 			|			setWindowPosition(getXLimit() - getWindowSize()[0], getWindowPosition()[1])
//	 * 			|  if ((pos[1] - getWindowPosition()[1]) < 200)
//	 * 			|	   if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1]-200))
//	 * 			|			setWindowPosition(getWindowPosition()[0], pos[1]-200)
//	 * 			|	   else
//	 * 			|	   		setWindowPosition(getWindowPosition()[0], 0)
//	 * 			|  else if (((getWindowPosition()[1] + getWindowSize()[1]) - pos[1]) < 200+getMyMazub().getHeight())
//	 * 			|	   if (canHaveAsWindowPosition(getWindowPosition()[0], pos[1]-getWindowSize()[1] + 200+getMyMazub().getHeight()))
//	 * 			|			setWindowPosition(getWindowPosition()[0],  pos[1]-getWindowSize()[1] + 200+getMyMazub().getHeight())
//	 * 			|	   else
//	 * 			|			setWindowPosition(getWindowPosition()[0], getYLimit() - getWindowSize()[1])
	 * 			| 
	 */
	public void adjustWindow() {
		if (getMyMazub() == null) return;
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
	
	/**
	 * Variable registering the x position of the target tile.
	 */
	private final int TARGET_TILE_X;
	
	/**
	 * Return the x position of the target tile.
	 */
	@Basic @Immutable
	public int getTargetTileX() {
		return TARGET_TILE_X;
	}

	/**
	 * Variable registering the x position of the target tile.
	 */
	private final int TARGET_TILE_Y;
	
	/**
	 * Return the y position of the target tile.
	 */
	@Basic @Immutable
	public int getTargetTileY() {
		return TARGET_TILE_Y;
	}

	/**
	 * Variable registering whether the game in this world is over.
	 */
	private boolean gameOver;

	/**
	 * Return whether the game is over.
	 */
	@Basic
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Set whether the game is over.
	 * 
	 * @param flag
	 * 		  The new game over state of this world
	 * @post  | new.isGameOver() == flag
	 * 
	 */
	private void setGameOver(boolean flag) {
		this.gameOver = flag;
	}
	
	/**
	 * Variable registering whether the player won.
	 */
	private boolean didPlayerWin;

	/**
	 * Return whether the player has won the game.
	 */
	@Basic
	public boolean getDidPlayerWin() {
		return didPlayerWin;
	}

	/**
	 * Set whether the player has won the game.
	 * 
	 * @param flag
	 * 		  The new player's winning state of this world
	 * @post  | new.getDidPlayerWin() == flag
	 */
	private void setDidPlayerWin(boolean flag) {
		this.didPlayerWin = flag;
	}
	
	/**
	 * Check whether the given x and y are inside the boundaries of the world.
	 * 
	 * @param x
	 * 		  The x position to check
	 * @param y
	 * 		  The y position to check
	 * 
	 * @return  | result == ! ( (x > getXLimit()) || (x < 0) || (y > getYLimit()) || (y < 0) )
	 */
	public boolean isInsideBoundaries(int x, int y) {
		return ! ( (x > getXLimit()) || (x < 0) || (y > getYLimit()) || (y < 0) );
	}
}
