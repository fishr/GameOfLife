import java.awt.Color;

public class Blinker extends Agent{

	public Blinker(Simulator sim, Grid g, boolean runOnce, double chance) {
		super(sim, g, runOnce, chance, Color.BLUE, 5, 5);

		this.topY=(int) Math.floor(Math.random()*(this.g.maxY-this.buffY));
		this.leftX=(int) Math.floor(Math.random()*(this.g.maxX-this.buffX));
	}

	@Override
	void update() {
		for(int i = 0; i < this.buffSize(); i++){
			if(this.buffer.get(i).getOnOff()){
				this.buffer.get(i).flip();
			}
			if(i==11||i==12||i==13){
				this.buffer.get(i).flip();
			}
			this.buffer.get(i).changeColor(Color.BLUE);
		}
		
	}
	
	@Override
	void preCopy(){
		this.topY=(int) Math.floor(Math.random()*(this.g.maxY-this.buffY));
		this.leftX=(int) Math.floor(Math.random()*(this.g.maxX-this.buffX));
	}

}
