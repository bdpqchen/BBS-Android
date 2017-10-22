package com.twtstudio.bbs.bdpqchen.bbs.search.model

/**
 * Created by bdpqchen on 17-10-22.
 */

/*
* {
    "err": 0,
    "data": [
        {
            "id": 170181,
            "title": "遂宁市船山区委员会组织部2017年秋季校园招聘",
            "author_id": 19544,
            "board_id": 189,
            "anonymous": 0,
            "author_name": "youzi",
            "author_nickname": "youzi",
            "c_post": 1,
            "visibility": 0,
            "t_reply": 1506674637,
            "t_create": 1506501754,
            "t_modify": 1506501754
        }
    ]
}*/
data class SearchThreadModel(val id: Int,
                             var title: String,
                             var t_create: Int,
                             var author_id: Int,
                             var anonymous: Int,
                             var author_name: String,
                             var board_id: Int
) : Model()

