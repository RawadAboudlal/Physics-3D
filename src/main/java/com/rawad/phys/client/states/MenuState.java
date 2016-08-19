package com.rawad.phys.client.states;

import com.rawad.gamehelpers.client.gamestates.State;
import com.rawad.gamehelpers.game.entity.Entity;
import com.rawad.phys.client.Client;
import com.rawad.phys.client.renderengine.DebugRender;
import com.rawad.phys.client.renderengine.Texture;
import com.rawad.phys.client.renderengine.WorldRender;
import com.rawad.phys.entity.EEntity;
import com.rawad.phys.entity.RenderingComponent;
import com.rawad.phys.entity.TransformComponent;
import com.rawad.phys.fileparser.ObjFileParser;
import com.rawad.phys.game.RenderingSystem;
import com.rawad.phys.loader.Loader;
import com.rawad.phys.math.Vector3f;

public class MenuState extends State {
	
	// Define renderers up here, initialize and add them in constructor.
	
	private Entity camera;
	
	private Entity crate;
	
	private Texture texture;
	
	@Override
	public void init() {
		
		camera = Entity.createEntity(EEntity.CAMERA);
		
		crate = Entity.createEntity(EEntity.CRATE);
		
		Client client = game.getProxies().get(Client.class);
		Loader loader = client.getLoaders().get(Loader.class);
		
		ObjFileParser objFileParser = client.getFileParsers().get(ObjFileParser.class);
		
		texture = loader.loadTexture("unknown");
		
		crate.getComponent(RenderingComponent.class).setTexture(texture);
		crate.getComponent(RenderingComponent.class).setModel(loader.loadModel(objFileParser, "sphere"));
		// TODO: Properly export monkey model.
		
		TransformComponent crateTransform = crate.getComponent(TransformComponent.class);
		crateTransform.setPosition(new Vector3f(0f, 0f, -3.5f));
		crateTransform.setRotationAxis(new Vector3f(1f, 1f, 0));
		
		WorldRender worldRender = new WorldRender(camera);
		
		masterRender.getRenders().put(worldRender);
		masterRender.getRenders().put(new DebugRender());
		
		gameSystems.put(new RenderingSystem(worldRender));
		
	}
	
	@Override
	public void terminate() {
		texture.delete();
	}
	
	@Override
	public void onActivate() {
		
		world.addEntity(camera);
		world.addEntity(crate);
		
	}
	
	@Override
	public void onDeactivate() {
		
	}
	
}
