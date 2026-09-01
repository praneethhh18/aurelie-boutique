package com.aurelie.boutique.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aurelie.boutique.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<String>,
    private val onSelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.VH>() {

    private var selected = 0

    inner class VH(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return VH(ItemCategoryBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val label = categories[position]
        holder.binding.chip.text = label
        holder.binding.chip.isChecked = position == selected
        holder.binding.chip.setOnClickListener {
            val previous = selected
            selected = holder.bindingAdapterPosition
            notifyItemChanged(previous)
            notifyItemChanged(selected)
            onSelected(label)
        }
    }
}
