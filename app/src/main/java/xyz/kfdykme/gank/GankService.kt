package xyz.kfdykme.gank

import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import xyz.kfdykme.gank.bean.DataEntity

/**
 * Created by wimkf on 2018/4/10.
 */
interface GankService{

    companion object {
        val BASEURL="http://gank.io/api/"
        val DEFAULT_NUMBER_PER_PAGE = "15"
    }



    @GET("data/{type}/{perPage}/{page}")
    fun getActivity(
            @Path("type")  type:String,
            @Path("perPage")  perPage:String,
            @Path("page") page:String
    ):Observable<DataEntity>

    @GET("search/query/{key}/category/{type}/count/{perPage}/page/{pageNum}")
    fun doSerach(
            @Path("key") key:String,
            @Path("type") type:String,
            @Path("perPage") perPage:String,
            @Path("pageNum") pageNum:String
    ):Observable<DataEntity>
}