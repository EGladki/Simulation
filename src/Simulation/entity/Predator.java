package Simulation.entity;

import Simulation.BFS;
import Simulation.Coordinates;
import Simulation.WorldMap;

import java.util.List;

public class Predator extends Creature {
    private static final int PREDATOR_DEFAULT_HP = 6;
    private static final int PREDATOR_DEFAULT_SPEED = 2;
    private static final int ATTACK_DAMAGE = 2;

    public Predator() {
        super(PREDATOR_DEFAULT_HP, PREDATOR_DEFAULT_SPEED);
    }

    @Override
    public int getDefaultHp() {
        return PREDATOR_DEFAULT_HP;
    }

    @Override
    public void makeMove(WorldMap worldMap, BFS bfs) {
        for (int i = 0; i < PREDATOR_DEFAULT_SPEED; i++) {
            go(worldMap, bfs);
        }
        attack(worldMap, bfs);
        takeDamageFromHunger(worldMap);
    }

    public void go(WorldMap worldMap, BFS bfs) {
        List<Coordinates> pathToFood = bfs.findPathToFood(worldMap, this);
        if (!pathToFood.isEmpty()) {
            Coordinates nextCoordinate = pathToFood.get(BFS.NEXT_COORDINATE);
            if (worldMap.isEmpty(nextCoordinate)) {
                worldMap.put(pathToFood.get(BFS.NEXT_COORDINATE), this);
                worldMap.remove(pathToFood.get(BFS.PREV_COORDINATE));
            }
        }
    }

    private void attack(WorldMap worldMap, BFS bfs) {
        List<Coordinates> surroundingCoordinates = bfs.getSurroundingCoordinates(worldMap, worldMap.getCoordinates(this));
        for (Coordinates coordinates : surroundingCoordinates) {
            if (!worldMap.isEmpty(coordinates)) {
                Entity currentEntity = worldMap.getEntity(coordinates);
                if (isEdible(currentEntity)) {
                    ((Creature) currentEntity).takeDamageFromAttack(ATTACK_DAMAGE, worldMap);
                    this.heal();
                    return;
                }
            }
        }
    }

    @Override
    public boolean isEdible(Entity entity) {
        return entity instanceof Herbivore;
    }
}
