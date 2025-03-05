package Simulation;

import Simulation.entity.*;

public class WorldMapFactory {
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_HERBIVORE_QUANTITY = 6;
    public static final int DEFAULT_PREDATOR_QUANTITY = 2;
    public static final int DEFAULT_GRASS_QUANTITY = 25;
    private static final int DEFAULT_TREE_QUANTITY = 10;
    private static final int DEFAULT_ROCK_QUANTITY = 10;

    public WorldMap createWorldMap(int width, int height) {
        WorldMap worldMap = new WorldMap(width, height);
        populateWithEntities(worldMap);
        return worldMap;
    }

    public void populateWithEntities(WorldMap worldMap) {
        placeNewEntityOnMap(worldMap, new Herbivore(), DEFAULT_HERBIVORE_QUANTITY);
        placeNewEntityOnMap(worldMap, new Predator(), DEFAULT_PREDATOR_QUANTITY);
        placeNewEntityOnMap(worldMap, new Grass(), DEFAULT_GRASS_QUANTITY);
        placeNewEntityOnMap(worldMap, new Tree(), DEFAULT_TREE_QUANTITY);
        placeNewEntityOnMap(worldMap, new Rock(), DEFAULT_ROCK_QUANTITY);
    }

    public void placeNewEntityOnMap(WorldMap worldMap, Entity entity, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Coordinates coordinates;
            do {
                coordinates = getRandomCoordinates(worldMap);
            } while (!worldMap.isValid(coordinates) || !worldMap.isEmpty(coordinates));
            try {
                Entity newEntity = entity.getClass().getDeclaredConstructor().newInstance();
                worldMap.put(coordinates, newEntity);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't create new instance of " + entity.getClass().getSimpleName(), e);
            }
        }
    }

    public Coordinates getRandomCoordinates(WorldMap worldMap) {
        return new Coordinates(((int) (Math.random() * worldMap.getWidth())),
                ((int) (Math.random() * worldMap.getHeight())));
    }

}
