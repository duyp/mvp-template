package com.duyp.architecture.mvp.dagger;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.duyp.architecture.mvp.data.local.RealmDatabase;
import com.duyp.architecture.mvp.data.local.user.UserDataStore;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 9/21/17.
 * Test module for {@link com.duyp.architecture.mvp.dagger.module.DataModule}
 */

@Module
public class TestDataModule {

    private final Context mContext;

    public TestDataModule(@ApplicationContext Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    UserDataStore provideUserRepo(CustomSharedPreferences sharedPreferences, Gson gson, RealmDatabase database) {
        return new UserDataStore(sharedPreferences, gson, database);
    }

    @Provides
    @Singleton
    protected UserManager provideUserManager(@ApplicationContext Context context, UserDataStore userDataStore,
                                             EventBus eventBus, GithubService service) {
        return new UserManager(context, userDataStore, eventBus, service);
    }
    @Provides
    @Singleton
    protected Realm provideRealm() {
        Realm.init(mContext);
        RealmConfiguration configuration = new RealmConfiguration.Builder().inMemory().name("test-realm").build();
        return Realm.getInstance(configuration);
    }

    @Provides
    @Singleton
    RealmDatabase provideRealmDatabase(Realm realm) {
        return new RealmDatabase(realm);
    }
}
