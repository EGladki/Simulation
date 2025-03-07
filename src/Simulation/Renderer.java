package Simulation;

import Simulation.entity.*;

public class Renderer {
    private static final String EMPTY_CELL_SPRITE = "🟫";
    private static final String HERBIVORE_SPRITE = "🐑";
    private static final String PREDATOR_SPRITE = "🐺";
    private static final String GRASS_SPRITE = "🍀";
    private static final String TREE_SPRITE = "🌳";
    private static final String ROCK_SPRITE = "🏔️";
    private static final String UNKNOWN_ENTITY_SPRITE = "⁉️";
    private static final String BACKGROUND_GREEN = "\033[42m";
    private static final String BACKGROUND_YELLOW = "\033[43m";
    private static final String BACKGROUND_RED = "\033[41m";
    private static final String RESET = "\u001B[0m";

    public void render(WorldMap worldMap) {
        for (int height = worldMap.getHeight() - 1; height >= 0; height--) {
            StringBuilder line = new StringBuilder();
            for (int width = 0; width < worldMap.getWidth(); width++) {
                Coordinates coordinates = new Coordinates(width, height);
                if (worldMap.isEmpty(coordinates)) {
                    line.append(EMPTY_CELL_SPRITE);
                } else {
                    Entity entity = worldMap.getEntity(coordinates);
                    String background = getBackground(entity);
                    String sprite = getSprite(entity);
                    line.append(background).append(sprite).append(RESET);
                }
            }
            System.out.println(line);
        }
        System.out.println();
    }

    private String getSprite(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Herbivore" -> HERBIVORE_SPRITE;
            case "Predator" -> PREDATOR_SPRITE;
            case "Grass" -> GRASS_SPRITE;
            case "Tree" -> TREE_SPRITE;
            case "Rock" -> ROCK_SPRITE;
            default -> UNKNOWN_ENTITY_SPRITE;
        };
    }

    private String getBackground(Entity entity) {
        if (entity instanceof Creature creature) {
            int hp = creature.getHp();
            int defaultHp = creature.getDefaultHp();
            if (hp == defaultHp) {
                return BACKGROUND_GREEN;
            } else if (hp < defaultHp && hp > defaultHp * 0.4) {
                return BACKGROUND_YELLOW;
            } else {
                return BACKGROUND_RED;
            }
        }
        return RESET;
    }

}
