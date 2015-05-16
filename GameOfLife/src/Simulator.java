import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

public class Simulator extends JFrame implements Runnable{

	final int endTime = 100;
	private int sec = 0;
	private int msec = 0;
	final int dt = 100; // msec
	private Grid grid;
	protected ArrayList<Agent> agents;
	private int syncCount=0;
	protected int syncReset=0;
	
	private final Lock lock = new ReentrantLock();
	private final Condition free = lock.newCondition();
	
	public Simulator (String initFile) {
		File tempFile = new File(initFile);
		if (!tempFile.exists())
			throw new IllegalArgumentException("no such init file exists in current directory");
		if (!initFile.endsWith(".csv"))
			throw new IllegalArgumentException("init file must be a csv file");
		Container c = getContentPane();
		grid = new Grid(this, initFile);
		c.add(grid, BorderLayout.CENTER);
		grid.setFocusable(true);
		grid.requestFocusInWindow();
		agents=new ArrayList<Agent>();
		agents.add(new Default(this,grid));
		agents.get(0).start();
		agents.add(new TileFlipper(this,grid,false, .5));
		agents.get(1).start();
		agents.add(new Blinker(this,grid, false, .25));
		agents.get(2).start();
		agents.add(new Glider(this,grid,false,.25));
		agents.get(3).start();
		}
	
	public int getSec() {
		return sec;
	}
	
	public int getMsec() {
		return msec;
	}

	public void unregister(Agent agent) {
		this.agents.remove(agent);
	}
	
	public synchronized void incSync(){
		this.syncReset++;
	}
	
	public synchronized int getSyncMsec(int ms, boolean runOnce) {
		while(ms==msec||this.syncCount==0){
			try{
				wait();
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
		if(!runOnce)
			this.syncCount--;
		notifyAll();

		return msec;
	}
	
	private synchronized void advanceClock() {
		while(this.syncCount!=0){
			try{
				wait();
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
			this.syncCount=this.syncReset;
			msec+=dt;
			if (msec>=1000) {
				sec++;
				msec-=1000;
			}
			
			try {
				Thread.sleep(100);
//					Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		notifyAll();
	}
	
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
		System.exit(NORMAL);
	}
}
