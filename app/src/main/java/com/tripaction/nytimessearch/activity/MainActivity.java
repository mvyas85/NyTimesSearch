package com.tripaction.nytimessearch.activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripaction.nytimessearch.R;
import com.tripaction.nytimessearch.adapters.RecyclerAdapter;
import com.tripaction.nytimessearch.models.Response;
import com.tripaction.nytimessearch.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Response.Doc> mNewsArticles = new ArrayList<>();
    private Button mSearchButton;
    private MainActivityViewModel mMainActivityViewModel;
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private EditText mEditTextQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.main_recycler_view);
        mProgressBar = findViewById(R.id.main_progress_bar);
        mSearchButton = findViewById(R.id.searchButton);
        mEditTextQuery = findViewById(R.id.searchQueryText);
        mEditTextQuery.requestFocus();
        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initRecyclerView();
        mMainActivityViewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });
        mMainActivityViewModel.getArticles().observe(this, articles -> {
            mNewsArticles.addAll(articles.getResponse().getDocs());
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new RecyclerAdapter(this, mNewsArticles);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void onSearchArticles(View view) {
        mNewsArticles.clear();
        hideKeyboardWhenSearching();
        String query = mEditTextQuery.getText().toString();
        mMainActivityViewModel.searchNYArticles(query);
    }

    private void hideKeyboardWhenSearching() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
