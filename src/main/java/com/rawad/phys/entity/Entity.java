package com.rawad.phys.entity;

import com.rawad.phys.util.ClassMap;

public final class Entity {
	
	private ClassMap<Component> components;
	
	private Entity() {
		super();
		
		components = new ClassMap<Component>();
		
	}
	
	public ClassMap<Component> getComponents() {
		return components;
	}
	
	public static Entity createEntity() {
		return new Entity();
	}
	
}
