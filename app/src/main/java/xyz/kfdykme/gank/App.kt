package xyz.kfdykme.gank

import android.app.Application
import io.realm.Realm

/**
 * Created by wimkf on 2018/4/10.
 */
class App:Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}