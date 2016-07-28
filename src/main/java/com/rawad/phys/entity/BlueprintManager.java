package com.rawad.phys.entity;

import java.util.HashMap;

public final class BlueprintManager {
	
	private static final HashMap<Object, Blueprint> blueprints = new HashMap<Object, Blueprint>();
	
	public static Blueprint addBlueprint(Object key, Blueprint value) {
		return blueprints.put(key, value);
	}
	
	public static Blueprint getBlueprint(Object key) {
		return blueprints.get(key);
	}
	
}
