package com.tripaction.nytimessearch.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tripaction.nytimessearch.R;
import com.tripaction.nytimessearch.models.Response;
import com.tripaction.nytimessearch.utils.ImageUtil;

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    public static final String ARTICLE_EXTRA = "article_extra";

    private View mView;
    private ImageView mDetailImage;
    private TextView mDetailTitle;
    private TextView mDetailReadMore;
    private Button mShareButton;
    private DetailFragmentInteraction onInteraction;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof DetailFragmentInteraction) {
            onInteraction = (DetailFragmentInteraction) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail, container, false);

        mDetailImage = mView.findViewById(R.id.detail_news_image);
        mDetailTitle = mView.findViewById(R.id.detail_news_title);
        mDetailReadMore = mView.findViewById(R.id.detail_news_read_more);
        mShareButton = mView.findViewById(R.id.detail_news_share);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Response.Doc article = bundle.getParcelable(ARTICLE_EXTRA);

            ImageUtil.displayNewsImage(getActivity(), mDetailImage, article);
            mDetailTitle.setText(article.getHeadline().getMain());
            mShareButton.setOnClickListener(v -> {
                Log.d(TAG, "onCreate() share button clicked.");
                openShareIntent(article);
            });
            mDetailReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onInteraction.onReadMoreClicked();
                }
            });
        } else {
            // Error handling code
        }

        return mView;
    }

    private void openShareIntent(Response.Doc article) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this article: "
                + article.getWebUrl());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public interface DetailFragmentInteraction {
        void onReadMoreClicked();
    }
}
