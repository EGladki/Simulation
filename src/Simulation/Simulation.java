package Simulation;

import Simulation.Actions.*;
import Simulation.entity.Entity;
import Simulation.entity.Herbivore;
import Simulation.entity.Predator;

import java.util.Scanner;

import static Simulation.Menu.*;
import static Simulation.WorldMapFactory.DEFAULT_HEIGHT;
import static Simulation.WorldMapFactory.DEFAULT_WIDTH;

public class Simulation {

    private final Scanner scanner = new Scanner(System.in);
    private WorldMap worldMap;
    private final WorldMapFactory worldMapFactory = new WorldMapFactory();
    private final Menu menu = new Menu();
    private final Actions moveAction = new MoveAction();
    private final Actions renderAction = new RenderAction();
    private final Actions bornAction = new BornAction();
    private volatile boolean running = true;
    private volatile boolean pause = false;

    public void start() {
        int mode = menu.start();
        switch (mode) {
            case DEFAULT_SETTINGS -> startDefaultSimulation();
            case CUSTOM_SETTINGS -> startCustomSimulation();
            case ENDLESS_SIMULATION -> startEndlessSimulation();
            case CUSTOM_ENDLESS_SIMULATION -> startCustomEndlessSimulation();
        }
    }

    private void runSimulation(WorldMap worldMap, boolean isEndless) {
        this.worldMap = worldMap;
        this.worldMap = worldMapFactory.createWorldMap(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        renderAction.execute(worldMap);
        Thread inputThread = new Thread(this::listenForUserInput);
        inputThread.start();
        while (running && (isEndless || containsHerbivoresAndPredators(worldMap))) {
            synchronized (this) {
                while (pause) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Exception from pause");
                    }
                }
            }

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException("Exception from sleep");
            }

            moveAction.execute(worldMap);
            renderAction.execute(worldMap);
            menu.pauseResumeMessage();
            menu.exitMessage();
            if (isEndless) {
                bornAction.execute(worldMap);
            }
        }
    }

    private void startDefaultSimulation() {
        this.worldMap = worldMapFactory.createWorldMap(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        runSimulation(worldMap, false);
    }

    private void startCustomSimulation() {
        menu.inputMapSizeMessage();
        int[] size = menu.getMapSizeInput();
        this.worldMap = worldMapFactory.createWorldMap(size[0], size[1]);
        runSimulation(worldMap, false);
    }

    private void startEndlessSimulation() {
        this.worldMap = worldMapFactory.createWorldMap(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        runSimulation(worldMap, true);
    }

    private void startCustomEndlessSimulation() {
        menu.inputMapSizeMessage();
        int[] size = menu.getMapSizeInput();
        this.worldMap = worldMapFactory.createWorldMap(size[0], size[1]);
        runSimulation(worldMap, true);
    }

    private void listenForUserInput() {
        while (running) {
            String input = scanner.nextLine();
            while (!menu.isInteger(input)) {
                menu.incorrectInputMessage();
                input = scanner.nextLine();
            }
            int command = Integer.parseInt(input);
            if (command == Menu.EXIT) {
                running = false;
                break;
            } else if (command == PAUSE) {
                synchronized (this) {
                    pause = !pause;
                    if (!pause) {
                        notifyAll();
                    }
                }
            } else {
                menu.incorrectInputMessage();
            }
        }
        scanner.close();
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