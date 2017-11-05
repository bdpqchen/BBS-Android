package com.twtstudio.bbs.bdpqchen.bbs.forum

/**
 * Created by bdpqchen on 17-11-5.
 *
 */
data class ForumBoardModel(var fid: Int, var forumName: String, var boardList: List<BoardModel>) {
    data class BoardModel(var bid: Int, var boardName: String)
}