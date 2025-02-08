package Simulation.Actions;

import Simulation.Coordinates;
import Simulation.WorldMap;
import Simulation.entity.*;


public class InitActions {
    private final WorldMap worldMap;

    public InitActions(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void populateWithCreatures() {
        fillWith(new Herbivore(), 1);
        fillWith(new Predator(), 1);
        fillWith(new Grass(), 0);
        fillWith(new Tree(), 0);
        fillWith(new Rock(), 0);
    }

    private void fillWith(Entity entity, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Coordinates coordinates;
            do {
                coordinates = getRandomCoordinates();
            } while (!worldMap.isCoordinateValid(coordinates) || !worldMap.isCellEmpty(coordinates));
            worldMap.putEntity(coordinates, entity);
        }
    }

    public Coordinates getRandomCoordinates() {
        return new Coordinates(((int) (Math.random() * worldMap.getWidth()) + 1),
                ((int) (Math.random() * worldMap.getHeight()) + 1));
    }
}