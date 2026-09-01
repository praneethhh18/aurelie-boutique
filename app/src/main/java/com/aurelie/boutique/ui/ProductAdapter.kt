package com.aurelie.boutique.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aurelie.boutique.R
import com.aurelie.boutique.databinding.ItemProductBinding
import com.aurelie.boutique.model.Product

class ProductAdapter(
    private val onClick: (Product, View) -> Unit
) : ListAdapter<Product, ProductAdapter.VH>(DIFF) {

    private val favourites = mutableSetOf<String>()

    inner class VH(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val res = binding.root.resources
            binding.image.setImageResource(product.tileRes)
            binding.image.layoutParams = binding.image.layoutParams.apply {
                height = res.getDimensionPixelSize(
                    if (product.aspectTall) R.dimen.tile_height_tall else R.dimen.tile_height
                )
            }

            binding.name.text = product.name
            binding.price.text = product.priceLabel

            binding.originalPrice.isVisible = product.onSale
            binding.originalPrice.text = product.originalPriceLabel
            binding.originalPrice.paintFlags =
                binding.originalPrice.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

            binding.badge.isVisible = product.isNew || product.onSale
            binding.badge.text = when {
                product.onSale -> product.discountLabel
                product.isNew -> res.getString(R.string.badge_new)
                else -> null
            }

            binding.swatches.removeAllViews()
            val context = binding.root.context
            val swatchSize = res.getDimensionPixelSize(R.dimen.swatch_size)
            val swatchGap = res.getDimensionPixelSize(R.dimen.swatch_gap)
            product.colors.take(4).forEach { color ->
                val dot = View(context).apply {
                    background = Swatches.dot(context, color)
                    layoutParams = ViewGroup.MarginLayoutParams(swatchSize, swatchSize).apply {
                        marginEnd = swatchGap
                    }
                }
                binding.swatches.addView(dot)
            }

            val favourited = favourites.contains(product.id)
            binding.favourite.setImageResource(
                if (favourited) R.drawable.ic_heart_filled else R.drawable.ic_heart
            )
            binding.favourite.setOnClickListener {
                if (!favourites.add(product.id)) favourites.remove(product.id)
                notifyItemChanged(bindingAdapterPosition)
            }

            binding.root.transitionName = "product_${product.id}"
            binding.root.setOnClickListener { onClick(product, binding.image) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return VH(ItemProductBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(a: Product, b: Product) = a.id == b.id
            override fun areContentsTheSame(a: Product, b: Product) = a == b
        }
    }
}
