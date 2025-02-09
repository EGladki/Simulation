package Simulation.entity;

import Simulation.Actions.BFS;
import Simulation.Coordinates;
import Simulation.WorldMap;

import java.util.List;

public abstract class Creature extends Entity {
    private int speed;
    private int hp;

    public void makeMove(WorldMap worldMap) {
        BFS bfs = new BFS();
        List<Coordinates> pathToFood = bfs.findPathToFood(worldMap, this);
        if (!pathToFood.isEmpty()) {
            worldMap.putEntity(pathToFood.get(1), this);
            worldMap.deleteEntity(pathToFood.get(0));
        }
    }

    public void eat() {
    }

    public abstract boolean isFoodFor(Entity entity);

}
