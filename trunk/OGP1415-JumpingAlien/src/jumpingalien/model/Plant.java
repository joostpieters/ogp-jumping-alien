package jumpingalien.model;

import jumpingalien.util.Sprite;
import be.kuleuven.cs.som.annotate.*;

public class Plant extends AutomaticObject {

	public Plant(World world, double x, double y, Sprite[] sprites) {
		super(world, x, y, 1, 1, sprites, 50, 0, 50, 50, 1, 0, false);
	}

	
	
	
	public void advanceTime(double duration) {
		
		if(getTimer()+duration > getGoal()) {
			super.advanceTime(0.5-getTimer());
			startNewMovement();
			super.advanceTime(duration-(getGoal()-getTimer()));
			setTimer(getTimer()+duration-getGoal());
			
		}
		else {
			super.advanceTime(duration);
			setTimer(getTimer()+duration);
		}
		
	}
	
	
	public void handleInteraction(double duration) {
		
		//Handled by Mazub
		return;
	}
	
	protected void startNewMovement() {
		setGoal(0.5);
		setTimer(0);
		endMove();
		if(this.getXDirection() == Direction.LEFT)
			this.startMove(Direction.RIGHT);
		else
			this.startMove(Direction.LEFT);
	}

	
	
	//Gegokt, want staat niet in opgave
	public Sprite getCurrentSprite() {
		if(getXDirection() == Direction.LEFT)
			return getSprites()[0];
		return getSprites()[1];
	}
	
	@Override
	public void terminate(boolean insta) {
		super.terminate(true);		
	}

}
