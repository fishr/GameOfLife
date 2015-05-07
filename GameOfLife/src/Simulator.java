
public class Simulator extends Thread{

	final int endTime = 100;
	
	private int sec = 0;
	private int msec = 0;
	private int dt = 100; // msec
	
	public Simulator () {}
	
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
			// TODO: repaint grid
			advanceClock();
		}
	}
	
	public static void main(String[] args) {
		// TODO: Pass reference to csv file
		// Grid g = new Grid(String "csvFile");
	}
}
