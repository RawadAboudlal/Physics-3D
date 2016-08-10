package com.rawad.phys.client;

import org.lwjgl.glfw.GLFW;

import com.rawad.gamehelpers.client.gamestates.IStateChangeListener;
import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.gamehelpers.client.gamestates.StateManager;
import com.rawad.gamehelpers.client.renderengine.IRenderable;
import com.rawad.gamehelpers.game.Game;
import com.rawad.gamehelpers.game.Proxy;
import com.rawad.phys.client.graphics.Window;

public class Client extends Proxy implements IRenderable, IStateChangeListener {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private StateManager sm;
	
	private Window window;
	
	@Override
	public void preInit(Game game) {
		super.preInit(game);
		
		sm = new StateManager(game, this);
		
	}
	
	@Override
	public void init() {
		
		if(!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW.");
		
		window = new Window(WIDTH, HEIGHT, game.getName(), false);
		
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void render() {
		
		sm.render();
		
		window.update();
		
	}
	
	@Override
	public void onStateChange(State oldState, State newState) {
		
	}
	
	@Override
	public void stop() {
		
		window.destroy();
		GLFW.glfwTerminate();
		
	}
	
}
