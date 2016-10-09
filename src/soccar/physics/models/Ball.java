package soccar.physics.models;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import soccar.physics.Game;

/**
 * @author Marc
 */
public class Ball implements Updateable {

    private final Body body; // The Box2D body
    private float radius;

    public Ball(int x, int y, float radius) {

        this.radius = radius;

        //Create an JBox2D body defination for ball.
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.linearDamping = 1.0f;
        bd.angularDamping = 1.0f;
        bd.position.set(x, y);

        CircleShape cs = new CircleShape();
        cs.m_radius = radius;

        // Create a fixture for ball
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.01f;
        fd.friction = 1.0f;
        fd.restitution = 1.0f;

        body = Game.WORLD.createBody(bd);
        body.createFixture(fd);
    }

    @Override
    public void update() {

        // Lateral velocity
//        Vec2 impulse = body.getLinearVelocity().mul(-1f);
//        body.applyLinearImpulse(impulse, body.getWorldCenter());
//
//        // Angular velocity
//        body.applyAngularImpulse(body.getInertia() / 100 * -body.getAngularVelocity());

//        // Forward velocity
//        Vec2 currentForwardNormal = getForwardVelocity();
//        float currentForwardSpeed = currentForwardNormal.normalize();
//        float dragForceMagnitude = -2 * currentForwardSpeed;
//        body.applyForce(currentForwardNormal.mul(dragForceMagnitude), body.getWorldCenter());
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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public double getAngle() {
        return Math.toDegrees(body.getAngle());
    }
}
