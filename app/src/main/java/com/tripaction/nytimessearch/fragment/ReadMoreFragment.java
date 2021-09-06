package com.tripaction.nytimessearch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.tripaction.nytimessearch.R;
import com.tripaction.nytimessearch.models.Response;

public class ReadMoreFragment extends Fragment {

    public static final String ARTICLE_EXTRA = "article_extra";
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_read_more, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Response.Doc article = bundle.getParcelable(ARTICLE_EXTRA);
            WebView webView = mView.findViewById(R.id.read_more_webview);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.loadUrl(article.getWebUrl());

        } else {
            // Error handling code
        }


        return mView;
    }

}
