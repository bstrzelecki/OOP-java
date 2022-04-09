package com.base.entities.plants;

import com.base.Journal;
import com.base.Position;

import java.awt.*;

public class Dandelion extends Plant {

    public Dandelion(Position position) {
        super(position);
        symbol = 'D';
        color = Color.cyan;
    }

    @Override
    public void update(Journal journal) {
        incrementAge();
        for (int i = 0; i < 3; i++) {
            spread(journal);
        }
    }
}
