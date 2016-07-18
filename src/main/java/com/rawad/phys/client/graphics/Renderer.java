package com.rawad.phys.client.graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

public abstract class Renderer {
	
	protected VertexArrayObject vao;
	
	public Renderer() {
		super();
		
		vao = new VertexArrayObject();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
	public abstract void render();
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void begin() {}
	public void end() {}
	
	/*/
	public void drawTexture(Texture texture, float x, float y, Color c) {
		drawTextureRegion(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight(), c);
	}
	
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth, 
			float regHeight, Color c) {
		
		// Vertex positions.
		float x1 = x;
		float y1 = y;
		float x2 = x + regWidth;
		float y2 = y + regHeight;
		
		// Texture coordinates.
		float s1 = regX / texture.getWidth();
		float t1 = regY / texture.getHeight();
		float s2 = (regX + regWidth) / texture.getWidth();
		float t2 = (regY + regHeight) / texture.getHeight();
		
		drawTextureRegion(texture, x1, y1, x2, y2, s1, t1, s2, t2, c);
		
	}
	
	public void drawTextureRegion(Texture texture, float x1, float y1, float x2, float y2, float s1, float t1, float s2, 
			float t2, Color c) {
		
		if(vertices.remaining() < 7 * 6) flush();// Need more vbo space.
		
		begin();
		
		texture.bind();
		
		float r = c.getRed();
		float g = c.getGreen();
		float b = c.getBlue();
		
		vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
		vertices.put(x1).put(y2).put(r).put(g).put(b).put(s1).put(t2);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);
		
		vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);
		vertices.put(x2).put(y1).put(r).put(g).put(b).put(s2).put(t1);
		
		vertexCount += 6;// Two triangles.
		
		end();
		
	}
	/**/
	public void dispose() {
		
		vao.delete();
		
	}
	
}
