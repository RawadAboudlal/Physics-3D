package com.rawad.phys.client.states;

import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.Client;
import com.rawad.phys.client.renderengine.DebugRender;
import com.rawad.phys.client.renderengine.Texture;
import com.rawad.phys.client.renderengine.WorldRender;
import com.rawad.phys.entity.AttachmentComponent;
import com.rawad.phys.entity.EEntity;
import com.rawad.phys.entity.RenderingComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.fileparser.ObjFileParser;
import com.rawad.phys.game.CameraFollowSystem;
import com.rawad.phys.game.CollisionSystem;
import com.rawad.phys.game.InputControlSystem;
import com.rawad.phys.game.JumpingSystem;
import com.rawad.phys.game.MovementSystem;
import com.rawad.phys.game.RenderingSystem;
import com.rawad.phys.game.RollingSystem;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Quaternionf;
import com.rawad.phys.math.Vector3f;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private Entity camera;
	
	private Entity crate;
	private Entity ball;
	
	private Texture texture;
	private Texture ballTexture;
	
	@Override
	public void init() {
		
		camera = Entity.createEntity(EEntity.CAMERA);
		
		crate = Entity.createEntity(EEntity.CRATE);
		ball = Entity.createEntity(EEntity.BALL);
		
		Client client = game.getProxies().get(Client.class);
		Loader loader = client.getLoaders().get(Loader.class);
		
		ObjFileParser objFileParser = client.getFileParsers().get(ObjFileParser.class);
		
		texture = loader.loadTexture("monkey");
		ballTexture = loader.loadTexture("unknown");
		
		AttachmentComponent attachmentComp = new AttachmentComponent();
		attachmentComp.setAttachedTo(ball);
		
		camera.addComponent(attachmentComp);
		
		crate.getComponent(RenderingComponent.class).setTexture(texture);
		crate.getComponent(RenderingComponent.class).setModel(loader.loadModel(objFileParser, "monkey"));
		
		TransformComponent crateTransform = crate.getComponent(TransformComponent.class);
		crateTransform.setPosition(new Vector3f(0f, 0f, -3.5f));
		crateTransform.setRotation(new Quaternionf(0f, 1f, 0f, 1f));
		
		ball.getComponent(RenderingComponent.class).setTexture(ballTexture);
		ball.getComponent(RenderingComponent.class).setModel(loader.loadModel(objFileParser, "sphere"));
		
		ball.getComponent(TransformComponent.class).setRotation(new Quaternionf(0f, 0f, 0f, 0f));
		
		gameSystems.put(new InputControlSystem(client.getInputBindings()));
		gameSystems.put(new JumpingSystem());
		gameSystems.put(new MovementSystem());
		gameSystems.put(new CollisionSystem());
		gameSystems.put(new RollingSystem());
		gameSystems.put(new CameraFollowSystem());
		
		WorldRender worldRender = new WorldRender(camera);
		
		masterRender.getRenders().put(worldRender);
		masterRender.getRenders().put(new DebugRender());
		
		gameSystems.put(new RenderingSystem(worldRender));
		
	}
	
	@Override
	public void terminate() {
		texture.delete();
		ballTexture.delete();
	}
	
	@Override
	public void onActivate() {
		
		world.addEntity(camera);
		world.addEntity(crate);
		world.addEntity(ball);
		
	}
	
	@Override
	public void onDeactivate() {
		
	}
	
}
