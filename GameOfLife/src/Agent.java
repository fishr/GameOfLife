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
	int leftX=0;
	int topY=0;
	
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
	
	synchronized void setROI(int x, int y){
		if(((this.leftX)>this.g.maxX)||((this.topY)>this.g.maxY)){
			throw new IllegalArgumentException("corner out of bounds");
		}
		
		this.topY=y;
		this.leftX =x;
	}
	
	void topLeftCopy(){
		if(((this.leftX+this.buffX)>this.g.maxX)||((this.topY+this.buffY)>this.g.maxY)){
			throw new IllegalArgumentException("buffer out of bounds");
		}
		
		for(int i = buffSize(); i>0; i--){
			this.g.lockTile(this, i);
			buffer.set(i, new Tile(this.g.getTile(i)));
		}
		
	}
	
	void writeBuffer(){
		for(int i = 0; i<buffSize(); i++){
			Tile temp = buffer.get(i);
			this.g.getTile(temp.getID()).copyTile(temp);
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
						this.topLeftCopy();
						this.update();
						this.writeBuffer();
					}
					this.waitForGo();
				}
			}
		}finally{
			this.g.releaseTiles(this);
		}
	}
}
