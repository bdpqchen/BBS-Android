package com.twtstudio.bbs.bdpqchen.bbs.forum.boards;

import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListModel;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class PreviewThreadModel {


    private ThreadListModel.BoardBean board;
    private List<ThreadListModel.ThreadBean> threadList;

    public ThreadListModel.BoardBean getBoard() {
        return board;
    }

    public void setBoard(ThreadListModel.BoardBean board) {
        this.board = board;
    }

    public List<ThreadListModel.ThreadBean> getThreadList() {
        return threadList;
    }

    public void setThreadList(List<ThreadListModel.ThreadBean> threadList) {
        this.threadList = threadList;
    }
}
