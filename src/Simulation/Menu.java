package Simulation;

import java.util.Scanner;

import static Simulation.WorldMapFactory.DEFAULT_HEIGHT;

public class Menu {
    private static final int MAX_SIZE = 30;
    static final int EXIT = 2;
    static final int PAUSE = 1;
    static final int DEFAULT_SETTINGS = 1;
    static final int CUSTOM_SETTINGS = 2;
    static final int ENDLESS_SIMULATION = 3;
    static final int CUSTOM_ENDLESS_SIMULATION = 4;
    private final Scanner scanner = new Scanner(System.in);

    public int start() {
        welcomeMessage();
        inputModeMessage();
        return getModeInput();
    }

    public int getModeInput() {
        String input = scanner.nextLine();
        while (!isInteger(input) || !isValidMode(input)) {
            incorrectInputMessage();
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
            case CUSTOM_ENDLESS_SIMULATION -> {
                return CUSTOM_ENDLESS_SIMULATION;
            }
        }
        return 0;
    }

    public int[] getMapSizeInput() {
        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                incorrectInputMessage();
                inputMapSizeMessage();
                continue;
            }
            if (!isInteger(parts[0]) || !isInteger(parts[1])) {
                incorrectInputMessage();
                inputMapSizeMessage();
                continue;
            }
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);
            if (!isValidSize(width) || !isValidSize(height)) {
                incorrectInputMessage();
                inputMapSizeMessage();
                continue;
            }
            return new int[]{width, height};
        }
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void welcomeMessage() {
        System.out.println("""
                Welcome to Simulation!
                Predators hunting for herbivores, herbivores searching for grass.
                Predators moves 2 cells at turn, herbivores moves 1 cell.
                Herbivore eats grass by stepping on cell containing it. When herbivore eat grass - it heals.
                Predators can attack herbivores and deal them damage. After successful attack predator heals.
                At the end of turn predators take damage from hunger. Creatures are highlighted based on their life points.
                """);
    }

    public void inputModeMessage() {
        System.out.printf("Please enter number and choose simulation mode:\n" +
                "%s - start simulation with default settings (10x10 map, ends when one type of creature remains)\n" +
                "%s - start simulation with custom map (custom map size, ends when one type of creature remains)\n" +
                "%s - start endless simulation (10x10 map, new creatures are born in process, simulation never ends\n" +
                "%s - start endless simulation with custom map (custom map size, new creatures are born in process, simulation never ends\n", DEFAULT_SETTINGS, CUSTOM_SETTINGS, ENDLESS_SIMULATION, CUSTOM_ENDLESS_SIMULATION);
    }

    public void inputMapSizeMessage() {
        System.out.printf("Enter two numbers from %d to %d separated by a space. 1st number - width, 2nd number - height\n", DEFAULT_HEIGHT, MAX_SIZE);
    }

    public void exitMessage() {
        System.out.printf("%s - exit from simulation\n", EXIT);
    }

    public void pauseResumeMessage() {
        System.out.printf("%s - pause/resume simulation\n", PAUSE);
    }

    public void incorrectInputMessage() {
        System.out.println("Incorrect input");
    }

    private boolean isValidMode(String s) {
        int input = Integer.parseInt(s);
        return input >= DEFAULT_SETTINGS && input <= CUSTOM_ENDLESS_SIMULATION;
    }

    private boolean isValidSize(int size) {
        return size >= DEFAULT_HEIGHT && size <= MAX_SIZE;
    }
}
