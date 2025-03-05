package Simulation.entity;

import Simulation.BFS;
import Simulation.Coordinates;
import Simulation.WorldMap;

import java.util.List;

public class Herbivore extends Creature {
    private static final int HERBIVORE_DEFAULT_HP = 6;
    private static final int HERBIVORE_DEFAULT_SPEED = 1;

    public Herbivore() {
        super(HERBIVORE_DEFAULT_HP, HERBIVORE_DEFAULT_SPEED);
    }

    @Override
    public int getDefaultHp() {
        return HERBIVORE_DEFAULT_HP;
    }

    @Override
    public void makeMove(WorldMap worldMap, BFS bfs) {
        for (int i = 0; i < HERBIVORE_DEFAULT_SPEED; i++) {
            go(worldMap, bfs);
        }
    }

    public void go(WorldMap worldMap, BFS bfs) {
        List<Coordinates> pathToFood = bfs.findPathToFood(worldMap, this);
        if (!pathToFood.isEmpty()) {
            Coordinates nextCoordinate = pathToFood.get(BFS.NEXT_COORDINATE);
            if (worldMap.getEntity(nextCoordinate) instanceof Grass) {
                this.heal();
            }
            worldMap.put(pathToFood.get(BFS.NEXT_COORDINATE), this);
            worldMap.remove(pathToFood.get(BFS.PREV_COORDINATE));
        }
    }

    @Override
    public boolean isEdible(Entity entity) {
        return entity instanceof Grass;
    }
}
