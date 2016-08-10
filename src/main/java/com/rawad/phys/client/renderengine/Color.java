package com.rawad.phys.client.renderengine;

public class Color {
	
	public static final Color WHITE = new Color(1f, 1f, 1f);
	public static final Color BLACK = new Color(0f, 0f, 0f);
	public static final Color RED = new Color(1f, 0f, 0f);
	public static final Color GREEN = new Color(0f, 1f, 0f);
	public static final Color BLUE = new Color(0f, 0f, 1f);
	
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public Color(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	
	public Color(float red, float green, float blue) {
		this(red, green, blue, 1f);
	}
	
	/**
	 * @return the red
	 */
	public float getRed() {
		return red;
	}
	
	/**
	 * @param red the red to set
	 */
	public void setRed(float red) {
		this.red = red;
	}
	
	/**
	 * @return the green
	 */
	public float getGreen() {
		return green;
	}
	
	/**
	 * @param green the green to set
	 */
	public void setGreen(float green) {
		this.green = green;
	}
	
	/**
	 * @return the blue
	 */
	public float getBlue() {
		return blue;
	}
	
	/**
	 * @param blue the blue to set
	 */
	public void setBlue(float blue) {
		this.blue = blue;
	}
	
	/**
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}
	
	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
}
