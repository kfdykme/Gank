package xyz.kfdykme.gank


import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import xyz.kfdykme.gank.base.BasePresenter
import xyz.kfdykme.gank.base.OnDetachListener
import xyz.kfdykme.gank.likes.LikedPresenter
import xyz.kfdykme.gank.likes.LikedView
import xyz.kfdykme.gank.search.SearchPresenter
import xyz.kfdykme.gank.search.SearchView
import java.util.*


class GankActivity : FragmentActivity(),OnDetachListener {

    private var mDrawerLayout: DrawerLayout? = null

    private var mNavigationView: NavigationView? = null

    var container:ViewGroup? = null

    var presenters :Stack<BasePresenter> = Stack()

    var cPresenter :BasePresenter? = null

    companion object {
        lateinit var gankPresenter:GankPresenter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initViews()

        gankPresenter = GankPresenter(
                GankView(container!!)
        )
        gankPresenter.attach()
        cPresenter = gankPresenter



    }


    override fun onPresenterDetach() {

        if(!presenters.empty()) {
            var p = presenters.pop()
            cPresenter = p
            cPresenter!!.attach()
        }
    }

    override fun onBackPressed() {

        if(cPresenter == gankPresenter) {
            gankPresenter.onBack()

        } else if(!presenters.empty()){
            var p = presenters.pop()
            cPresenter = p
            cPresenter!!.attach()
        } else
            super.onBackPressed()
    }



    private fun initViews() {
        mDrawerLayout = findViewById<View>(R.id.main_DrawerLayout) as DrawerLayout
        mNavigationView = findViewById<View>(R.id.nav_view) as NavigationView
        container = findViewById(R.id.container)


        mDrawerLayout!!.addDrawerListener(object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val width = drawerView.width
                container!!.translationX = width * slideOffset
                val paddingLeft = width.toDouble() * 0.618 * (1 - slideOffset).toDouble()
                mNavigationView!!.setPadding(paddingLeft.toInt(), 0, 0, 0)

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })

        mNavigationView!!.setNavigationItemSelectedListener { menuItem ->
            mDrawerLayout!!.closeDrawers()
            when (menuItem.itemId) {
                R.id.nav_liked -> {

                    presenters.push(cPresenter)
                    var likedPresenter = LikedPresenter(
                            LikedView(container!!),
                            this
                    )
                    likedPresenter.attach()
                    cPresenter = likedPresenter


                    Snackbar.make(container!!,"Liked",Snackbar.LENGTH_SHORT).show()

                    //Toast.makeText(this@GankActivity, "Selected Liked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_search -> {

                    presenters.push(cPresenter)
                    var searchPresenter = SearchPresenter(
                            SearchView(container!!),
                            this
                    )
                    searchPresenter.attach()
                    cPresenter = searchPresenter

                    Snackbar.make(container!!,"Selected Search",Snackbar.LENGTH_SHORT).show()
            }
            }

            false
        }

    }





}
