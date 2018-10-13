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


data class HotBean(
		val err: Int,
		val data: HotData
)

data class HotData(
		val latest: List<Latest>,
		val hot: List<Hot>
)

data class Hot(
		val id: Int,
		val title: String,
		var author_id: Int,
		val board_id: Int,
		val anonymous: Int,
		val like: Int,
		var author_name: String,
		val author_nickname: String,
		val c_post: Int,
		val b_top: Int,
		val b_elite: Int,
		val b_locked: Int,
		val visibility: Int,
		val t_reply: Int,
		val t_create: Int,
		val t_modify: Int,
		val content: String,
		val board_name: String,
		val recent: Int
)

data class Latest(
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
		val t_modify: Int,
		val board_name: String
)

data class RankBean(
		val err: Int,
		val data: RankData
)

data class RankData(
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


data class AcEntity(
		val err: Int,
		val data: List<Data>
)

data class Data(
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