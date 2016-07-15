package com.rawad.phys.client.states;

import com.rawad.phys.client.fileparser.ObjFileParser;
import com.rawad.phys.client.renderengine.TexturedModelRenderer;
import com.rawad.phys.loader.Loader;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	public MenuState() {
		super();
		
		masterRenderer.getRenderers().put(new TexturedModelRenderer());
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void onActive() {
		
		ObjFileParser objFileParser = new ObjFileParser();
		
		masterRenderer.getRenderers().get(TexturedModelRenderer.class).setModel(Loader.loadModel(objFileParser, "cube"));
		
	}
	
}
