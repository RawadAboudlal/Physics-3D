package com.rawad.phys.client.renderengine;

import com.rawad.gamehelpers.client.renderengine.LayerRender;
import com.rawad.gamehelpers.game.World;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.TransformComponent;

public class WorldRender extends LayerRender {
	
	private World world;
	
	private TransformComponent cameraTransform;
	
	public WorldRender(World world, Entity camera) {
		super();
		
		this.world = world;
		
		this.cameraTransform = camera.getComponent(TransformComponent.class);
		// This has view matrix, UserViewComponent should have projection matrix?
	}
	
	@Override
	public void render() {
		
		// projection -> view -> model transforms?
		
	}
	
}
