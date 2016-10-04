package soccar.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import soccar.io.Keyboard;
import soccar.physics.enumerations.ThrottleAction;
import soccar.physics.enumerations.SteerAction;
import soccar.physics.models.Ball;
import soccar.physics.models.Car;
import soccar.physics.models.Wall;
import soccar.physics.Game;

/**
 * @author Marc
 */
public class Soccar extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Stage settings
        primaryStage.setTitle("Soccar - Physics Prototype");
        primaryStage.setResizable(false);

        // Scene settings
        Group root = new Group();
        Scene scene = new Scene(root, Game.WIDTH, Game.HEIGHT);
        Canvas canvas = new Canvas(Game.WIDTH, Game.HEIGHT);

        // Get GC and start game
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Game game = new Game(gc);

        // Create ball
        Ball ball = new Ball(50, 80, 4);
        game.addEntity(ball);
        game.addDrawable(new soccar.ui.models.Ball(ball));

        // Create car
        float carWidth = 6.0f;
        float carHeight = carWidth * 47 / 20;
        float tireWidth = carWidth / 5;
        float tireHeight = tireWidth * 2;

        Car car = new Car(50, 50, carWidth, carHeight, 0, tireWidth, tireHeight);
        game.addEntity(car);
        game.addDrawable(new soccar.ui.models.Car(car));

        // Create timeline
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0f / Game.FPS); // Set duration per frame

        // Handle input
        scene.setOnKeyPressed((KeyEvent key) -> Keyboard.handleKeyPressed(car, key.getCode()));
        scene.setOnKeyReleased((KeyEvent key) -> Keyboard.handleKeyReleased(car, key.getCode()));

        // Create an ActionEvent, on trigger it executes a time step and moves the entities to new position 
        EventHandler<ActionEvent> actionEvent = (ActionEvent t) -> game.nextFrame();

        // Set ActionEvent and duration to the KeyFrame. The ActionEvent is trigged when KeyFrame execution is over.
        KeyFrame frame = new KeyFrame(duration, actionEvent);
        timeline.getKeyFrames().add(frame);
        timeline.playFromStart();

        // Show scene
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
