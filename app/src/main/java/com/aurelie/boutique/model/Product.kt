package com.aurelie.boutique.model

/**
 * A single bodycon dress in the catalog.
 *
 * [tileRes] points at a decorative gradient drawable used as the product image
 * placeholder — swap it for a real photo URL + image loader when wiring a backend.
 * [aspectTall] drives the staggered grid rhythm on the home screen.
 */
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val priceCents: Int,
    val originalPriceCents: Int? = null,
    val rating: Float,
    val reviewCount: Int,
    val tileRes: Int,
    val colors: List<Int>,
    val sizes: List<String> = listOf("XS", "S", "M", "L", "XL"),
    val description: String,
    val aspectTall: Boolean = false,
    val isNew: Boolean = false
) {

    val priceLabel: String get() = formatUsd(priceCents)
    val originalPriceLabel: String? get() = originalPriceCents?.let { formatUsd(it) }
    val onSale: Boolean get() = originalPriceCents != null && originalPriceCents > priceCents

    val discountLabel: String?
        get() {
            val original = originalPriceCents ?: return null
            if (original <= priceCents) return null
            val pct = ((original - priceCents) * 100f / original).toInt()
            return "-$pct%"
        }

    companion object {
        fun formatUsd(cents: Int): String {
            val dollars = cents / 100
            val remainder = cents % 100
            return "$%,d.%02d".format(dollars, remainder)
        }
    }
}
