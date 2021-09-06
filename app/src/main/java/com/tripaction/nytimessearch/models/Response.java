package com.tripaction.nytimessearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<Doc> docs;

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public static class Doc implements Parcelable {

        @SerializedName("web_url")
        @Expose
        private String webUrl;
        private List<Multimedia> multimedia = null;
        private Headline headline;

        protected Doc() {
            multimedia = new ArrayList<>();
        }

        protected Doc(Parcel in) {
            this();
            webUrl = in.readString();
            in.readTypedList(multimedia, Multimedia.CREATOR);
            headline = in.readParcelable(Headline.class.getClassLoader());
        }

        public static final Creator<Doc> CREATOR = new Creator<Doc>() {
            @Override
            public Doc createFromParcel(Parcel in) {
                return new Doc(in);
            }

            @Override
            public Doc[] newArray(int size) {
                return new Doc[size];
            }
        };

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public List<Multimedia> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(List<Multimedia> multimedia) {
            this.multimedia = multimedia;
        }


        public Headline getHeadline() {
            return headline;
        }

        public void setHeadline(Headline headline) {
            this.headline = headline;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(webUrl);
            dest.writeTypedList(multimedia);
            dest.writeParcelable(headline, flags);
        }
    }
}
