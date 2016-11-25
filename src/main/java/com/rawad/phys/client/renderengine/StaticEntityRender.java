package com.rawad.phys.client.renderengine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;

import com.rawad.gamehelpers.client.renderengine.Render;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.buffers.IndexBufferObject;
import com.rawad.phys.client.renderengine.buffers.VertexBufferObject;
import com.rawad.phys.client.renderengine.shaders.StaticEntityShader;
import com.rawad.phys.entity.RenderingComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.game.RenderingSystem;
import com.rawad.phys.math.Matrix4f;

public class StaticEntityRender extends Render {
	
	private VertexArrayObject vao;
	
	private IndexBufferObject ibo;
	private VertexBufferObject vbo;
	
	private StaticEntityShader shader;
	
	private Matrix4f projection;
	
	private TransformComponent cameraTransform;
	
	public StaticEntityRender(Entity camera) {// TODO: Implement -> set view.
		super();
		
		vao = new VertexArrayObject();
		
		shader = new StaticEntityShader();
		
		ibo = new IndexBufferObject();
		vbo = new VertexBufferObject();
		
		vao.bind();
		
		ibo.bind();
		vbo.bind();
		
		projection = Matrix4f.perspective(90, 640f / 480f, 0.1f, 100f);
		
		shader.use();
		shader.initVertexAttributes();
		
		cameraTransform = camera.getComponent(TransformComponent.class);
		
	}
	
	public void start() {
		
		vao.bind();
		
		shader.use();
		
		shader.setUniform("projection", projection);
		shader.setUniform("view", RenderingSystem.transformToMatrix4f(cameraTransform));
		
	}
	
	public void render(Entity e) {
		
		RenderingComponent renderingComp = e.getComponent(RenderingComponent.class);
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		
		renderingComp.getTexture().bind();
		
		Model model = renderingComp.getModel();
		
		shader.setUniform("model", RenderingSystem.transformToMatrix4f(transformComp));
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);// Note: GL_TEXTURE# -> # has to equal value given to setUniform
		shader.setUniform("modelTexture", 0);
		
		ibo.uploadData(model.getIndices(), GL15.GL_STATIC_DRAW);
		vbo.uploadData(model.getData(), GL15.GL_STATIC_DRAW);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
	}
	
}
