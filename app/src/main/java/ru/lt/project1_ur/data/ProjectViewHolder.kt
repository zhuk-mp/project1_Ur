package ru.lt.project1_ur.data

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ru.lt.project1_ur.R
import ru.lt.project1_ur.state.ProjectViewState

class ProjectViewHolder (
    view: View,
    private val listener: OnItemClickListener
) : RecyclerView.ViewHolder(view) {
    fun bindCatalog(model: ProjectViewState.Catalog) {

        itemView.setOnClickListener {
            listener.onItemClick(model)
        }

        val imageView: ImageView = itemView.findViewById(R.id.imageView_avatar)
        val imageUrl = model.avatar
        if (imageUrl.isNotEmpty())
            Picasso.get()
                .load(imageUrl)
                .resize(150, 150)
                .centerCrop()
                .transform(CropCircleTransformation())
                .into(imageView)
        else
            itemView.findViewById<ImageView>(R.id.imageView_avatar).setImageResource(R.drawable.avatar)

        itemView.findViewById<TextView>(R.id.textView_sfera).apply {
            text = model.sfera
        }
    }
    fun bindPerson(model: ProjectViewState.Person) {

        itemView.setOnClickListener {
            listener.onItemClick(model)
        }

        val imageView: ImageView = itemView.findViewById(R.id.imageView_avatar)
        val imageUrl = model.avatar

        if (imageUrl.isNotEmpty())
            Picasso.get()
                .load(imageUrl)
                .resize(200, 200)
                .centerCrop()
                .transform(CropCircleTransformation())
                .into(imageView)
        else
            itemView.findViewById<ImageView>(R.id.imageView_avatar).setImageResource(R.drawable.avatar)

        itemView.findViewById<TextView>(R.id.textView_name).apply {
            text = if (model.name.length > 40) model.name.substring(0, 40) + "..." else  model.name
        }
        itemView.findViewById<TextView>(R.id.textView_spec).apply {
            text = model.spec ?: ""
        }
        itemView.findViewById<TextView>(R.id.textView_address).apply {
            text = if (model.address != null )if (model.address.length > 20) model.address.substring(0, 20) + "..." else  model.address else ""
        }
        itemView.findViewById<TextView>(R.id.textView_count).apply {
            text = model.count.toString()
        }
        itemView.findViewById<TextView>(R.id.textView_rew).apply {
            text = model.stars.toString()
        }
    }
}