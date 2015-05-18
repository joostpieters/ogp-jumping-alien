package jumpingalien.model.elements;

import jumpingalien.model.World;
import jumpingalien.model.World.TerrainType;

/** 
 * A class of game tiles. This class has an association with the class World.
 * 
 * @author 	Andreas Schryvers & Jonathan Oostvogels
 * 			2e Bachelor ingenieurswetenschappen
 * 			Subversion repository: https://code.google.com/p/ogp-jumping-alien/
 */
public class GameTile implements GameElement {

	public GameTile(double x, double y, World world, TerrainType terrain) {
		X = x;
		Y = y;
		MY_WORLD = world;
		terrainType = terrain;
	}

	@Override
	public double getX() {
		return X;
	}

	@Override
	public double getY() {
		return Y;
	}

	@Override
	public int getHeight() {
		return getMyWorld().getTileLength();
	}

	@Override
	public int getWidth() {
		return getHeight();
	}

	@Override
	public World getMyWorld() {
		return MY_WORLD;
	}
	
	public TerrainType getTerrainType() {
		return terrainType;
	}
	
	public void setTerrainType(TerrainType terrainType) {
		this.terrainType = terrainType;
	}
	
	private final World MY_WORLD;
	private final double X;
	private final double Y;
	private TerrainType terrainType;
}