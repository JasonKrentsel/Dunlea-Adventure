package com.game.PlayerManager;

public class PlayerSensorState {
    public boolean top = false;
    public boolean bottem = false;
    public boolean left = false;
    public boolean right = false;

    @Override
    public String toString() {
        return "Top: "+top+"     Bottem: "+bottem+"     Left: "+left+"     Right: "+right;
    }
}
