package Simulation.Actions;
import Simulation.Coordinates;
import Simulation.WorldMap;
import Simulation.entity.Eatable;
import Simulation.entity.Entity;
import java.util.*;


public class BFS {
    private final int[][] coordinatesShift = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};


    public List<Coordinates> findPathToFood(WorldMap worldMap, Entity entity) {
        Coordinates from = worldMap.getCoordinates(entity);
        Queue<Coordinates> coordinatesToCheck = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();

        visited.add(from);
        coordinatesToCheck.add(from);
        cameFrom.put(from, null);

        while (!coordinatesToCheck.isEmpty()) {
            Coordinates current = coordinatesToCheck.poll();
            if (worldMap.getEntity(current) instanceof Eatable) {
                return buildPath(cameFrom, current);
            }
            List<Coordinates> surroundingCoordinates = findSurroundingCoordinates(worldMap, current);
            for (Coordinates neighbor : surroundingCoordinates) {
                if (!visited.contains(neighbor) && worldMap.isCoordinateValid(neighbor)) {
                    visited.add(neighbor);
                    coordinatesToCheck.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    public List<Coordinates> buildPath(Map<Coordinates, Coordinates> cameFrom, Coordinates to) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = to;
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public List<Coordinates> findSurroundingCoordinates(WorldMap worldMap, Coordinates coordinates) {
        List<Coordinates> surroundingCoordinates = new ArrayList<>();

        for (int[] shift : coordinatesShift) {
            int newWidth = coordinates.getWidth() + shift[0];
            int newHeight = coordinates.getHeight() + shift[1];

            Coordinates newCoordinate = new Coordinates(newWidth, newHeight);
            if (worldMap.isCoordinateValid(newCoordinate)) {
                surroundingCoordinates.add(new Coordinates(newWidth, newHeight));
            }
        }
        return surroundingCoordinates;
    }
}
