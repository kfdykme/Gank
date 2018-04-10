package xyz.kfdykme.gank.search

import xyz.kfdykme.gank.GankActivity
import xyz.kfdykme.gank.base.BasePresenter
import xyz.kfdykme.gank.base.OnDetachListener
import xyz.kfdykme.gank.bean.DataEntity
import xyz.kfdykme.gank.bean.result

/**
 * Created by wimkf on 2018/4/10.
 */
class SearchPresenter(val view:SearchView,val l:OnDetachListener):BasePresenter{

    init {
        view.presenter =this
    }
    fun openWebDow(result: DataEntity.result){
        GankActivity.gankPresenter.openWebDow(result)
        detach()
    }

    override fun attach() {
        view.onAttach()
    }

    override fun detach() {
        l.onPresenterDetach()
        view.onDetach()
   }

}