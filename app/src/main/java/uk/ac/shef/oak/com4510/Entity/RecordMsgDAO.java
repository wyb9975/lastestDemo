/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.Entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * The interface Record msg dao.
 */
@Dao
public interface RecordMsgDAO {
    /**
     * Insert all.
     *
     * @param recordMsg the record msg
     */
    @Insert
    void insertAll(RecordMsg... recordMsg);

    /**
     * Insert.
     *
     * @param recordMsg the record msg
     */
    @Insert
    void insert(RecordMsg recordMsg);

    /**
     * Delete.
     *
     * @param recordMsg the record msg
     */
    @Delete
    void delete (RecordMsg recordMsg);

    /**
     * Retrieve all data list.
     *
     * @return the list
     */
    @Query("SELECT * FROM RecordMsg ORDER BY date ASC")
    List<RecordMsg> retrieveAllData();

    /**
     * Delete all.
     *
     * @param recordMsg the record msg
     */
    @Delete
    void deleteAll(RecordMsg...recordMsg);
}
