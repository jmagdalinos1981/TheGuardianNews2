package com.johnmagdalinos.android.theguardiannews.model;

/**
 * Represents a single article from the API
 */

public class NewsArticle {

    /** Member variables */
    private String sectionId;
    private String webPublicationDate;
    private String webTitle;
    private String webUrl;

    /** Getters */
    public String getSectionId() {
        return sectionId;
    }
    public String getPublicationDate() {
        return webPublicationDate;
    }
    public String getWebTitle() {
        return webTitle;
    }
    public String getWebUrl() {
        return webUrl;
    }

    /** Setters */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }
    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
