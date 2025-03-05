package Simulation;

import Simulation.Actions.*;
import Simulation.entity.Entity;
import Simulation.entity.Herbivore;
import Simulation.entity.Predator;

import java.util.Scanner;

import static Simulation.WorldMapFactory.DEFAULT_HEIGHT;
import static Simulation.WorldMapFactory.DEFAULT_WIDTH;

public class Simulation {
    private static final int DEFAULT_SETTINGS = 1;
    private static final int CUSTOM_SETTINGS = 2;
    private static final int ENDLESS_SIMULATION = 3;
    private static final int MAX_SIZE = 25;
    private static final int EXIT = 1;
    private final Scanner scanner = new Scanner(System.in);
    private WorldMap worldMap;
    private final WorldMapFactory worldMapFactory = new WorldMapFactory();
    private final Renderer renderer = new Renderer();
    private final Actions moveAction = new MoveAction();
    private final Actions renderAction = new RenderAction();
    private final Actions bornAction = new BornAction();
    private volatile boolean running = true;

    public void start() {
        renderer.welcomeMessage();
        renderer.inputModeMessage();
        int mode = getModeInput();
        switch (mode) {
            case DEFAULT_SETTINGS -> startDefaultSimulation();
            case CUSTOM_SETTINGS -> startCustomSimulation();
            case ENDLESS_SIMULATION -> startEndlessSimulation();
        }
    }

    private void startDefaultSimulation() {
        this.worldMap = worldMapFactory.createWorldMap(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        renderAction.execute(worldMap);
        Thread inputThread = new Thread(this::listenForUserInput);
        inputThread.start();
        while (running && containsHerbivoresAndPredators(worldMap)) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            moveAction.execute(worldMap);
            renderAction.execute(worldMap);
        }
    }

    private void startCustomSimulation() {
        renderer.inputMapSizeMessage();
        int[] size = getMapSizeInput();
        this.worldMap = worldMapFactory.createWorldMap(size[0], size[1]);
        renderAction.execute(worldMap);
        Thread inputThread = new Thread(this::listenForUserInput);
        inputThread.start();
        while (running && containsHerbivoresAndPredators(worldMap)) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            moveAction.execute(worldMap);
            renderAction.execute(worldMap);
        }
    }

    private void startEndlessSimulation() {
        this.worldMap = worldMapFactory.createWorldMap(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        renderAction.execute(worldMap);
        Thread inputThread = new Thread(this::listenForUserInput);
        inputThread.start();
        while (running) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            moveAction.execute(worldMap);
            renderAction.execute(worldMap);
            bornAction.execute(worldMap);
        }
    }

    private void listenForUserInput() {
        while (running) {
            String input = scanner.nextLine();
            while (!isInteger(input)) {
                renderer.incorrectInputMessage();
                input = scanner.nextLine();
            }
            if (Integer.parseInt(input) == EXIT) {
                running = false;
                break;
            } else {
                renderer.incorrectInputMessage();
            }
        }
        scanner.close();
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int getModeInput() {
        String input = scanner.nextLine();
        while (!isInteger(input) || !isValidMode(input)) {
            renderer.incorrectInputMessage();
            input = scanner.nextLine();
        }
        switch (Integer.parseInt(input)) {
            case DEFAULT_SETTINGS -> {
                return DEFAULT_SETTINGS;
            }
            case CUSTOM_SETTINGS -> {
                return CUSTOM_SETTINGS;
            }
            case ENDLESS_SIMULATION -> {
                return ENDLESS_SIMULATION;
            }
        }
        return 0;
    }

    private int[] getMapSizeInput() {
        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (parts.length != 2){
                renderer.incorrectInputMessage();
                renderer.inputMapSizeMessage();
                continue;
            }
            if (!isInteger(parts[0]) || !isInteger(parts[1])){
                renderer.incorrectInputMessage();
                renderer.inputMapSizeMessage();
                continue;
            }

            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);

            if (!isValidSize(width) || !isValidSize(height)) {
                renderer.incorrectInputMessage();
                renderer.inputMapSizeMessage();
                continue;
            }
            return new int[]{width, height};
        }
    }

    private boolean isValidMode(String s) {
        int input = Integer.parseInt(s);
        return input >= DEFAULT_SETTINGS && input <= ENDLESS_SIMULATION;
    }

    private boolean isValidSize(int size) {
        return size >= DEFAULT_HEIGHT && size <= MAX_SIZE;
    }

    private boolean containsHerbivoresAndPredators(WorldMap worldMap) {
        boolean hasHerbivore = false;
        boolean hasPredator = false;
        for (Entity entity : worldMap.getAll()) {
            if (entity instanceof Herbivore) {
                hasHerbivore = true;
            } else if (entity instanceof Predator) {
                hasPredator = true;
            }
            if (hasHerbivore && hasPredator) {
                return true;
            }
        }
        return false;
    }
}