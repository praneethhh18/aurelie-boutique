package com.aurelie.boutique

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aurelie.boutique.data.Catalog
import com.aurelie.boutique.databinding.ActivityMainBinding
import com.aurelie.boutique.model.Product
import com.aurelie.boutique.ui.CategoryAdapter
import com.aurelie.boutique.ui.GridSpacingItemDecoration
import com.aurelie.boutique.ui.ProductAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets()

        setupToolbar()
        setupCategories()
        setupProductGrid()
        setupBottomNav()

        render(Catalog.forCategory("All"))
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = bars.top)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNav) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = bars.bottom)
            insets
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_search -> { toast(getString(R.string.hint_search)); true }
                R.id.action_bag -> { toast(getString(R.string.hint_bag)); true }
                else -> false
            }
        }
    }

    private fun setupCategories() {
        binding.categoryList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryList.adapter = CategoryAdapter(Catalog.categories) { category ->
            render(Catalog.forCategory(category))
            binding.productGrid.smoothScrollToPosition(0)
        }
    }

    private fun setupProductGrid() {
        productAdapter = ProductAdapter { product, _ ->
            startActivity(
                Intent(this, ProductDetailActivity::class.java)
                    .putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
            )
        }
        binding.productGrid.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = productAdapter
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = 2,
                    spacing = resources.getDimensionPixelSize(R.dimen.grid_gutter)
                )
            )
            itemAnimator = null
        }
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId != R.id.nav_shop) toast(getString(R.string.hint_coming_soon))
            item.itemId == R.id.nav_shop
        }
    }

    private fun render(items: List<Product>) {
        productAdapter.submitList(items)
        binding.resultCount.text =
            resources.getQuantityString(R.plurals.result_count, items.size, items.size)
        binding.emptyState.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun toast(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.bottomNav)
            .show()
}
