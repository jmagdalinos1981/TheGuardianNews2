package com.johnmagdalinos.android.newsworld.model.sectionsdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by Gianni on 08/02/2018.
 */

@Dao
public interface SectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSections(Section... sections);

    @Update
    void updateSections(Section... sections);

    @Delete
    void deleteSections(Section... sections);

    @Query("SELECT * FROM sections")
    Section[] loadAllSections();

    @Query("SELECT * FROM sections WHERE isSelected = :checked")
    Section[] loadSelectedSection(boolean checked);

    @Query("DELETE FROM sections")
    void deleteAllSections();
}
