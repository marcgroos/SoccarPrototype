package soccar.physics.models;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import soccar.physics.Game;
import soccar.physics.enumerations.SteerAction;
import soccar.physics.enumerations.ThrottleAction;

/**
 * Four-wheeled vehicle's representation.
 */
public class Car implements Updateable {

    private final Body body;

    private final Wheel frontLWheel;
    private final Wheel frontRWheel;
    private final Wheel backLWheel;
    private final Wheel backRWheel;

    private final float width;
    private final float height;

    private float wheelsSteerAngle;

    private SteerAction steerAction = SteerAction.NONE;
    private ThrottleAction throttleAction = ThrottleAction.IDLE;
    private boolean handbrake;

    public Car(float x, float y, float width, float height, float angle, float wheelWidth, float wheelDiameter) {


        this.width = width;
        this.height = height;

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(x, y);
        bd.angle = (float) Math.toRadians(angle);
        bd.bullet = true;   // prevents tunneling
        bd.linearDamping = 0.2f;   // simulates friction
        bd.angularDamping = 0.2f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fd = new FixtureDef();
        fd.density = 0.2f;
//        fd.friction = 0.5f;
//        fd.restitution = 0.5f;
        fd.shape = shape;

        this.body = Game.WORLD.createBody(bd);
        this.body.createFixture(fd);

        // Create wheels
        this.frontLWheel = new Wheel(-width / 2.3f, height / 4f, wheelWidth, wheelDiameter, true, this);
        this.frontRWheel = new Wheel(width / 2.3f, height / 4f, wheelWidth, wheelDiameter, true, this);
        this.backLWheel = new Wheel(-width / 2.3f, -height / 4f, wheelWidth, wheelDiameter, false, this);
        this.backRWheel = new Wheel(width / 2.3f, -height / 4f, wheelWidth, wheelDiameter, false, this);
    }

    @Override
    public void update() {

        // Kill sideways velocity
        frontLWheel.updateFriction();
        frontRWheel.updateFriction();
        backLWheel.updateFriction();
        backRWheel.updateFriction();

        // update throttleAction
        frontLWheel.setAction(throttleAction);
        frontRWheel.setAction(throttleAction);

        // Update revolving wheels
        updateWheelsSteerAngle(steerAction, Game.MS_PER_FRAME);
        frontLWheel.setAngle(wheelsSteerAngle);
        frontRWheel.setAngle(wheelsSteerAngle);

        // Apply force to powered wheels
        frontLWheel.updateDrive();
        frontRWheel.updateDrive();
    }


    private void updateWheelsSteerAngle(SteerAction steerAction, float msDuration) {

        float wheelMaxSteerAngle = (float) Math.toRadians(Game.WHEEL_MAX_STEER_ANGLE);
        float angleDiff = (wheelMaxSteerAngle / Game.WHEEL_MAX_TURN_IN_MS) * msDuration;

        if (steerAction == SteerAction.STEER_LEFT) {
            wheelsSteerAngle = Math.min(Math.max(wheelsSteerAngle, 0) + angleDiff, wheelMaxSteerAngle);
        } else if (steerAction == SteerAction.STEER_RIGHT) {
            wheelsSteerAngle = Math.max(Math.min(wheelsSteerAngle, 0) - angleDiff, -wheelMaxSteerAngle);
        } else {
            wheelsSteerAngle = 0;
        }
    }

    public Wheel getFrontLWheel() {
        return frontLWheel;
    }

    public Wheel getFrontRWheel() {
        return frontRWheel;
    }

    public Wheel getBackLWheel() {
        return backLWheel;
    }

    public Wheel getBackRWheel() {
        return backRWheel;
    }

    public SteerAction getSteerAction() {
        return steerAction;
    }

    public void setSteerAction(SteerAction steerAction) {
        this.steerAction = steerAction;
    }

    public ThrottleAction getThrottleAction() {
        return throttleAction;
    }

    public void setThrottleAction(ThrottleAction throttleAction) {
        this.throttleAction = throttleAction;
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

    public void setHandbrake(boolean handbrake) {
        this.handbrake = handbrake;
    }

    public boolean isHandbrake() {
        return handbrake;
    }

    public Body getBody() {
        return body;
    }
}