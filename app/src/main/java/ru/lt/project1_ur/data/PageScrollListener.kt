package ru.lt.project1_ur.data

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.lt.project1_ur.state.ProjectViewState

class PageScrollListener(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var isLoading = false
    var onLoadMore: (() -> Unit)? = null

    fun setLoading(loading: Boolean) {
        isLoading = loading
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount - 12
        val visibleItemCount = layoutManager.childCount
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading) {
            if ((visibleItemCount + firstVisiblePosition) >= totalItemCount) {
                isLoading = true
                onLoadMore?.invoke()
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(model: ProjectViewState.Person)
    fun onItemClick(model: ProjectViewState.Catalog)
}