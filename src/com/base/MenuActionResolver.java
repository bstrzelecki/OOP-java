package com.base;

import com.base.ui.IMenuActionListener;
import com.base.ui.MainWindow;

import javax.swing.*;

class MenuActionResolver implements IMenuActionListener {
    private final MainWindow window;

    public MenuActionResolver(MainWindow window) {
        this.window = window;
    }

    @Override
    public void onSave(String file) {
        Main.world.save(file);
    }

    @Override
    public void onLoad(String file) {
        Main.world.load(file);
        Main.world.draw();
    }

    @Override
    public void onClear() {
        Main.world.clear();
    }

    @Override
    public void onSetSize(int newSizeX, int newSizeY) {
        Main.world.reSize(newSizeX, newSizeY);
    }

    @Override
    public void onMapChange() {
        Serializer tmp = Main.world.serialize();
        if (Main.world instanceof GridWorld) {
            Main.world = new HexWorld(Main.world.getSizeX(), Main.world.getSizeY());
        } else {
            Main.world = new GridWorld(Main.world.getSizeX(), Main.world.getSizeY());
        }
        Main.world.setWindow(window);
        window.setOnKeyStrokeAction(Main.world::runOneFrame);
        window.setEndTurnButtonAction(e -> Main.world.runOneFrame());
        window.setOnEntitySpawnAction((position, entity) -> {
            if (Main.world.isTileEmpty(position)) {
                Main.world.addEntity(entity);
                Main.world.draw();
            } else {
                JOptionPane.showMessageDialog(window, "Tile was not empty!", "Spawing failed", JOptionPane.PLAIN_MESSAGE);
            }
        });
        window.setCellConverter(Main.world.getConverter());
        Main.world.deserialize(tmp);
        Main.world.draw();
    }
}
