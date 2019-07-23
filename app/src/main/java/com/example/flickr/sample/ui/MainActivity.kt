package com.example.flickr.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.example.flickr.R
import com.example.flickr.sample.adapter.PhotosAdapter
import com.example.flickr.sample.models.ClientRespBean
import com.example.flickr.sample.models.PhotoItem
import com.example.flickr.sample.models.PhotoResult
import com.example.flickr.sample.utils.AppConstants
import com.example.newyorkarticle.Retrofit2Client
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }


    /**
     * Sets the recycler view with Scroll Listener for pagination support
     */
    private fun setupRecyclerView() {

        val gridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        item_recycler_view.layoutManager = gridLayoutManager
        showBottomLoading()
        isLoading = true
        callGetPhotosApi(page)

        //listen to scrolling, and calculate page number to load new items
        //custom support for pagination, improves performance
        val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItems = IntArray(3)
                val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems)[0]
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                        page++;
                        isLoading = true
                        showProgressDialog()
                        callGetPhotosApi(page)
                    }
                }
            }
        }

        //set the scroll listener
        item_recycler_view.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private var isLoading : Boolean  = false
    private var isLastPage : Boolean  = false
    private var page : Int = 1
    private var totalPages : Int = 0
    private  lateinit var adapter : PhotosAdapter
    val photosList: MutableList<PhotoItem> = ArrayList<PhotoItem>()

    private fun callGetPhotosApi( page : Int) {

        val retrofit = Retrofit2Client.getInstance(getApplicationContext())
        val call = retrofit.apiInterface.getSearchedPhotos(AppConstants.METHOD_SEARCH,AppConstants.FORMAT,AppConstants.API_KEY,
            AppConstants.DEFAULT_QUERY,AppConstants.NOCALLBACK,AppConstants.SAFESEARCH,page)
        call.enqueue(object : Callback<ClientRespBean> {
            override fun onResponse(call: Call<ClientRespBean>, response: Response<ClientRespBean>) {
                if (response.isSuccessful()) {
                    isLoading = false
                    dismissProgressDialog()
                    val respBean = response.body()
                    if (respBean!!.photos!=null) {
                        val gson = Gson()
                        val clientResponseStr = gson.toJson(respBean.photos)
                        val resBean = gson.fromJson<PhotoResult>(clientResponseStr, PhotoResult::class.java)

                        if(resBean != null ){
                            photosList.addAll(resBean.photo)
                            totalPages = resBean.pages

                            if(page == totalPages){
                                isLastPage = true
                            }
                            item_recycler_view.visibility = View.VISIBLE
                            if(page == 1){
                                 adapter = PhotosAdapter(this@MainActivity,photosList)
                                item_recycler_view.adapter = adapter
                            }else{
                                item_recycler_view.adapter!!.notifyItemRangeInserted(item_recycler_view.adapter!!.itemCount,photosList.size)
                            }

                        }


                    } else if (respBean.photos== null) {
                        onError(page)
                    } else {
                        onError(page)
                    }

                } else {
                    onError(page)
                }
            }

            override fun onFailure(call: Call<ClientRespBean>, t: Throwable) {
                if (t is IOException) {
                    onError(page)
                } else {
                    onError(page)
                }
            }
        })
    }


    /**
     * Updates the list with photos data
     */
     fun refreshItemList() {
//        itemAdapter?.notifyDataSetChanged()
        adapter!!.notifyDataSetChanged()
    }

    /**
     *  Shows empty photos list messages
     */
     fun showEmptyListUI() {
        item_recycler_view.visibility = View.GONE
        empty_list_text.visibility = View.VISIBLE
    }

    /**
     *  Shows bottom loading when fetching new elements
     */
     fun showBottomLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    /**
     * Hide the bottom loading
     */
     fun hideBottomLoading() {
        progress_bar.visibility = View.GONE
    }



     fun initItemList(photoItemList: List<PhotoItem>) {
    }



     fun showProgressDialog() {
        progress_bar.visibility = View.VISIBLE
    }

     fun dismissProgressDialog() {
        progress_bar.visibility = View.GONE
    }

     fun showToastMessage(message: String?) {
         Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
    }

     fun showToastMessage(stringResourceId: Int) {
    }

     fun showSnackBarMessage(message: String?) {
    }

     fun showSnackBarMessage(stringResourceId: Int) {
    }

     fun onError(message: String?) {
    }

     fun onError(resId: Int) {
         if(page ==1)
             showEmptyListUI()
         else
             showToastMessage(resources.getString(R.string.no_more_photos_found))
    }
}
