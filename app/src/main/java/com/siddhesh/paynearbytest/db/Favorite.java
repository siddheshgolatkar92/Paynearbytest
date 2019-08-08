package com.siddhesh.paynearbytest.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "combinations")
    private String combinations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCombinations() {
        return combinations;
    }

    public void setCombinations(String combinations) {
        this.combinations = combinations;
    }
}
