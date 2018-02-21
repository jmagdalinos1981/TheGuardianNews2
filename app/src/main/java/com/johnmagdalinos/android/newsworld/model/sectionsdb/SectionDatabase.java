package com.johnmagdalinos.android.newsworld.model.sectionsdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Gianni on 08/02/2018.
 */

@Database(entities = {Section.class}, version = 1)
public abstract class SectionDatabase extends RoomDatabase {
    public abstract SectionDao sectionDao();
}
