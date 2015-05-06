import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Agent {

	Simulator sim;
	Grid g;
	ArrayList<Tile> buffer;
	boolean runOnce;
	double chance;
	int sec;
	int msec;
	Color c;
	int buffX;
	int buffY;
	
	public Agent(Simulator sim, Grid g, boolean runOnce, double chance, Color c, int buffX, int buffY){
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
	
	abstract void update();
}
