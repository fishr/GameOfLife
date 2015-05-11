
public class ColorHSL {

	private int h;
	private int s;
	private int v;
	private String color;
	private int decay;
	
	public ColorHSL (String c, int d) {
		color = c;
		decay = d;
		// TODO: map colors to hsv and assign them here
	}
	
	public ColorHSL(ColorHSL copy) {
		synchronized(copy){
			this.h=copy.h;
			this.s=copy.s;
			this.v=copy.v;
			this.color=copy.color;
			this.decay=copy.decay;
		}
	}

	public int[] getHSV() {
		return new int[] {h, s, v};
	}
	
	public void decayColor() {
		// TODO: decrement or increment saturation by some reasonable number
	}
}
