package soccar.ui.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import soccar.physics.Utility;

/**
 * @author Marc
 */
public class Ball implements Drawable {

    private final soccar.physics.models.Ball ball;

    public Ball(soccar.physics.models.Ball ball) {
        this.ball = ball;
    }

    @Override
    public void draw(GraphicsContext gc) {

        float radius = Utility.toPixelWidth(ball.getRadius());
        float x = Utility.toPixelX(ball.getX()) - radius;
        float y = Utility.toPixelY(ball.getY()) - radius;

        gc.setFill(Color.BLACK);
        gc.fillOval(x, y, radius * 2, radius * 2);
    }

}
