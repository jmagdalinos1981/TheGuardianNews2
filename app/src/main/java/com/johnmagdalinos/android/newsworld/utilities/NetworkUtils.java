package com.johnmagdalinos.android.newsworld.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.johnmagdalinos.android.newsworld.model.JSONObject;
import com.johnmagdalinos.android.newsworld.model.Section;
import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Contains all the utilities used by the model to access the Guardian API.
 */

public class NetworkUtils implements Callback<JSONObject> {
    /** Member variables */
    private RetrofitCallback mCallback;

    /** Base url of the api */
    private static final String BASE_URL = "https://content.guardianapis.com";

    /** Callback used to pass the News Articles to the Model */
    public interface RetrofitCallback {
        void onRetrofitCompleted(ArrayList<NewsArticle> articles);
    }

    /** Retrofit Interface */
    public interface TheGuardianAPI {
        @GET("/search")
        Call<JSONObject> loadArticles(
                @Query(Constants.KEY_SECTIONS) String section,
                @Query(Constants.KEY_API_KEY) String apiKey);
    }

    /** Setups Retrofit and Gson */
    public void start(RetrofitCallback retrofitCallback, ArrayList<Section> sections) {
        mCallback = retrofitCallback;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TheGuardianAPI theGuardianAPI = retrofit.create(TheGuardianAPI.class);

        Call<JSONObject> call;
        for (Section section : sections) {
            String sectionTitle = section.getTitle();
            // Check if the user selected "All news"
            if (sectionTitle.equals("allnews")) {
                call = theGuardianAPI.loadArticles(null, ApiKeys.API_KEY);
            } else {
                call = theGuardianAPI.loadArticles(sectionTitle, ApiKeys.API_KEY);
            }
            call.enqueue(this);
        }
    }

    /** Called when Retrofit was successful */
    @Override
    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
        if (response.isSuccessful()) {
            JSONObject model = response.body();
            JSONObject.Response jsonResponse = model.getResponse();
            ArrayList<NewsArticle> articles = jsonResponse.getResults();

            mCallback.onRetrofitCompleted(articles);
        }
    }

    /** Called when Retrofit Failed */
    @Override
    public void onFailure(Call<JSONObject> call, Throwable t) {
        t.printStackTrace();
    }

}
