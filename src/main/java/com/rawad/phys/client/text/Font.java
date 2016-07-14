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
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.lwjgl.BufferUtils;

import com.rawad.phys.client.graphics.Color;
import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.graphics.Texture;
import com.sun.istack.internal.logging.Logger;

public class Font {
	
	private static final String CHARSET = "IBM037";
	
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
		
		Charset charset = Charset.defaultCharset();
		
		try {
			charset = Charset.forName(CHARSET);
		} catch(IllegalCharsetNameException ex) {
			Logger.getLogger(getClass()).log(Level.WARNING, CHARSET + " is not a legal charset name; using default "
					+ "charset isntead.");
		} catch(UnsupportedCharsetException ex) {
			Logger.getLogger(getClass()).log(Level.WARNING, CHARSET + " is not supported by the current JVM; using "
					+ "default charset instead.");
		}
		
		ArrayList<Character> characters = new ArrayList<Character>();
		
		for(int i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
			
			char c = (char) i;
			
			if(Character.isISOControl(c)) continue;// Could use i directly here, for UTF-16 support.
			
			String s = Character.toString(c);
			
			byte[] encoded = s.getBytes(charset);
			String decoded = new String(encoded, charset);
			
			if(s.equals(decoded)) characters.add(c);
			
		}
		
		for(Character c: characters) {
			
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
		
		for(Character c: characters) {
			
			BufferedImage charImage = createCharImage(font, c, antialias);
			if(charImage == null) continue;
			
			int charWidth = charImage.getWidth();
			int charHeight = charImage.getHeight();
			
			Glyph glyph = new Glyph(charWidth, charHeight, offset, image.getHeight() - charHeight);
			g.drawImage(charImage, offset, 0, null);
			offset += glyph.getWidth();
			
			glyphs.put(c, glyph);
			
		}
		
		for(Glyph glyph: glyphs.values()) {
			glyph.setU(glyph.getX() / (float) imageWidth);
			glyph.setV(glyph.getY() / (float) imageHeight);
			glyph.setU2((glyph.getX() + glyph.getWidth()) / (float) imageWidth);
			glyph.setV2((glyph.getY() + glyph.getHeight()) / (float) imageHeight);
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
			lineWidth += glyph.getWidth();
			
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
			lineHeight = Math.max(lineHeight, glyph.getHeight());
			
		}
		
		height += lineHeight;
		return height;
		
	}
	
	public void drawText(Renderer renderer, CharSequence text, float x, float y, Color c) {
		
		int textHeight = getHeight(text);
		
		float drawX = x;
		float drawY = y;
		
		if(textHeight > fontHeight) drawY += textHeight - fontHeight;
		
		for(int i = 0; i < text.length(); i++) {
			
			char ch = text.charAt(i);
			
			if(ch == NL) {
				
				drawY -= fontHeight;
				drawX = x;
				
				continue;
				
			}
			
			if(ch == CARRIAGE_RETURN) continue;
			
			Glyph glyph = glyphs.get(ch);
			
			renderer.drawTextureRegion(texture, drawX, drawY, drawX + glyph.getWidth(), drawY + glyph.getHeight(), 
					glyph.getU(), glyph.getV(), glyph.getU2(), glyph.getV2(), c);
			
			drawX += glyph.getWidth();
			
		}
		
	}
	
	public void drawText(Renderer renderer, CharSequence text, float x, float y) {
		this.drawText(renderer, text, x, y, Color.WHITE);
	}
	
	public void dispose() {
		texture.delete();
	}
	
}
