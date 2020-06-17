package com.pigojump;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Scores.class}, version = 1)
public abstract class ScoresDB extends RoomDatabase {
    public abstract ScoresDao ScoresDao();
}
