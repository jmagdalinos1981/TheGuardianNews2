package com.johnmagdalinos.android.newsworld.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A section object used to populate the Navigation Drawer in the Main Activity
 */

public class Section implements Parcelable {
    /** Member variables */
    private String section_id;

    private String title;

    /** Class constructor */
    public Section(String section_id, String title) {
        this.section_id = section_id;
        this.title = title;
    }

    /** Class constructor for a parcel */
    protected Section(Parcel source) {
        section_id = source.readString();
        title = source.readString();
    }

    /** Getters */
    public String getSection_id() {
        return section_id;
    }
    public String getTitle() {
        return title;
    }

    /** Setters */
    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(section_id);
        dest.writeString(title);
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}
