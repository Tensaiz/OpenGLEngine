package InputManager;

import renderEngine.DisplayManager;

import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Keyboard {

    private static long window = DisplayManager.getWindow();

    public static boolean isKeyDown(int key) {
        if (glfwGetKey(window, key) == 0) {
            return false;
        } else {
            return true;
        }
    }

}
