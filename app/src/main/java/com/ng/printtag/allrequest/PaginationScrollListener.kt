package com.ng.printtag.allrequest

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.allrequest.ActivityAllRequests.Companion.isLastPage
import com.ng.printtag.allrequest.ActivityAllRequests.Companion.isLoading


abstract class PaginationScrollListener(internal var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    /*public abstract int getTotalPageCount();*/

    /* abstract val isLastPage: Boolean

     abstract var isLoad: Boolean*/


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
}