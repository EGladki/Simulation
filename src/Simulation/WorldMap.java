package Simulation;

import Simulation.entity.Entity;

import java.util.*;

public class WorldMap {
    private final int width;
    private final int height;

    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void put(Coordinates coordinates, Entity entity) {
        if (isValid(coordinates)) {
            entities.put(coordinates, entity);
        } else
            throw new IllegalArgumentException("Invalid coordinates");
    }

    public void remove(Coordinates coordinates) {
        if (isValid(coordinates)) {
            entities.remove(coordinates);
        } else
            throw new IllegalArgumentException("Invalid coordinates");
    }

    public boolean isEmpty(Coordinates coordinates) {
        if (isValid(coordinates)) {
            return !entities.containsKey(coordinates);
        }
        throw new IllegalArgumentException("Invalid coordinates");
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
        if (isValid(coordinates)) {
            return entities.get(coordinates);
        }
        throw new IllegalArgumentException("Invalid coordinates");
    }

    public boolean isValid(Coordinates coordinates) {
        return coordinates.width() >= 0 && coordinates.width() < width &&
                coordinates.height() >= 0 && coordinates.height() < height;
    }

    public List<Entity> getAll() {
        return new ArrayList<>(entities.values());
    }

    public boolean contains(Entity entity) {
        return entities.containsValue(entity);
    }

}

