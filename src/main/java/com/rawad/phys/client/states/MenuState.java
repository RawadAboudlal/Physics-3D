package com.rawad.phys.client.states;

import com.rawad.phys.client.fileparser.ObjFileParser;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.renderengine.TexturedModelRenderer;
import com.rawad.phys.loader.Loader;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private Texture texture;
	
	public MenuState() {
		super();
		
		masterRenderer.getRenderers().put(new TexturedModelRenderer());
		
	}
	
	@Override
	public void update() {
		
	}
	
	/* (non-Javadoc)
	 * @see com.rawad.phys.client.states.State#onActive()
	 */
	@Override
	public void onActive() {
		
		ObjFileParser objFileParser = new ObjFileParser();
		
		texture = Loader.loadTexture("unknown");
		
		masterRenderer.getRenderers().get(TexturedModelRenderer.class).setModel(Loader.loadModel(objFileParser, "cube"), 
				texture);
		
	}
	
	@Override
	public void onDeactive() {
		super.onDeactive();
		
		texture.delete();
		
	}
	
}
