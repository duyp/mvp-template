package com.duyp.architecture.mvp.data.remote;

import com.duyp.architecture.mvp.data.model.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by duypham on 9/7/17.
 * Github Rest API retrofit service (See https://developer.github.com/v3/)
 */

public interface GithubService {

    @GET("user")
    Observable<User> login(@Header(RemoteConstants.HEADER_AUTH) String basicToken);

    @GET("/users/{username}")
    Observable<User> getUser(@Path("username") String username);
}
