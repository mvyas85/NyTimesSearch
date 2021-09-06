package com.tripaction.nytimessearch.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tripaction.nytimessearch.R;
import com.tripaction.nytimessearch.models.Multimedia;
import com.tripaction.nytimessearch.models.Response;

import java.util.List;

public class ImageUtil {
    private static final String TAG = ImageUtil.class.getSimpleName();
    public static final String IMAGE_BASE_URL = "https://www.nytimes.com/";

    public static void displayNewsImage(Context context, ImageView imageView, Response.Doc jsonResponse) {

        List<Multimedia> multimedia = jsonResponse.getMultimedia();
        if (multimedia != null && multimedia.size() != 0) {
            Log.d(TAG, "displayNewsImage() | news image displaying");
            // Free image : https://www.freeimages.com/photo/news-1317046
            String imageUrl = IMAGE_BASE_URL + multimedia.get(0).getUrl();
            Picasso.with(context).load(imageUrl).into(imageView);
        } else {
            Log.d(TAG, "displayNewsImage() | default image displaying");
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_news));
        }
    }
}
