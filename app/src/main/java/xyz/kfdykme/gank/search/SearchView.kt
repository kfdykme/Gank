package xyz.kfdykme.gank.search

import android.os.*
import android.support.design.widget.Snackbar
import android.support.v4.app.*
import android.view.*
import android.widget.*
import android.widget.AdapterView.*

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import xyz.kfdykme.gank.*
import xyz.kfdykme.gank.bean.*

import java.util.*

import android.view.View.OnClickListener
import android.widget.TextView.*
import android.support.v7.widget.*
import xyz.kfdykme.gank.adapter.*
import xyz.kfdykme.gank.base.OnDetachListener

class SearchView(val parent:ViewGroup) {

    private var mSpinner: Spinner? = null

    private var mButton: Button? = null

    private var mEditText: EditText? = null

    private var mRecyclerView: RecyclerView? = null

    private var mRecyclerViewAdpter: RecyclerViewAdapter? = null


    private var mResults: MutableList<DataEntity.result>? = null


    private val page = 1

    private var type = "all"

    private var lastKeyNType = "nullnull"

    private val SEARCH = "search"

    //private final String UPDATE = "update";

    private val LOADING = "loading"


    //check is Search a new key or search more data
    private val isSearch: Boolean
        get() = lastKeyNType != mEditText!!.text.toString() + type


    val view:View

    var presenter:SearchPresenter? = null

    init {
        view = LayoutInflater.from(parent.context).inflate(R.layout.view_search,parent,false)
        initViews(view)

        //reload data at restart
        if (mResults != null) {
            addViews(mResults)
        }
    }



    fun onAttach() {
        parent!!.removeAllViews()
        parent!!.addView(view)
    }

    fun onDetach() {
        parent!!.removeView(view)
    }

    private fun initViews(view: View) {


        //initial RecyclerView
        mRecyclerView = view.findViewById<View>(R.id.dialog_fargment_search_recyclerview) as RecyclerView
        val manager = LinearLayoutManager(parent.context)
        manager.orientation = OrientationHelper.VERTICAL
        mRecyclerView!!.layoutManager = manager
        mRecyclerView!!.itemAnimator = DefaultItemAnimator()


        //initial mEditText
        mEditText = view.findViewById<View>(R.id.searchEditText) as EditText

        //no action now ??????????????
        mEditText!!.setOnEditorActionListener { p1, p2, p3 ->
            doSearch()
            false
        }

        //initial searchButton
        mButton = view.findViewById<View>(R.id.searchButton) as Button
        mButton!!.setOnClickListener { doSearch() }

        //initial spinner
        mSpinner = view.findViewById<View>(R.id.searchSpinner) as Spinner
        val spinnerStrings = arrayOf("all", "Android", "iOS", "前端")
        val mSpinnerAdapter = ArrayAdapter(parent.context,
                android.R.layout.simple_list_item_1,
                spinnerStrings)
        mSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        mSpinner!!.adapter = mSpinnerAdapter
        mSpinner!!.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(p1: AdapterView<*>, view: View, p3: Int, p4: Long) {
                val mTv = view as TextView
                type = mTv.text.toString()
            }

            override fun onNothingSelected(p1: AdapterView<*>) {

            }


        }


    }


    private fun doSearch() {
        //as a tip to tell it is loading data from net
        mButton!!.text = LOADING
        val key = mEditText!!.text.toString()

        Retrofit.Builder()
                .baseUrl(GankService.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(GankService::class.java)
                .doSerach(key, type, GankService.DEFAULT_NUMBER_PER_PAGE, page.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { dataEntity ->
                    Snackbar.make(mButton!!,"Search finished.",Snackbar.LENGTH_SHORT).show()
                    if (!isSearch) {
                        mResults!!.addAll(dataEntity.results)
                    } else {
                        mResults = dataEntity.results
                    }

                    addViews(mResults)

                    lastKeyNType = mEditText!!.text.toString() + type
                }

    }


    fun addViews(results: List<DataEntity.result>?) {

        //set search TextView's text to loading
        mButton!!.text = LOADING

        if (mRecyclerViewAdpter == null) {
            //initRecyclerViewAdapter
            mRecyclerViewAdpter = RecyclerViewAdapter(parent.context, results)

            mRecyclerViewAdpter!!.setOnItemClickListener { position ->

                presenter?.openWebDow( mRecyclerViewAdpter!!.mResults[position])


            }

            //setAdapter
            mRecyclerView!!.adapter = mRecyclerViewAdpter

        } else {
            mRecyclerViewAdpter!!.addData(results)
        }

        mButton!!.text = SEARCH


    }


}
