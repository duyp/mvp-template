package com.duyp.architecture.mvp.dagger.module;

import android.content.Context;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.duyp.architecture.mvp.data.local.dao.IssueDao;
import com.duyp.architecture.mvp.data.local.dao.IssueDaoImpl;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDaoImpl;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.local.user.UserRepo;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 9/18/17.
 * Data module provider
 */

@Module
public class DataModule {

    private final Context mContext;

    public DataModule(@ApplicationContext Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    UserRepo provideUserRepo(CustomSharedPreferences sharedPreferences, Gson gson) {
        return new UserRepo(sharedPreferences, gson);
    }

    @Provides
    @Singleton
    protected UserManager provideUserManager(@ApplicationContext Context context, UserRepo userDataStore,
                                             EventBus eventBus, GithubService service) {
        return new UserManager(context, userDataStore, eventBus, service);
    }
    @Provides
    @Singleton
    protected Realm provideRealm() {
        int schemaVersion = 1; // first version
        Realm.init(mContext);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(schemaVersion)
                .migration((realm, oldVersion, newVersion) -> {
                    // migrate realm here
                })
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    RepositoryDao provideRepositoryDao(Realm realm) {
        return new RepositoryDaoImpl(realm);
    }

    @Provides
    @Singleton
    IssueDao provideIssueDao(Realm realm) {
        return new IssueDaoImpl(realm);
    }
}