/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.Entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordMsgDAO {
    @Insert
    void insertAll(RecordMsg... recordMsg);

    @Insert
    void insert(RecordMsg recordMsg);

    @Delete
    void delete (RecordMsg recordMsg);

    @Query("SELECT * FROM RecordMsg ORDER BY id ASC")
    List<RecordMsg> retrieveAllData();

    @Delete
    void deleteAll(RecordMsg...recordMsg);
}
