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
        exitMessage();
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

    public void welcomeMessage() {
        System.out.println("""
                Welcome to Simulation!
                Predators hunting for herbivores, herbivores searching for grass.
                Predators moves 2 cells at turn, herbivores moves 1 cell.
                Herbivore eats grass by stepping on cell containing it. When herbivore eat grass - it heals.
                Predators can attack herbivores and deal them damage. After successful attack predator heals.
                At the end of turn predators take damage from hunger.
                Creatures are highlighted based on their life points.
                """);
    }

    public void inputModeMessage() {
        System.out.println("""
                Please enter number and choose simulation mode:
                1 - start simulation with default settings (10x10 map, ends when one type of creature remains)
                2 - start simulation with custom map (custom map size, ends when one type of creature remains)
                3 - start endless simulation (10x10 map, new creatures are born in process, simulation never ends)""");
    }

    public void inputMapSizeMessage() {
        System.out.println("Enter two numbers from 10 to 30 separated by a space. 1st number - width, 2nd number - height");
    }

    public void exitMessage() {
        System.out.println("1 - exit from simulation");
    }

    public void incorrectInputMessage() {
        System.out.println("Incorrect input");
    }

}
