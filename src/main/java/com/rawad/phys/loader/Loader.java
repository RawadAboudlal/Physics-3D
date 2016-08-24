package com.rawad.phys.loader;

import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import com.rawad.gamehelpers.fileparser.xml.EntityFileParser;
import com.rawad.gamehelpers.game.entity.Blueprint;
import com.rawad.gamehelpers.resources.ALoader;
import com.rawad.gamehelpers.utils.Util;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.client.renderengine.Texture;
import com.rawad.phys.client.renderengine.shaders.ShaderType;
import com.rawad.phys.entity.EEntity;
import com.rawad.phys.fileparser.ObjFileParser;

public class Loader extends ALoader {
	
	private static final String FOLDER_RES = "res";
	private static final String FOLDER_ENTITY_BLUEPRINTS = "entities";
	private static final String FOLDER_MODELS = "models";
	private static final String FOLDER_TEXTURES = "textures";
	
	private static final String EXTENSION_ENTITY = "xml";
	private static final String EXTENSION_MODEL = "obj";
	private static final String EXTENSION_TEXTURE = "png";
	
	public Loader() {
		super(FOLDER_RES);
	}
	
	public Texture loadTexture(String name) {
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		STBImage.stbi_set_flip_vertically_on_load(GL_TRUE);
		
		String path = getFilePathFromParts(EXTENSION_TEXTURE, FOLDER_TEXTURES, name);
		
		ByteBuffer image = STBImage.stbi_load(path, w, h, comp, STBImage.STBI_rgb_alpha);
		
		if(image == null) throw new RuntimeException("Failed to load texture file ("+ path +")." + Util.NL
				+ STBImage.stbi_failure_reason());
		
		return new Texture(w.get(), h.get(), image);
		
	}
	
	public Model loadModel(ObjFileParser parser, String name) {
		
		String path = getFilePathFromParts(EXTENSION_MODEL, FOLDER_MODELS, name);
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			
			parser.parseFile(reader);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return parser.getModel();
		
	}
	
	public Blueprint loadEntityBlueprint(EntityFileParser parser, String name) {
		
		String path = getFilePathFromParts(EXTENSION_ENTITY, FOLDER_ENTITY_BLUEPRINTS, name);
		
		final String[] contextPaths = {
			EEntity.class.getPackage().getName(),	
		};
		
		parser.setContextPaths(contextPaths);
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			parser.parseFile(reader);
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		Blueprint blueprint = new Blueprint(parser.getEntity());
		
		return blueprint;
		
	}
	
	public static String loadShaderSource(Class<? extends Object> clazz, CharSequence shaderName, ShaderType shaderType) {
		
		StringBuilder builder = new StringBuilder();
		
		String name = shaderName + REGEX_EXTENSION + shaderType.getExtensionName();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clazz.getgetResourceAsStream(name)))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				builder.append(line).append(Util.NL);
			}
			
		} catch(Exception ex) {
			throw new RuntimeException("Failed to load shader " + shaderName + " of type " + shaderType + ". "
					+ System.lineSeparator() + ex.getMessage());
		}
		
		return builder.toString();
		
	}
	
}
