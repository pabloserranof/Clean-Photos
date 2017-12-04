package com.pabloserrano.cleancomponents.ui.browse

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import com.pabloserrano.cleancomponents.domain.model.PhotoDomain
import com.pabloserrano.cleancomponents.ui.R
import com.pabloserrano.cleancomponents.ui.test.TestApplication
import com.pabloserrano.cleancomponents.ui.test.util.PhotoFactory
import com.pabloserrano.cleancomponents.ui.test.util.RecyclerViewMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BrowseActivityTest {

    @Rule @JvmField
    val activity = ActivityTestRule<BrowseActivity>(BrowseActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubPhotoRepositoryGetPhotos(Flowable.just(PhotoFactory.makePhotoList(2)))
        activity.launchActivity(null)
    }

    @Test
    fun photosDisplay() {
        val photos = PhotoFactory.makePhotoList(1)
        stubPhotoRepositoryGetPhotos(Flowable.just(photos))
        activity.launchActivity(null)

        checkPhotoDetailsDisplay(photos[0], 0)
    }

    @Test
    fun photosAreScrollable() {
        val photos = PhotoFactory.makePhotoList(20)
        stubPhotoRepositoryGetPhotos(Flowable.just(photos))
        activity.launchActivity(null)

        photos.forEachIndexed { index, photo ->
            onView(withId(R.id.recycler_browse)).perform(RecyclerViewActions.
                    scrollToPosition<RecyclerView.ViewHolder>(index))
            checkPhotoDetailsDisplay(photo, index) }
    }

    private fun checkPhotoDetailsDisplay(photo: PhotoDomain, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_browse).atPosition(position))
                .check(matches(hasDescendant(withText(photo.id))))
    }

    private fun stubPhotoRepositoryGetPhotos(single: Flowable<List<PhotoDomain>>) {
        whenever(TestApplication.appComponent().photoRepository().getCuratedPhotos())
                .thenReturn(single)
    }

}