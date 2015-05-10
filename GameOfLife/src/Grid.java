//TODO: Build GUI

import java.io.*;
import java.util.*;

public class Grid {

	private Simulator sim;
	protected final int maxX;
	protected final int maxY;
	private ArrayList<Tile> tiles;
	
	public Grid (Simulator s, String csvFile) {
		if (s == null)
			throw new NullPointerException("sim is null");
		sim = s;
		int x = 0;
		int y = 0;
		BufferedReader br = null;
		String line = "";
		String splitter = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
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
		maxX = x+1;
		maxY = y+1;
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
}
