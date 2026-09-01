# AURÉLIE — Bodycon Dress Boutique (Android UI)

A polished storefront UI for a bodycon-dress shop, built on the agreed stack:

- **Kotlin**, JVM target **17**
- **XML layouts + ViewBinding** (no Compose, no `findViewById`)
- **AndroidX AppCompat 1.7.0** + **Material Components 1.12.0**
- Theme: `Theme.Material3.DayNight.NoActionBar` (full light **and** dark palettes)
- **Gradle Kotlin DSL**, AGP 8.5, `compileSdk 34` / `minSdk 24` / `targetSdk 34`

## Screens

| Screen | File | Highlights |
| --- | --- | --- |
| Catalog | `MainActivity` + `activity_main.xml` | Serif wordmark, editorial hero, sticky category chips, 2-column **staggered** product grid, edge-to-edge, bottom nav |
| Product detail | `ProductDetailActivity` + `activity_product_detail.xml` | Full-bleed hero with scrim, colour swatches with selection ring, size `ChipGroup`, sticky gradient **Add to bag** bar |

## Design language

- **Palette** — rosewood `#B76E79` primary, soft gold `#C6A16B` accent, warm ivory `#FBF6F2` ground, espresso `#2B2320` ink. Dark theme in `values-night/`.
- **Type** — `serif` wordmark with wide tracking; `sans-serif-light` display headings; `sans-serif-medium` for price and labels. Scale defined as `TextAppearance.Aurelie.*` in `values/themes.xml`.
- **Product imagery** is placeholder: per-colour gradient tiles (`drawable/tile_*.xml`) with a translucent dress silhouette. Replace `Product.tileRes` with a real photo pipeline (Coil/Glide) when a backend exists.
- **Components** — `MaterialCardView` product cards with hairline stroke + zero elevation, `Chip` filters with a checked espresso state, a gradient `MaterialButton` (`drawable/bg_button_primary.xml`).

## Run it

Open the folder in Android Studio (Koala or newer). It will generate the Gradle wrapper JAR on first sync, then Run ▶ the `app` config on a device/emulator (API 24+).

Everything is sample data in `data/Catalog.kt` — no network, no permissions.
