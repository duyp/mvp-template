package com.duyp.architecture.mvp.dagger.component;

import android.content.Context;

import com.duyp.architecture.mvp.dagger.module.AppModule;
import com.duyp.architecture.mvp.dagger.module.NetworkModule;
import com.duyp.architecture.mvp.data.local.UserManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Duy Pham on 4/30/17.
 *
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {

    Context context();

    UserManager userManager();

//    AskTutorService askTutorService();

//    UserComponent getUserComponent(UserModule userModule);

}
