/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.Entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import io.reactivex.annotations.NonNull;

import static androidx.room.Room.databaseBuilder;

@Database(entities = {RecordMsg.class}, version = 1, exportSchema = false)
public abstract class RecordMsgDataBase extends RoomDatabase{
    public abstract RecordMsgDAO recordMsgDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile RecordMsgDataBase INSTANCE;

    public static RecordMsgDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecordMsgDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = databaseBuilder(context.getApplicationContext(),
                            RecordMsgDataBase.class, "photo_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
        }
    };
}
