package InputManager;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import renderEngine.DisplayManager;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

    private static long window = DisplayManager.getWindow();

    private double yOffset = 0;
    private boolean button1Down = false;
    private boolean button2Down = false;


    private double oldMousePosX;
    private double oldMousePosY;
    private double mousePosX;
    private double mousePosY;

    public Mouse() {

        GLFW.glfwSetScrollCallback(window, new GLFWScrollCallback() {
            @Override public void invoke (long window, double x, double y) {
                System.out.println(y);
                yOffset = y;
            }
        });

        GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                    button1Down = true;
                    oldMousePosX = mousePosX;
                } else if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE){
                    button1Down = false;
                } else if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS){
                    button2Down = true;
                    oldMousePosY = mousePosY;
                } else if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_RELEASE){
                    button2Down = false;
                }
            }
        });

        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mousePosX = xpos;
                mousePosY = ypos;
            }
        });

    }

    public float getDWheel() {
        return (float) yOffset;
    }

    public boolean getMouseButton1() {
        return button1Down;
    }

    public boolean getMouseButton2() {
        return button2Down;
    }

    public float getMouseDx() {
        float newPos = (float) (mousePosX - oldMousePosX);
        oldMousePosX = mousePosX;
        return newPos;
    }

    public float getMouseDY() {
        float newPos = (float) (mousePosY - oldMousePosY);
        oldMousePosY = mousePosY;
        return newPos;
    }
}
