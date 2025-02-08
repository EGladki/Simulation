package Simulation;

import Simulation.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final int width;
    private final int height;
    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public WorldMap() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void putEntity(Coordinates coordinates, Entity entity) {
        if (isCoordinateValid(coordinates)) {
            entities.put(coordinates, entity);
        } else
            throw new IllegalArgumentException("Invalid coordinates");
    }

    public void deleteEntity(Coordinates coordinates) {
        if (isCoordinateValid(coordinates)) {
            entities.remove(coordinates);
        } else
            throw new IllegalArgumentException("Invalid coordinates");
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        if (isCoordinateValid(coordinates)) {
            return !entities.containsKey(coordinates);
        }
        throw new IllegalArgumentException("Invalid coordinates");
    }

    public boolean isEntityOnMap(Entity entity) {
        return entities.containsValue(entity);
    }

    public Coordinates getCoordinates(Entity entity) {
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
            if (entry.getValue().equals(entity)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Entity not found on the map");
    }

    public Entity getEntity(Coordinates coordinates) {
        if (isCoordinateValid(coordinates)) {
            return entities.get(coordinates);
        }
        throw new IllegalArgumentException("Invalid coordinates");
    }

    public boolean isCoordinateValid(Coordinates coordinates) {
        return coordinates.getWidth() > 0 && coordinates.getWidth() <= width &&
                coordinates.getHeight() > 0 && coordinates.getHeight() <= height;
    }


}

