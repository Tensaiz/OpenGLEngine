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



        RawModel model = OBJLoader.loadObjModel("stall", loader);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(staticModel, new Vector3f(0, -2.5f, -25), 0,0,0,1);
        Light light = new Light(new Vector3f(0,0, -20), new Vector3f(1, 1, 1));


        Camera camera = new Camera();

        Sync sync = new Sync();

        MasterRenderer renderer = new MasterRenderer();

        while(!DisplayManager.closed()) {
            DisplayManager.updateDisplayBuffers();

            entity.increaseRotation(0, 0.2f, 0);
            camera.move();

            renderer.processEntity(entity);
            renderer.render(light, camera);

            // FPS sync
            sync.sync(144);

            // render
            DisplayManager.updateDisplayEvents();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }

}
