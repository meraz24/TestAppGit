package com.vsple.githubtestappmeraz.Models.SearchRepoModel

import java.io.Serializable

data class License(
    val key: String,
    val name: String,
    val node_id: String,
    val spdx_id: String,
    val url: String
): Serializable