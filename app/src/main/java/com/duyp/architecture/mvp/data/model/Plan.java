package com.duyp.architecture.mvp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Plan {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("space")
    @Expose
    public Long space;
    @SerializedName("collaborators")
    @Expose
    public Long collaborators;
    @SerializedName("private_repos")
    @Expose
    public Long privateRepos;
//
//    public static class Converter {
//        @TypeConverter
//        Plan toPlan(String json) {
//
//        }
//    }
}