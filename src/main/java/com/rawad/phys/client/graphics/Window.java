package com.rawad.phys.client.graphics;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class Window {
	
	private final long id;
	
	private boolean vsync;
	
	public Window(int width, int height, CharSequence title, boolean vsync) {
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		long tempWindow = glfwCreateWindow(1, 1, "", NULL, NULL);
		glfwMakeContextCurrent(tempWindow);
		GLCapabilities glCaps = GL.createCapabilities();
		glfwDestroyWindow(tempWindow);
		
		glfwDefaultWindowHints();
		
		if(glCaps.OpenGL32) {
			
			glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
			glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
			
		} else {
			throw new RuntimeException("OpenGL 3.2 is not suppported; try updating graphics driver.");
		}
		
		glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW_FALSE);// TODO: Make multiple screen resolutions.
		
		id = glfwCreateWindow(width, height, title, NULL, NULL);
		if(id == NULL) {
			GLFW.glfwTerminate();
			throw new RuntimeException("Failed to create GLFW window.");
		}
		
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(id, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
		
		glfwMakeContextCurrent(id);;
		GL.createCapabilities();
		
		setVSync(vsync);
		
	}
	
	public boolean isClosing() {
		return GLFW.glfwWindowShouldClose(id);
	}
	
	public void setTitle(CharSequence title) {
		GLFW.glfwSetWindowTitle(id, title);
	}
	
	public void update() {
		GLFW.glfwSwapBuffers(id);
		GLFW.glfwPollEvents();
	}
	
	/**
	 * Destory window and release callbacks.
	 */
	public void destroy() {
		GLFW.glfwDestroyWindow(id);
	}
	
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
		
		if(vsync)
			GLFW.glfwSwapInterval(1);
		else
			GLFW.glfwSwapInterval(0);
		
	}
	
	public boolean isVSyncEnabled() {
		return vsync;
	}
	
}
