package com.rawad.phys.entity;

public enum EEntity {
	
	CAMERA("camera"),
	CRATE("crate"),
	BALL("ball");
	
	private final String name;
	
	private EEntity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
