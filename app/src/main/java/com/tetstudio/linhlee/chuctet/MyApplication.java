package com.tetstudio.linhlee.chuctet;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.mz.ZAndroidSystemDK;
import com.tetstudio.linhlee.chuctet.database.DatabaseAdapter;

/**
 * Created by lequy on 1/18/2017.
 */

public class MyApplication extends Application {
    private static DatabaseAdapter mDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        ZAndroidSystemDK.initApplication(this, this.getApplicationContext().getPackageName());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mDbHelper = new DatabaseAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();
    }

    public static DatabaseAdapter getDatabase() {
        return mDbHelper;
    }
}
