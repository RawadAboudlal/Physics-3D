package com.rawad.phys.loader;

import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import com.rawad.phys.client.fileparser.ObjFileParser;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.model.Model;
import com.rawad.phys.util.Util;

public class Loader {
	
	private static final Executor loadingExecutor = Executors.newSingleThreadExecutor((Runnable r) -> {
		Thread t = new Thread(r, "Loading Thread");
		t.setDaemon(true);
		return t;
	});
	
	private static final String FOLDER_RES = "res";
	private static final String FOLDER_MODELS = "models";
	private static final String FOLDER_TEXTURES = "textures";
	
	private static final String EXTENSION_MODEL = ".obj";
	private static final String EXTENSION_TEXTURE = ".png";
	
	public static Texture loadTexture(String name) {
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		STBImage.stbi_set_flip_vertically_on_load(GL_TRUE);
		
		String path = Loader.getFullPath(FOLDER_RES, FOLDER_TEXTURES, name) + EXTENSION_TEXTURE;
		
		ByteBuffer image = STBImage.stbi_load(path, w, h, comp, STBImage.STBI_rgb_alpha);
		
		if(image == null) throw new RuntimeException("Failed to load texture file ("+ path +")." + System.lineSeparator()
				+ STBImage.stbi_failure_reason());
		
		return new Texture(w.get(), h.get(), image);
		
	}
	
	public static Model loadModel(ObjFileParser parser, String name) {
		
		String path = Loader.getFullPath(FOLDER_RES, FOLDER_MODELS, name) + EXTENSION_MODEL;
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			
			parser.parseFile(reader);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return parser.getModel();
		
	}
	
	public static String getFullPath(String... pathParts) {
		return Util.getStringFromLines(pathParts, File.separator, false);
	}
	
	public static void addTask(Runnable runnableToLoad) {
		loadingExecutor.execute(runnableToLoad);
	}
	
}