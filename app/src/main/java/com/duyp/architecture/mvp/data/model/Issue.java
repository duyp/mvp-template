package com.duyp.architecture.mvp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.duyp.architecture.mvp.data.model.def.IssueStates;
import com.duyp.architecture.mvp.utils.roomConverters.DateConverter;
import com.duyp.architecture.mvp.utils.roomConverters.LabelConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 9/17/17.
 */


@Getter
@Setter
@Entity
public class Issue {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    Long id;

    @ColumnInfo(name = "repo_id")
    transient Long repoId;

    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("repository_url")
    @Expose
    String repositoryUrl;
    @SerializedName("labels_url")
    @Expose
    String labelsUrl;
    @SerializedName("comments_url")
    @Expose
    String commentsUrl;
    @SerializedName("events_url")
    @Expose
    String eventsUrl;
    @SerializedName("html_url")
    @Expose
    String htmlUrl;
    @SerializedName("number")
    @Expose
    Long number;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("user")
    @Expose
    @Embedded(prefix = "user_")
    User user;
    @SerializedName("labels")
    @Expose
    @TypeConverters(LabelConverter.class)
    List<Label> labels = null;
    @SerializedName("state")
    @Expose
    @IssueStates
    String state;
    @SerializedName("locked")
    @Expose
    Boolean locked;
    @SerializedName("assignee")
    @Expose
    @Embedded(prefix = "assignee_")
    User assignee;
//    @SerializedName("assignees")
//    @Expose
//    List<User> assignees = null;
//    @SerializedName("milestone")
//    @Expose
//    Object milestone;
    @SerializedName("comments")
    @Expose
    Long comments;
    @SerializedName("created_at")
    @Expose
    @TypeConverters(DateConverter.class)
    Date createdAt;
    @SerializedName("updated_at")
    @Expose
    @TypeConverters(DateConverter.class)
    Date updatedAt;
    @SerializedName("closed_at")
    @Expose
    @TypeConverters(DateConverter.class)
    Date closedAt;
    @SerializedName("author_association")
    @Expose
    String authorAssociation;
    @SerializedName("body")
    @Expose
    String body;
}
