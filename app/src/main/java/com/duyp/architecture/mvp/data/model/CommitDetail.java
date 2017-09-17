package com.duyp.architecture.mvp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitDetail {

//    @SerializedName("author")
//    @Expose
//    private Author author;
    @SerializedName("committer")
    @Expose
    private Author committer;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("comment_count")
    @Expose
    private long commentCount;
}