package Simulation;

import Simulation.Actions.BFS;
import Simulation.entity.*;


public class Renderer {
    private static final String EMPTY_CELL_SPRITE = "🟫";
    private static final String HERBIVORE_SPRITE = "🐑";
    private static final String PREDATOR_SPRITE = "🐺";
    private static final String GRASS_SPRITE = "🍀";
    private static final String TREE_SPRITE = "🌳";
    private static final String ROCK_SPRITE = "🏔️";
    private static final String UNKNOWN_SPRITE = "⁉️";

    public static final String RESET = "\u001B[0m";
    public static final String BRIGHT_BACKGROUND_YELLOW = "\u001B[43;1m";



    public void render(WorldMap worldMap) {


        for (int height = worldMap.getHeight(); height > 0; height--) {
            String line = "";
            for (int width = 1; width <= worldMap.getWidth(); width++) {
                Coordinates coordinates = new Coordinates(width, height);
                if (worldMap.isCellEmpty(coordinates)) {
                    line += EMPTY_CELL_SPRITE ;
                } else {
                    line += getSprite(worldMap.getEntity(coordinates));
                }
            }
            System.out.println(line);
        }
    }

    private String getSprite(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Herbivore" -> HERBIVORE_SPRITE;
            case "Predator" -> PREDATOR_SPRITE;
            case "Grass" -> GRASS_SPRITE;
            case "Tree" -> TREE_SPRITE;
            case "Rock" -> ROCK_SPRITE;
            default -> UNKNOWN_SPRITE;
        };

    }
}
