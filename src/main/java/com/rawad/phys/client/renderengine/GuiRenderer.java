package com.rawad.phys.client.renderengine;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.text.Font;

public class GuiRenderer extends Renderer {
	
	private Font font;
	
	public GuiRenderer() {
		super();
		
		font = new Font();
		
	}
	
	@Override
	public void render() {
		font.drawText(this, "Test 123", 10, 10);
	}
	
}
