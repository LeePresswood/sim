package com.leepresswood.constants

/**
 * Created by Lee on 3/23/2016.
 */
class PlayStateConstants {
    public static final float GRAVITY_X = 0.0f
    public static final float GRAVITY_Y = -9.81f

    public static final int VELOCITY_ITERATIONS = 6
    public static final int POSITION_ITERATIONS = 2

    public static final float PIXELS_PER_METER = 100f

    //Category bits.
    /* Note:
     * Do powers of two. These are all bits.
     * Default collision mask bit is -1 for everything (think: 1111 1111 1111 1111). That is to say that everything collides with everything else.
     * Category is used as a description of the particular entity. Mask is a list of all the possible categories
     * that object should collide with.
     *
     * Note about the "list" of categories: It's not an actual list. For performance reasons, it's better to do
     * bit addition. That's why we do powers of two. Each 1 position ib the binary bits means a positive connection.
     * To get the "list", simply add together all these bits.
     */
    public static final short BIT_GROUND = 2
    public static final short BIT_BOX = 4
    public static final short BIT_BALL = 8
}
