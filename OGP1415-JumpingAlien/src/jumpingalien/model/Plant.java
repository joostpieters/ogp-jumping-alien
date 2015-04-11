package jumpingalien.model;

import jumpingalien.util.Sprite;

public class Plant extends GameObject {

	public Plant(World world, double x, double y, Sprite[] sprites) {
		super(world, x, y, 1, 1, 1, sprites, 50, 0, 50, 50, 0, 0);
		startMove(Direction.RIGHT);
	}

	
	
	
	public void advanceTime(double duration) {
		
		if(getTimer()+duration > 0.5) {
			super.advanceTime(0.5-getTimer());
			changeDirection();
			super.advanceTime(duration-(0.5-getTimer()));
			setTimer(getTimer()+duration-0.5);
			
		}
		else {
			super.advanceTime(duration);
			setTimer(getTimer()+duration);
		}
		
	}
	
	
	public void handleInteraction(double duration) {
		return;
	}
	

	
	private void changeDirection() {
		if(this.getXDirection() == Direction.LEFT) {
			this.endMove();
			this.startMove(Direction.RIGHT);
		}
		else {
			this.endMove();
			this.startMove(Direction.LEFT);
		}
	}
	
	
	//Gegokt, want staat niet in opgave
	public Sprite getCurrentSprite() {
		if(getXDirection() == Direction.LEFT)
			return getSprites()[0];
		return getSprites()[1];
	}

}
