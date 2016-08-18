package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.Texture;


public class RenderingComponent extends Component {
	
	private Model model = new Model();
	
	private Texture texture = null;
	
	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}
	
	/**
	 * @param model the model to set
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	
	/**
	 * @return the texture
	 */
	public Texture getTexture() {
		return texture;
	}
	
	/**
	 * @param texture the texture to set
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof RenderingComponent) {
			
			RenderingComponent renderingComp = (RenderingComponent) comp;
			
			return renderingComp;
			
		}
		
		return comp;
		
	}
	
}
