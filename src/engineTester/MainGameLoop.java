package engineTester;

import renderEngine.Sync;
import entities.Entity;
import models.TexturedModel;
import org.joml.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import javax.xml.soap.Text;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();


        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0,
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel, new Vector3f(-1, 0, 0), 0,0,0,1);

        Sync sync = new Sync();

        while(!DisplayManager.closed()) {
            DisplayManager.updateDisplayBuffers();

            entity.increasePosition(0.002f, 0, 0);
            entity.increaseRotation(0, 1, 0);

            renderer.prepare();
            // game logic
            shader.start();
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
