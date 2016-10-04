package soccar.ui.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import soccar.physics.Utility;
import soccar.physics.models.Wheel;

/**
 * @author Marc
 */
public class Car implements Drawable {

    private final soccar.physics.models.Car car;
    private final soccar.physics.models.Wheel frontLWheel;
    private final soccar.physics.models.Wheel frontRWheel;
    private final soccar.physics.models.Wheel backLWheel;
    private final soccar.physics.models.Wheel backRWheel;

    public Car(soccar.physics.models.Car car) {
        this.car = car;
        this.frontLWheel = car.getFrontLWheel();
        this.frontRWheel = car.getFrontRWheel();
        this.backLWheel = car.getBackLWheel();
        this.backRWheel = car.getBackRWheel();
    }

    @Override
    public void draw(GraphicsContext gc) {

        // Draw wheels
        drawWheel(frontLWheel, gc);
        drawWheel(frontRWheel, gc);
        drawWheel(backLWheel, gc);
        drawWheel(backRWheel, gc);

        // Draw body
        drawBody(gc);

    }

    private void drawBody(GraphicsContext gc) {

        float width = Utility.toPixelWidth(car.getWidth());
        float height = Utility.toPixelHeight(car.getHeight());

        float x = Utility.toPixelX(car.getX());
        float y = Utility.toPixelY(car.getY());

        gc.save(); // Save the canvas so we can draw a rotated rectangle

        gc.translate(x, y); // Set the origin point of the rotation
        gc.rotate(-car.getAngle()); // Set the angle of the rotation
        gc.setFill(Color.RED);
        gc.fillRect(-width / 2, -height / 2, width, height); // Draw the rectangle from the top left

        gc.restore(); // Restore canvas to display a rotated image
    }

    private void drawWheel(Wheel wheel, GraphicsContext gc) {

        float width = Utility.toPixelWidth(wheel.getWidth());
        float height = Utility.toPixelHeight(wheel.getHeight());

        float x = Utility.toPixelX(wheel.getCurrentX());
        float y = Utility.toPixelY(wheel.getCurrentY());

        gc.save();

        gc.translate(x, y); // Set the origin point of the rotation
        gc.rotate(-wheel.getAngle()); // Set the angle of the rotation
        gc.setFill(Color.GREY);
        gc.fillRect(-width / 2, -height / 2, width, height); // Draw the rectangle from the top left

        gc.restore();
    }

}
