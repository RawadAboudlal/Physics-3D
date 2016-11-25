package com.rawad.phys.game;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.AttachmentComponent;
import com.rawad.phys.entity.DirectionComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.entity.UserViewComponent;
import com.rawad.phys.math.Vector3f;

public class CameraFollowSystem extends GameSystem {
	
	public CameraFollowSystem() {
		super();
		
		compatibleComponentTypes.add(TransformComponent.class);
		compatibleComponentTypes.add(UserViewComponent.class);
		compatibleComponentTypes.add(AttachmentComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
		AttachmentComponent attachmentComp = e.getComponent(AttachmentComponent.class);
		
		Entity attachedTo = attachmentComp.getAttachedTo();
		
		if(attachedTo == null) return;
		
		TransformComponent transformComp = e.getComponent(TransformComponent.class);
		TransformComponent attachedToTransform = attachedTo.getComponent(TransformComponent.class);
		
		Vector3f offset = new Vector3f(0f, 0f, -5f);
		
		DirectionComponent attachedToDirection = attachedTo.getComponent(DirectionComponent.class);
		
		if(attachedToDirection != null) offset = attachedToDirection.getForward().scale(-5f);
		
		transformComp.setPosition(attachedToTransform.getPosition().negate().add(offset));
		
	}
	
}
