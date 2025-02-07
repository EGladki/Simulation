import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import entity.Predator;

public class Renderer {
    private static final String EMPTY_CELL_SPRITE = "🟫";
    private static final String HERBIVORE_SPRITE = "🐇";
    private static final String PREDATOR_SPRITE = "🦊";
    private static final String GRASS_SPRITE = "🍀";
    private static final String UNKNOWN_SPRITE = "⁉️";

    public void render(WorldMap worldMap) {
        for (int height = worldMap.getHeight(); height > 0; height--) {
            String line = "";
            for (int width = 1; width <= worldMap.getWidth(); width++) {
                Coordinates coordinates = new Coordinates(width, height);
                if (worldMap.isCellEmpty(coordinates)) {
                    line += EMPTY_CELL_SPRITE;
                } else {
                    line += getSprite(worldMap.getEntity(coordinates));
                }
            }
            System.out.println(line);
        }
    }

    private String getSprite(Entity entity) {
        if (entity instanceof Herbivore) {
            return HERBIVORE_SPRITE;
        }
        if (entity instanceof Predator) {
            return PREDATOR_SPRITE;
        }
        if (entity instanceof Grass) {
            return GRASS_SPRITE;
        }
        return UNKNOWN_SPRITE;
    }




}
