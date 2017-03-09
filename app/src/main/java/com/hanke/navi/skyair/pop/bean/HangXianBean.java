package com.hanke.navi.skyair.pop.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Asus on 2017/1/5.
 */
public class HangXianBean implements Parcelable {

    public String hangxian;//航线

    public String getHangxian() {
        return hangxian;
    }

    public void setHangxian(String hangxian) {
        this.hangxian = hangxian;
    }

    @Override
    public String toString() {
        return "HangXianBean{" +
                "hangxian='" + hangxian + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hangxian);
    }

    public HangXianBean() {
    }

    protected HangXianBean(Parcel in) {
        this.hangxian = in.readString();
    }

    public static final Parcelable.Creator<HangXianBean> CREATOR = new Parcelable.Creator<HangXianBean>() {
        @Override
        public HangXianBean createFromParcel(Parcel source) {
            return new HangXianBean(source);
        }

        @Override
        public HangXianBean[] newArray(int size) {
            return new HangXianBean[size];
        }
    };
}
