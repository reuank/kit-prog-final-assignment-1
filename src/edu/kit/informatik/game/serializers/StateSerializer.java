package edu.kit.informatik.game.serializers;

public class StateSerializer {
    public static String serialize(int state) {
        return (state == 0 ? "**" : "P" + state);
    }
}