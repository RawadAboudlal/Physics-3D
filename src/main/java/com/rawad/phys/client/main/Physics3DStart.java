package com.rawad.phys.client.main;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
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
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Physics3DStart {
	
	private static final String TITLE = "Physics 3D";
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	public static void main(String[] args) {
		
		GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to intialize GLFW.");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
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
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		while(!glfwWindowShouldClose(windowId)) {
			
//			width.rewind();
//			height.rewind();
			
			glfwGetFramebufferSize(windowId, width, height);
			
			glViewport(0, 0, width.get(0), width.get(0));
			glClear(GL_COLOR_BUFFER_BIT);
			
			width.rewind();
			height.rewind();
			
			glMatrixMode(GL_MODELVIEW);
			
			glLoadIdentity();
			glRotatef((float) glfwGetTime() * 50f, 0f, 0f, 1f);
			
			glBegin(GL11.GL_TRIANGLES);
			glColor3f(1f, 0f, 0f);
			glVertex3f(-0.5f, -0.5f, 0f);
            glColor3f(0f, 1f, 0f);
            glVertex3f(0.5f, -0.5f, 0f);
            glColor3f(0f, 0f, 1f);
            glVertex3f(0f, 0.5f, 0f);
			glEnd();
			
			glfwSwapBuffers(windowId);
			glfwPollEvents();// Use GLFW.glfwWaitEvents() so the application waits until an event occurs.
			
		}
		
		glfwDestroyWindow(windowId);
		keyCallback.free();
		
		glfwTerminate();
		errorCallback.free();
		
	}
	
}
