package com.example.qbook.backyardbotany;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemData implements Parcelable {
    public String name;
    public String info;
    public String tips;
    public String imgFilepath;

    public ItemData(String name, String info, String tips, String imgFilepath) {
        this.name = name;
        this.info = info;
        this.tips = tips;
        this.imgFilepath = imgFilepath;
    }

    protected ItemData(Parcel in) {
        name = in.readString();
        info = in.readString();
        tips = in.readString();
        imgFilepath = in.readString();
    }

    public static final Creator<ItemData> CREATOR = new Creator<ItemData>() {
        @Override
        public ItemData createFromParcel(Parcel in) {
            return new ItemData(in);
        }

        @Override
        public ItemData[] newArray(int size) {
            return new ItemData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.info);
        dest.writeString(this.tips);
        dest.writeString(this.imgFilepath);
    }
}

