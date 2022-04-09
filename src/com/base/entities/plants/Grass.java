package com.base.entities.plants;

import com.base.Position;

import java.awt.*;

public class Grass extends Plant {
    public Grass(Position position) {
        super(position);
        strength = 0;
        color = Color.green;
        symbol = 'G';
    }
}
