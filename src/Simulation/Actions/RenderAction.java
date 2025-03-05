package Simulation.Actions;

import Simulation.Renderer;
import Simulation.WorldMap;

public class RenderAction implements Actions {
    private final Renderer renderer = new Renderer();

    @Override
    public void execute(WorldMap worldMap) {
        renderer.render(worldMap);
    }

}
