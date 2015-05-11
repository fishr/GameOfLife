import java.io.*;
import java.util.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;

public class Grid extends JPanel{

	private Simulator sim;
	protected final int maxX;
	protected final int maxY;
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
				for (int i=0; i<cells.length; i++) {
					boolean on = false;
					if (cells[i].equals("1"))
						on = true;
					Tile t = new Tile(sim, this, x, y, on);
					tiles.add(t);
					x++;
				}
				y++;
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		maxX = x;
		maxY = y;
		setPreferredSize(new Dimension(tileSize*maxX,tileSize*maxY));
		repaint();
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int i=0; i<tiles.size(); i++) {
			int x = tiles.get(i).getCoordinates()[0];
			int y = tiles.get(i).getCoordinates()[1];
			boolean on = tiles.get(i).getOnOff();
			if (on)
				g2.setPaint(Color.BLACK); // TODO: Add color instead of default
			else
				g2.setPaint(Color.WHITE);
			g2.fill(new Rectangle2D.Double(tileSize*x, tileSize*y, tileSize, tileSize));
		}
	}
}
