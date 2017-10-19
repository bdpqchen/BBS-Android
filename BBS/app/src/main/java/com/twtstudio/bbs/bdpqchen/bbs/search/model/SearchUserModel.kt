package com.twtstudio.bbs.bdpqchen.bbs.search.model

/**
 * Created by bdpqchen on 17-10-19.
 */
data class SearchUserModel(val data: List<DataModel>)

data class DataModel(var id: Int, var name: String)
