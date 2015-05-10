import java.util.*;


public class Tile {

	private final int ID; // ID, x, and y - all 0 indexed
	private final int x;
	private final int y;
	private int decay = 10; // undecayed = 10; fully decayed to black/white = 0;
	private Color color;
	private boolean onOff;
	
	private Simulator sim;
	private Grid grid;
	
	public Tile (Simulator s, Grid g, int x, int y, boolean on) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException ("inputs cannot be negative");
		if (sim == null || g == null)
			throw new NullPointerException("sim or grid is null");
		sim = s;
		grid = g;
		this.x = x;
		this.y = y;
		ID = y*grid.maxX + x;
		onOff = on;
	}
	
	public Tile (Simulator s, Grid g, int id, boolean on) {
		if (sim == null || g == null)
			throw new NullPointerException("sim or grid is null");
		sim = s;
		grid = g;
		ID = id;
		x = id%grid.maxX;
		y = id/grid.maxX;
		onOff = on;
	}
	
	public Tile(Tile copy){
		synchronized(copy){
			this.ID=copy.ID;
			this.x=copy.x;
			this.y=copy.y;
			this.decay=copy.decay;
			this.color=new Color(copy.color);
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
	
	public void setDecay(int d) {
		decay = d;
	}
	
	public void decayTile() {
		decay-=1;
		if (decay < 0)
			decay = 0;
	}
	
	public void flip() {
		onOff = !onOff;
	}
	
	public boolean getOnOff() {
		return onOff;
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
