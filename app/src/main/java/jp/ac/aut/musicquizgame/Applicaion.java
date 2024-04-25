package jp.ac.aut.musicquizgame;

import android.app.Application;

import androidx.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Applicaion extends Application{
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {

        return null;
    }
}
