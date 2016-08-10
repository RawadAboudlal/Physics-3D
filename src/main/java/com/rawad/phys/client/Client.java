package com.rawad.phys.client;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.rawad.gamehelpers.client.gamestates.IStateChangeListener;
import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.gamehelpers.client.gamestates.StateManager;
import com.rawad.gamehelpers.client.renderengine.IRenderable;
import com.rawad.gamehelpers.game.Game;
import com.rawad.gamehelpers.game.Proxy;

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
		
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
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
	
	public Window getWindow() {
		return window;
	}
	
}
