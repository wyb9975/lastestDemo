/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com6510.Entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * RecordMsgDAO is the interface to provide some measure to operates the database.
 * This interface contains insert, deletions, changes, and query of database data.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
@Dao
public interface RecordMsgDAO {
    /**
     * Insert Several records to database.
     *
     * @param recordMsg the record message list
     */
    @Insert
    void insertAll(RecordMsg... recordMsg);

    /**
     * Insert a record to database.
     *
     * @param recordMsg the record message
     */
    @Insert
    void insert(RecordMsg recordMsg);

    /**
     * Delete a record from database.
     *
     * @param recordMsg the record message
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
     * Delete Several records from database.
     *
     * @param recordMsg the record msg list
     */
    @Delete
    void deleteAll(RecordMsg...recordMsg);
}
