package com.johnmagdalinos.android.theguardiannews.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.johnmagdalinos.android.theguardiannews.model.JSONModel;
import com.johnmagdalinos.android.theguardiannews.model.NewsArticle;

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

public class NetworkUtils implements Callback<JSONModel> {
    /** Member variables */
    private RetrofitCallback mCallback;

    /** Keys used in the API */
    private static final String KEY_API_KEY = "api-key";

    /** Base url of the api */
    private static final String BASE_URL = "https://content.guardianapis.com";

    /** Callback used to pass the News Articles to the Model */
    public interface RetrofitCallback {
        void onRetrofitCompleted(ArrayList<NewsArticle> articles);
    }

    /** Retrofit Interface */
    public interface TheGuardianAPI {
        @GET("/search")
        Call<JSONModel> loadArticles(@Query(KEY_API_KEY) String apiKey);
    }

    /** Setups Retrofit and Gson */
    public void start(RetrofitCallback retrofitCallback) {
        mCallback = retrofitCallback;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TheGuardianAPI theGuardianAPI = retrofit.create(TheGuardianAPI.class);

        Call<JSONModel> call = theGuardianAPI.loadArticles(ApiKeys.API_KEY);
        call.enqueue(this);
    }

    /** Called when Retrofit was successful */
    @Override
    public void onResponse(Call<JSONModel> call, Response<JSONModel> response) {
        if (response.isSuccessful()) {
            JSONModel model = response.body();
            JSONModel.Response jsonResponse = model.getResponse();
            ArrayList<NewsArticle> articles = jsonResponse.getResults();

            mCallback.onRetrofitCompleted(articles);
        }
    }

    /** Called when Retrifut Failed */
    @Override
    public void onFailure(Call<JSONModel> call, Throwable t) {
        t.printStackTrace();
    }

}
