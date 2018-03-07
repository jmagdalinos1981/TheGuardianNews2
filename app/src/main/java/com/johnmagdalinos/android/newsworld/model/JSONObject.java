package com.johnmagdalinos.android.newsworld.model;

import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;

import java.util.ArrayList;

/**
 * Model used in GSON to parse the JSON result from the API
 */

public class JSONObject {
    private Response response;

    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {
        private ArrayList<NewsArticle> results;
        private String status;

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public ArrayList<NewsArticle> getResults() {
            return results;
        }
        public void setResults(ArrayList<NewsArticle> results) {
            this.results = results;
        }
    }

}
