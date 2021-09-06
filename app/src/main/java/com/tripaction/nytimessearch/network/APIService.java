package com.tripaction.nytimessearch.network;

import com.tripaction.nytimessearch.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API reference : https://developer.nytimes.com/docs/articlesearch-product/1/overview
 */
public interface APIService {

    @GET("svc/search/v2/articlesearch.json")
    Call<SearchResponse> getArticleSearch(@Query("q") String queryText);
}