package com.rawad.phys.entity;

/**
 * Contains all the data required to make an instance of {@linkplain com.rawad.phys.entity.Entity}.
 * 
 * @author Rawad
 *
 */
public final class Blueprint {
	
	private final Entity entityBase;
	
	public Blueprint(Entity entityBase) {
		super();
		
		this.entityBase = entityBase;
		
	}
	
	public Entity getEntityBase() {
		return entityBase;
	}
	
}
