package com.rawad.phys.client.main;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.opengl.GL11;

import com.rawad.phys.client.graphics.Renderer;
import com.rawad.phys.client.graphics.Texture;
import com.rawad.phys.client.graphics.Window;

public class Physics3DStart {
	
	private static final String TITLE = "Physics 3D";
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	public static void main(String... args) {
		
		if(!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW.");
		
		Window window = new Window(WIDTH, HEIGHT, TITLE, true);
		
		Renderer renderer = new Renderer();
		renderer.init();
		
		Texture texture = Texture.loadTexture("res/image.png");
		texture.bind();
		
		GL11.glClearColor(0.5f, 0.5f, 1f, 1f);
		
		while(!window.isClosing()) {
			
			renderer.clear();
			
//			renderer.drawTexture(texture, 20f, 20f, Color.RED);
			renderer.drawText("Hello 3D World!", 0f, 0f);
			
			window.update();
			
		}
		
		texture.delete();
		renderer.dispose();
		window.destroy();
		glfwTerminate();
		
	}
	
	/*/
	public static void main(String[] args)  throws URISyntaxException {
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
		
	}/**/
	
}
