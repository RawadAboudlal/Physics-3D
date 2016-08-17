package com.rawad.phys.client.renderengine;

import com.rawad.gamehelpers.client.renderengine.Render;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.renderengine.shaders.StaticEntityShader;

public class StaticEntityRender extends Render {
	
	private VertexArrayObject vao;
	
	private StaticEntityShader shader;
	
	public StaticEntityRender() {
		super();
		
		vao = new VertexArrayObject();
		
		shader = new StaticEntityShader();
		
		
	}
	
	public void render(Entity e) {
		
		vao.bind();
		
		shader.use();
		
//		RenderingComponent renderingComp = e.getComponent(RenderingComponent.class);
		
		
		
	}
	
}
