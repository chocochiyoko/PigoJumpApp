package com.pigojump;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ScoresDao {
    @Query("SELECT * FROM scores")
    List<Scores> getAll();

    @Query("SELECT * FROM scores ORDER BY score DESC LIMIT 10")
    List<Scores> getTopTen();

    @Query("SELECT COUNT(*) + 1 FROM scores WHERE :currscore < score")
    int getRank(int currscore);

    @Insert
    void insertAll(Scores... users);

}
