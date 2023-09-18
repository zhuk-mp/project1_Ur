package ru.lt.project1_ur.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.lt.project1_ur.R
import ru.lt.project1_ur.state.ProjectViewState

class ProjectAdapterPerson(private val listener: OnItemClickListener) : RecyclerView.Adapter<ProjectViewHolder>()  {

    private var items = mutableListOf<ProjectViewState.Person>()

    fun setItems(newItems: List<ProjectViewState.Person>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun addItems(items: List<ProjectViewState.Person>) {
        val size = this.items.size
        this.items.addAll(items)
        notifyItemInserted(size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val layoutRes = R.layout.item_cart_person
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ProjectViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bindPerson(items[position])

    }
}