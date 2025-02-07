import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import entity.Predator;

public class Simulation {
    private WorldMapFactory worldMapFactory;
    WorldMap worldMap;

    public void run() {
        worldMap = WorldMapFactory.createCustomWorldMap(7,7);
        populateWithCreatures(5, 2, 10);

        Renderer renderer = new Renderer();
        renderer.render(worldMap);

    }
    public void populateWithCreatures(int HerbivoresQuantity, int PredatorsQuantity, int GrassQuantity) {
        fillWith(new Herbivore(), HerbivoresQuantity);
        fillWith(new Predator(), PredatorsQuantity);
        fillWith(new Grass(), GrassQuantity);
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
