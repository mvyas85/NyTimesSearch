package com.tripaction.nytimessearch.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tripaction.nytimessearch.R;
import com.tripaction.nytimessearch.fragment.DetailFragment;
import com.tripaction.nytimessearch.fragment.ReadMoreFragment;
import com.tripaction.nytimessearch.models.Response;


public class DetailActivity extends AppCompatActivity implements DetailFragment.DetailFragmentInteraction {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String ARTICLE_EXTRA = "article_extra";

    private Fragment mDetailFragment;
    private Fragment mReadMoreFragment;
    private Response.Doc mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            mDetailFragment = new DetailFragment();
            mReadMoreFragment = new ReadMoreFragment();
        } else {
            //retrieve
        }
        mArticle = getIntent().getParcelableExtra(ARTICLE_EXTRA);
        loadFragment(mDetailFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTICLE_EXTRA, mArticle);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onReadMoreClicked() {
        loadFragment(mReadMoreFragment);
    }
}
