
public class Color {

	private int h;
	private int s;
	private int v;
	private String color;
	private int decay;
	
	public Color (String c, int d) {
		color = c;
		decay = d;
		// TODO: map colors to hsv and assign them here
	}
	
	public int[] getHSV() {
		return new int[] {h, s, v};
	}
	
	public void decayColor() {
		// TODO: decrement or increment saturation by some reasonable number
	}
}
