package com.rawad.phys.client.text;

public class Glyph {
	
	public final int width;
	public final int height;
	public final int x;
	public final int y;
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param x x-coord on font texture.
	 * @param y y-coord on font texture.
	 */
	public Glyph(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
}
