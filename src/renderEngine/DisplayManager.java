package renderEngine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;


public class DisplayManager {

    private static long window;
    private static final String TITLE = "OpenGL Engine";
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    public static void createDisplay() {
        if (!GLFW.glfwInit()) {
            System.err.println("Error: Couldn't create GLFW window");
            System.exit(-1);
        }

        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);

        if (window == 0) {
            System.err.println("Error: Window couldn't be created");
            System.exit(-1);
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
    }

    public static void updateDisplayBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public static void updateDisplayEvents() {
        GLFW.glfwPollEvents();
    }

    public static void closeDisplay() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public static boolean closed() {
        return GLFW.glfwWindowShouldClose(window);
    }

}
