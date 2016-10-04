package soccar.ui.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import soccar.physics.Utility;

/**
 * @author Marc
 */
public class Ball implements Drawable {

    private final soccar.physics.models.Ball ball;
    private final Image ballImage = new Image("puck.png");

    public Ball(soccar.physics.models.Ball ball) {
        this.ball = ball;
    }

    @Override
    public void draw(GraphicsContext gc) {

        float radius = Utility.toPixelWidth(ball.getRadius());
        float x = Utility.toPixelX(ball.getX());
        float y = Utility.toPixelY(ball.getY());


        gc.save(); // Save the canvas so we can draw a rotated rectangle
        gc.translate(x, y); // Set the origin point of the rotation
        gc.rotate(-ball.getAngle()); // Set the angle of the rotation
        gc.drawImage(ballImage, -radius, -radius, radius * 2, radius * 2); // Draw the rectangle from the top left
        gc.restore(); // Restore canvas to display a rotated image

//        gc.setFill(Color.BLACK);
//        gc.fillOval(x, y, radius * 2, radius * 2);

    }

}
