package com.eatnumber1.uvb.server;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * @author Russell Harmon
 * @since 1:55:39 PM
 */
public class GameMap {
    @SerializedName("py/object")
    private final String pythonObjectName;

    private final char snowman;

    private final char tree;

    private final char player;

    private final char snowball;

    private final char edge;

    private final char me;

    private final char unknown;

    private final int radius;

    private final Map<Point, Character>[] objects;

    public GameMap( String pythonObjectName, char snowman, char tree, char player, char snowball, char edge, char me, char unknown, int radius, Map<Point, Character>[] objects ) {
        this.pythonObjectName = pythonObjectName;
        this.snowman = snowman;
        this.tree = tree;
        this.player = player;
        this.snowball = snowball;
        this.edge = edge;
        this.me = me;
        this.unknown = unknown;
        this.radius = radius;
        this.objects = objects;
    }
}
