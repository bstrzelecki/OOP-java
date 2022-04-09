package com.base;

import java.awt.*;
import java.util.ArrayList;

public class RenderBatch {
    private final ArrayList<SpriteDescriptor> sprites = new ArrayList<>();

    public ArrayList<SpriteDescriptor> getSprites() {
        return sprites;
    }

    public void draw(char symbol, Color color, int x, int y) {
        sprites.add(new SpriteDescriptor(color, new Position(x, y), symbol));
    }

    public void clear() {
        sprites.clear();
    }
}
