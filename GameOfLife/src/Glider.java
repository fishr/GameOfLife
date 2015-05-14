import java.awt.Color;
import java.util.ArrayList;


public class Glider extends Agent{

	public Glider(Simulator sim, Grid g, boolean runOnce, double chance) {
		super(sim, g, runOnce, chance, Color.GREEN, 5, 5);
		this.topY=(int) Math.floor(Math.random()*this.g.maxY);
		this.leftX=(int) Math.floor(Math.random()*this.g.maxX);
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
		}
	}

}
