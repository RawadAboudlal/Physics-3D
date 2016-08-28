package com.rawad.phys.game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import com.rawad.gamehelpers.game.GameSystem;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.entity.ControllerComponent;

public class ControlSystem extends GameSystem implements GLFWKeyCallbackI {
	
	public ControlSystem() {
		super();
		
		compatibleComponentTypes.add(ControllerComponent.class);
		
	}
	
	@Override
	public void tick(Entity e) {
		
	}
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		if(action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
			
		}
		
	}
	
}
