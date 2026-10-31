package com.fongmi.android.ok.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.fongmi.android.ok.bean.Site;

import java.util.List;

@Dao
public abstract class SiteDao extends BaseDao<Site> {

    @Query("SELECT * FROM Site")
    public abstract List<Site> findAll();
}
