package com.twtstudio.bbs.bdpqchen.bbs.individual.model;

import java.util.List;

/**
 * Created by HP on 2017/5/14.
 */

public class CollectionBean {

    /**
     * err : 0
     * data : [{"id":1,"title":"控制台发帖","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"aaaaaaaaa","b_top":0,"b_elite":0,"visibility":0,"t_create":1494719929,"t_modify":1494719929},{"id":2,"title":"再次控制台发帖","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","b_top":0,"b_elite":0,"visibility":0,"t_create":1494720361,"t_modify":1494720361}]
     */

    public int err;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * title : 控制台发帖
         * author_id : 21141
         * board_id : 1
         * author_name : naiveuser
         * author_nickname : aaaaaaaaa
         * b_top : 0
         * b_elite : 0
         * visibility : 0
         * t_create : 1494719929
         * t_modify : 1494719929
         */

        public int id;
        public String title;
        public int author_id;
        public int board_id;
        public String author_name;
        public String author_nickname;
        public int b_top;
        public int b_elite;
        public int visibility;
        public int t_create;
        public int t_modify;
    }
}
