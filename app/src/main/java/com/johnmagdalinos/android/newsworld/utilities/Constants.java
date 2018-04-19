package com.johnmagdalinos.android.newsworld.utilities;

/**
 * Constants used throught the app.
 */

public class Constants {
    /** Keys */
    public static final String KEY_API_KEY = "api-key";
    public static final String KEY_API_SECTION = "section";
    public static final String KEY_URL = "url";
    public static final String KEY_SYNC_SERVICE = "sync_service";
    public static final String KEY_SECTION_ID = "section_id";
    public static final String KEY_ALL_NEWS = "all_news";

    /** Keys for SharedPreferences */
    public static final String KEY_SECTIONS = "sections";
    public static final String KEY_LAST_SYNC_DATE = "syncDate";
    public static final String KEY_PREFS_CHANGED = "prefs_changed";

    /** Values for Sync Service */
    public static final String GET_ARTICLES = "get_articles";
    public static final String SEARCH_ARTICLES = "search_articles";

    /** Database Name */
    public static final String DB_NAME = "articles.db";

    /** Broadcast receiver action for network operation */
    public static final String ACTION_SYNC_COMPLETED = "com.johnmagdalinos.android.newsworld" +
            ".SYNC_COMPLETED";
}
