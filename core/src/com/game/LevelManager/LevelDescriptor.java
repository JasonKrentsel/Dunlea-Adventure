package com.game.LevelManager;

import com.badlogic.gdx.files.FileHandle;

public class LevelDescriptor {
    public FileHandle tmxLocation;
    public String name;

    public LevelDescriptor(String name,FileHandle tmxLocation){
        this.name = name;
        this.tmxLocation = tmxLocation;
    }
}
