package com.aurelie.boutique.data

import com.aurelie.boutique.R
import com.aurelie.boutique.model.Product

/**
 * In-memory sample catalog. Replace with a repository backed by Retrofit / Room
 * when the storefront gets a real API.
 */
object Catalog {

    val categories = listOf(
        "All", "New In", "Ruched", "Mini", "Midi", "Cut-Out", "Long Sleeve", "Going Out"
    )

    private val rosewood = 0xFFB76E79.toInt()
    private val espresso = 0xFF2B2320.toInt()
    private val ivory = 0xFFEFE6DD.toInt()
    private val olive = 0xFF7C7A55.toInt()
    private val wine = 0xFF6E2A3A.toInt()
    private val sky = 0xFF9FB4C7.toInt()
    private val blush = 0xFFE7C4C9.toInt()
    private val sand = 0xFFC6A16B.toInt()

    val products: List<Product> = listOf(
        Product(
            id = "aur-01",
            name = "Seraphine Ruched Mini Dress",
            category = "Ruched",
            priceCents = 8900,
            originalPriceCents = 12800,
            rating = 4.8f,
            reviewCount = 214,
            tileRes = R.drawable.tile_rosewood,
            colors = listOf(rosewood, espresso, ivory),
            description = "Second-skin stretch crepe with signature side ruching that " +
                "sculpts every curve. A square neckline and hidden back zip keep it clean.",
            aspectTall = true,
            isNew = true
        ),
        Product(
            id = "aur-02",
            name = "Noir Square-Neck Midi",
            category = "Midi",
            priceCents = 11200,
            rating = 4.9f,
            reviewCount = 402,
            tileRes = R.drawable.tile_espresso,
            colors = listOf(espresso, wine),
            description = "The little black dress, recut. Thick straps, a corseted bodice " +
                "and a thigh-high split in a ponte knit that holds its shape all night."
        ),
        Product(
            id = "aur-03",
            name = "Halcyon Cut-Out Bodycon",
            category = "Cut-Out",
            priceCents = 9600,
            rating = 4.6f,
            reviewCount = 88,
            tileRes = R.drawable.tile_olive,
            colors = listOf(olive, espresso, sand),
            description = "Sculptural waist cut-outs framed by a twist detail. Ribbed " +
                "modal jersey with four-way stretch for a locked-in fit.",
            aspectTall = true
        ),
        Product(
            id = "aur-04",
            name = "Lumière Long-Sleeve Dress",
            category = "Long Sleeve",
            priceCents = 10400,
            originalPriceCents = 13000,
            rating = 4.7f,
            reviewCount = 156,
            tileRes = R.drawable.tile_wine,
            colors = listOf(wine, espresso, ivory),
            description = "Slinky mesh sleeves over a lined bodycon base. A high crew neck " +
                "up top, a mid-thigh hem below — engineered contrast."
        ),
        Product(
            id = "aur-05",
            name = "Aria Cowl-Back Slip",
            category = "Going Out",
            priceCents = 8200,
            rating = 4.5f,
            reviewCount = 73,
            tileRes = R.drawable.tile_sky,
            colors = listOf(sky, blush, ivory),
            description = "Bias-cut satin-jersey that pours over the body with a low cowl " +
                "back. Adjustable straps, no zip, packs to nothing.",
            aspectTall = true
        ),
        Product(
            id = "aur-06",
            name = "Muse Strapless Mini",
            category = "Mini",
            priceCents = 7400,
            originalPriceCents = 9900,
            rating = 4.4f,
            reviewCount = 51,
            tileRes = R.drawable.tile_blush,
            colors = listOf(blush, espresso),
            description = "Internal boning and silicone grip tape keep this bandeau mini " +
                "exactly where you put it. Double-layer power stretch.",
            isNew = true
        ),
        Product(
            id = "aur-07",
            name = "Celestine Ruched Midi",
            category = "Ruched",
            priceCents = 12800,
            rating = 4.9f,
            reviewCount = 311,
            tileRes = R.drawable.tile_sand,
            colors = listOf(sand, espresso, wine),
            description = "Floor-grazing column with center-front ruching and a cowl neck. " +
                "Heavyweight matte jersey that skims, never clings.",
            aspectTall = true
        ),
        Product(
            id = "aur-08",
            name = "Vesper One-Shoulder",
            category = "Going Out",
            priceCents = 9900,
            rating = 4.7f,
            reviewCount = 129,
            tileRes = R.drawable.tile_rosewood,
            colors = listOf(rosewood, espresso, sky),
            description = "Asymmetric one-shoulder neckline with a single sculpted sleeve. " +
                "Compression scuba knit for a smooth, seamless line.",
            isNew = true
        )
    )

    fun byId(id: String): Product = products.first { it.id == id }

    fun forCategory(category: String): List<Product> = when (category) {
        "All" -> products
        "New In" -> products.filter { it.isNew }
        else -> products.filter { it.category == category }
    }
}
