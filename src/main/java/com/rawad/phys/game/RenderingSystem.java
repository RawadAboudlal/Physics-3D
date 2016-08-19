package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.renderengine.WorldRender;
import com.rawad.phys.entity.RenderingComponent;
import com.rawad.phys.entity.TransformComponent;

public class RenderingSystem extends GameSystem {
	
	private WorldRender worldRender;
	
	public RenderingSystem(WorldRender worldRender) {
		super();
		
		this.worldRender = worldRender;
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(RenderingComponent.class);
		
	}
	
	@Override
	public void tick() {
		
		worldRender.getEntities().clear();
		
		super.tick();
		
	}
	
	@Override
	public void tick(Entity e) {
		
		worldRender.getEntities().add(e);
		
	}
	
}
