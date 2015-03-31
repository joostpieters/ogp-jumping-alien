package jumpingalien.part2.facade;

import java.util.Collection;

import jumpingalien.model.GameObject;
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
			throw new ModelException(exc); //Hide orange bar
		}
	}

	@Override
	public void endJump(Mazub alien) {
		try {
			alien.endJump();
		}
		catch (JumpingException exc) {
			throw new ModelException(exc); //Hide orange bar
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
	public void advanceTime(Mazub alien, double dt) {
		// TODO Auto-generated method stub
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
		int[][] emptyArray2 = {};
		World.TerrainType[] emptyArray3 = {};
		GameObject[] emptyArray4 = {};
		int[] windowSize = {visibleWindowWidth,visibleWindowHeight};
		World myWorld = new World(tileSize*nbTilesX-1,tileSize*nbTilesY-1, tileSize, 
				emptyArray4, emptyArray2, emptyArray3, emptyArray2,
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
		int left
		int right
		int top
		int bottom
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY,
			int tileType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMazub(World world, Mazub alien) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isImmune(Mazub alien) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlant(World world, Plant plant) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Plant> getPlants(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Plant plant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addShark(World world, Shark shark) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Shark> getSharks(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Shark shark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School createSchool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSlime(World world, Slime slime) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getLocation(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School getSchool(Slime slime) {
		// TODO Auto-generated method stub
		return null;
	}

}
