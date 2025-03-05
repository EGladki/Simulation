package Simulation;

import Simulation.entity.Creature;
import Simulation.entity.Entity;

import java.util.*;

public class BFS {
    private static final int[][] COORDINATE_SHIFTS = {{-1, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
    private static final int WIDTH_SHIFT = 0;
    private static final int HEIGHT_SHIFT = 1;
    public static final int PREV_COORDINATE = 0;
    public static final int NEXT_COORDINATE = 1;

    public List<Coordinates> findPathToFood(WorldMap worldMap, Entity entity) {
        Coordinates from = worldMap.getCoordinates(entity);
        Queue<Coordinates> coordinatesToCheck = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

        coordinatesToCheck.add(from);
        visited.add(from);
        cameFrom.put(from, null);

        while (!coordinatesToCheck.isEmpty()) {
            Coordinates current = coordinatesToCheck.poll();
            if (isFoodOnCoordinates(worldMap, entity, current)) {
                return buildPath(cameFrom, current);
            }
            List<Coordinates> surroundingCoordinates = getSurroundingCoordinates(worldMap, current);
            for (Coordinates neighbor : surroundingCoordinates) {
                if (!visited.contains(neighbor)) {
                    if (!isCoordinateBlockedByUneatable(worldMap, entity, neighbor)) {
                        visited.add(neighbor);
                        coordinatesToCheck.add(neighbor);
                        cameFrom.put(neighbor, current);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Coordinates> buildPath(Map<Coordinates, Coordinates> cameFrom, Coordinates to) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = to;
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public List<Coordinates> getSurroundingCoordinates(WorldMap worldMap, Coordinates coordinates) {
        List<Coordinates> surroundingCoordinates = new ArrayList<>();
        for (int[] shift : COORDINATE_SHIFTS) {
            int newWidth = coordinates.width() + shift[WIDTH_SHIFT];
            int newHeight = coordinates.height() + shift[HEIGHT_SHIFT];
            Coordinates newCoordinate = new Coordinates(newWidth, newHeight);
            if (worldMap.isValid(newCoordinate)) {
                surroundingCoordinates.add(new Coordinates(newWidth, newHeight));
            }
        }
        return surroundingCoordinates;
    }

    private boolean isCoordinateBlockedByUneatable(WorldMap worldMap, Entity entity, Coordinates neighbor) {
        return !(worldMap.isEmpty(neighbor) || isFoodOnCoordinates(worldMap, entity, neighbor));
    }

    private boolean isFoodOnCoordinates(WorldMap worldMap, Entity entity, Coordinates current) {
        Entity targetEntity = worldMap.getEntity(current);
        return ((Creature) entity).isEdible(targetEntity);
    }


}
