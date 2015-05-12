package jumpingalien.model;

public interface GameElement {
	public abstract double getX();
	public abstract double getY();
	public abstract int getHeight();
	public abstract int getWidth();	
	public abstract World getMyWorld();
}
