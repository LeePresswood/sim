package com.leepresswood.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
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
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.leepresswood.constants.ApplicationConstants
import com.leepresswood.constants.InputGameConstants
import com.leepresswood.constants.PlayStateConstants
import com.leepresswood.entities.Crystal
import com.leepresswood.entities.Player
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
    private boolean debug = false

    private TiledMap tiled_map
    private OrthogonalTiledMapRenderer map_renderer
    private float tile_size

    private Player player
    private ArrayList<Crystal> crystals

    protected Play(GameStateManager gsm) {
        super(gsm)

        createWorld()
        createPlayer()
        createTiles()
    }

    @Override
    void handleInput() {
        if (InputGameConstants.isPressed(InputGameConstants.BUTTON1)){
            if (contact_handler.isPlayerOGround()){
                //In Newtons. Default mass is 1KG.
                player.getBody().applyForceToCenter(0, 250f, true)
            }
        }
        if (InputGameConstants.isPressed(InputGameConstants.BUTTON2)){

        }
    }

    @Override
    void update(float delta) {
        handleInput()
        world.step(delta, PlayStateConstants.POSITION_ITERATIONS, PlayStateConstants.VELOCITY_ITERATIONS)
        player.update(delta)

        for(Crystal crystal : crystals){
            crystal.update(delta)
        }
    }

    @Override
    void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.setProjectionMatrix(game_cam.combined)
        player.render(batch)
        for(Crystal crystal : crystals){
            crystal.render(batch)
        }

        map_renderer.setView(game_cam)
        map_renderer.render()

        if(debug){
            debug_renderer.render(world, b2d_cam.combined)
        }

        contact_handler.getBodiesToRemove().each {body ->
            crystals.remove(body.getUserData().data)
            world.destroyBody(body)
            player.collectCrystal()
        }
        contact_handler.getBodiesToRemove().clear()
    }

    @Override
    void dispose() {

    }

    private void createWorld(){
        b2d_cam = new OrthographicCamera()
        b2d_cam.setToOrtho(false, ApplicationConstants.V_WIDTH / PlayStateConstants.PIXELS_PER_METER as float, ApplicationConstants.V_HEIGHT / PlayStateConstants.PIXELS_PER_METER as float)
        world = new World(new Vector2(PlayStateConstants.GRAVITY_X, PlayStateConstants.GRAVITY_Y), true)
        debug_renderer = new Box2DDebugRenderer()
        contact_handler = new ContactHandler()
        world.setContactListener(contact_handler)
    }

    private void createPlayer(){
        BodyDef definition = new BodyDef()
        PolygonShape shape = new PolygonShape()
        FixtureDef fixtureDef = new FixtureDef()

        definition.position.set(30f / PlayStateConstants.PIXELS_PER_METER as float, 200f / PlayStateConstants.PIXELS_PER_METER as float)
        definition.type = BodyDef.BodyType.DynamicBody
        definition.linearVelocity.set(0.5f, 0f)

        Body body = world.createBody(definition)

        shape.setAsBox(13f / PlayStateConstants.PIXELS_PER_METER as float, 13f / PlayStateConstants.PIXELS_PER_METER as float)

        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_PLAYER
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_RED
        body.createFixture(fixtureDef).setUserData(data : "body")

        //Foot sensor
        shape.setAsBox(13f / PlayStateConstants.PIXELS_PER_METER as float, 2f / PlayStateConstants.PIXELS_PER_METER as float, new Vector2(0 / PlayStateConstants.PIXELS_PER_METER as float, -13 / PlayStateConstants.PIXELS_PER_METER as float), 0f)

        fixtureDef.shape = shape
        fixtureDef.filter.categoryBits = PlayStateConstants.BIT_PLAYER
        fixtureDef.filter.maskBits = PlayStateConstants.BIT_RED + PlayStateConstants.BIT_CRYSTAL

        /**
         * Let's talk about isSensor.
         * Sensors are regular fixtures in the sense that they detect collision, but they are transparent.
         * That is to say that they do not physically affect the simulation of the game. Instead, they aim
         * to act as the "side" sensor for a block.
         */
        fixtureDef.isSensor = true

        body.createFixture(fixtureDef).setUserData(data : "foot")

        player = new Player(body)

        body.setUserData(data : player)
    }

    private void createTiles(){
        tiled_map = new TmxMapLoader().load("maps/test.tmx")
        map_renderer = new OrthogonalTiledMapRenderer(tiled_map)
        tile_size = tiled_map.getProperties().get("tilewidth") as float

        TiledMapTileLayer layer
        layer = (TiledMapTileLayer) tiled_map.getLayers().get("red")
        createLayer(layer, PlayStateConstants.BIT_RED)
        layer = (TiledMapTileLayer) tiled_map.getLayers().get("green")
        createLayer(layer, PlayStateConstants.BIT_GREEN)
        layer = (TiledMapTileLayer) tiled_map.getLayers().get("blue")
        createLayer(layer, PlayStateConstants.BIT_BLUE)

        createCrystals(tiled_map)
    }

    private void createLayer(TiledMapTileLayer layer, short bits){
        BodyDef definition = new BodyDef()
        FixtureDef fixtureDef = new FixtureDef()

        for(int row = 0; row < layer.getHeight(); row++){
            for(int column = 0; column < layer.getWidth(); column++){
                Cell cell = layer.getCell(column, row)
                if (!cell?.getTile()){
                    continue
                }

                ChainShape chainShape = new ChainShape()
                Vector2[] vectors = new Vector2[3]

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

    private void createCrystals(TiledMap tiled_map){
        crystals = new ArrayList<>()
        MapLayer layer = tiled_map.getLayers().get("crystals")

        BodyDef definition = new BodyDef()
        FixtureDef fixtureDef = new FixtureDef()


        for(MapObject object : layer.getObjects()){
            float x = object.getProperties().get("x") / PlayStateConstants.PIXELS_PER_METER as float
            float y = object.getProperties().get("y") / PlayStateConstants.PIXELS_PER_METER as float

            definition.position.set(x, y)

            CircleShape shape = new CircleShape()
            shape.setRadius(8f / PlayStateConstants.PIXELS_PER_METER as float)

            fixtureDef.shape = shape
            fixtureDef.isSensor = true
            fixtureDef.filter.categoryBits = PlayStateConstants.BIT_CRYSTAL
            fixtureDef.filter.maskBits = PlayStateConstants.BIT_PLAYER

            Body body = world.createBody(definition)
            body.createFixture(fixtureDef).setUserData(data : "crystal")

            Crystal crystal = new Crystal(body)
            crystals.add(crystal)

            body.setUserData(data : crystal)
        }
    }
}
