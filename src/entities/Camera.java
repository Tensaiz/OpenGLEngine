package entities;

import InputManager.Keyboard;
import InputManager.Mouse;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(100, 35, 50);
    private float pitch = 10;
    private float yaw;
    private float roll;

    private Player player;
    private Mouse mouse = new Mouse();

    public Camera(Player player){
        this.player = player;
    }

    public void move() {
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.y = player.getPosition().y + verticalDistance;
        position.z = player.getPosition().z - offsetZ;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch() {
        if (mouse.getMouseButton2()) {
            float pitchChange = mouse.getMousePosY() * 0.001f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer() {
        if (mouse.getMouseButton1()) {
            float angleChange = mouse.getMousePosX() * 0.003f;
            angleAroundPlayer -= angleChange;
        }
    }

}
