package com.rawad.phys.client.renderengine;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.util.ClassMap;

public class MasterRenderer {
	
	private ClassMap<Renderer> renderers;
	
	public MasterRenderer() {
		renderers = new ClassMap<Renderer>(true);
	}
	
	public void render() {
		
		for(Renderer renderer: renderers.getOrderedMap()) {
			renderer.begin();
			renderer.render();
			renderer.end();
		}
		
	}
	
	public void dispose() {
		for(Renderer renderer: renderers.getOrderedMap()) {
			renderer.dispose();
		}
	}
	
	public ClassMap<Renderer> getRenderers() {
		return renderers;
	}
	
}
