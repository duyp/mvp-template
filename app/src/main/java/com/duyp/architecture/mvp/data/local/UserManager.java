package com.duyp.architecture.mvp.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.dagger.component.UserComponent;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.GithubService;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;

/**
 * Created by Duy Pham on 5/19/17.
 * Injected class for managing User by using User Component
 */

@Singleton
public class UserManager {

    // debug tag
    private static final String TAG = "UserManager";

    @NonNull
    protected Context mContext;

    @NonNull
    protected Realm mRealm;

    @Nullable
    protected User mUser;

    // user data store
    @NonNull
    protected final UserRepo mUserRepo;

    // user component
    @Nullable
    protected UserComponent mUserComponent;

    @NonNull
    protected EventBus mEventBus;

    @NonNull
    protected GithubService mGithubService;

    @Inject
    public UserManager(@NonNull Context context, @NonNull UserRepo userRepo, @NonNull Realm realm,
                       @NonNull EventBus eventBus, @NonNull GithubService service) {
        this.mContext = context;
        this.mUserRepo = userRepo;
        this.mEventBus = eventBus;
        this.mRealm = realm;
        this.mGithubService = service;
    }

//    protected UserComponent createUserComponent(@NonNull User user, @NonNull String token) {
//        return InjectionHelper.getAppComponent(mContext).getUserComponent(new UserModule(user, token));
//    }
//
//    public void setUser(@NonNull User user) {
//        this.mUser = user;
//        userDataStore.setUser(user);
//    }
//
//    public User getUser() {
//        if (mUser != null) {
//            return mUser;
//        }
//        return userDataStore.getUser();
//    }
//
//    /**
//     * Start user session in test environment
//     * @param user user info
//     * @param token user token
//     */
//    private void startUserSession(@NonNull User user, String token) {
//        if (userComponent != null) {
//            stopUserSession();
//        }
//        userComponent = createUserComponent(user, token);
//        setUser(user);
//        signInFirebase(user);
//        Log.d(TAG, "User session started!");
//        Log.d("token", token);
//    }
//
//    // Stop user session (clear user data from both memory and shared pref)
//    private void stopUserSession() {
//        userComponent = null;
//        mUser = null;
//        userDataStore.clearUser();
//        Log.d(TAG, "User session stopped!");
//    }
//
//    // check if has saved user -> start new session
//    // return true if has saved user
//    public boolean checkForSavedUserAndStartSessionIfHas() {
//        if (userComponent == null) {
//            User savedUser = userDataStore.getUser();
//            if (savedUser != null) {
//                startUserSession(savedUser, userDataStore.getUserToken());
//                return true;
//            }
//            return false;
//        }
//        return true;
//    }
//
//    //Update current user data by new user if are the same
//    public void updateUserIfEquals(User newUser) {
//        if (mUser != null && mUser.equals(newUser)) {
//            if (TextUtils.isEmpty(newUser.getFirebase_token())) {
//                newUser.setFirebase_token(mUser.getFirebase_token());
//                newUser.setFirebase_uid(mUser.getFirebase_uid());
//            }
//            if (TextUtils.isEmpty(newUser.getToken())) {
//                newUser.setToken(mUser.getToken());
//            }
//            setUser(newUser);
//            mEventBus.post(new Events.UserUpdateEvent());
//            Log.d(TAG, "Update user success!");
//        }
//    }
//
//    /**
//     * Save current User on memory to storage
//     */
//    public void saveCurrentUser() {
//        userDataStore.setUser(mUser);
//    }
//
//    /*
//    * Update current user account from server
//    * All errors will be ignored
//     */
//    public void updateUser() {
//        if (mUser != null && userComponent != null) {
//            Log.d(TAG, "Retrieving user...(" + mUser.getAccount_id() + ")");
//            userComponent.getUserService().getAccount(
//                    mUser.getAccount_id(), ConstantApi.SECURITY_GUARD
//
//            ).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
//                    .map(BaseEntity::getData)
//                    .doOnNext(this::updateUserIfEquals)
//                    .doOnError(throwable -> Log.d(TAG, ErrorEntity.getError(throwable).getErrorText()))
//                    .subscribe();
//        }
//    }
//
//    /**
//     * Save user to shared preference and start user session
//     * @param user user after logging in
//     */
//    public void doAfterLogin(@NonNull User user) {
//        String token = user.getToken();
//        userDataStore.setUserToken(token);
//        startUserSession(user, token);
//    }
//
//    // logout user
//    public void logout() {
//        reset();
//
//        // send logout broadcast (tutor service will stopSelf)
//        mEventBus.post(new Events.LogoutEvent());
//
//        // navigate user to Login Page
//        Intent intent = new Intent(mContext, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//    }
//
//    public void reset() {
//        logOutApi();
//
//        // Firebase
//        if (userComponent != null) {
//            userComponent.getUserFirebaseManager().stopSession();
//        }
//        unRegisterFirebaseFcm();
//        FirebaseAuth.getInstance().signOut();
//
//        // stop user session
//        stopUserSession();
//
//        // cancel all notification
//        NotificationManager notifManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (notifManager != null) {
//            notifManager.cancelAll();
//        }
//
//        // clear data (shared preference and realm)
//        Functions.reset(mContext);
//        realmHelper.clearAll();
//    }
//
//    // send firebase FCM token to our server
//    public void registerFirebaseFcm() {
//        if (userComponent != null) {
//            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            String deviceId = Functions.getDeviceId(mContext);
//            Log.d("TAG_FIREBASE_FCM_TOKEN", "sending FCM token: " + refreshedToken);
//            userComponent.getUserService().addFcmToken(
//                    ConstantApi.SECURITY_GUARD, refreshedToken, deviceId, "2"
//            ).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
//                    .subscribe(new BaseObserver<BaseEntity>() {
//                        @Override
//                        public void onNext(@io.reactivex.annotations.NonNull BaseEntity entity) {
//                            Log.d("TAG_FIREBASE_FCM_TOKEN", "FCM token sent!");
//                        }
//                    });
//        }
//    }
//
//    // remove firebase FCM token on our server
//    public void unRegisterFirebaseFcm() {
//        if (userComponent != null) {
//            String deviceId = Functions.getDeviceId(mContext);
//            userComponent.getUserService().deleteFcmToken(
//                    ConstantApi.SECURITY_GUARD, deviceId
//            ).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
//                    .subscribe(new BaseObserver<BaseEntity>() {
//                        @Override
//                        public void onNext(@io.reactivex.annotations.NonNull BaseEntity entity) {
//                            Log.d("TAG_FIREBASE_FCM_TOKEN", "FCM token deleted!");
//                        }
//                    });
//        }
//    }
//
//    // sign in firebase with custom token received from api
//    private void signInFirebase(final @NonNull User user) {
//        if (!TextUtils.isEmpty(user.getFirebase_token())) {
//            final FirebaseAuth mFirebaseAuthen = FirebaseAuth.getInstance();
//            mFirebaseAuthen.signInWithCustomToken(user.getFirebase_token()).addOnCompleteListener(task -> {
//            FirebaseUser firebaseUser = mFirebaseAuthen.getCurrentUser();
//            if (firebaseUser != null) {
//                String uid = firebaseUser.getUid();
//                if (!TextUtils.isEmpty(uid)) {
//                    Log.d("TAG_FIREBASE", "Firebase SignIn completed with uid:" + uid);
//                    user.setFirebase_uid(uid);
//                    updateUserIfEquals(user);
//                    if (userComponent != null) {
//                        userComponent.getUserFirebaseManager().startSession(uid);
//                    }
//                    registerFirebaseFcm();
//                    mEventBus.post(new Events.FirebaseSignInEvent(uid));
//                }
//            }
//            });
//        }
//    }
//
//    // log out from our server
//    // all error will be ignored
//    private void logOutApi() {
//        if (userComponent != null && mUser != null) {
//            userComponent.getUserService().logout(
//                    ConstantApi.SECURITY_GUARD, mUser.getAccount_id(), Functions.getSerialNo(mContext)
//            ).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
//                    .subscribe(new BaseObserver<BaseEntity>() {});
//        }
//    }
//
//    // update new user credit received from firebase to user data
//    public void updateUserCredits(String newCredit) {
//        if (mUser != null) {
//            mUser.setK_user_credits(newCredit);
//            userDataStore.setUser(mUser);
//            EventBus.getDefault().post(new Events.UserCreditChangeEvent(newCredit));
//        }
//    }
}