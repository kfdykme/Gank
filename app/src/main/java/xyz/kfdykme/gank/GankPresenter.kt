package xyz.kfdykme.gank

import android.view.KeyEvent
import android.view.View
import xyz.kfdykme.gank.base.BasePresenter
import xyz.kfdykme.gank.bean.DataEntity
import xyz.kfdykme.gank.bean.Likes

/**
 * Created by wimkf on 2018/4/10.
 */
class GankPresenter(val view:GankView):BasePresenter{
    override fun detach() {

    }

    init {
        view.presenter = this
    }

    override fun attach(){
        view.attach()
    }

    fun onBack():Boolean{

        if (view.wv?.webView!!.visibility != View.GONE) {
            if (view.wv?.webView!!.canGoBack()) {
                view.wv?.webView!!.goBack()
            } else {
                view.wv?.presenter!!.onGoBackWebDow()
            }
            return true
        } else {
            return false
        }

    }

    fun openWebDow(re: DataEntity.result){
        view.wv?.presenter!!.openWebDow(re);
    }

    fun openWebDow(likes: Likes ){
        view.wv?.presenter!!.openWebDow(likes);
    }
}