package com.leepresswood.constants

/**
 * Created by Lee on 3/24/2016.
 */
class InputGameConstants {
    public static boolean[] keys
    public static boolean[] previous_keys

    public static final int NUM_KEYS = 2
    public static final int BUTTON1 = 0
    public static final int BUTTON2 = 1

    static {
        keys = new boolean[NUM_KEYS]
        previous_keys = new boolean[NUM_KEYS]
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            previous_keys[i] = keys[i]
        }
    }

    public static void setKey(int i, boolean b) {
        keys[i] = b
    }

    public static boolean isDown(int i){
        return keys[i]
    }

    public static boolean isPressed(int i){
        return keys[i] && !previous_keys[i]
    }
}
