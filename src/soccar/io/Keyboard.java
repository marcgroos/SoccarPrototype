package soccar.io;

import javafx.scene.input.KeyCode;
import soccar.physics.enumerations.SteerAction;
import soccar.physics.enumerations.ThrottleAction;
import soccar.physics.models.Car;

/**
 * Created by Marc on 3-10-2016.
 */
public class Keyboard {

    public static void handleKeyPressed(Car car, KeyCode keyCode) {
        switch (keyCode) {
            case W:
                car.setThrottleAction(ThrottleAction.ACCELERATE);
                break;
            case S:
                car.setThrottleAction(ThrottleAction.REVERSE);
                break;
            case A:
                car.setSteerAction(SteerAction.STEER_LEFT);
                break;
            case D:
                car.setSteerAction(SteerAction.STEER_RIGHT);
                break;
            case SPACE:
                car.setHandbrake(true);
        }
    }

    public static void handleKeyReleased(Car car, KeyCode keyCode) {
        switch (keyCode) {
            case W:
                car.setThrottleAction(ThrottleAction.IDLE);
                break;
            case S:
                car.setThrottleAction(ThrottleAction.IDLE);
                break;
            case A:
                car.setSteerAction(SteerAction.NONE);
                break;
            case D:
                car.setSteerAction(SteerAction.NONE);
                break;
            case SPACE:
                car.setHandbrake(false);
        }
    }

}
