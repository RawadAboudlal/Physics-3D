package com.rawad.phys.entity;

public enum EEntity {
	
	CAMERA("camera");
	
	private final String name;
	
	private EEntity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
