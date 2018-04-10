package xyz.kfdykme.gank.likes

import xyz.kfdykme.gank.GankActivity
import xyz.kfdykme.gank.base.BasePresenter
import xyz.kfdykme.gank.base.OnDetachListener
import xyz.kfdykme.gank.bean.Likes

/**
 * Created by wimkf on 2018/4/10.
 */
class LikedPresenter(val view: LikedView,val l:OnDetachListener):BasePresenter{



    init {
        view.presenter = this
    }
    override fun attach() {
        view.attach()
    }

    fun openWebDow(liked: Likes){
        GankActivity.gankPresenter.openWebDow(liked)

        detach()
    }

    override fun detach() {
        l?.onPresenterDetach()
        view.deAttach()
    }
}