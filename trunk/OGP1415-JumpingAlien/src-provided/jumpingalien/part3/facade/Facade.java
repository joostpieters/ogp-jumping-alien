package jumpingalien.part3.facade;
import java.util.Optional;

import jumpingalien.model.Program;
import jumpingalien.model.ProgramFactory;
import jumpingalien.model.School;
import jumpingalien.model.World;
import jumpingalien.model.elements.Buzam;
import jumpingalien.model.elements.Plant;
import jumpingalien.model.elements.Shark;
import jumpingalien.model.elements.Slime;
import jumpingalien.model.statements.Statement;
import jumpingalien.model.Type;
import jumpingalien.model.Expression;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.util.Sprite;

public class Facade extends jumpingalien.part2.facade.Facade implements IFacadePart3 {
	@Override
	public Buzam createBuzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		Buzam myBuzam = new Buzam(null,(double) pixelLeftX, (double) pixelBottomY,sprites, null);
		return myBuzam;
	}

	@Override
	public Buzam createBuzamWithProgram(int pixelLeftX, int pixelBottomY,
			Sprite[] sprites, Program program) {
		Buzam myBuzam = new Buzam(null,(double) pixelLeftX, (double) pixelBottomY,sprites, program);
		return myBuzam;
	}

	@Override
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		return new Plant(null,(double) x, (double) y, sprites, program);
	}

	@Override
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		return new Shark(null, x, y, sprites, program);
	}

	@Override
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		return new Slime(null, x, y, sprites, school, program);
	}

	@Override
	public ParseOutcome<?> parse(String text) {
		IProgramFactory<Expression, Statement, Type, Program> factory = new ProgramFactory();
		ProgramParser<Expression, Statement, Type, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parseResult = parser.parseString(text);
		if(parseResult.isPresent())
			return ParseOutcome.success(parseResult.get());
		else
			return ParseOutcome.failure(parser.getErrors());
	}

	@Override
	public boolean isWellFormed(Program program) {
		return program.isWellFormed();
	}

	@Override
	public void addBuzam(World world, Buzam buzam) {
		world.addObject(buzam);
	}

	@Override
	public int[] getLocation(Buzam alien) {
		return alien.getPosition();
	}

	@Override
	public double[] getVelocity(Buzam alien) {
		return new double[] {alien.getXVelocity()/100,alien.getYVelocity()/100};
	}

	@Override
	public double[] getAcceleration(Buzam alien) {
		return new double[] {alien.getXAcceleration()/100,alien.getYAcceleration()/100};
	}

	@Override
	public int[] getSize(Buzam alien) {
		return new int[] {alien.getWidth(),alien.getHeight()};
	}

	@Override
	public Sprite getCurrentSprite(Buzam alien) {
		return alien.getCurrentSprite();
	}

	@Override
	public int getNbHitPoints(Buzam alien) {
		return alien.getHitPoints();
	}
}