package com.base.ui;

public interface IMenuActionListener {
    void onSave(String file);

    void onLoad(String file);

    void onClear();

    void onSetSize(int newSizeX, int newSizeY);

    void onMapChange();
}
