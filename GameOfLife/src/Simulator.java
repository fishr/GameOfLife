import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;

public class Simulator extends JFrame implements Runnable{

	final int endTime = 100;
	private int sec = 0;
	private int msec = 0;
	private int dt = 100; // msec
	private Grid grid;
	private ArrayList<Agent> agents;
	private int syncCount=0;
	
	private final Lock lock = new ReentrantLock();
	private final Condition free = lock.newCondition();
	
	public Simulator (String initFile) {
		Container c = getContentPane();
		grid = new Grid(this, initFile);
		c.add(grid, BorderLayout.CENTER);
		agents=new ArrayList<Agent>();
		agents.add(new Default(this,grid));
		agents.get(0).start();
	}
	
	public int getSec() {
		return sec;
	}
	
	public int getMsec() {
		return msec;
	}
	
	public int getSyncMsec(int ms) {
		this.lock.lock();
		try{
			this.syncCount--;
			while(ms==msec){
				try{
					free.await();
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
			free.signalAll();
		}finally{
			lock.unlock();
		}
		return msec;
	}
	
	private void advanceClock() {
		this.lock.lock();
		try{
			while(this.syncCount>0){
				try{
					free.await();
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
			msec+=dt;
			if (msec>=1000) {
				sec++;
				msec-=1000;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.syncCount=this.agents.size();
			free.signalAll();
		}finally{
			lock.unlock();
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
