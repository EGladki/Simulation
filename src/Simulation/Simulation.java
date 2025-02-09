package Simulation;

import Simulation.Actions.InitActions;
import Simulation.entity.Creature;
import Simulation.entity.Entity;
import Simulation.entity.Predator;

import java.util.List;

public class Simulation {
    private WorldMapFactory worldMapFactory;
    WorldMap worldMap;

    public void startSimulation() {
//        worldMap = WorldMapFactory.createCustomWorldMap(5, 5);
        worldMap = WorldMapFactory.createDefaultWorldMap();
        InitActions initActions = new InitActions(worldMap);
        initActions.populateWithCreatures();
        Renderer renderer = new Renderer();
        renderer.render(worldMap);


        for (int i = 0; i < 2; i++) {
            List<Entity> allEntities = worldMap.allEntities();
            for (Entity entity : allEntities) {
                if (entity instanceof Creature) {
                    ((Creature) entity).makeMove(worldMap);
                    System.out.println();
                    renderer.render(worldMap);
                }
            }
        }
    }


}
