package com.leepresswood.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.ChainShape
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

    private Body player_body

    private TiledMap tiled_map
    private OrthogonalTiledMapRenderer map_renderer
    private float tile_size

    protected Play(GameStateManager gsm) {
        super(gsm)

        b2d_cam = new OrthographicCamera()
        b2d_cam.setToOrtho(false, ApplicationConstants.V_WIDTH / PlayStateConstants.PIXELS_PER_METER as float, ApplicationConstants.V_HEIGHT / PlayStateConstants.PIXELS_PER_METER as float)
        world = new World(new Vector2(PlayStateConstants.GRAVITY_X, PlayStateConstants.GRAVITY_Y), true)
        debug_renderer = new Box2DDebugRenderer()
        contact_handler = new ContactHandler()
        world.setContactListener(contact_handler)

        createPlayer()
        createTiles()
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

    private void createPlayer(){
        BodyDef definition = new BodyDef()
        PolygonShape shape = new PolygonShape()
        FixtureDef fixtureDef = new FixtureDef()

        definition.position.set(45f / PlayStateConstants.PIXELS_PER_METER as float, 200f / PlayStateConstants.PIXELS_PER_METER as float)
        definition.type = BodyDef.BodyType.DynamicBody

        player_body = world.createBody(definition)

        shape.setAsBox(5f / PlayStateConstants.PIXELS_PER_METER as float, 5f / PlayStateConstants.PIXELS_PER_METER as float)

        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_PLAYER
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_RED
        player_body.createFixture(fixtureDef).setUserData(body : true)

        //Foot sensor
        shape.setAsBox(2f / PlayStateConstants.PIXELS_PER_METER as float, 2f / PlayStateConstants.PIXELS_PER_METER as float, new Vector2(0 / PlayStateConstants.PIXELS_PER_METER as float, -5 / PlayStateConstants.PIXELS_PER_METER as float), 0f)

        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_PLAYER
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_RED

        /**
         * Let's talk about isSensor.
         * Sensors are regular fixtures in the sense that they detect collision, but they are transparent.
         * That is to say that they do not physically affect the simulation of the game. Instead, they aim
         * to act as the "side" sensor for a block.
         */
        fixtureDef.isSensor = true

        player_body.createFixture(fixtureDef).setUserData(foot : true)
    }

    private void createTiles(){
        tiled_map = new TmxMapLoader().load("maps/test.tmx")
        map_renderer = new OrthogonalTiledMapRenderer(tiled_map)

        TiledMapTileLayer layer

        layer = (TiledMapTileLayer) tiled_map.getLayers().get("red")
        createLayer(layer, PlayStateConstants.BIT_RED)
        layer = (TiledMapTileLayer) tiled_map.getLayers().get("green")
        createLayer(layer, PlayStateConstants.BIT_GREEN)
        layer = (TiledMapTileLayer) tiled_map.getLayers().get("blue")
        createLayer(layer, PlayStateConstants.BIT_BLUE)

        tile_size = tiled_map.getProperties().get("tilewidth") as float
    }

    private void createLayer(TiledMapTileLayer layer, short bits){
        BodyDef definition = new BodyDef()
        FixtureDef fixtureDef = new FixtureDef()
        ChainShape chainShape = new ChainShape()
        Vector2[] vectors = new Vector2[3]

        for(int row = 0; row < layer.getHeight(); row++){
            for(int column = 0; column < layer.getWidth(); column++){
                Cell cell = layer.getCell(column, row)
                if (!cell?.getTile()){
                    continue
                }

                //Create body and fixture
                definition.type = BodyDef.BodyType.StaticBody
                definition.position.set((column + 0.5) * tile_size / PlayStateConstants.PIXELS_PER_METER as float, (row + 0.5) * tile_size / PlayStateConstants.PIXELS_PER_METER as float)

                //For the shape of ground tiles, use a ChainShape to avoid the issue of getting stuck between tiles.
                //Create chain of vectors. Note that these are in relation to the definition position from above.
                //Because we're focused on the center of the tile, move half the size in each direction and find the corners.
                vectors[0] = new Vector2(-tile_size / 2f / PlayStateConstants.PIXELS_PER_METER as float, -tile_size / 2f / PlayStateConstants.PIXELS_PER_METER as float)    //Bottom-left corner of tile.
                vectors[1] = new Vector2(-tile_size / 2f / PlayStateConstants.PIXELS_PER_METER as float, tile_size / 2f / PlayStateConstants.PIXELS_PER_METER as float)     //Top-left corner of tile.
                vectors[2] = new Vector2(tile_size / 2f / PlayStateConstants.PIXELS_PER_METER as float, tile_size / 2f / PlayStateConstants.PIXELS_PER_METER as float)      //Top-right corner of tile.
                chainShape.createChain(vectors)

                fixtureDef.friction = 0
                fixtureDef.shape = chainShape
                fixtureDef.filter.categoryBits = bits
                fixtureDef.filter.maskBits = PlayStateConstants.BIT_PLAYER
                fixtureDef.isSensor = false

                world.createBody(definition).createFixture(fixtureDef)
            }
        }
    }
}
