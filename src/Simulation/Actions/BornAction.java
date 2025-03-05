package Simulation.Actions;

import Simulation.WorldMap;
import Simulation.WorldMapFactory;
import Simulation.entity.Entity;
import Simulation.entity.Grass;
import Simulation.entity.Herbivore;
import Simulation.entity.Predator;

import java.util.List;

public class BornAction implements Actions {
    private final WorldMapFactory worldMapFactory = new WorldMapFactory();

    @Override
    public void execute(WorldMap worldMap) {
        bornMissingEntities(worldMap);
    }

    public void bornMissingEntities(WorldMap worldMap) {
        List<Entity> entities = worldMap.getAll();
        int herbivoreCount = 0;
        int predatorCount = 0;
        int grassCount = 0;

        for (Entity entity : entities) {
            if (entity instanceof Herbivore) {
                herbivoreCount++;
            } else if (entity instanceof Predator) {
                predatorCount++;
            } else if (entity instanceof Grass) {
                grassCount++;
            }
        }
        if (herbivoreCount < WorldMapFactory.DEFAULT_HERBIVORE_QUANTITY) {
            worldMapFactory.placeNewEntityOnMap(worldMap, new Herbivore(), WorldMapFactory.DEFAULT_HERBIVORE_QUANTITY - herbivoreCount);
        }
        if (predatorCount < WorldMapFactory.DEFAULT_PREDATOR_QUANTITY) {
            worldMapFactory.placeNewEntityOnMap(worldMap, new Predator(), WorldMapFactory.DEFAULT_PREDATOR_QUANTITY - predatorCount);
        }
        if (grassCount < WorldMapFactory.DEFAULT_GRASS_QUANTITY) {
            worldMapFactory.placeNewEntityOnMap(worldMap, new Grass(), WorldMapFactory.DEFAULT_GRASS_QUANTITY - grassCount);
        }

    }


}
