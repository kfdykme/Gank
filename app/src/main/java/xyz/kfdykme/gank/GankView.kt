package xyz.kfdykme.gank

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.kfdykme.gank.view.ViewPagerIndicator
import xyz.kfdykme.gank.web.WebDow
import java.util.*

/**
 * Created by wimkf on 2018/4/10.
 */

class GankView(val parent: ViewGroup) {

    var presenter:GankPresenter? = null

    internal var view: View? = null

    var vp: ViewPager? = null

    var mAdapter: FragmentPagerAdapter? = null

    var mContents = ArrayList<ArtcleListFragment>()

    val mTitles = Arrays.asList("Android", "iOS", "前端", "福利")

    var wv:WebDow? = null

    init {
        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_gank,parent,false)

        initDatas(parent.context as FragmentActivity)
        initViews()
    }


    fun attach(){
        parent.removeAllViews()
        parent.addView(view)

    }




    fun initDatas(context: FragmentActivity) {
        for (title in mTitles) {
            val fragment = ArtcleListFragment.newInstance(title)
            fragment.title = title
            mContents.add(fragment)

        }


        mAdapter = object : FragmentPagerAdapter(context.supportFragmentManager) {

            override fun getItem(p1: Int): android.support.v4.app.Fragment {

                return mContents[p1]
            }


            override fun getCount(): Int {

                return mContents.size
            }


        }


    }

    fun initViews(){

        vp = view!!.findViewById(R.id.mViewPager)
        wv = view!!.findViewById<WebDow>(R.id.mWebDow)

        val vpindicator = view!!.findViewById<ViewPagerIndicator>(R.id.mViewPagerIndicator)

        vpindicator!!.setVisibleTabCount(4)
        vpindicator!!.setTabItems(mTitles)
        vp!!.setAdapter(mAdapter)


        vpindicator!!.setViewPager(vp, 0)

    }
}
