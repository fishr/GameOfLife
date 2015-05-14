import java.io.*;
import java.util.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;

public class Grid extends JPanel implements KeyListener{

	private Simulator sim;
	
	Hashtable<Agent, PriorityQueue<Integer>> agents;
	
	protected int maxX;
	protected int maxY;
	private ArrayList<Tile> tiles;
	private static int tileSize = 20; // pixels to a side
	
	public Grid (Simulator s, String csvFile) {
		if (s == null)
			throw new NullPointerException("sim is null");
		sim = s;
		tiles = new ArrayList<Tile>();
		// parse csv file
		int x = 0;
		int y = 0;
		BufferedReader br = null;
		String line = "";
		String splitter = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				x = 0;
				String[] cells = line.split(splitter);
				this.maxX=cells.length;
				for (int i=0; i<cells.length; i++) {
					boolean on = false;
					Color c = Color.GRAY;
					if (cells[i].equals("1"))
						on = true;
					Tile t = new Tile(sim, this, x, y, on, c, 25);
					tiles.add(t);
					x++;
				}
				y++;
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		maxY = y;
		setPreferredSize(new Dimension(tileSize*maxX,tileSize*maxY));
		addKeyListener(this);
		repaint();
		
		this.agents=new Hashtable<Agent, PriorityQueue<Integer>>();
	}

	// Note: the next two functions return pointers to the tiles themselves, not to copies
	public Tile getTile(int x, int y) {
		if (x >= maxX || y >= maxY)
			throw new IllegalArgumentException("x or y out of bounds");
		if (x < 0 || y < 0) 
			throw new IllegalArgumentException("x or y cannot be negative");
		int id = y*maxX + x;
		return tiles.get(id);
	}
	
	public Tile getTile(int id) {
		if (id > tiles.size() || id < 0)
			throw new IllegalArgumentException();
		return tiles.get(id);
	}
	
	public void lockTile(Agent taker, Integer id){
		synchronized(this.agents){
			if(!this.agents.containsKey(taker)){
				PriorityQueue<Integer> q = new PriorityQueue<Integer>();
				this.agents.put(taker, q);
			}
		}
		
		if(this.agents.get(taker).peek()==null||id<this.agents.get(taker).peek()){
			this.agents.get(taker).add(id);
			getTile(id).lock.lock();
		}else{
			throw new IllegalArgumentException("cannot lock tile of higher id");
		}
	}
	
	public void lockTile(Agent taker, int x, int y){
		synchronized(this.agents){
			if(!this.agents.containsKey(taker)){
				PriorityQueue<Integer> q = new PriorityQueue<Integer>();
				this.agents.put(taker, q);
			}
		}
		
		if(this.agents.get(taker).peek()==null||getTile(x,y).getID()<this.agents.get(taker).peek()){
			this.agents.get(taker).add(getTile(x,y).getID());
			getTile(x,y).lock.lock();
		}else{
			throw new IllegalArgumentException("cannot lock tile of higher id");
		}
	}
	
	public void unlockTile(Agent taker, Integer id){
		synchronized(this.agents){
			if(this.agents.get(taker).contains(id)){
				getTile(id).lock.unlock();
				this.agents.get(taker).remove(id);
			}
		}
	}
	
	public void unlockTile(Agent taker, int x, int y){
		synchronized(this.agents){
			if(this.agents.get(taker).contains(getTile(x,y).getID())){
				getTile(x,y).lock.unlock();
				this.agents.get(taker).remove(getTile(x,y).getID());
			}
		}
	}
	
	public void releaseTiles(Agent dead){
		synchronized(this.agents){
			PriorityQueue<Integer> q = this.agents.get(dead);
			if(q==null)
				return;
			while(!q.isEmpty()){
				unlockTile(dead, getTile(q.remove()).getID());
			}
			
			this.agents.remove(dead);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int i=0; i<tiles.size(); i++) {
			int x = tiles.get(i).getCoordinates()[0];
			int y = tiles.get(i).getCoordinates()[1];
			Color c = tiles.get(i).getColorHSL().getColorRGB();
			g2.setPaint(c);
			g2.fill(new Rectangle2D.Double(tileSize*x, tileSize*y, tileSize, tileSize));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		System.out.println("key typed: " + c);
		if (c == 'g') {
			Glider g = new Glider(sim, this, true, 1);
			this.sim.agents.add(g);
			g.start();
		}
		if (c == 't') {
			TileFlipper t = new TileFlipper(sim, this, true, 1);
			this.sim.agents.add(t);
			t.start();
		}
		if (c == 'b') {
			Blinker b = new Blinker(sim, this, true, 1);
			this.sim.agents.add(b);
			b.start();
		}
	}

}
