package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.LightComponent;
import com.rawad.phys.entity.TransformComponent;

public class LightingSystem extends GameSystem {
	
	public LightingSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(LightComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
	}
	
}
