package Simulation.Actions;

import Simulation.BFS;
import Simulation.WorldMap;
import Simulation.entity.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MoveAction implements Actions {
    private final BFS bfs = new BFS();

    @Override
    public void execute(WorldMap worldMap) {
        Queue<Creature> creatures = getAllCreatures(worldMap);
        moveCreature(creatures, worldMap);
    }

    public Queue<Creature> getAllCreatures(WorldMap worldMap) {
        List<Entity> entities = worldMap.getAll();
        Queue<Creature> creatures = new LinkedList<>();
        for (Entity entity : entities) {
            if (entity instanceof Creature creature) {
                creatures.add(creature);
            }
        }
        return creatures;
    }

    public void moveCreature(Queue<Creature> creatures, WorldMap worldMap) {
        while (hasCreature(creatures)) {
            Creature creature = creatures.poll();
            if (!worldMap.contains(creature)) {
                continue;
            }
            creature.makeMove(worldMap, bfs);
        }
    }

    public boolean hasCreature(Queue<Creature> creatures) {
        return !creatures.isEmpty();
    }

}
