package engineTester;

import entities.Camera;
import entities.Light;
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

        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(staticModel, new Vector3f(0, -5, -25), 0,0,0,1);
        Light light = new Light(new Vector3f(0,-5, -15), new Vector3f(1, 1, 1));


        Camera camera = new Camera();

        Sync sync = new Sync();

        while(!DisplayManager.closed()) {
            DisplayManager.updateDisplayBuffers();

            entity.increaseRotation(0, 0.2f, 0);
            camera.move();
            renderer.prepare();
            // game logic
            shader.start();
            shader.loadLight(light);
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
