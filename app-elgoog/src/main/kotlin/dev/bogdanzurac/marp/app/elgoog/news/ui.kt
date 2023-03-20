package dev.bogdanzurac.marp.app.elgoog.news

import kotlinx.datetime.Instant

val composeNewsArticleModelPreview =
    NewsArticleModel(
        "123",
        "https://cdn.pixabay.com/photo/2012/08/27/14/19/mountains-55067__340.png",
        listOf("John Doe", "Jane Doe"),
        "Beautiful landscape in today's news",
        "https://www.reuters.com/",
        "This is a little description of today's article that summarizes the entire article in just a few words",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        Instant.parse("2023-03-04T19:43:33+0000")
    )