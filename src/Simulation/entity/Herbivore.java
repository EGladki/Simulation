package Simulation.entity;

public class Herbivore extends Creature {



    @Override
    public boolean isFoodFor(Entity entity) {
        return entity instanceof Grass;
    }
}
