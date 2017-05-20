package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-11.
 */

public class ThreadListModel {


    /**
     * board : {"id":1,"forum_id":1,"forum_name":"这是一个板块","name":"这是一个子版块","info":"这是子板块简介","admin":"","c_thread":40,"visibility":0}
     * thread : [{"id":44,"title":"Hello","author_id":21148,"board_id":1,"author_name":"Halcao","author_nickname":"Halcao","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495244800,"t_create":1495244800,"t_modify":1495244800},{"id":43,"title":"sssss","author_id":21148,"board_id":1,"author_name":"Halcao","author_nickname":"Halcao","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495242162,"t_create":1495242162,"t_modify":1495242162},{"id":42,"title":"来念两首诗吧","author_id":21148,"board_id":1,"author_name":"Halcao","author_nickname":"Halcao","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495241984,"t_create":1495241984,"t_modify":1495241984},{"id":41,"title":"来念两首诗吧","author_id":21148,"board_id":1,"author_name":"Halcao","author_nickname":"Halcao","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495241974,"t_create":1495241974,"t_modify":1495241974},{"id":14,"title":"念诗吧","author_id":21148,"board_id":1,"author_name":"Halcao","author_nickname":"Halcao","c_post":2,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495236241,"t_create":1494970286,"t_modify":1494970286},{"id":40,"title":"新人发帖","author_id":21150,"board_id":1,"author_name":"simpleUser","author_nickname":"simpleUser","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495236218,"t_create":1495236218,"t_modify":1495236218},{"id":39,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495235501,"t_create":1495235501,"t_modify":1495235501},{"id":16,"title":"sajkfhsjkadf","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":18,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495230328,"t_create":1494972708,"t_modify":1494972708},{"id":29,"title":"测试5/17-2","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":2,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204944,"t_create":1495049173,"t_modify":1495049173},{"id":37,"title":"测试5/20/5","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":2,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204920,"t_create":1495204820,"t_modify":1495204820},{"id":36,"title":"测试5/20/4","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204781,"t_create":1495204781,"t_modify":1495204781},{"id":35,"title":"测试5/20/3","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204775,"t_create":1495204775,"t_modify":1495204775},{"id":34,"title":"测试5/20/2","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204768,"t_create":1495204768,"t_modify":1495204768},{"id":33,"title":"测试5/20","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495204758,"t_create":1495204758,"t_modify":1495204758},{"id":28,"title":"测试5/17","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":1,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495189752,"t_create":1495048713,"t_modify":1495048713},{"id":30,"title":"测试5/17-3","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":2,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495189181,"t_create":1495049350,"t_modify":1495049350},{"id":32,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495185672,"t_create":1495185672,"t_modify":1495185672},{"id":31,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495185554,"t_create":1495185554,"t_modify":1495185554},{"id":3,"title":"《锦瑟》（唐）李商隐","author_id":21146,"board_id":1,"author_name":"EasonK","author_nickname":"EasonK","c_post":4,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495126386,"t_create":1494814381,"t_modify":1494814381},{"id":9,"title":"垂死病中惊坐起，谈笑风生又一年","author_id":21142,"board_id":1,"author_name":"zhyupe12","author_nickname":"zhyupe12","c_post":3,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495061158,"t_create":1494867109,"t_modify":1494867109},{"id":27,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":1,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495022126,"t_create":1495010951,"t_modify":1495010951},{"id":26,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495010949,"t_create":1495010949,"t_modify":1495010949},{"id":25,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495010947,"t_create":1495010947,"t_modify":1495010947},{"id":24,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495010666,"t_create":1495010666,"t_modify":1495010666},{"id":23,"title":"一二三四五六七八九十一二三四五六","author_id":21147,"board_id":1,"author_name":"Arsener","author_nickname":"Arsener","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495010631,"t_create":1495010631,"t_modify":1495010631},{"id":19,"title":"嗨嗨嗨","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494979068,"t_create":1494979068,"t_modify":1494979068},{"id":18,"title":"赴戍登程口占示家人【其二】","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494978968,"t_create":1494978968,"t_modify":1494978968},{"id":17,"title":"赴戍登程口占示家人","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494978824,"t_create":1494978824,"t_modify":1494978824},{"id":15,"title":"RichEditorTest","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494972477,"t_create":1494972477,"t_modify":1494972477},{"id":13,"title":"aaaaa","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":3,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494967304,"t_create":1494966817,"t_modify":1494966817},{"id":12,"title":"哈哈哈哈哈","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":2,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494888746,"t_create":1494885605,"t_modify":1494885605},{"id":11,"title":"富文本编辑","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":4,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494888643,"t_create":1494878012,"t_modify":1494878012},{"id":10,"title":"只羡鸳鸯不羡仙","author_id":21146,"board_id":1,"author_name":"EasonK","author_nickname":"EasonK","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494875813,"t_create":1494875813,"t_modify":1494875813},{"id":8,"title":"aaaaa","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494866763,"t_create":1494866763,"t_modify":1494866763},{"id":7,"title":"test","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494866734,"t_create":1494866734,"t_modify":1494866734},{"id":6,"title":"莫笑农家腊酒浑，弄出一个大新闻","author_id":21141,"board_id":1,"author_name":"naiveuser","author_nickname":"asdafff","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494866565,"t_create":1494866565,"t_modify":1494866565},{"id":5,"title":"《锦瑟》（唐）李商隐","author_id":21146,"board_id":1,"author_name":"EasonK","author_nickname":"EasonK","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494865183,"t_create":1494865183,"t_modify":1494865183},{"id":4,"title":"《锦瑟》（唐）李商隐","author_id":21146,"board_id":1,"author_name":"EasonK","author_nickname":"EasonK","c_post":0,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1494817991,"t_create":1494817991,"t_modify":1494817991}]
     */

    private BoardBean board;
    private List<ThreadBean> thread;

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public List<ThreadBean> getThread() {
        return thread;
    }

    public void setThread(List<ThreadBean> thread) {
        this.thread = thread;
    }

    public static class BoardBean {
        /**
         * id : 1
         * forum_id : 1
         * forum_name : 这是一个板块
         * name : 这是一个子版块
         * info : 这是子板块简介
         * admin :
         * c_thread : 40
         * visibility : 0
         */

        private int id;
        private int forum_id;
        private String forum_name;
        private String name;
        private String info;
        private String admin;
        private int c_thread;
        private int visibility;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getForum_id() {
            return forum_id;
        }

        public void setForum_id(int forum_id) {
            this.forum_id = forum_id;
        }

        public String getForum_name() {
            return forum_name;
        }

        public void setForum_name(String forum_name) {
            this.forum_name = forum_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public int getC_thread() {
            return c_thread;
        }

        public void setC_thread(int c_thread) {
            this.c_thread = c_thread;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }
    }

    public static class ThreadBean {
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
         */

        private int id;
        private String title;
        private int author_id;
        private int board_id;
        private String author_name;
        private String author_nickname;
        private int c_post;
        private int b_top;
        private int b_elite;
        private int visibility;
        private int t_reply;
        private int t_create;
        private int t_modify;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
        }

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getAuthor_nickname() {
            return author_nickname;
        }

        public void setAuthor_nickname(String author_nickname) {
            this.author_nickname = author_nickname;
        }

        public int getC_post() {
            return c_post;
        }

        public void setC_post(int c_post) {
            this.c_post = c_post;
        }

        public int getB_top() {
            return b_top;
        }

        public void setB_top(int b_top) {
            this.b_top = b_top;
        }

        public int getB_elite() {
            return b_elite;
        }

        public void setB_elite(int b_elite) {
            this.b_elite = b_elite;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }

        public int getT_reply() {
            return t_reply;
        }

        public void setT_reply(int t_reply) {
            this.t_reply = t_reply;
        }

        public int getT_create() {
            return t_create;
        }

        public void setT_create(int t_create) {
            this.t_create = t_create;
        }

        public int getT_modify() {
            return t_modify;
        }

        public void setT_modify(int t_modify) {
            this.t_modify = t_modify;
        }
    }
}
