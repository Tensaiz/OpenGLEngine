package engineTester;

import entities.Camera;
import renderEngine.*;
import entities.Entity;
import models.TexturedModel;
import org.joml.Vector3f;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

import javax.xml.soap.Text;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -50), 0,0,0,1);

        Camera camera = new Camera();

        Sync sync = new Sync();

        while(!DisplayManager.closed()) {
            DisplayManager.updateDisplayBuffers();

            entity.increaseRotation(0, 0.5f, 0);
            camera.move();
            renderer.prepare();
            // game logic
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();

            // FPS sync
            sync.sync(144);

            // render
            DisplayManager.updateDisplayEvents();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
