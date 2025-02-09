package Simulation.entity;

public class Predator extends Creature {

    @Override
    public boolean isFoodFor(Entity entity) {
        return entity instanceof Herbivore;
    }
}
