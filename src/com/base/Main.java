package com.base;

import com.base.entities.animals.*;
import com.base.entities.plants.*;
import com.base.ui.MainWindow;

import javax.swing.*;

public class Main {

    static World world;

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        world = new HexWorld(20, 20);


        window.setEndTurnButtonAction(e -> world.runOneFrame());
        window.setOnKeyStrokeAction(world::runOneFrame);
        window.setOnEntitySpawnAction((position, entity) -> {
            if (world.isTileEmpty(position)) {
                world.addEntity(entity);
                world.draw();
            } else {
                JOptionPane.showMessageDialog(window, "Tile was not empty!", "Spawing failed", JOptionPane.PLAIN_MESSAGE);
            }
        });
        window.setCellConverter(world.getConverter());
        window.setMenuActionListener(new MenuActionResolver(window));


        world.setWindow(window);

        world.addEntity(new Sheep(new Position(0, 0)));

        world.addEntity(new Human(new Position(5, 5)));
        world.addEntity(new Sheep(new Position(1, 1)));
        world.addEntity(new Sheep(new Position(1, 2)));

        world.addEntity(new Wolf(new Position(2, 1)));
        world.addEntity(new Wolf(new Position(2, 2)));

        world.addEntity(new Antelope(new Position(3, 1)));
        world.addEntity(new Antelope(new Position(3, 2)));

        world.addEntity(new Fox(new Position(4, 1)));
        world.addEntity(new Fox(new Position(4, 2)));

        world.addEntity(new Turtle(new Position(5, 1)));
        world.addEntity(new Turtle(new Position(5, 2)));

        world.addEntity(new Belladonna(new Position(6, 1)));
        world.addEntity(new Belladonna(new Position(6, 2)));

        world.addEntity(new Grass(new Position(7, 1)));
        world.addEntity(new Grass(new Position(7, 2)));

        world.addEntity(new Dandelion(new Position(8, 1)));
        world.addEntity(new Dandelion(new Position(8, 2)));

        world.addEntity(new Guarana(new Position(9, 1)));
        world.addEntity(new Guarana(new Position(9, 2)));

        world.addEntity(new Hogweed(new Position(10, 1)));
        world.addEntity(new Hogweed(new Position(10, 2)));

        world.addEntity(new CyberSheep(new Position(15, 15)));
        world.addEntity(new Sheep(new Position(19, 19)));

        world.draw();
    }

}
