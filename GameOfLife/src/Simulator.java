import java.awt.*;

import javax.swing.JFrame;

public class Simulator extends JFrame implements Runnable{

	final int endTime = 100;
	private int sec = 0;
	private int msec = 0;
	private int dt = 100; // msec
	private Grid grid;
	
	public Simulator (String initFile) {
		Container c = getContentPane();
		grid = new Grid(this, initFile);
		c.add(grid, BorderLayout.CENTER);
	}
	
	public synchronized int getSec() {
		return sec;
	}
	
	public synchronized int getMsec() {
		return msec;
	}
	
	private void advanceClock() {
		msec+=dt;
		if (msec>=1000) {
			sec++;
			msec-=1000;
		}
	}
	
	// TODO: Add synchronization with Agent classes
	public void run() {
		while (getSec() < endTime) {
			if (getMsec()%dt == 0) {
				grid.repaint();
				advanceClock();
			}
		}
	}
	
	public static void main(String[] args) {
		if (args.length<1)
			throw new IllegalArgumentException ("Please provide a csv startup file");
		String csvFile = args[0];
		Simulator sim = new Simulator(csvFile);
		sim.pack();
		sim.setVisible(true);
		sim.run();
	}
}
