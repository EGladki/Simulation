import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import entity.Predator;

public class WorldMapFactory {

    public static WorldMap createDefaultWorldMap() {
        return new WorldMap();
    }

    public static WorldMap createCustomWorldMap(int width, int height) {
        return new WorldMap(width, height);
    }





}
