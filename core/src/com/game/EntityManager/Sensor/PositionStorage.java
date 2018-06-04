package com.game.EntityManager.Sensor;

import java.util.ArrayList;

public class PositionStorage<E> {
    ArrayList<E> list = new ArrayList<E>();

    public PositionStorage(){
        for(int x =0;x<6;x++)
        list.add(null);
    }

    private int posToIndex(Position pos){
        switch (pos){
            case Top:
                return 0;
            case Bottem:
                return 1;
            case TopLeft:
                return 2;
            case BottemLeft:
                return 3;
            case TopRight:
                return 4;
            case BottemRight:
                return 5;
        }
        return -1;
    }

    public void set(Position pos,E value){
        list.set(posToIndex(pos),value);
    }

    public E get(Position pos){
        return list.get(posToIndex(pos));
    }

    public ArrayList<E> getList() {
        return list;
    }
}
