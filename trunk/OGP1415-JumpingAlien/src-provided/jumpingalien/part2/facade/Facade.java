package jumpingalien.part2.facade;

import java.util.Collection;

import jumpingalien.model.JumpingException;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.World.TerrainType;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacadePart2 {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		Mazub myMazub = new Mazub(null,(double) pixelLeftX, (double) pixelBottomY,sprites);
		return myMazub;
	}

	@Override
	public int[] getLocation(Mazub alien) {
		return alien.getPosition();
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		return new double[] {alien.getXVelocity()/100,alien.getYVelocity()/100};
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		return new double[] {alien.getXAcceleration()/100,alien.getYAcceleration()/100};
	}

	@Override
	public int[] getSize(Mazub alien) {
		return new int[] {alien.getWidth(),alien.getHeight()};
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		return alien.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub alien) {
		try {
			alien.startJump();
		}
		catch (JumpingException exc) {
			//throw new ModelException(exc); //Hide orange bar
		}
	}

	@Override
	public void endJump(Mazub alien) {
		try {
			alien.endJump();
		}
		catch (JumpingException exc) {
			//throw new ModelException(exc); //Hide orange bar
		}
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		alien.startMove(Mazub.Direction.LEFT);
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		if(alien.getXDirection() == Mazub.Direction.LEFT)
			alien.endMove();
	}

	@Override
	public void startMoveRight(Mazub alien) {
		alien.startMove(Mazub.Direction.RIGHT);
	}

	@Override
	public void endMoveRight(Mazub alien) {
		if(alien.getXDirection() == Mazub.Direction.RIGHT)
			alien.endMove();
	}

	@Override
	public void startDuck(Mazub alien) {
		try {
			alien.startDuck();
		}
		catch(IllegalStateException exc) {
			throw new ModelException("Already ducking!"); //Hide orange bar
		}
	}

	@Override
	public void endDuck(Mazub alien) {
		try {
			alien.endDuck();
		}
		catch(IllegalStateException exc) {
			throw new ModelException("Not yet ducking!"); //Hide orange bar
		}
	}

	@Override
	@Deprecated
	public void advanceTime(Mazub alien, double dt) {
		return;
	}

	@Override
	public int getNbHitPoints(Mazub alien) {
		return alien.getHitPoints();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		int[] windowSize = {visibleWindowWidth,visibleWindowHeight};
		World myWorld = new World(tileSize*nbTilesX-1,tileSize*nbTilesY-1, tileSize,
				windowSize, targetTileX, targetTileY);
		return myWorld;
	}

	@Override
	public int[] getWorldSizeInPixels(World world) {
		return new int[] {world.getXLimit(), world.getYLimit()};
	}

	@Override
	public int getTileLength(World world) {
		return world.getTileLength();
	}

	@Override
	public void startGame(World world) {
		return;
	}

	@Override
	public boolean isGameOver(World world) {
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) {
		return world.getDidPlayerWin();
	}

	@Override
	public void advanceTime(World world, double dt) {
		world.advanceTime(dt);
	}

	@Override
	public int[] getVisibleWindow(World world) {
		int left = world.getWindowPosition()[0];
		int right = left + world.getWindowSize()[0];
		int bottom = world.getWindowPosition()[1];
		int top = bottom + world.getWindowSize()[1];
		int[] result = {left,bottom,right,top};
		return result;
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		return new int[] {tileX*world.getTileLength(), tileY*world.getTileLength()};
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {
		int l = world.getTileLength();
		int size = (((pixelRight+l)-(pixelLeft-l))/l) //HORIZONTAL
							*(((pixelTop+l)-(pixelBottom-l))/l); //VERTICAL
		int[][] result = new int[size][2];
		int i = pixelLeft;
		int j = pixelBottom;
		int k = 0;
		while(j<pixelTop+l) {
			while(i<pixelRight+l) {
				result[k][0] = i/l;
				result[k][1] = j/l;
				i += l;
				k += 1;
			}
			i = pixelLeft;
			j += l;
		}
		return result;
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {
		TerrainType result = world.getTerrainAt(pixelX, pixelY);
		if(result == TerrainType.AIR)
			return 0;
		if(result == TerrainType.SOLID_GROUND)
			return 1;
		if(result == TerrainType.WATER)
			return 2;
		//if(result == TerrainType.MAGMA)
		return 3;
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY,
			int tileType) {
		TerrainType[] LOOKUP = {TerrainType.AIR, TerrainType.SOLID_GROUND,
									TerrainType.WATER, TerrainType.MAGMA};
		world.setTerrainAt(tileX,tileY,LOOKUP[tileType]);

	}

	@Override
	public void setMazub(World world, Mazub alien) {
		world.addObject(alien);
	}

	@Override
	public boolean isImmune(Mazub alien) {
		return alien.getTimeToBeImmune() > 0;
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		return new Plant(null,(double) x, (double) y, sprites);
	}

	@Override
	public void addPlant(World world, Plant plant) {
		world.addObject(plant);
	}

	@Override
	public Collection<Plant> getPlants(World world) {
		return world.getAllInstancesOf(Plant.class);
	}

	@Override
	public int[] getLocation(Plant plant) {
		return plant.getPosition();
	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		return plant.getCurrentSprite();
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		return new Shark(null, x, y, sprites);
	}

	@Override
	public void addShark(World world, Shark shark) {
		world.addObject(shark);

	}

	@Override
	public Collection<Shark> getSharks(World world) {
		return world.getAllInstancesOf(Shark.class);
	}

	@Override
	public int[] getLocation(Shark shark) {
		return shark.getPosition();
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		return shark.getCurrentSprite();
	}

	@Override
	public School createSchool() {
		return new School();
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		return new Slime(null, x, y, sprites, school);
	}

	@Override
	public void addSlime(World world, Slime slime) {
		world.addObject(slime);
	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		return world.getAllInstancesOf(Slime.class);
	}

	@Override
	public int[] getLocation(Slime slime) {
		return slime.getPosition();
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		return slime.getCurrentSprite();
	}

	@Override
	public School getSchool(Slime slime) {
		return slime.getSchool();
	}

}
