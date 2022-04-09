package com.base.entities.animals;

import com.base.Position;

import java.awt.*;

public class Wolf extends Animal {
    public Wolf(Position position) {
        super(position);
        strength = 9;
        initiative = 5;
        symbol = 'W';
        color = Color.red;
    }
}
