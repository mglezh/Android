package com.mglezh.todolistfragments.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cursomovil on 23/03/15.
 */

public class ToDo implements Parcelable {
    private String  task;
    private Date created;
    private int mData;

    public ToDo(String task) {
        this.task = task;
        this.created = new Date();
    }

    public ToDo(String task, Date created) {
        this.task = task;
        this.created = created;
    }

    public String getTask() {
        return task;
    }

    public Date getCreated() {return this.created; }

    public String getCreatedFormated() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
        return sdf.format(this.created);
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(task);
        dest.writeLong(created.getTime());
    }

    public static final Parcelable.Creator<ToDo> CREATOR
            = new Parcelable.Creator<ToDo>() {
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

    private ToDo(Parcel in) {
        task = in.readString();
        created = new Date(in.readLong());
    }

}
