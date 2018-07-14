package com.twtstudio.bbs.bdpqchen.bbs.main


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


data class RankBean(
        val err: Int,
        val data: Data
)

data class Data(
        val after: Int,
        val rank: List<Rank>
)

data class Rank(
        val name: String,
        val nickname: String,
        val signature: String,
        val points: Int,
        val c_online: Int,
        val group: Int,
        val t_create: Int,
        val points_inc: Int,
        val id: Int
)