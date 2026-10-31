package com.fongmi.android.ok.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.fongmi.android.ok.bean.Live;

import java.util.List;

@Dao
public abstract class LiveDao extends BaseDao<Live> {

    @Query("SELECT * FROM Live")
    public abstract List<Live> findAll();

    @Query("SELECT * FROM Live WHERE name = :name")
    public abstract Live find(String name);
}
