package com.rawad.phys.client;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL11;

import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.gamehelpers.client.gamestates.StateChangeListener;
import com.rawad.gamehelpers.client.gamestates.StateChangeRequest;
import com.rawad.gamehelpers.client.gamestates.StateManager;
import com.rawad.gamehelpers.client.renderengine.Renderable;
import com.rawad.gamehelpers.game.Game;
import com.rawad.gamehelpers.game.Proxy;
import com.rawad.gamehelpers.game.entity.Blueprint;
import com.rawad.gamehelpers.game.entity.BlueprintManager;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.input.InputAction;
import com.rawad.phys.client.input.InputBindings;
import com.rawad.phys.client.input.KeyInputCallback;
import com.rawad.phys.client.input.MouseInputCallback;
import com.rawad.phys.client.states.MenuState;
import com.rawad.phys.entity.ControllerComponent;
import com.rawad.phys.entity.DirectionComponent;
import com.rawad.phys.entity.EEntity;
import com.rawad.phys.entity.MovementComponent;
import com.rawad.phys.entity.RenderingComponent;
import com.rawad.phys.entity.RollingComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.entity.UserViewComponent;
import com.rawad.phys.fileparser.ObjFileParser;
import com.rawad.phys.loader.Loader;

public class Client extends Proxy implements Renderable, StateChangeListener {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private StateManager sm;
	
	private Window window;
	
	private GLFWWindowCloseCallbackI windowCloseCallback;
	
	private KeyInputCallback keyCallback;
	private MouseInputCallback mouseCallback;
	
	private InputBindings inputBindings;
	
	@Override
	public void preInit(Game game) {
		super.preInit(game);
		
		inputBindings = new InputBindings();
		
		inputBindings.put(InputAction.FORWARD, GLFW.GLFW_KEY_W);
		inputBindings.put(InputAction.BACKWARD, GLFW.GLFW_KEY_S);
		inputBindings.put(InputAction.RIGHT, GLFW.GLFW_KEY_D);
		inputBindings.put(InputAction.LEFT, GLFW.GLFW_KEY_A);
		inputBindings.put(InputAction.JUMP, GLFW.GLFW_KEY_SPACE);
		
		keyCallback = new KeyInputCallback(inputBindings);
		mouseCallback = new MouseInputCallback(inputBindings);
		
		sm = new StateManager(game, this);
		
		Loader loader = new Loader();
		
		ObjFileParser objFileParser = new ObjFileParser();
		
		loaders.put(loader);
		
		fileParsers.put(objFileParser);
		
		windowCloseCallback = (window) -> {
			game.requestStop();
		};
		
		Entity camera = Entity.createEntity();
		camera.addComponent(new TransformComponent()).addComponent(new UserViewComponent())
				.addComponent(new DirectionComponent());
		BlueprintManager.addBlueprint(EEntity.CAMERA, new Blueprint(camera));
		
		Entity crate = Entity.createEntity();
		crate.addComponent(new TransformComponent()).addComponent(new RenderingComponent());
		BlueprintManager.addBlueprint(EEntity.CRATE, new Blueprint(crate));
		
		Entity ball = Entity.createEntity();
		ball.addComponent(new TransformComponent()).addComponent(new MovementComponent())
			.addComponent(new DirectionComponent()).addComponent(new RollingComponent())
			.addComponent(new ControllerComponent()).addComponent(new RenderingComponent());
		BlueprintManager.addBlueprint(EEntity.BALL, new Blueprint(ball));
		
	}
	
	@Override
	public void init() {
		
		if(!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW.");
		
		window = new Window(WIDTH, HEIGHT, game.getName(), false);
		window.setCloseCallback(windowCloseCallback);
		window.setKeyCallback(keyCallback);
		window.setMouseCallback(mouseCallback);
		
		GL11.glClearColor(0.5f, 0.5f, 1f, 1f);
		
		GL11.glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		sm.addState(new MenuState());
		
		sm.setCurrentState(sm.getStates().get(MenuState.class));
		sm.setState(StateChangeRequest.instance(MenuState.class));
		
		update = true;
		
	}
	
	@Override
	public void tick() {
		
		render();
		
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
	public void terminate() {
		
		update = false;
		
		window.destroy();
		GLFW.glfwTerminate();
		
	}
	
	public Window getWindow() {
		return window;
	}
	
	public InputBindings getInputBindings() {
		return inputBindings;
	}
	
}
