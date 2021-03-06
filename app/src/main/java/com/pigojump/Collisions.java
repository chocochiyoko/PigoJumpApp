package com.pigojump;

import com.pigojump.MapElements.GameMap;

import java.util.ArrayList;

public class Collisions {
    GameMap gameMap;
    private Pigo pigo;
    public Collisions(GameMap gameMap, Pigo pigo){
        this.gameMap = gameMap;
        this.pigo = pigo;
    }

    public void checkCollisions(int width){
//        ArrayList<GameObject> check = gameMap.checkPigoCollision(pigo);
        ArrayList<GameObject> check = gameMap.pigoCollision2(pigo);

        if (!check.isEmpty()){
            pigo.allCollisions2(check);
        }
        pigo.checkBorder(width);
    }
}
