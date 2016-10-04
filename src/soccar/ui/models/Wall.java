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
        float x = Utility.toPixelX(wall.getX()) - width / 2;
        float y = Utility.toPixelY(wall.getY()) - height / 2;

        gc.setFill(Color.DARKCYAN);
        gc.fillRect(x, y, width, height); // Draw the rectangle from the top left
    }

}
