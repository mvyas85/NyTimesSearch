package com.tripaction.nytimessearch.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tripaction.nytimessearch.models.SearchResponse;
import com.tripaction.nytimessearch.network.APIService;
import com.tripaction.nytimessearch.network.RetroInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private MutableLiveData<SearchResponse> mArticles;
    private MutableLiveData<Boolean> mIsFetchingData;

    public MainActivityViewModel() {
        mArticles = new MutableLiveData<>();
        mIsFetchingData = new MutableLiveData<>();
    }

    public MutableLiveData<SearchResponse> getArticles() {
        return mArticles;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsFetchingData;
    }

    public void searchNYArticles(String searchFor) {
        callAPI(searchFor);
    }

    private void callAPI(String searchFor) {
        mIsFetchingData.setValue(true);
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<SearchResponse> call = apiService.getArticleSearch(searchFor);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                Log.d(TAG, "callAPI() | onResponse: " + response.body());
                mArticles.postValue(response.body());
                mIsFetchingData.setValue(false);
            }

            @Override
            public void onFailure(Call<SearchResponse> errorResponse, Throwable t) {
                Log.d(TAG, "onFailure() | errorResponse: " + errorResponse.toString());
                mArticles.postValue(null);
                mIsFetchingData.setValue(false);
            }
        });
    }
}
