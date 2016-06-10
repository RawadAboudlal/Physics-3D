package com.rawad.phys.client.text;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;

import com.rawad.phys.client.graphics.Texture;

public class Font {
	
	/**
	 * ASCII 0-31 are control codes so start at 32 to get actual characters.
	 */
	private static final int START_ASCII = 32;
	private static final int MAX_ASCII = 256;
	private static final int DEL_ASCII = 127;
	
	private static final char NL = '\n';
	private static final char CARRIAGE_RETURN = '\r';
	
	private final Map<Character, Glyph> glyphs;
	
	private final Texture texture;
	
	private int fontHeight;
	
	public Font(java.awt.Font font, boolean antialias) {
		glyphs = new HashMap<Character, Glyph>();
		texture = createFontTexture(font, antialias);
	}
	
	public Font(java.awt.Font font) {
		this(font, true);
	}
	
	public Font(boolean antialias) {
		this(new java.awt.Font(MONOSPACED, PLAIN, 16), antialias);		
	}
	
	public Font() {
		this(true);
	}
	
	private Texture createFontTexture(java.awt.Font font, boolean antialias) {
		
		int imageWidth = 0;
		int imageHeight = 0;
		
		for(int i = START_ASCII; i < MAX_ASCII; i++) {
			if(i == DEL_ASCII) continue;// Skip delete ASCII.
			
			char c = (char) i;
			
			BufferedImage charImage = createCharImage(font, c, antialias);
			if(charImage == null) continue;
			
			imageWidth += charImage.getWidth();
			imageHeight = Math.max(imageHeight, charImage.getHeight());
			
		}
		
		fontHeight = imageHeight;
		
		// For actual font texture.
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		
		int offset = 0;
		
		for(int i = START_ASCII; i < MAX_ASCII; i++) {
			if(i == DEL_ASCII) continue;
			
			char c = (char) i;
			
			BufferedImage charImage = createCharImage(font, c, antialias);
			if(charImage == null) continue;
			
			int charWidth = charImage.getWidth();
			int charHeight = charImage.getHeight();
			
			Glyph glyph = new Glyph(charWidth, charHeight, offset, image.getHeight() - charHeight);
			g.drawImage(charImage, offset, 0, null);
			offset += glyph.width;
			
			glyphs.put(c, glyph);
			
		}
		
		AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);// Flips horizontally to get origin to
		transform.translate(0, -image.getHeight());// bottom left instead of top left.
		
		AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		
		image = transformOp.filter(image, null);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);// Using ARGB, each is a byte so 4 bytes.
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				
				int pixel = pixels[y * width + x];// Format is 0xAARRGGBB.
				
				// & 0xFF takes the two right-most hex bits.
				buffer.put((byte) ((pixel >> 16) & 0xFF));// Red: 0xAARRGGBB >> 16 = 0x0000AARR.
				buffer.put((byte) ((pixel >> 8) & 0xFF));// Green: 0xAARRGGBB >> 8 = 0x00AARRGG.
				buffer.put((byte) (pixel & 0xFF));// Blue: 0xAARRGGBB >> 0 = 0xAARRGGBB.
				buffer.put((byte) ((pixel >> 24) & 0xFF));// Alpha: 0xAARRGGBB >> 24 = 0x000000AA.
				
			}
		}
		
		buffer.flip();
		
		return new Texture(width, height, buffer);
		
	}
	
	private BufferedImage createCharImage(java.awt.Font font, char c, boolean antialias) {
		
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);// More efficient w/-out alpha?
		Graphics2D g = image.createGraphics();
		
		if(antialias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		g.dispose();
		
		int charWidth = fm.charWidth(c);
		int charHeight = fm.getHeight();
		
		if(charWidth <= 0) return null;
		
		image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
		g = image.createGraphics();
		if(antialias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setFont(font);
		g.setPaint(java.awt.Color.WHITE);
		g.drawString(String.valueOf(c), 0, fm.getAscent());
		g.dispose();
		
		return image;
		
	}
	
	public int getWidth(CharSequence text) {
		
		int width = 0;
		int lineWidth = 0;
		
		for(int i = 0; i < text.length(); i++) {
			
			char c = text.charAt(i);
			
			if(c == NL) {
				width = Math.max(width, lineWidth);
				lineWidth = 0;
				
				continue;
			}
			
			if(c == CARRIAGE_RETURN) continue;
			
			Glyph glyph = glyphs.get(c);
			lineWidth += glyph.width;
			
		}
		
		width = Math.max(width, lineWidth);
		return width;
		
	}
	
	public int getHeight(CharSequence text) {
		
		int height = 0;
		int lineHeight = 0;
		
		for(int i = 0; i < text.length(); i++) {
			
			char c = text.charAt(i);
			
			if(c == NL) {
				height += lineHeight;
				lineHeight = 0;
				
				continue;
			}
			
			if(c == CARRIAGE_RETURN) continue;
			
			Glyph glyph = glyphs.get(c);
			lineHeight = Math.max(lineHeight, glyph.height);
			
		}
		
		height += lineHeight;
		return height;
		
	}
	
	
	
	public void dispose() {
		texture.delete();
	}
	
}
