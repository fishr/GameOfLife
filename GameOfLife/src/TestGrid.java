import static org.junit.Assert.*;

import org.junit.Test;

public class TestGrid {

	// Constructor Tests
	@Test (expected = NullPointerException.class)
	public void testConstructor_nullSim() {
		Simulator sim = null;
		Grid g = new Grid (sim, "blank_big.csv");
	}
	
	@Test
	public void testConstructor_maxXmaxY() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid g = new Grid(sim, "blank_big.csv");
		assertEquals(g.maxX, 20);
		assertEquals(g.maxY, 30);
	}
	
	// Method Tests
	@Test (expected = IllegalArgumentException.class)
	public void testGetTile_xyHigh() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid g = new Grid(sim, "blank_big.csv");
		g.getTile(50,50);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetTile_xyNegative() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid g = new Grid(sim, "blank_big.csv");
		g.getTile(-3, -8);
	}

	@Test
	public void testGetTile_xyInRange() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid g = new Grid(sim, "blank_big.csv");
		Tile t = g.getTile(5,10);
		assertEquals(t.getCoordinates()[0], 5);
		assertEquals(t.getCoordinates()[1], 10);
		assertEquals(t.getID(),205);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetTile_IDHigh() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid g = new Grid(sim, "blank_big.csv");
		Tile t = g.getTile(3000);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetTile_IDNegative() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid g = new Grid(sim, "blank_big.csv");
		Tile t = g.getTile(-5);
	}

	@Test
	public void testGetTile_IDInRange() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid g = new Grid(sim, "blank_big.csv");
		Tile t = g.getTile(15);
		assertEquals(t.getID(), 15);
	}

}
