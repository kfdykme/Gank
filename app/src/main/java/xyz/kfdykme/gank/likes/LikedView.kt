package xyz.kfdykme.gank.likes

import android.view.*

import io.realm.Realm
import xyz.kfdykme.gank.*
import xyz.kfdykme.gank.bean.*
import java.util.*
import android.support.v7.app.*
import android.content.*
import android.support.v7.widget.*
import xyz.kfdykme.gank.adapter.*

public class LikedView(val parent: ViewGroup) {
    private val mRecyclerView: RecyclerView

    private var adapter: RecyclerViewAdapter2Like? = null

    private var mLikes: List<Likes>? = null

    var view: View? = null

    var presenter: LikedPresenter? = null

    init {
        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_liked, parent, false)
        //initRecyclerView
        mRecyclerView = view!!.findViewById<View>(R.id.dialog_fragment_like_recyclerview) as RecyclerView
        val manager = LinearLayoutManager(parent.context)
        manager.orientation = OrientationHelper.VERTICAL
        mRecyclerView.layoutManager = manager
        mRecyclerView.itemAnimator = DefaultItemAnimator()


        mLikes = Realm.getDefaultInstance().where(Likes::class.java).findAll()

        if (mLikes != null) {
            addViews(mLikes)
        }


    }


    fun attach() {
        parent!!.removeAllViews()
        parent!!.addView(view)
    }

    fun deAttach() {
        parent!!.removeView(view)
    }


    fun addViews(likes: List<Likes>?) {


        adapter = RecyclerViewAdapter2Like(parent!!.context, likes)

        //set listener
        adapter!!.setOnItemClickListener { position ->

            presenter?.openWebDow(adapter!!.mLikes[position])

            deAttach()
        }

        adapter!!.setOnItemLongClickListener { position ->
            //create a dialog to ask is sure
            val builder = AlertDialog.Builder(parent!!.context)
            builder.setTitle("Detele this record?")
                    .setPositiveButton("Yes") { p1, p2 ->
                        Realm.getDefaultInstance()
                                .executeTransaction { realm ->
                                    adapter!!.mLikes[position].deleteFromRealm()
                                    mLikes = realm.where(Likes::class.java).findAll()
                                    addViews(mLikes)
                                }
                    }.setNegativeButton("No", null)
            builder.create().show()
        }

        mRecyclerView.adapter = adapter
    }


}
