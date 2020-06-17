package com.pigojump;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class Scores {
    @PrimaryKey
    public long scoreid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "score")
    public int score;

    public Scores(String name, int score){
        this.name = name;
        this.score = score;
        this.scoreid = System.currentTimeMillis();
    }

    @Override
    public String toString(){
        return name + "..." + score;
    }
}