package com.leepresswood.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.leepresswood.constants.ApplicationConstants
import com.leepresswood.constants.InputGameConstants
import com.leepresswood.constants.PlayStateConstants
import com.leepresswood.handlers.ContactHandler
import com.leepresswood.handlers.GameStateManager

/**
 * Created by Lee on 3/23/2016.
 */
public class Play extends GameState{
    private World world
    private Box2DDebugRenderer debug_renderer
    private OrthographicCamera b2d_cam
    private ContactHandler contact_handler
    private TiledMap tiled_map
    private OrthogonalTiledMapRenderer map_renderer

    private Body player_body

    protected Play(GameStateManager gsm) {
        super(gsm)

        world = new World(new Vector2(PlayStateConstants.GRAVITY_X, PlayStateConstants.GRAVITY_Y), true)
        debug_renderer = new Box2DDebugRenderer()

        contact_handler = new ContactHandler()
        world.setContactListener(contact_handler)

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
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_PLAYER
        body.createFixture(fixtureDef).setUserData(banana : 2000)

        //Falling box.
        definition.position.set(0 / PlayStateConstants.PIXELS_PER_METER as float, 50 / PlayStateConstants.PIXELS_PER_METER as float)
        definition.type = BodyDef.BodyType.DynamicBody

        player_body = world.createBody(definition)

        shape.setAsBox(5 / PlayStateConstants.PIXELS_PER_METER as float, 5 / PlayStateConstants.PIXELS_PER_METER as float)

        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_PLAYER
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_GROUND
        player_body.createFixture(fixtureDef).setUserData(body : true)

        //Foot sensor
        shape.setAsBox(2 / PlayStateConstants.PIXELS_PER_METER as float, 2 / PlayStateConstants.PIXELS_PER_METER as float, new Vector2(0 / PlayStateConstants.PIXELS_PER_METER as float, -5 / PlayStateConstants.PIXELS_PER_METER as float), 0f)

        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_PLAYER
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_GROUND

        /**
         * Let's talk about isSensor.
         * Sensors are regular fixtures in the sense that they detect collision, but they are transparent.
         * That is to say that they do not physically affect the simulation of the game. Instead, they aim
         * to act as the "side" sensor for a block.
         */
        fixtureDef.isSensor = true

        player_body.createFixture(fixtureDef).setUserData(foot : true)

        b2d_cam = new OrthographicCamera(ApplicationConstants.V_WIDTH / PlayStateConstants.PIXELS_PER_METER as float, ApplicationConstants.V_HEIGHT / PlayStateConstants.PIXELS_PER_METER as float)


        //Tiled map
        tiled_map = new TmxMapLoader().load("maps/test.tmx")
        map_renderer = new OrthogonalTiledMapRenderer(tiled_map)
    }

    @Override
    void handleInput() {
        if (InputGameConstants.isPressed(InputGameConstants.BUTTON1)){
            if (contact_handler.isPlayerOGround()){
                //In Newtons. Default mass is 1KG.
                player_body.applyForceToCenter(0, 200f, true)
            }
        }
        if (InputGameConstants.isPressed(InputGameConstants.BUTTON2)){

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

        map_renderer.setView(game_cam)
        map_renderer.render()

        debug_renderer.render(world, b2d_cam.combined)
    }

    @Override
    void dispose() {

    }
}
