package com.rawad.phys.client.text;

public class Glyph {
	
	private final int width;
	private final int height;
	
	private final int x;
	private final int y;
	
	private float u;
	private float v;
	
	private float u2;
	private float v2;
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param x x-coord on font texture.
	 * @param y y-coord on font texture.
	 */
	public Glyph(int width, int height, int x, int y) {
		super();
		
		this.width = width;
		this.height = height;
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return the u
	 */
	public float getU() {
		return u;
	}
	
	/**
	 * @param u the u to set
	 */
	public void setU(float u) {
		this.u = u;
	}
	
	/**
	 * @return the v
	 */
	public float getV() {
		return v;
	}
	
	/**
	 * @param v the v to set
	 */
	public void setV(float v) {
		this.v = v;
	}
	
	/**
	 * @return the u2
	 */
	public float getU2() {
		return u2;
	}
	
	/**
	 * @param u2 the u2 to set
	 */
	public void setU2(float u2) {
		this.u2 = u2;
	}
	
	/**
	 * @return the v2
	 */
	public float getV2() {
		return v2;
	}
	
	/**
	 * @param v2 the v2 to set
	 */
	public void setV2(float v2) {
		this.v2 = v2;
	}
	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
}
