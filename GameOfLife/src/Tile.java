import java.awt.Color;
import java.util.*;
import java.util.concurrent.locks.*;


public class Tile {

	private final int ID; // ID, x, and y - all 0 indexed
	private final int x;
	private final int y;
	private int decay = 0; // undecayed (full color) = 0; fully decayed to black/white = 25;
	private ColorHSL colorHSL;
	private boolean onOff;
	
	final Lock lock = new ReentrantLock();
	final Condition occupied  = lock.newCondition();
	
	private final Simulator sim;
	private final Grid grid;
	
	public Tile (Simulator s, Grid g, int x, int y, boolean on, Color rgb) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException ("inputs cannot be negative");
		if (s == null || g == null)
			throw new NullPointerException("sim or grid is null");
		this.sim = s;
		this.grid = g;
		if (x < 0 || x >= grid.maxX || y<0) // cannot test y >= maxY because tiles created before maxY known (before csv file fully parsed)
			throw new IllegalArgumentException ("x or y out of range " + grid.maxX + ", " + x + "; " + grid.maxY + ", " + y);
		this.x = x;
		this.y = y;
		ID = y*grid.maxX + x;
		onOff = on;
		colorHSL = new ColorHSL(this, rgb, 0);
	}
	
	public Tile (Simulator s, Grid g, int x, int y, boolean on, Color rgb, int d) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException ("inputs cannot be negative");
		if (s == null || g == null)
			throw new NullPointerException("sim or grid is null");
		this.sim = s;
		this.grid = g;
		if (x < 0 || x >= grid.maxX || y<0)
			throw new IllegalArgumentException ("x or y out of range " + grid.maxX + ", " + x + "; " + grid.maxY + ", " + y);
		this.x = x;
		this.y = y;
		ID = y*grid.maxX + x;
		onOff = on;
		if (d >= 0 && d <=25)
			decay = d;
		else
			decay = 0;
		colorHSL = new ColorHSL(this, rgb, decay);
	}
	
	public Tile(Tile copy){
		if (copy == null)
			throw new NullPointerException("Copy tile is null");
		synchronized(copy){
			this.ID=copy.ID;
			this.x=copy.x;
			this.y=copy.y;
			this.decay=copy.decay;
			this.colorHSL=new ColorHSL(copy.colorHSL, this);
			this.onOff=copy.onOff;
			
			this.sim=copy.sim;
			this.grid=copy.grid;
		}
	}
	
	synchronized void copyTile(Tile copy){
		this.decay=copy.getDecay();
		this.colorHSL=new ColorHSL(copy.getColor(), this);
		this.onOff=copy.getOnOff();
	}
	
	public int getID() {
		return ID;
	}
	
	public int[] getCoordinates() {
		return new int[]{x,y};
	}
	
	public ColorHSL getColor(){
		return this.colorHSL;
	}
	
	public int getDecay() {
		return this.decay;
	}

	public void decayTile() {
		decay++;
		if (decay >= 25)
			decay = 25;
		colorHSL.decayColor();
	}
	
	public synchronized void flip() {
		onOff = !onOff;
		colorHSL.flipColor();
	}
	
	public boolean getOnOff() {
		return this.onOff;
	}
	
	public ColorHSL getColorHSL() {
		return colorHSL;
	}
	
	public void changeColor(Color rgb, int d) {
		colorHSL = new ColorHSL(this, rgb, d);
	}
	
	public void changeColor(Color rgb) {
		colorHSL = new ColorHSL(this, rgb, 0);
	}
	
}
