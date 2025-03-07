package Simulation.entity;

import Simulation.BFS;
import Simulation.Coordinates;
import Simulation.WorldMap;

public abstract class Creature extends Entity {
    private int hp;

    public Creature(int hp, int speed) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public abstract int getDefaultHp();

    public abstract void makeMove(WorldMap worldMap, BFS bfs);

    public abstract boolean isEdible(Entity entity);

    protected void takeDamageFromAttack(int damage, WorldMap worldMap) {
        this.hp -= damage;
        if (this.hp <= 0) {
            die(worldMap);
        }
    }

    protected void takeDamageFromHunger(WorldMap worldMap) {
        this.hp -= 1;
        if (this.hp <= 0) {
            die(worldMap);
        }
    }

    private void die(WorldMap worldMap) {
        Coordinates coordinates = worldMap.getCoordinates(this);
        worldMap.remove(coordinates);
    }

    protected void heal() {
        if (this.getHp() < getDefaultHp()) {
            this.hp += 1;
        }
    }

}
