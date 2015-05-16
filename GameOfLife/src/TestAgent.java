import static org.junit.Assert.*;

import java.awt.Color;

import junit.framework.Assert;

import org.junit.Test;

public class TestAgent {

	@Test (expected = IllegalArgumentException.class)
	public void testAgent_null1(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker((Simulator)null, gd, false, 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAgent_null2(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, null, false, 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAgent_low(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, false, -1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAgent_zero(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, false, 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAgent_high(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, false, 10);
	}
	
	@Test
	public void testrunCheck1(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, Double.MIN_VALUE);
		Assert.assertEquals(a.runCheck(), false);
	}
	
	@Test
	public void testrunCheck2(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		Assert.assertEquals(a.runCheck(), true);
	}
	
	@Test
	public void testbuffSize(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		Assert.assertEquals(a.buffSize(), 9);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsetROI1(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		a.setROI(-1, 1);
	}
	
	@Test
	public void testsetROI2( ){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		a.setROI(0,  1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testsetROI3( ){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		a.setROI(1,  -1);
	}
	
	@Test
	public void testsetROI4(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		a.setROI( 1,  0);
	}
	
	@Test 
	public void testsetROI5(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		a.setROI( 1,  1);
	}
	
	@Test  (expected = IllegalArgumentException.class)
	public void testsetROI6(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		a.setROI( Integer.MAX_VALUE,  1);
	}
	
	@Test  (expected = IllegalArgumentException.class)
	public void testsetROI7(){
		Simulator s = new Simulator("blank_huge.csv");
		Grid gd = new Grid(s,"blank_huge.csv");
		Agent a = new Blinker(s, gd, true, 1);
		a.setROI( 1,  Integer.MAX_VALUE);
	}
	
}
