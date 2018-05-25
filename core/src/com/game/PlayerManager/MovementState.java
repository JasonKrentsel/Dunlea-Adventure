package com.game.PlayerManager;

public class MovementState {
    enum State {
        halted,
        moving,
        sliding
    }
    State state = State.halted;
    private boolean isRight = true;
    private boolean isJumping = false;

    public boolean isRight(){
        return isRight;
    }
    public boolean isJumping(){
        return isJumping;
    }
    public void setSide(boolean toRight){
        isRight = toRight;
    }
    public void setJump(boolean toSet){
        isJumping = toSet;
    }
}
