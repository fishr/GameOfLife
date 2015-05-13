import java.util.*;
import java.util.concurrent.locks.*;


public class Tile {

	private final int ID; // ID, x, and y - all 0 indexed
	private final int x;
	private final int y;
	private int decay = 10; // undecayed = 10; fully decayed to black/white = 0;
	private ColorHSL color;
	private boolean onOff;
	
	final Lock lock = new ReentrantLock();
	final Condition occupied  = lock.newCondition();
	
	private Simulator sim;
	private Grid grid;
	
	public Tile (Simulator s, Grid g, int x, int y, boolean on) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException ("inputs cannot be negative");
		if (s == null || g == null)
			throw new NullPointerException("sim or grid is null");
		this.sim = s;
		this.grid = g;
		this.x = x;
		this.y = y;
		this.ID = y*grid.maxX + x;
		this.onOff = on;
	}
	
	public Tile (Simulator s, Grid g, int id, boolean on) {
		if (sim == null || g == null)
			throw new NullPointerException("sim or grid is null");
		this.sim = s;
		this.grid = g;
		this.ID = id;
		this.x = id%grid.maxX;
		this.y = id/grid.maxX;
		this.onOff = on;
	}
	
	public Tile(Tile copy){
		synchronized(copy){
			this.ID=copy.ID;
			this.x=copy.x;
			this.y=copy.y;
			this.decay=copy.decay;
			this.color=new ColorHSL(copy.color);
			this.onOff=copy.onOff;
			
			this.sim=copy.sim;
			this.grid=copy.grid;
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public int[] getCoordinates() {
		return new int[]{x,y};
	}
	
	public int getDecay() {
		return this.decay;
	}
	
	public synchronized void setDecay(int d) {
		this.decay = d;
	}
	
	public synchronized void decayTile() {
		this.decay-=1;
		if (this.decay < 0)
			this.decay = 0;
	}
	
	public synchronized void flip() {
		this.onOff = !onOff;
	}
	
	public boolean getOnOff() {
		return this.onOff;
	}
	
	public ArrayList<Tile> getNeighbors() {
		ArrayList<Tile> neighbors = new ArrayList<Tile>();
		for (int i=x-1; i<=x+1; i++) {
			for (int j=y-1; j<=y+1; j++) {
				if (i!=j && i>=0 && j>=0 && i<grid.maxX && j<grid.maxY) {
					neighbors.add(grid.getTile(i,j));
				}
			}
		}
		return neighbors;
	}

}
