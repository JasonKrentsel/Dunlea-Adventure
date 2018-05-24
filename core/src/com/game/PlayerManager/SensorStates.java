package com.game.PlayerManager;

public class SensorStates {
    public boolean top = false;
    public boolean bottem = false;
    public boolean topLeft = false;
    public boolean topRight = false;
    public boolean bottemLeft = false;
    public boolean bottemRight = false;

    @Override
    public String toString() {
        return "Top: "+top+"     Bottem: "+bottem+"     TopLeft: "+topLeft+"     TopRight: "+topRight;
    }
}
