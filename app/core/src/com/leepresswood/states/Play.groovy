package com.leepresswood.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.leepresswood.constants.ApplicationConstants
import com.leepresswood.constants.PlayStateConstants
import com.leepresswood.handlers.GameStateManager

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

        //Static body.
        BodyDef definition = new BodyDef()
        definition.position.set((0 / PlayStateConstants.PIXELS_PER_METER).floatValue(), (0 / PlayStateConstants.PIXELS_PER_METER).floatValue())
        definition.type = BodyDef.BodyType.StaticBody

        Body body = world.createBody(definition)

        PolygonShape shape = new PolygonShape()
        shape.setAsBox((50f / PlayStateConstants.PIXELS_PER_METER).floatValue(), (5f / PlayStateConstants.PIXELS_PER_METER).floatValue())

        FixtureDef fixtureDef = new FixtureDef()
        fixtureDef.shape = shape
        body.createFixture(fixtureDef)

        //Falling box.
        definition.position.set((0 / PlayStateConstants.PIXELS_PER_METER).floatValue(), (50 / PlayStateConstants.PIXELS_PER_METER).floatValue())
        definition.type = BodyDef.BodyType.DynamicBody

        body = world.createBody(definition)

        shape.setAsBox((5 / PlayStateConstants.PIXELS_PER_METER).floatValue(), (5 / PlayStateConstants.PIXELS_PER_METER).floatValue())

        fixtureDef.shape = shape
        fixtureDef.restitution = 0.0f
        body.createFixture(fixtureDef)

        b2d_cam = new OrthographicCamera((ApplicationConstants.V_WIDTH / PlayStateConstants.PIXELS_PER_METER).floatValue(), (ApplicationConstants.V_HEIGHT / PlayStateConstants.PIXELS_PER_METER).floatValue())
    }

    @Override
    void handleInput() {

    }

    @Override
    void update(float delta) {
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
