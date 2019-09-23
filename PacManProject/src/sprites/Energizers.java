package sprites;

import java.util.ArrayList;
import java.util.List;

public class Energizers {
	
	private List<Energizer> energizers = new ArrayList<Energizer>();
	
	public void add(Energizer e) {
		energizers.add(e);
	}
	
	public List<Energizer> getEnergizers(){
		return energizers;
	}
	
	public Energizer getEnergizerNb(int nb) {
		return energizers.get(nb);
	}
	
	public void removeEnergizerNb(int nb) {
		energizers.remove(nb);
	}
}
