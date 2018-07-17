package com.twtstudio.bbs.bdpqchen.bbs.main.announce


data class AnnounceBean(
        val err: Int,
        val data: List<AnnounceEntity>
)

data class AnnounceEntity(
        val id: Int,
        val title: String,
        val author_id: Int,
        val board_id: Int,
        val anonymous: Int,
        val like: Int,
        val author_name: String,
        val author_nickname: String,
        val c_post: Int,
        val b_top: Int,
        val b_elite: Int,
        val b_locked: Int,
        val visibility: Int,
        val t_reply: Int,
        val t_create: Int,
        val t_modify: Int
)