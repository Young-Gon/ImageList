package com.gondev.imagelist.domain.network.response

/**
 *
"id": "0",
"author": "Alejandro Escamilla",
"width": 5616,
"height": 3744,
"url": "https://unsplash.com/photos/yC-Yzbqy7PY",
"download_url": "https://picsum.photos/id/0/5616/3744"
 */
data class ImageData (
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String,
)