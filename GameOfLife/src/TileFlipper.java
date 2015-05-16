import java.awt.Color;


public class TileFlipper extends Agent{

	public TileFlipper(Simulator sim, Grid g, boolean runOnce, double chance) {
		super(sim, g, runOnce, chance, Color.RED, 1, 1);
		this.setROI((int) Math.floor(Math.random()*(this.g.maxX-this.buffX)),(int) Math.floor(Math.random()*(this.g.maxY-this.buffY)));
	}

	@Override
	void update() {
		this.buffer.get(0).flip();
		this.buffer.get(0).changeColor(Color.RED);
	}
	
	@Override
	void preCopy(){
		this.setROI((int) Math.floor(Math.random()*(this.g.maxX-this.buffX)),(int) Math.floor(Math.random()*(this.g.maxY-this.buffY)));
	}

}
