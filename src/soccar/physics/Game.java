package soccar.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import soccar.physics.models.Updateable;
import soccar.physics.models.Wall;
import soccar.ui.models.Drawable;

import java.util.ArrayList;

/**
 * @author Marc
 */
public class Game {

    // Create a JBox2D word and set the gravity
    public static final World WORLD = new World(new Vec2(0.0f, 0.0f), true);

    // Screen dimensions
    public static final float WIDTH = 1000f;
    public static final float HEIGHT = 1000f;
    public static final int PIXELS_PER_METER = 10;

    // Refresh rate
    public static final int FPS = 60;
    public static final int MS_PER_FRAME = 1000 / FPS;

    // Car attributes
    public static final int CAR_MAX_SPEED = 10;
    public static final int CAR_MAX_REVERSE_SPEED = 10;
    public static final int CAR_POWER = 10;

    // Car wheel attributes
    public static final int WHEEL_MAX_STEER_ANGLE = 25;
    public static final int WHEEL_MAX_TURN_IN_MS = 100;

    // Instance vars
    private ArrayList<Updateable> entities;
    private ArrayList<Drawable> drawables;
    private GraphicsContext gc;

    public Game(GraphicsContext gc) {
        this.entities = new ArrayList<>();
        this.drawables = new ArrayList<>();
        this.gc = gc;

        addWalls();
    }

    public void nextFrame() {
        // Create time step
        // Set iteration count 8 for velocity and 3 for positions
        Game.WORLD.step(1.0f / Game.FPS, 6, 3);

        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        entities.forEach(Updateable::update);
        drawables.forEach(d -> d.draw(gc));
    }

    private void addCar() {
        // TODO: Implement...
    }

    private void addWalls() {

        ArrayList<float[]> walls = new ArrayList<>();
        walls.add(new float[] {1, 50, 2, 100, 0}); // Left
        walls.add(new float[] {99, 50, 2, 100, 0}); // Right
        walls.add(new float[] {50, 99, 100, 2, 0}); // Top
        walls.add(new float[] {50, 1, 100, 2, 0}); // Bottom
        walls.add(new float[] {0, 95, 10, 50, 135}); // Top-left corner
        walls.add(new float[] {100, 95, 10, 50, 225}); // Top-right corner
        walls.add(new float[] {0, 5, 10, 50, 225}); // Bottom-left corner
        walls.add(new float[] {100, 5, 10, 50, 135}); // Bottom-right corner

        for (float[] w : walls) {
            Wall wall = new Wall(w[0], w[1], w[2], w[3], w[4]);
            entities.add(wall);
            drawables.add(new soccar.ui.models.Wall(wall));
        }
    }

    public ArrayList<Updateable> getEntities() {
        return entities;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void addEntity(Updateable entity) {
        entities.add(entity);
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }
}
