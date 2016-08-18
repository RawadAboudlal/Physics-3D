package com.rawad.phys.client.renderengine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.rawad.gamehelpers.client.renderengine.Render;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.buffers.IndexBufferObject;
import com.rawad.phys.client.renderengine.buffers.VertexBufferObject;
import com.rawad.phys.client.renderengine.shaders.StaticEntityShader;
import com.rawad.phys.entity.RenderingComponent;

public class StaticEntityRender extends Render {
	
	private VertexArrayObject vao;
	
	private IndexBufferObject ibo;
	private VertexBufferObject vbo;
	
	private StaticEntityShader shader;
	
	public StaticEntityRender() {
		super();
		
		vao = new VertexArrayObject();
		
		shader = new StaticEntityShader();
		
		ibo = new IndexBufferObject();
		vbo = new VertexBufferObject();
		
		vao.bind();
		
		ibo.bind();
		vbo.bind();
		
	}
	
	public void start() {
		
		vao.bind();
		
		shader.use();
		
	}
	
	public void render(Entity e) {
		
		RenderingComponent renderingComp = e.getComponent(RenderingComponent.class);
		
		renderingComp.getTexture().bind();
		
		Model model = renderingComp.getModel();
		
		ibo.uploadData(model.getIndices(), GL15.GL_STATIC_DRAW);
		vbo.uploadData(model.getData(), GL15.GL_STATIC_DRAW);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		
	}
	
}
