import java.awt.Color;
import java.util.ArrayList;


public class Glider extends Agent{

	public Glider(Simulator sim, Grid g, boolean runOnce, double chance) {
		super(sim, g, runOnce, chance, Color.GREEN, 5, 5);
		this.setROI((int) Math.floor(Math.random()*(this.g.maxX-this.buffX)),(int) Math.floor(Math.random()*(this.g.maxY-this.buffY)));
		System.out.println("glider");
	}

	@Override
	void update() {
		ArrayList<Integer> blackTiles = new ArrayList<Integer>();
		blackTiles.add(7); blackTiles.add(13); blackTiles.add(16); blackTiles.add(17); blackTiles.add(18);
		for (int i=0; i<25; i++) {
			if (blackTiles.contains(i)) {
				if (!this.buffer.get(i).getOnOff())
					this.buffer.get(i).flip();
			}
			else {
				if (this.buffer.get(i).getOnOff())
					this.buffer.get(i).flip();
			}
			this.buffer.get(i).changeColor(Color.GREEN);
		}
	}
	
	@Override
	void preCopy(){
		this.setROI((int) Math.floor(Math.random()*(this.g.maxX-this.buffX)),(int) Math.floor(Math.random()*(this.g.maxY-this.buffY)));
	}

}
