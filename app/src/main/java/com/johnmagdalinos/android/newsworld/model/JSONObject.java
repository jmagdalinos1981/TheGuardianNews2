package com.johnmagdalinos.android.newsworld.model;

import com.johnmagdalinos.android.newsworld.model.database.Article;

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
        private ArrayList<Article> results;
        private String status;

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public ArrayList<Article> getResults() {
            return results;
        }
        public void setResults(ArrayList<Article> results) {
            this.results = results;
        }
    }

}
