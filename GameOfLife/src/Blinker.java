import java.awt.Color;

public class Blinker extends Agent{

	public Blinker(Simulator sim, Grid g, boolean runOnce, double chance) {
		super(sim, g, runOnce, chance, Color.BLUE, 3, 3);

		this.setROI((int) Math.floor(Math.random()*(this.g.maxX-this.buffX)),(int) Math.floor(Math.random()*(this.g.maxY-this.buffY)));
	}

	@Override
	void update() {
		for(int i = 0; i < this.buffSize(); i++){
			if(this.buffer.get(i).getOnOff()){
				this.buffer.get(i).flip();
			}
			if(i==3||i==4||i==5){
				this.buffer.get(i).flip();
			}
			this.buffer.get(i).changeColor(Color.BLUE);
		}
		
	}
	
	@Override
	void preCopy(){
		this.setROI((int) Math.floor(Math.random()*(this.g.maxX-this.buffX)),(int) Math.floor(Math.random()*(this.g.maxY-this.buffY)));
	}

}
