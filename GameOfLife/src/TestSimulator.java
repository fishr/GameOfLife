import static org.junit.Assert.*;

import org.junit.Test;


public class TestSimulator {

	// Constructor Tests
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_nonExistantInitFile() {
		String badFile = new String("this_file_does_not_exist.csv");
		Simulator sim = new Simulator (badFile);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_nonCSVFile() {
		String badFile = new String("notcsv.txt");
		Simulator sim = new Simulator(badFile);
	}
	
	@Test
	public void testConstructor_goodFile() {
		String goodFile = new String("blank_big.csv");
		Simulator sim = new Simulator(goodFile);
		// no exception should be thrown
	}
	
	@Test
	public void testUnregister() {
		Simulator sim = new Simulator("blank_big.csv");
		Grid grid = new Grid(sim, "blank_big.csv");
		TileFlipper t = new TileFlipper(sim,grid,false, .5);
		sim.agents.add(t);
		assertTrue(sim.agents.contains(t));
		sim.unregister(t);
		assertFalse(sim.agents.contains(t));
	}
	
	@Test
	public void testIncSync() {
		Simulator sim = new Simulator("blank_big.csv");
		sim.syncReset = 3;
		sim.incSync();
		assertEquals(sim.syncReset, 4);
	}
	
}
