package com.vsple.githubtestappmeraz.Models.SearchRepoModel

import java.io.Serializable

data class SearchDataModel(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
): Serializable