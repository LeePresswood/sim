package com.leepresswood.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.leepresswood.constants.ApplicationConstants
import com.leepresswood.constants.InputGameConstants
import com.leepresswood.constants.PlayStateConstants
import com.leepresswood.handlers.ContactHandler
import com.leepresswood.handlers.GameStateManager
import sun.reflect.generics.tree.Tree

/**
 * Created by Lee on 3/23/2016.
 */
public class Play extends GameState{
    private World world
    private Box2DDebugRenderer debug_renderer
    private OrthographicCamera b2d_cam

    protected Play(GameStateManager gsm) {
        super(gsm)

        world = new World(new Vector2(PlayStateConstants.GRAVITY_X, PlayStateConstants.GRAVITY_Y), true)
        debug_renderer = new Box2DDebugRenderer()

        world.setContactListener(new ContactHandler())

        //Static body.
        BodyDef definition = new BodyDef()
        definition.position.set((0 / PlayStateConstants.PIXELS_PER_METER).floatValue(), (0 / PlayStateConstants.PIXELS_PER_METER).floatValue())
        definition.type = BodyDef.BodyType.StaticBody

        Body body = world.createBody(definition)

        PolygonShape shape = new PolygonShape()
        shape.setAsBox(50f / PlayStateConstants.PIXELS_PER_METER as float, 5f / PlayStateConstants.PIXELS_PER_METER as float)

        FixtureDef fixtureDef = new FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_GROUND
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_BOX + PlayStateConstants.BIT_BALL
        body.createFixture(fixtureDef).setUserData(banana : 2000)

        //Falling box.
        definition.position.set(0 / PlayStateConstants.PIXELS_PER_METER as float, 50 / PlayStateConstants.PIXELS_PER_METER as float)
        definition.type = BodyDef.BodyType.DynamicBody

        body = world.createBody(definition)

        shape.setAsBox(5 / PlayStateConstants.PIXELS_PER_METER as float, 5 / PlayStateConstants.PIXELS_PER_METER as float)

        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_BOX
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_GROUND
        body.createFixture(fixtureDef).setUserData(banana : 0)

        //Create ball
        definition.position.set(25 / PlayStateConstants.PIXELS_PER_METER as float, 60 / PlayStateConstants.PIXELS_PER_METER as float)
        body = world.createBody(definition)

        CircleShape circleShape = new CircleShape()
        circleShape.setRadius(5f / PlayStateConstants.PIXELS_PER_METER as float)
        fixtureDef.shape = circleShape
        fixtureDef.restitution = 1.0f
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_BALL
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_GROUND
        body.createFixture(fixtureDef).setUserData(banana : 123)

        b2d_cam = new OrthographicCamera((ApplicationConstants.V_WIDTH / PlayStateConstants.PIXELS_PER_METER).floatValue(), (ApplicationConstants.V_HEIGHT / PlayStateConstants.PIXELS_PER_METER).floatValue())
    }

    @Override
    void handleInput() {
        if (InputGameConstants.isDown(InputGameConstants.BUTTON1)){
            println "Hold Z"
        }
        if (InputGameConstants.isPressed(InputGameConstants.BUTTON2)){
            println "Pressed X"
        }
    }

    @Override
    void update(float delta) {
        handleInput()

        world.step(delta, PlayStateConstants.POSITION_ITERATIONS, PlayStateConstants.VELOCITY_ITERATIONS)
    }

    @Override
    void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)
        debug_renderer.render(world, b2d_cam.combined)
    }

    @Override
    void dispose() {

    }
}
