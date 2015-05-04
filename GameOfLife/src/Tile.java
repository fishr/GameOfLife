
public class Tile {

	private final int ID; // ID, x, and y - all 0 indexed
	private final int x;
	private final int y;
	private int decay = 10; // undecayed = 10; fully decayed to black/white = 0;
	private Color color;
	private boolean onOff;
	
	private Simulator sim;
	private Grid grid;
	
	public Tile (Simulator s, Grid g, int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException ("inputs cannot be negative");
		if (sim == null || g == null)
			throw new NullPointerException("sim or grid is null");
		sim = s;
		grid = g;
		this.x = x;
		this.y = y;
		ID = y*grid.maxX + x;
	}
	
	public Tile (Simulator s, Grid g, int id) {
		if (sim == null || g == null)
			throw new NullPointerException("sim or grid is null");
		sim = s;
		grid = g;
		//TODO: Assign ID, x, y
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
	
	public void flip() {
		onOff = !onOff;
	}
	
	public boolean getOnOff() {
		return onOff;
	}
}
