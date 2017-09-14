package com.duyp.architecture.mvp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.data.model.base.BaseResponse;
import com.duyp.architecture.mvp.utils.DateConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 9/7/17.
 * Github User model
 */

@Getter
@Setter
@Entity
public class User extends BaseResponse {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("login")
    @Expose
    public String login;
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;
    @SerializedName("gravatar_id")
    @Expose
    public String gravatarId;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("html_url")
    @Expose
    public String htmlUrl;
    @SerializedName("followers_url")
    @Expose
    public String followersUrl;
    @SerializedName("following_url")
    @Expose
    public String followingUrl;
    @SerializedName("gists_url")
    @Expose
    public String gistsUrl;
    @SerializedName("starred_url")
    @Expose
    public String starredUrl;
    @SerializedName("subscriptions_url")
    @Expose
    public String subscriptionsUrl;
    @SerializedName("organizations_url")
    @Expose
    public String organizationsUrl;
    @SerializedName("repos_url")
    @Expose
    public String reposUrl;
    @SerializedName("events_url")
    @Expose
    public String eventsUrl;
    @SerializedName("received_events_url")
    @Expose
    public String receivedEventsUrl;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("site_admin")
    @Expose
    public Boolean siteAdmin;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("blog")
    @Expose
    public String blog;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("hireable")
    @Expose
    public Boolean hireable;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("public_repos")
    @Expose
    public Long publicRepos;
    @SerializedName("public_gists")
    @Expose
    public Long publicGists;
    @SerializedName("followers")
    @Expose
    public Long followers;
    @SerializedName("following")
    @Expose
    public Long following;
    @TypeConverters(DateConverter.class)
    @SerializedName("created_at")
    @Expose
    public Date createdAt;
    @TypeConverters(DateConverter.class)
    @SerializedName("updated_at")
    @Expose
    public Date updatedAt;
    @SerializedName("private_gists")
    @Expose
    public Long privateGists;
    @SerializedName("total_private_repos")
    @Expose
    public Long totalPrivateRepos;
    @SerializedName("owned_private_repos")
    @Expose
    public Long ownedPrivateRepos;
    @SerializedName("disk_usage")
    @Expose
    public Long diskUsage;
    @SerializedName("collaborators")
    @Expose
    public Long collaborators;
    @SerializedName("two_factor_authentication")
    @Expose
    public Boolean twoFactorAuthentication;

    public boolean equals(@Nullable User user) {
        return user != null && id.equals(user.getId());
    }
    // TODO: 9/10/17 Create type converter for Plan class
//    @SerializedName("plan")
//    @Expose
//    public Plan plan;
}
