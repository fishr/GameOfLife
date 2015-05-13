
public class Default extends Agent {

	public Default(Simulator sim, Grid g) {
		super(sim, g, false, 1, c, g.maxX, g.maxY);
		// TODO Auto-generated constructor stub
	}

	@Override
	void update() {

	}
	
	@Override
	void topLeftCopy(int x, int y){
		topLeftCopy(0,0);
	}
	
	void topLeftCopy(){
		topLeftCopy(0,0);
	}

}
