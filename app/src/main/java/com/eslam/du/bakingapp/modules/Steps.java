package com.eslam.du.bakingapp.modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Steps implements Parcelable {
    @Expose
    @SerializedName("thumbnailURL")
    private String thumbnailURL;
    @Expose
    @SerializedName("videoURL")
    private String videoURL;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("shortDescription")
    private String shortDescription;
    @Expose
    @SerializedName("id")
    private int id;

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnailURL);
        dest.writeString(this.videoURL);
        dest.writeString(this.description);
        dest.writeString(this.shortDescription);
        dest.writeInt(this.id);
    }

    public Steps() {
    }

    protected Steps(Parcel in) {
        this.thumbnailURL = in.readString();
        this.videoURL = in.readString();
        this.description = in.readString();
        this.shortDescription = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
