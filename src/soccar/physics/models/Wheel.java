package soccar.physics.models;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import soccar.physics.Game;
import soccar.physics.enumerations.ThrottleAction;

public class Wheel {

    public final Body body;
    public final Car car;
    public final Body carBody;

    private final float width;
    private final float height;

    private float desiredSpeed = 0;

    public Wheel(float relXPos, float relYPos, float wheelWidth, float wheelDiameter,
                 boolean steerable, Car car) {

        this.car = car;
        this.carBody = car.getBody();

        this.width = wheelWidth;
        this.height = wheelDiameter;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.angularDamping = 0.2f;
        bd.linearDamping = 0.2f;
        bd.position = carBody.getWorldPoint(new Vec2(relXPos, relYPos));
        bd.angle = carBody.getAngle();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wheelWidth / 2, wheelDiameter / 2);

        FixtureDef fd = new FixtureDef();
        fd.density = 1.0f;
        fd.isSensor = true; // do not include wheels in collision system (for performance)
        fd.shape = shape;

        body = Game.WORLD.createBody(bd);
        body.createFixture(fd);

        if (steerable) {
            RevoluteJointDef jd = new RevoluteJointDef();
            jd.initialize(carBody, body, body.getWorldCenter());
            jd.enableMotor = true;
            Game.WORLD.createJoint(jd);
        } else {
            PrismaticJointDef jd = new PrismaticJointDef();
            jd.initialize(carBody, body, body.getWorldCenter(), new Vec2(1, 0));
            jd.enableLimit = true;
            jd.lowerTranslation = jd.upperTranslation = 0;
            Game.WORLD.createJoint(jd);
        }
    }

    /**
     * Sets wheel's velocity vector with sideways velocity subtracted.
     */

    public Vec2 getLateralVelocity() {
        Vec2 currentRightNormal = body.getWorldVector(new Vec2(1, 0));
        return currentRightNormal.mul(Vec2.dot(currentRightNormal, body.getLinearVelocity()));
    }

    public Vec2 getForwardVelocity() {
        Vec2 currentRightNormal = body.getWorldVector(new Vec2(0, 1));
        return currentRightNormal.mul(Vec2.dot(currentRightNormal, body.getLinearVelocity()));
    }

    public void updateFriction() {

        float massDiv = car.isHandbrake() ? 10 : 2;

        // Lateral velocity
        Vec2 impulse = getLateralVelocity().mul(-body.getMass() / massDiv);
        body.applyLinearImpulse(impulse, body.getWorldCenter());


        // Angular velocity
//        body.applyAngularImpulse(0.1f * body.getInertia() * -body.getAngularVelocity());

        // Forward velocity
//        Vec2 currentForwardNormal = getForwardVelocity();
//        float currentForwardSpeed = currentForwardNormal.normalize();
//        float dragForceMagnitude = -2 * currentForwardSpeed;
//        body.applyForce(currentForwardNormal.mul(dragForceMagnitude), body.getWorldCenter());
    }

    public void updateDrive() {
        Vec2 currentForwardNormal = body.getWorldVector(new Vec2(0, 1));
        float currentSpeed = Vec2.dot(getForwardVelocity(), currentForwardNormal);

        float force;

        if (desiredSpeed > currentSpeed)
            force = Game.CAR_POWER * 100;
        else if (desiredSpeed < currentSpeed)
            force = -Game.CAR_POWER * 100;
        else
            return;

        body.applyForce(currentForwardNormal.mul(force), body.getWorldCenter());
    }

    public void setAction(ThrottleAction throttleAction) {
        switch (throttleAction) {
            case ACCELERATE:
                desiredSpeed = Game.CAR_MAX_SPEED * 10;
                break;
            case REVERSE:
                desiredSpeed = -Game.CAR_MAX_REVERSE_SPEED * 10;
                break;
            case IDLE:
                desiredSpeed = 0;
                break;
            default:
                desiredSpeed = 0;
                break;
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public double getAngle() {
        return Math.toDegrees(body.getAngle());
    }

    /**
     * Sets wheel's angle relative to the car's body (in degrees).
     */
    public void setAngle(float angle) {
        body.m_sweep.a = carBody.getAngle() + angle;
    }

}