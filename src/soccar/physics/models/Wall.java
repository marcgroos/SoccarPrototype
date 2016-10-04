package soccar.physics.models;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import soccar.physics.Game;

/**
 * @author Marc
 */
public class Wall implements Updateable {

    private Body body; // The Box2D body

    private float width;
    private float height;

    public Wall(float x, float y, float width, float height, float angle) {

        this.width = width;
        this.height = height;

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width / 2, height / 2);

        FixtureDef fd = new FixtureDef();
        fd.friction = 0;
        fd.shape = ps;

        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.angle = (float) Math.toRadians(angle);

        body = Game.WORLD.createBody(bd);
        body.createFixture(fd);
    }

    public Body getBody() {
        return body;
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public double getAngle() {
        return Math.toDegrees(body.getAngle());
    }

    @Override
    public void update() {

    }
}
