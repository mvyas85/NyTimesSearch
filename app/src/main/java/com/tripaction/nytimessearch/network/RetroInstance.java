package com.tripaction.nytimessearch.network;

import com.tripaction.nytimessearch.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    private static final String BASE_URL = "https://api.nytimes.com/";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {

        if (retrofit == null) {

            //Use retrofit networking library to make API calls
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();

            httpClientBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api-key", API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder().url(url);

                return chain.proceed(requestBuilder.build());
            });

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}