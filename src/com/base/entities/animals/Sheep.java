package com.base.entities.animals;

import com.base.Position;

import java.awt.*;

public class Sheep extends Animal {
    public Sheep(Position position) {
        super(position);
        symbol = 'S';
        color = Color.CYAN;
        strength = 4;
        initiative = 4;
    }
}
