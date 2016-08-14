package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.entity.UserViewComponent;

public class CameraRoamingSystem extends GameSystem {
	
	public CameraRoamingSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(UserViewComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
	}
	
}
