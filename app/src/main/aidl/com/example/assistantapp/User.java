package com.example.assistantapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ouyangym on 2016/10/25.
 */
public class User implements Parcelable {
    private String mName;

    private int mAge;

//    public User() {
//    }

    public User(String name, int age) {
        mName = name;
        mAge = age;
    }

    public User(Builder builder) {
        setName(builder.mName);
        setAge(builder.mAge);
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mAge);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new Builder()
                    .name(parcel.readString())
                    .age(parcel.readInt())
                    .build();
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    @Override
    public String toString() {
        return "User_name : " + this.getName() + "   age : " + this.getAge();
    }

    public static class Builder {
        private String mName;

        private int mAge;

        public Builder name(String name) {
            mName = name;
            return this;
        }

        public Builder age(int age) {
            mAge = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
