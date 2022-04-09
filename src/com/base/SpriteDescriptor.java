package com.base;

import java.awt.*;

class SpriteDescriptor {
    private final Color color;
    private final Position position;
    private final char symbol;

    public SpriteDescriptor(Color color, Position position, char symbol) {
        this.color = color;
        this.position = position;
        this.symbol = symbol;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public char getSymbol() {
        return symbol;
    }
}
