package com.duyp.architecture.mvp.data.remote;

import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by duypham on 9/7/17.
 * Github Rest API retrofit service (See https://developer.github.com/v3/)
 */

public interface GithubService {

    @GET("user")
    Observable<Response<User>> login(@Header(RemoteConstants.HEADER_AUTH) String basicToken);

    @GET("users/{username}")
    Observable<Response<User>> getUser(@Path("username") String username);

    @GET("repositories")
    Observable<Response<List<Repository>>> getAllPublicRepositories(@Query("since") @Nullable Long sinceRepoId);
}
