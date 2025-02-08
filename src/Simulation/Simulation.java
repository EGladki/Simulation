package Simulation;

import Simulation.Actions.InitActions;

public class Simulation {
    private WorldMapFactory worldMapFactory;
    WorldMap worldMap;

    public void run() {
//        worldMap = Simulation.WorldMapFactory.createCustomWorldMap(7, 7);
        worldMap = WorldMapFactory.createDefaultWorldMap();
        InitActions initActions = new InitActions(worldMap);
        initActions.populateWithCreatures();

        Renderer renderer = new Renderer();
        renderer.render(worldMap);
    }

}
