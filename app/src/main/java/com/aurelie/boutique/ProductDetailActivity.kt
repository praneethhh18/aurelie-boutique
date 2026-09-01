package com.aurelie.boutique

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.aurelie.boutique.data.Catalog
import com.aurelie.boutique.databinding.ActivityProductDetailBinding
import com.aurelie.boutique.model.Product
import com.aurelie.boutique.ui.Swatches
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private var selectedSize: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_PRODUCT_ID) ?: run { finish(); return }
        val product = Catalog.byId(id)

        applyInsets()
        bind(product)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = bars.top)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.buyBar) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = bars.bottom.coerceAtLeast(view.paddingBottom))
            insets
        }
    }

    private fun bind(product: Product) {
        binding.heroImage.setImageResource(product.tileRes)
        binding.category.text = product.category.uppercase()
        binding.name.text = product.name
        binding.price.text = product.priceLabel

        binding.originalPrice.visibility = if (product.onSale) View.VISIBLE else View.GONE
        binding.originalPrice.text = product.originalPriceLabel
        binding.originalPrice.paintFlags =
            binding.originalPrice.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

        binding.rating.text = getString(R.string.rating_format, product.rating, product.reviewCount)
        binding.description.text = product.description

        binding.badge.visibility = if (product.isNew || product.onSale) View.VISIBLE else View.GONE
        binding.badge.text = when {
            product.onSale -> product.discountLabel
            product.isNew -> getString(R.string.badge_new)
            else -> null
        }

        buildSwatches(product)
        buildSizes(product)

        binding.addToBag.setOnClickListener {
            val size = selectedSize
            if (size == null) {
                Snackbar.make(binding.root, getString(R.string.select_size_first), Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.buyBar)
                    .show()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.added_to_bag, product.name, size),
                    Snackbar.LENGTH_LONG
                ).setAnchorView(binding.buyBar).show()
            }
        }
    }

    private fun buildSwatches(product: Product) {
        binding.swatchGroup.removeAllViews()
        val size = resources.getDimensionPixelSize(R.dimen.swatch_size_large)
        val gap = resources.getDimensionPixelSize(R.dimen.swatch_gap)

        fun repaint(selectedIndex: Int) {
            for (i in 0 until binding.swatchGroup.childCount) {
                binding.swatchGroup.getChildAt(i).background =
                    Swatches.selectableDot(this, product.colors[i], i == selectedIndex)
            }
        }

        product.colors.forEachIndexed { index, _ ->
            val dot = View(this).apply {
                layoutParams = android.view.ViewGroup.MarginLayoutParams(size, size).apply {
                    marginEnd = gap
                }
                setOnClickListener { repaint(index) }
            }
            binding.swatchGroup.addView(dot)
        }
        repaint(0)
    }

    private fun buildSizes(product: Product) {
        binding.sizeGroup.removeAllViews()
        product.sizes.forEach { label ->
            val chip = (layoutInflater.inflate(
                R.layout.item_size_chip, binding.sizeGroup, false
            ) as Chip).apply {
                text = label
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) selectedSize = label
                }
            }
            binding.sizeGroup.addView(chip)
        }
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }
}
