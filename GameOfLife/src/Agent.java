import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Agent extends Thread{

	final Simulator sim;
	final Grid g;
	Hashtable<Integer, Tile> buffer;
	final boolean runOnce;
	double chance;
	int sec;
	int msec;
	Color c;
	int buffX;
	int buffY;
	private int leftX=0;
	private int topY=0;
	
	public Agent(Simulator sim, Grid g, boolean runOnce, double chance, Color c, int buffX, int buffY){
		if(chance>1 || chance<=0 ||sim==null||g==null||c==null||buffX<1||buffY<1){
			throw new IllegalArgumentException();
		}
		
		this.sim=sim;
		this.g=g;
		this.runOnce=runOnce;
		this.chance=chance;
		this.c=c;
		this.buffX=buffX;
		this.buffY=buffY;
		
		this.buffer=new Hashtable<Integer,Tile>();
		
		this.msec=sim.getMsec()-this.sim.dt;
		this.sec=sim.getSec();
		
		if(!this.runOnce)
			this.sim.incSync();
	}
	
	boolean runCheck(){
		double r = Math.random();
		return chance>r;
	}
	
	int buffSize(){
		return this.buffX*this.buffY;
	}
	
	synchronized void setROI(int x, int y){
		if(((x)>this.g.maxX)||((y)>this.g.maxY)||y<0||x<0){
			throw new IllegalArgumentException("corner out of bounds");
		}
		if(((x+this.buffX)>=this.g.maxX)||((y+this.buffY)>=this.g.maxY)){
			throw new IllegalArgumentException("buffer out of bounds");
		}
		
		this.topY=y;
		this.leftX =x;
	}
	
	void topLeftCopy(){
		
		for(int i = buffSize()-1; i>=0; i--){
			int x = this.leftX + i%this.buffX;
//			int y = this.topY + Math.floorDiv(i, this.buffX); TODO: Switch back to floorDiv
			int y = this.topY + i/this.buffX;
			this.g.lockTile(this, x, y);
			buffer.put(i, new Tile(this.g.getTile(x,y)));
		}
		
	}
	
	void writeBuffer(){
		for(int i = 0; i<buffSize(); i++){
			int x = this.leftX + i%this.buffX;
//			int y = this.topY + Math.floorDiv(i, this.buffX); TODO: Switch back to floorDiv
			int y = this.topY + i/this.buffX;
			Tile temp = buffer.get(i);
			this.g.getTile(x,y).copyTile(temp);
			this.g.unlockTile(this,x,y);
		}
	}
	
	void waitForGo(){
		this.msec=this.sim.getSyncMsec(this.msec, this.runOnce);
		this.sec=this.sim.getSec();
	}
	
	abstract void update();
	
	public void run(){
		
		try{
			while(this.sec<this.sim.endTime){
				this.waitForGo();
				if(this.runCheck()){
					this.preCopy();
					this.topLeftCopy();
					this.update();
					this.writeBuffer();
				}
				
				if(this.runOnce)
					break;
			}
		}finally{
			this.g.releaseTiles(this);
		}
	}

	void preCopy() {
		
	}
}
