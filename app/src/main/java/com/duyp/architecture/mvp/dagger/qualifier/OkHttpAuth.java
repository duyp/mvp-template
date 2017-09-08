package com.duyp.architecture.mvp.dagger.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by duypham on 9/8/17.
 * Authorization header {@link okhttp3.OkHttpClient}
 */

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface OkHttpAuth {
}
