package soccar.physics;

/**
 * Created by Marc on 3-10-2016.
 */
public class Utility {

    // Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelX(float x) {
        return x * Game.PIXELS_PER_METER;
    }

    // Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelY(float y) {
        return Game.HEIGHT - (y * Game.PIXELS_PER_METER);
    }

    // Convert a JBox2D width to pixel width
    public static float toPixelWidth(float width) {
        return width * Game.PIXELS_PER_METER;
    }

    // Convert a JBox2D height to pixel height
    public static float toPixelHeight(float height) {
        return height * Game.PIXELS_PER_METER;
    }
}
