package com.adson.android.flickrapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.adson.android.flickrapp.Injection
import com.adson.android.flickrapp.R
import com.adson.android.flickrapp.RecyclerItemClickListener
import com.adson.android.flickrapp.adapters.PhotosAdapter
import com.adson.android.flickrapp.models.FlickrPhoto
import com.adson.android.flickrapp.viewmodels.FlickrViewModel
import kotlinx.android.synthetic.main.activity_main.*

internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"
class MainActivity : AppCompatActivity(), RecyclerItemClickListener.OnRecyclerClickListener {

    companion object {
        private const val PARAM_QUERY : String = "PARAM_QUERY"
    }

    private lateinit var viewModel : FlickrViewModel
    private val adapter = PhotosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
            .get(FlickrViewModel::class.java)

        initAdapter()
        setupScrollListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu?.findItem(R.id.app_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            var currentQuery = intent.getStringExtra(SearchManager.QUERY)
            fetchCurrentQuery(currentQuery)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PARAM_QUERY, viewModel.lastQueryValue())
    }

    /**
     * Scroll listener for the recyclerview that triggers lazy loading of photos.
     */
    private fun setupScrollListener() {
        val layoutManager = myRecyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
        myRecyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    private fun initAdapter() {
        myRecyclerView.adapter = adapter
        myRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(this, myRecyclerView, this))
        viewModel.photos.observe(this, Observer<PagedList<FlickrPhoto>> {
            searchLoading.visibility = View.GONE
            if (it?.size != 0) {
                myRecyclerView.visibility = View.VISIBLE
                adapter.submitList(it)
            }
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            searchLoading.visibility = View.GONE
            Toast.makeText(this, "Houston we have a problem $it", Toast.LENGTH_LONG).show()

        })
    }

    private fun fetchCurrentQuery(query: String) {
        if (query == null) return
        searchLoading.visibility = View.VISIBLE
        myRecyclerView.visibility = View.GONE
        viewModel.searchPhotos(query)

    }

    override fun onItemClick(view: View?, position: Int) {
        val photo = adapter.currentList?.get(position)
        if(photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }
}
