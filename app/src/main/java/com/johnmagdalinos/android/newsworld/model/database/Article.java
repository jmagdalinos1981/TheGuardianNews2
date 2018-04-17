package com.johnmagdalinos.android.newsworld.model.database;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Represents a single article from the API.
 * Used in Room and in Retrofit
 */

@Entity(tableName = "articles",
        primaryKeys = {"webTitle"})
public class Article {

    /** Member variables/Database Columns */
    @NonNull
    private String webTitle;
    private String sectionId;
    private String webPublicationDate;
    private String webUrl;

    /** Getters */
    public String getWebTitle() {
        return webTitle;
    }
    public String getSectionId() {
        return sectionId;
    }
    public String getWebPublicationDate() {
        return webPublicationDate;
    }
    public String getWebUrl() {
        return webUrl;
    }

    /** Setters */
    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
