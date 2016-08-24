package com.rawad.phys.client;

import com.rawad.gamehelpers.game.GameManager;
import com.rawad.phys.game.Physics3D;

public class Physics3DStart {
	
	// Game Development (Multi-threading): 2nd answer
	// http://gamedev.stackexchange.com/questions/2116/multi-threaded-game-engine-design-resources
	// In depth explanation of multi-threading models:
	// http://www.gamasutra.com/view/feature/130247/multithreaded_game_engine_.php?print=1
	// Use Compute Shaders to keep particles on GPU.
	
	// OpenGL Resources: https://www.opengl.org/documentation/books/ and http://openglbook.com/the-book.html
	// http://gamedev.stackexchange.com/questions/32876/good-resources-for-learning-modern-opengl-3-0-or-later
	// http://www.opengl-tutorial.org
	// Matrices: http://www.matrix44.net/cms/notes/opengl-3d-graphics/basic-3d-math-matrices
	// Visualizing Matrices: http://www.matrix44.net/cms/notes/opengl-3d-graphics/coordinate-systems-in-opengl
	// Detailed explanation of rendering vertices in OpenGL: https://www.opengl.org/wiki/Vertex_Specification
	// OpenGL, the ultimate guide: https://www.opengl.org/registry/doc/glspec45.core.withchanges.pdf
	// OpenGL, semi(?)-ultimate guide: https://open.gl
	
	// Good point about interesting games:
	//http://gamedev.stackexchange.com/questions/128775/how-to-avoid-players-getting-lost-in-and-or-bored-by-the-meta-game
	
	/*/ Rendering best practice:
	
	initialization:
    for each batch
        generate, store, and bind a VAO
        bind all the buffers needed for a draw call (VBO for position, normal, indexing)
        unbind the VAO
	
	main loop/whenever you render:
	    for each batch
	        bind VAO
	        glDrawArrays(...); or glDrawElements(...); etc.
	    unbind VAO
	
	/**/
	
	private static final Physics3D game = new Physics3D();
	
	private static final Client client = new Client();
	
	public static void main(String... args) {
		
		game.getProxies().put(client);
		
		GameManager.launchGame(game);
		
	}
	
	/*/
	private static TexturedModelShader program;
	private static VertexArrayObject vao;
	
	private static IntBuffer indicesBuffer;
	private static FloatBuffer verticesBuffer;
	
	private static Model cube;
	
	private static Matrix4f model;
	private static Matrix4f view;
	private static Matrix4f projection;
	
	private static int vbo;
	private static int ibo;
	
	private static int locationu_model;
	
	private static final void init() {
		
		program = new TexturedModelShader();
		
		vao = new VertexArrayObject();
		
		vbo = GL15.glGenBuffers();
		ibo = GL15.glGenBuffers();
		
		program.use();
		vao.bind();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		
		final int[] indices = {
				0, 1, 2,
				2, 3, 0,
				1, 5, 6,
				6, 2, 1,
				3, 2, 6,
				6, 7, 3,
				5, 4, 7,
				7, 6, 5,
				0, 4, 7,
				7, 3, 0,
				0, 4, 5,
				5, 1, 0,
		};
		
		final float[] vertices = {
				-1, -1, 1,
				1, -1, 1,
				1, 1, 1,
				-1, 1, 1,
				-1, -1, -1,
				1, -1, -1,
				1, 1, -1,
				-1, 1, -1,
		};
		
		indicesBuffer = BufferUtils.createIntBuffer(indices.length);
		verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		
		indicesBuffer.put(indices);
		verticesBuffer.put(vertices);
		
		indicesBuffer.flip();
		verticesBuffer.flip();
		
		cube = Loader.loadModel(new ObjFileParser(), "cube");
		indicesBuffer = cube.getIndices();
		verticesBuffer = cube.getVertices();
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		
		int location_position = program.getAttributeLocation("position");
		GL20.glEnableVertexAttribArray(location_position);
		GL20.glVertexAttribPointer(location_position, Vector3f.SIZE, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		
		GLFW.glfwGetWindowSize(GLFW.glfwGetCurrentContext(), widthBuffer, heightBuffer);
		
		int width = widthBuffer.get();
		int height = heightBuffer.get();
		
		model = new Matrix4f().multiply(Matrix4f.translate(0f, 0f, -3.5f));
		view = new Matrix4f();
		projection = Matrix4f.perspective(90f, (float) width / (float) height, 0.1f, 100f);
		
		locationu_model = program.getUniformLocation("model");
		program.setUniform(locationu_model, model);
		
		int locationu_view = program.getUniformLocation("view");
		program.setUniform(locationu_view, view);
		
		int locationu_projection = program.getUniformLocation("projection");
		program.setUniform(locationu_projection, projection);
		
	}
	
	private static final void render() {
		
		program.use();
		vao.bind();
		
		model.multiply(Matrix4f.rotate(1f / 5f, 0f, 1f, 0f));
		program.setUniform(locationu_model, model);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, indicesBuffer.capacity(), GL11.GL_UNSIGNED_INT, 0);
		
	}
	
	private static final void dispose() {
		
		GL15.glDeleteBuffers(vbo);
		GL15.glDeleteBuffers(ibo);
		
		vao.delete();
		program.delete();
		
	}/**/
	
	/*/
	// Indexed Quad
	final int[] indices = {
			2, 1, 0,
			2, 3, 0,
	};
	
	final float[] vertices = {
			-1f, -1f, 0,
			1f, -1f, 0,
			1f, 1f, 0,
			-1f, 1f, 0,
	};
	/**/
	
	/*/
	// Indexed Cube
	final int[] indices = {
			0, 1, 2,
			2, 3, 0,
			1, 5, 6,
			6, 2, 5,
			3, 2, 6,
			6, 7, 3,
			5, 4, 7,
			7, 6, 5,
			0, 4, 7,
			7, 3, 0,
			0, 4, 5,
			5, 1, 0
	};
	
	final float[] vertices = {
			-1, -1, 1,
			1, -1, 1,
			1, 1, 1,
			-1, 1, 1,
			-1, -1, -1,
			1, -1, -1,
			1, 1, -1,
			-1, 1, -1
	};/**/
	
	/*/
	private float[] vertex = {
		 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
		// Top
		 -0.2f, 0.8f, 0.0f, 0.0f, 1.0f, 0.0f,
		 0.2f, 0.8f, 0.0f, 0.0f, 0.0f, 1.0f,
		 0.0f, 0.8f, 0.0f, 0.0f, 1.0f, 1.0f,
		 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
		// Bottom
		 -0.2f, -0.8f, 0.0f,  0.0f, 0.0f, 1.0f ,
		 0.2f, -0.8f, 0.0f,  0.0f, 1.0f, 0.0f ,
		 0.0f, -0.8f, 0.0f,  0.0f, 1.0f, 1.0f ,
		 0.0f, -1.0f, 0.0f,  1.0f, 0.0f, 0.0f ,
		// Left
		 -0.8f, -0.2f, 0.0f,  0.0f, 1.0f, 0.0f ,
		 -0.8f, 0.2f, 0.0f,  0.0f, 0.0f, 1.0f ,
		 -0.8f, 0.0f, 0.0f,  0.0f, 1.0f, 1.0f ,
		 -1.0f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f ,
		// Right
		 0.8f, -0.2f, 0.0f, 0.0f, 0.0f, 1.0f ,
		 0.8f, 0.2f, 0.0f, 0.0f, 1.0f, 0.0f ,
		 0.8f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f ,
		 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f
	};
	
	private int[] index = {
			// Top
			0, 1, 3,
			0, 3, 2,
			3, 1, 4,
			3, 4, 2,
			// Bottom
			0, 5, 7,
			0, 7, 6,
			7, 5, 8,
			7, 8, 6,
			// Left
			0, 9, 11,
			0, 11, 10,
			11, 9, 12,
			11, 12, 10,
			// Right
			0, 13, 15,
			0, 15, 14,
			15, 13, 16,
			15, 16, 14
	};
	/**/
	
	// Minimum required for rendering a trianlge on screen.
	/*/
	float[] vertices = {
			-1.0f, -1.0f, 0.0f,
			1.0f, -1.0f, 0.0f,
			0.0f,  1.0f, 0.0f,
	};
	int vao = GL30.glGenVertexArrays();
	GL30.glBindVertexArray(vao);
	
	int vbo = GL15.glGenBuffers();
	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
	
	// v Put in loop, called every rendering cycle.
	GL20.glEnableVertexAttribArray(vao);
	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
	
	GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length);
	
	GL20.glDisableVertexAttribArray(vao);/**/
	
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
