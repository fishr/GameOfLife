import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Agent extends Thread{

	final Simulator sim;
	final Grid g;
	ArrayList<Tile> buffer;
	final boolean runOnce;
	double chance;
	int sec;
	int msec;
	ColorHSL c;
	int buffX;
	int buffY;
	
	public Agent(Simulator sim, Grid g, boolean runOnce, double chance, ColorHSL c, int buffX, int buffY){
		if(chance>1 || chance<=0 ||sim.equals(null)||g.equals(null)||c.equals(null)||buffX<1||buffY<1){
			throw new IllegalArgumentException();
		}
		
		this.sim=sim;
		this.g=g;
		this.runOnce=runOnce;
		this.chance=chance;
		this.c=c;
		this.buffX=buffX;
		this.buffY=buffY;
		
		this.buffer=new ArrayList<Tile>();
		this.buffer.ensureCapacity(buffX*buffY);
		
		this.msec=sim.getMsec();
		this.sec=sim.getSec();
	}
	
	boolean runCheck(){
		double r = ThreadLocalRandom.current().nextDouble();
		return chance>r;
	}
	
	int buffSize(){
		return this.buffX*this.buffY;
	}
	
	void topLeftCopy(int x, int y){
		if(((x+this.buffX)>this.g.maxX)||((y+this.buffY)>this.g.maxY)){
			throw new IllegalArgumentException();
		}
		
		//TODO grab tiles from grid and put in buffer

		for(int i = this.buffX*this.buffY; i>0; i--){
			this.g.lockTile(this, i);
			buffer.set(i, new Tile(this.g.getTile(i)));
		}
		
	}
	
	void waitForGo(){
		//TODO
	}
	
	abstract void update();
	
	public void run(){
		try{
			if(this.runOnce){
				this.update();
			}else{
				while(this.sec<this.sim.endTime){
					if(this.runCheck()){
						this.update();
					}
					this.waitForGo();
				}
			}
		}finally{
			this.g.releaseTiles(this);
		}
	}
}
