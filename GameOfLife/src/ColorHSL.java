// Code adapted from Rob Camick's HSLColor class
// Methods toRGB(), fromRGB(), and HueToRGB() directly copied
// Documentation: https://tips4java.wordpress.com/2009/07/05/hsl-color/
// Original code: http://www.camick.com/java/source/HSLColor.java

import java.awt.Color;

public class ColorHSL {

	private Tile tile;
	private float[] hsl;
	private Color rgb;
	private int decay;
	
	public ColorHSL (Tile t, Color rgb, int d) {
		if (t == null || rgb == null)
			throw new NullPointerException("tile or color are null");
		this.rgb = rgb;
		this.tile = t;
		if (d < 0 || d > 25)
			throw new IllegalArgumentException ("decay must be in range [0,25]");
		decay = d;
		hsl = fromRGB(rgb);
		if (tile.getOnOff())
			hsl[2] = 25.0f - decay;
		else
			hsl[2] = 75.0f + decay;
	}
	
	public ColorHSL(ColorHSL copy, Tile t) {
		synchronized(copy){
			this.hsl = copy.hsl;
			this.decay = copy.decay;
			this.rgb = copy.rgb;
			this.tile = t;
		}
	}
	
	public void flipColor() {
		hsl[2] = 100-hsl[2];
	}
	
	public float[] getHSL() {
		return hsl;
	}
	
	public Color decayColor() {
		decay++;
		if (decay > 25)
			decay = 25;
		boolean on = tile.getOnOff();
		if (on) {
			hsl[2] = 25.0f - decay;
		}
		else {
			hsl[2] = 75.0f + decay;
		}
		return toRGB(hsl);
	}
	
	public Color getColorRGB() {
		return toRGB(hsl);
	}
	
	public Color getInitRGB() {
		return rgb;
	}
	
	///////////// All code below this point credited to Rob Camick //////////////////////
	public static Color toRGB (float[] hsl) {
		float h = hsl[0]; float s = hsl[1]; float l = hsl[2];
		if (s < 0.0f || s > 100.0f)
			throw new IllegalArgumentException("Saturation outside expected range");
		if (l < 0.0f || l > 100.0f)
			throw new IllegalArgumentException("Luminance outside expected range");

		//  Formula needs all values between 0 - 1.

		h = h % 360.0f;
		h /= 360f;
		s /= 100f;
		l /= 100f;
		float q = 0;
		if (l < 0.5)
			q = l * (1 + s);
		else
			q = (l + s) - (s * l);
		float p = 2 * l - q;
		float r = Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)));
		float g = Math.max(0, HueToRGB(p, q, h));
		float b = Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)));
		r = Math.min(r, 1.0f);
		g = Math.min(g, 1.0f);
		b = Math.min(b, 1.0f);
		return new Color(r, g, b, 1.0f);
	}
	
	public static float[] fromRGB (Color color) {
	//  Get RGB values in the range 0 - 1
		float[] rgb = color.getRGBColorComponents( null );
		float r = rgb[0];
		float g = rgb[1];
		float b = rgb[2];

		//	Minimum and Maximum RGB values are used in the HSL calculations
		float min = Math.min(r, Math.min(g, b));
		float max = Math.max(r, Math.max(g, b));

		//  Calculate the Hue
		float h = 0;
		if (max == min)
			h = 0;
		else if (max == r)
			h = ((60 * (g - b) / (max - min)) + 360) % 360;
		else if (max == g)
			h = (60 * (b - r) / (max - min)) + 120;
		else if (max == b)
			h = (60 * (r - g) / (max - min)) + 240;

		//  Calculate the Luminance
		float l = (max + min) / 2;

		//  Calculate the Saturation
		float s = 0;

		if (max == min)
			s = 0;
		else if (l <= .5f)
			s = (max - min) / (max + min);
		else
			s = (max - min) / (2 - max - min);
		
		return new float[] {h, s * 100, l * 100};
	}
	
	private static float HueToRGB(float p, float q, float h)
	{
		if (h < 0) h += 1;
		if (h > 1 ) h -= 1;
		if (6 * h < 1)
			return p + ((q - p) * 6 * h);
		if (2 * h < 1 )
			return  q;
		if (3 * h < 2)
			return p + ( (q - p) * 6 * ((2.0f / 3.0f) - h) );
   		return p;
	}
}
