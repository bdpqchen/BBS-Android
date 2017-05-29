package com.twtstudio.bbs.bdpqchen.bbs.main.historyHot;

import com.twtstudio.bbs.bdpqchen.bbs.main.model.LatestPostModel;

import java.util.List;

/**
 * Created by zhangyulong on 5/13/17.
 */

public class HistoryHotModel extends LatestPostModel{
    public static class DataBean {
        public List<HistoryhotBean> historyhot;

        public static class HistoryhotBean {
            /**
             * id : 44
             * title : Hello
             * author_id : 21148
             * board_id : 1
             * author_name : Halcao
             * author_nickname : Halcao
             * c_post : 0
             * b_top : 0
             * b_elite : 0
             * visibility : 0
             * t_reply : 1495244800
             * t_create : 1495244800
             * t_modify : 1495244800
             * board_name : 这是一个子版块
             */

            public int id;
            public String title;
            public int author_id;
            public int board_id;
            public String author_name;
            public String author_nickname;
            public int c_post;
            public int b_top;
            public int b_elite;
            public int visibility;
            public int t_reply;
            public int t_create;
            public int t_modify;
            public String board_name;
        }
    }
}

