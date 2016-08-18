package com.rawad.phys.client.renderengine;

import java.util.ArrayList;

import com.rawad.gamehelpers.client.renderengine.LayerRender;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.TransformComponent;

public class WorldRender extends LayerRender {
	
	private ArrayList<Entity> entities;
	
	private StaticEntityRender entityRender;
	
	private TransformComponent cameraTransform;
	
	public WorldRender(Entity camera) {
		super();
		
		this.cameraTransform = camera.getComponent(TransformComponent.class);
		// This has view matrix, UserViewComponent should have projection matrix?
		
		entities = new ArrayList<Entity>();
		
		entityRender = new StaticEntityRender();
		
	}
	
	@Override
	public void render() {
		
		entityRender.start();
		
		for(Entity e: entities) {
			entityRender.render(e);
		}
		
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
}
