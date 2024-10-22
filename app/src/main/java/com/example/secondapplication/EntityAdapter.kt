package com.example.secondapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Entity(val name: String, val summary: String, val description: String)

class EntityAdapter(
    private val context: Context,
    private val entities: List<Entity>,
    private val onItemClick: (Entity) -> Unit
) : RecyclerView.Adapter<EntityAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.entity_name)
        val summary: TextView = itemView.findViewById(R.id.entity_summary)
        val description: TextView = itemView.findViewById(R.id.entity_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_identity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = entities[position]
        holder.name.text = entity.name
        holder.summary.text = entity.summary
        holder.description.text = entity.description

        holder.itemView.setOnClickListener {
            onItemClick(entity)
        }
    }

    override fun getItemCount() = entities.size
}