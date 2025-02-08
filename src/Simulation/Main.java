package Simulation;

import Simulation.Actions.BFS;
import Simulation.entity.Entity;
import Simulation.entity.Herbivore;
import Simulation.entity.Predator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Simulation simulation = new Simulation();
//        simulation.run();


        WorldMap worldMap = new WorldMap(10,10);
        BFS bfs = new BFS();
        Herbivore herbivore = new Herbivore();
        Predator predator = new Predator();
        worldMap.putEntity(new Coordinates(1,9), herbivore);
        worldMap.putEntity(new Coordinates(5,5), predator);
        Renderer renderer = new Renderer();

        renderer.render(worldMap);

        List<Coordinates> pathToFood = bfs.findPathToFood(worldMap, predator);


        int x = 123;

    }
}
