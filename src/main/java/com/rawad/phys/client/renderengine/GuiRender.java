package com.rawad.phys.client.renderengine;

import com.rawad.gamehelpers.client.renderengine.LayerRender;
import com.rawad.phys.client.text.Font;

public class GuiRender extends LayerRender {
	
	private Font font;
	
	public GuiRender() {
		super();
		
		font = new Font();
		
	}
	
	@Override
	public void render() {
		font.drawText(this, "Test 123", 10, 10);
	}

	@Override
	protected String getShaderName() {
		return null;
		
	}
	
}
