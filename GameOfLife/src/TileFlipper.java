import java.awt.Color;


public class TileFlipper extends Agent{

	public TileFlipper(Simulator sim, Grid g, boolean runOnce, double chance) {
		super(sim, g, runOnce, chance, Color.RED, 1, 1);
		this.topY=(int) Math.floor(Math.random()*this.g.maxY);
		this.leftX=(int) Math.floor(Math.random()*this.g.maxX);
	}

	@Override
	void update() {
		this.buffer.get(0).flip();
		this.buffer.get(0).changeColor(Color.RED);
	}
	
	@Override
	void preCopy(){
		this.topY=(int) Math.floor(Math.random()*this.g.maxY);
		this.leftX=(int) Math.floor(Math.random()*this.g.maxX);
	}

}
