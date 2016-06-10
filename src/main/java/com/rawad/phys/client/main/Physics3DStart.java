package com.rawad.phys.client.main;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import com.rawad.phys.math.Matrix4f;

public class Physics3DStart {
	
	private static final String TITLE = "Physics 3D";
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	public static void main(String[] args) throws URISyntaxException {
		
		GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to intialize GLFW.");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		long windowId = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
		
		if(windowId == NULL) {
			glfwTerminate();
			throw new RuntimeException("Failed to create GLFW window.");
		}
		
		GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if(action == GLFW.GLFW_PRESS) {
					switch(key) {
					case GLFW_KEY_ESCAPE:
						glfwSetWindowShouldClose(window, true);
						break;
						
						default:
							break;
					}
					
				}
			}
		};
		
		glfwSetKeyCallback(windowId, keyCallback);
		
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(windowId, vidMode.width() / 2 - (WIDTH / 2), vidMode.height() / 2 - (HEIGHT / 2));
		
		glfwMakeContextCurrent(windowId);
		GL.createCapabilities();
		
		glfwSwapInterval(1);
		glfwShowWindow(windowId);
		
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		// How to handle texture wrapping.
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
		// How to handle texture scaling.
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);// Or GL11.GL_LINEAR but much slower (takes
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);// average of 4 nearby pixels).
		
		STBImage.stbi_set_flip_vertically_on_load(1);// Because OpenGL uses bottom left as 0,0 while images normally use
		// top left as 0,0.
		
		IntBuffer texWidth = BufferUtils.createIntBuffer(1);
		IntBuffer texHeight = BufferUtils.createIntBuffer(1);
		IntBuffer texData = BufferUtils.createIntBuffer(1);// Decoded ARGB values (or any other format).
		
		ByteBuffer image = STBImage.stbi_load("res/image.png", texWidth, texHeight, texData, STBImage.STBI_rgb_alpha);
		
		if(image == null) throw new RuntimeException("Failed to load texture. " + STBImage.stbi_failure_reason());
		
		GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, texWidth.get(), texHeight.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, 
				texData);
		
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, loadShader("shader.vert"));
		glCompileShader(vertexShader);
		
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, loadShader("shader.frag"));
		glCompileShader(fragmentShader);
		
		int shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glBindFragDataLocation(shaderProgram, 0, "fragColor");// Optional with one out variable in frag shader.
		glLinkProgram(shaderProgram);
		
		int status = glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS);
		if(status != GL_TRUE) throw new RuntimeException("Shader program wasn't linked. " 
						+ GL20.glGetProgramInfoLog(shaderProgram));
		
		glUseProgram(shaderProgram);
		
		int posAttrib = glGetAttribLocation(shaderProgram, "position");
		glEnableVertexAttribArray(posAttrib);
		glVertexAttribPointer(posAttrib, 
				3, // Size of data, per vertex.
				GL_FLOAT, // Type of data.
				false, // Normalized.
				6 * Float.BYTES, // The stride to get from one VBO to the next. Wwe use 2 attribs each w/ 3 floats.
				0);
		
		int colAttrib = glGetAttribLocation(shaderProgram, "color");
		glEnableVertexAttribArray(colAttrib);
		glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 6 * Float.BYTES, 
				3 * Float.BYTES);// Every third position in our vbo (scaled by bytes in float).
		
		int uniModel = glGetUniformLocation(shaderProgram, "model");
		Matrix4f model = new Matrix4f();
		glUniformMatrix4fv(uniModel, false, model.getBuffer());
		
		int uniView = glGetUniformLocation(shaderProgram, "view");
		Matrix4f view = new Matrix4f();
		glUniformMatrix4fv(uniView, false, view.getBuffer());
		
		int uniProj = glGetUniformLocation(shaderProgram, "projection");
		Matrix4f projection = Matrix4f.orthographic(120, WIDTH / HEIGHT, -1f, 1f, -1f, 1f);
		glUniformMatrix4fv(uniProj, false, projection.getBuffer());
		
		int vao = glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		FloatBuffer vertices = BufferUtils.createFloatBuffer(3 * 6);
		vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);// Counter-clockwise order.
		vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
		vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
		vertices.flip();
		
		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(vbo, vertices, GL_STATIC_DRAW);
		
		while(!glfwWindowShouldClose(windowId)) {
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 0);
			
			glfwSwapBuffers(windowId);
			glfwPollEvents();// Use GLFW.glfwWaitEvents() so the application waits until an event occurs.
			
		}
		
		GL30.glDeleteVertexArrays(vao);
		GL15.glDeleteBuffers(vbo);
		GL20.glDeleteShader(fragmentShader);
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteProgram(shaderProgram);
		
		glfwDestroyWindow(windowId);
		keyCallback.free();
		
		glfwTerminate();
		errorCallback.free();
		
	}
	
	private static String loadShader(String shaderFileName) {
		
		StringBuilder re = new StringBuilder();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(Physics3DStart.class
				.getResourceAsStream(shaderFileName)))) {
			
			String line = "";
			
			while((line = reader.readLine()) != null) {
				re.append(line).append(System.lineSeparator());
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return re.toString();
		
	}
	
}
