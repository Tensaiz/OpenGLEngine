package renderEngine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;


public class DisplayManager {

    private static long window;
    private static final String TITLE = "OpenGL Engine";
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static long lastFrameTime;
    private static float delta;

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
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplayBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public static void updateDisplayEvents() {
        GLFW.glfwPollEvents();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = getCurrentTime();
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static void closeDisplay() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public static boolean closed() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public static long getWindow() {
        return window;
    }

    public static String getTitle() {
        return TITLE;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }

}
