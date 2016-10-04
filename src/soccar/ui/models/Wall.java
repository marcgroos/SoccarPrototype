package soccar.ui.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import soccar.physics.Utility;

/**
 * @author Marc
 */
public class Wall implements Drawable {

    private final soccar.physics.models.Wall wall;

    public Wall(soccar.physics.models.Wall wall) {
        this.wall = wall;
    }

    @Override
    public void draw(GraphicsContext gc) {


        float width = Utility.toPixelWidth(wall.getWidth());
        float height = Utility.toPixelHeight(wall.getHeight());

        float x = Utility.toPixelX(wall.getX());
        float y = Utility.toPixelY(wall.getY());


        gc.save(); // Save the canvas so we can draw a rotated rectangle
        gc.translate(x, y); // Set the origin point of the rotation
        gc.rotate(-wall.getAngle()); // Set the angle of the rotation
        gc.setFill(Color.DARKCYAN);
        gc.fillRect(-width / 2, -height / 2, width, height); // Draw the rectangle from the top left
        gc.restore(); // Restore canvas to display a rotated image
    }

}
