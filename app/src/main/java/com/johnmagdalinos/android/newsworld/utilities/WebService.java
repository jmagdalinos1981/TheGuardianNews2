package com.johnmagdalinos.android.newsworld.utilities;

import com.johnmagdalinos.android.newsworld.model.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {
    @GET("/search")
    Call<JSONObject> loadArticles(
            @Query(Constants.KEY_API_SECTION) String section,
            @Query(Constants.KEY_API_KEY) String apiKey);
}
