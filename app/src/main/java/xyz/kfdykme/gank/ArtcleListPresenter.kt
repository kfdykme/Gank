package xyz.kfdykme.gank

import retrofit2.Retrofit
import xyz.kfdykme.gank.bean.*
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArtcleListPresenter(private val mView: ArtcleListContract.View) : ArtcleListContract.Presenter {
    override fun attach() {

    }


    init {

        this.mView.setPresenter(this)

    }

    override fun detach() {


    }


    override fun loadArticle(type: String, page: Int) {

        Retrofit
                .Builder()
                .baseUrl(GankService.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(GankService::class.java)
                .getActivity(type,GankService.DEFAULT_NUMBER_PER_PAGE,page.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({date: DataEntity? ->
                    mView.addViews(date?.results)
                })



    }


}
