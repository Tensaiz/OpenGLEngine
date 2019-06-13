package engineTester;

import entities.Camera;
import entities.Light;
import entities.Player;
import renderEngine.*;
import entities.Entity;
import models.TexturedModel;
import org.joml.Vector3f;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        RawModel model = OBJLoader.loadObjModel("tree", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
                new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);

        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            entities.add(new Entity(staticModel,
                    new Vector3f(random.nextFloat() * 800 - 400,
                                 0,
                                 random.nextFloat() * -600),
                                0 ,0 ,0, 3));

            entities.add(new Entity(grass,
                    new Vector3f(random.nextFloat() * 800 - 400,
                            0,
                            random.nextFloat() * -600),
                    0 ,0 ,0, 1));

            entities.add(new Entity(fern,
                    new Vector3f(random.nextFloat() * 800 - 400,
                            0,
                            random.nextFloat() * -600),
                    0 ,0 ,0, 0.6f));
        }

        Light light = new Light(new Vector3f(100,5000, 5000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);

        Sync sync = new Sync();

        MasterRenderer renderer = new MasterRenderer();

        RawModel bunnyModel = OBJLoader.loadObjModel("stanfordBunny", loader);
        TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(
                loader.loadTexture("white")
        ));

        Player player = new Player(stanfordBunny, new Vector3f(100, 0, -50), 0,0 ,0 , 1);

        Camera camera = new Camera(player);

        while(!DisplayManager.closed()) {
            DisplayManager.updateDisplayBuffers();

            camera.move();
            player.move();

            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for (Entity entity: entities) {
                renderer.processEntity(entity);
            }

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
