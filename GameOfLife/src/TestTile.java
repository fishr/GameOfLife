import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.*;

public class TestTile {

	// Constructor Tests
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_xyLow() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, -3, -5, true, Color.RED);
	}
	
	@Test
	public void testConstructor_xyInRange() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED);
		assertEquals(t.getCoordinates()[0], 6);
		assertEquals(t.getCoordinates()[1], 3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_xyHigh() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		Tile t = new Tile (sim, g, 30, 27, true, Color.RED);
	}
	
	@Test (expected = NullPointerException.class)
	public void testConstructor_sgNull() {
		Simulator sim = null;
		Grid g = null;
		Tile t = new Tile(sim, g, 6,3, true, Color.RED);
	}
	
	@Test 
	public void testConstructor_decayOutOfRange() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		int decay = 500;
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED, decay);
		assertEquals(t.getDecay(), 0);
	}
	
	@Test
	public void testConstructor_decayInRange() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		int decay = 15;
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED, decay);
		assertEquals(t.getDecay(), decay);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCopyConstructor_copyNull() {
		Tile t = null;
		Tile tCopy = new Tile(t);
	}

	
	// Method Tests
	@Test
	public void testDecayTile_undecayed() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		int decay = 15;
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED, decay);
		t.decayTile();
		assertEquals(16, t.getDecay());
	}
	
	@Test
	public void testDecayTile_fullyDecayed() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		int decay = 25;
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED, decay);
		t.decayTile();
		assertEquals(25, t.getDecay());
	}
	
	@Test
	public void testFlip_onToOff() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		int decay = 25;
		Tile t = new Tile (sim, g, 6, 3, true, Color.RED, decay);
		t.flip();
		assertEquals(t.getOnOff(), false);
	}
	
	@Test
	public void testFile_offToOn() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		int decay = 25;
		Tile t = new Tile (sim, g, 6, 3, false, Color.RED, decay);
		t.flip();
		assertEquals(t.getOnOff(), true);
	}
	
	@Test
	public void testChangeColor() {
		Simulator sim = new Simulator ("infin.csv");
		Grid g = new Grid (sim, "infin.csv");
		int decay = 25;
		Tile t = new Tile (sim, g, 6, 3, false, Color.RED, decay);
		t.changeColor(Color.GREEN);
		assertEquals(t.getColorHSL().getInitRGB(), Color.GREEN);
	}
}
