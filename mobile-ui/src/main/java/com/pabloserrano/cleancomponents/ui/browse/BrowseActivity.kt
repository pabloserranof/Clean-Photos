package com.pabloserrano.cleancomponents.ui.browse

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.pabloserrano.cleancomponents.ui.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import com.pabloserrano.cleancomponents.presentation.browse.BrowsePhotosViewModel
import com.pabloserrano.cleancomponents.presentation.browse.BrowsePhotosViewModelFactory
import com.pabloserrano.cleancomponents.presentation.data.Resource
import com.pabloserrano.cleancomponents.presentation.data.ResourceState
import com.pabloserrano.cleancomponents.presentation.model.PhotoView
import com.pabloserrano.cleancomponents.ui.mapper.PhotosUIMapper
import com.pabloserrano.cleancomponents.ui.widget.empty.EmptyListener
import com.pabloserrano.cleancomponents.ui.widget.error.ErrorListener
import javax.inject.Inject

class BrowseActivity: AppCompatActivity() {

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: PhotosUIMapper
    @Inject lateinit var viewModelFactory: BrowsePhotosViewModelFactory
    private lateinit var browsePhotosViewModel: BrowsePhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)

        browsePhotosViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowsePhotosViewModel::class.java)

        setupBrowseRecycler()
        setupViewListeners()
    }

    override fun onStart() {
        super.onStart()
        browsePhotosViewModel.getCuratedPhotos().observe(this,
                Observer<Resource<List<PhotoView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message) })

    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
    }

    private fun handleDataState(resourceState: ResourceState, data: List<PhotoView>?,
                                message: String?) {

        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<PhotoView>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data!= null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(photoList: List<PhotoView>) {
        browseAdapter.photos = photoList.map { mapper.mapToViewModel(it) }
        browseAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            browsePhotosViewModel.fetchCuratedPhotos()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            browsePhotosViewModel.fetchCuratedPhotos()
        }
    }
}