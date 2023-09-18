package ru.lt.project1_ur.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lt.project1_ur.R
import ru.lt.project1_ur.state.ProjectViewState

class ProjectAdapterCatalog(private val listener: OnItemClickListener) : RecyclerView.Adapter<ProjectViewHolder>()  {

    private var items = mutableListOf<ProjectViewState.Catalog>()

    fun setItems(newItems: List<ProjectViewState.Catalog>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun addItems(items: List<ProjectViewState.Catalog>) {
        val size = this.items.size
        this.items.addAll(items)
        notifyItemInserted(size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val layoutRes = R.layout.item_cart_catalog
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ProjectViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bindCatalog(items[position])

    }
}