import java.awt.Color;
import java.util.*;


public class Tile {

	private final int ID; // ID, x, and y - all 0 indexed
	private final int x;
	private final int y;
	private int decay = 0; // undecayed (full color) = 0; fully decayed to black/white = 5;
	private ColorHSL colorHSL;
	private boolean onOff;
	
	private Simulator sim;
	private Grid grid;
	
	public Tile (Simulator s, Grid g, int x, int y, boolean on, Color rgb) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException ("inputs cannot be negative");
		if (s == null || g == null)
			throw new NullPointerException("sim or grid is null");
		sim = s;
		grid = g;
		this.x = x;
		this.y = y;
		ID = y*grid.maxX + x;
		onOff = on;
		colorHSL = new ColorHSL(rgb, 0);
	}
	
	public Tile (Simulator s, Grid g, int id, boolean on, Color rgb) {
		if (sim == null || g == null)
			throw new NullPointerException("sim or grid is null");
		sim = s;
		grid = g;
		ID = id;
		x = id%grid.maxX;
		y = id/grid.maxX;
		onOff = on;
		colorHSL = new ColorHSL(rgb, 0);
	}
	
	public Tile(Tile copy){
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
	
	public int getID() {
		return ID;
	}
	
	public int[] getCoordinates() {
		return new int[]{x,y};
	}
	
	public int getDecay() {
		return decay;
	}
	
	public void decayTile() {
		decay++;
		if (decay > 5)
			decay = 5;
		colorHSL.decayColor();
	}
	
	public void flip() {
		onOff = !onOff;
		colorHSL.flipColor();
	}
	
	public boolean getOnOff() {
		return onOff;
	}
	
	public ColorHSL getColorHSL() {
		return colorHSL;
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
