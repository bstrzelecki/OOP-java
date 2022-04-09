package com.base.ui;

import com.base.Position;
import com.base.entities.Entity;

public interface IEntitySpawnActionListener {
    void onEntitySpawnAction(Position position, Entity entity);
}
