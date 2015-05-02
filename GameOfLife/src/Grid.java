
public class Grid {

	protected final int maxX;
	protected final int maxY;
	
	public Grid (int sizeX, int sizeY) {
		if (sizeX<=0 || sizeY<=0)
			throw new IllegalArgumentException("size must be greater than 0");
		maxX = sizeX;
		maxY = sizeY;
	}
}
