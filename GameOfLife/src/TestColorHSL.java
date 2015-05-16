import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;


public class TestColorHSL {

	// Constructor Tests
	@Test (expected = NullPointerException.class)
	public void testConstructor_nullTileAndColor() {
		ColorHSL c = new ColorHSL(null, null, 5);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_decayOutOfRange() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED);
		ColorHSL c = new ColorHSL(t, Color.BLUE, 30);
	}

	@Test
	public void testConstructor_decayInRange() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED);
		ColorHSL c = new ColorHSL(t, Color.BLUE, 15);
		assertTrue(true);
	}
	
	@Test
	public void testConstructor_TileOn_checkHSL() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED);
		ColorHSL c = new ColorHSL(t, Color.BLUE, 15);
		assertEquals(c.getHSL()[2], 10.0, 1E-6);
	}
	
	@Test
	public void testConstructor_TileOff_checkHSL() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, false, Color.RED);
		ColorHSL c = new ColorHSL(t, Color.BLUE, 15);
		assertEquals(c.getHSL()[2], 90.0, 1E-6);
	}
	
	
	// Method Tests
	@Test
	public void testFlipColor() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, false, Color.RED);
		ColorHSL c = new ColorHSL(t, Color.BLUE, 15);
		assertEquals(c.getHSL()[2], 90.0, 1E-6);
		c.flipColor();
		assertEquals(c.getHSL()[2], 10.0, 1E-6);
	}
	
	@Test
	public void decayColor_TileOn() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED);
		ColorHSL c = new ColorHSL(t, Color.BLUE, 15);
		assertEquals(c.getHSL()[2], 10.0, 1E-6);
		c.decayColor();
		assertEquals(c.getHSL()[2], 9.0, 1E-6);
	}
	
	@Test
	public void decayColor_TileOff() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, false, Color.RED);
		ColorHSL c = new ColorHSL(t, Color.BLUE, 15);
		assertEquals(c.getHSL()[2], 90.0, 1E-6);
		c.decayColor();
		assertEquals(c.getHSL()[2], 91.0, 1E-6);
	}
}
