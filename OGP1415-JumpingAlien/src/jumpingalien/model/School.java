package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

public class School {

	public School() {
		slimes = new HashSet<Slime>();
	}
	
	public Set<Slime> getSlimes() {
		return slimes;
	}
	
	public boolean hasAsSlime(Slime slime) {
		return getSlimes().contains(slimes);
	}
	
	public void addAsSlime(Slime slime) {
		getSlimes().add(slime);
	}
	
	public void removeAsSlime(Slime slime) {
		getSlimes().remove(slime);
	}
	
	public int getNbSlimes() {
		return getSlimes().size();
	}
	
	public boolean canHaveAsSlime(Slime slime) {
		return slime != null;
	}
	
	private Set<Slime> slimes;


}
