package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class ThreadModel {

    /**
     * thread : {"id":179,"title":"I/O 2017 Recap","author_id":21141,"author_name":"naiveuser","author_nickname":"asdafff","c_post":13,"b_top":0,"b_elite":0,"visibility":0,"t_reply":1495891466,"t_create":1495808968,"t_modify":1495852844,"content":"[img]https://bbs.twtstudio.com/api/img/63[/img]\nExplore the grounds, check out this year&amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;rsquo;s hands-on learning experiences, listen to featured technical talks, and see what people had to say in the social feed.\n","friend":0,"in_collection":0}
     * board : {"id":24,"forum_id":14,"forum_name":"学术文艺","name":"互联网技术"}
     * post : [{"id":118,"author_id":21146,"author_name":"EasonK","author_nickname":"等待AMS只有骨折","content":"哇塞，好大的图片诶","floor":2,"t_create":1495811823,"t_modify":0,"friend":0},{"id":119,"author_id":21149,"author_name":"testadmin","author_nickname":"testadmin","content":"&amp;#91;&amp;#93;&amp;#91;&amp;#93;&amp;#91;&amp;#93;&amp;#91;&amp;#93;&amp;#91;&amp;#93;&amp;#91;&amp;#93;&amp;#91;&amp;#93;\n","floor":3,"t_create":1495817671,"t_modify":0,"friend":0},{"id":120,"author_id":21141,"author_name":"naiveuser","author_nickname":"asdafff","content":"引用 #2 EasonK的评论：\n[quote]哇塞，好大的图片诶[/quote]\n是哈\n","floor":4,"t_create":1495851716,"t_modify":0,"friend":0},{"id":125,"author_id":19667,"author_name":"bdpqchen","author_nickname":"starter","content":"回复的回复烂了","floor":9,"t_create":1495867453,"t_modify":0,"friend":0},{"id":133,"author_id":21149,"author_name":"testadmin","author_nickname":"testadmin","content":"tg43quhy534qreu54e\n引用 #9 bdpqchen的评论：\n[quote]回复的回复烂了[/quote]\n","floor":10,"t_create":1495883604,"t_modify":0,"friend":0},{"id":135,"author_id":21148,"author_name":"Halcao","author_nickname":"Halcao","content":"你们才烂了呢哼！","floor":11,"t_create":1495885576,"t_modify":0,"friend":0},{"id":138,"author_id":21147,"author_name":"Arsener","author_nickname":"Arsener","content":"小哥哥","floor":12,"t_create":1495886530,"t_modify":0,"friend":0},{"id":139,"author_id":21147,"author_name":"Arsener","author_nickname":"Arsener","content":"小哥哥","floor":13,"t_create":1495886556,"t_modify":0,"friend":0},{"id":154,"author_id":21142,"author_name":"zhyupe12","author_nickname":"zhyupe12","content":"[list=1]\n[*]test&amp;#91;&amp;#93;\n[/list]\n","floor":14,"t_create":1495891466,"t_modify":0,"friend":0}]
     */

    private ThreadBean thread;
    private BoardBean board;
    private List<PostBean> post;

    public ThreadBean getThread() {
        return thread;
    }

    public void setThread(ThreadBean thread) {
        this.thread = thread;
    }

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public List<PostBean> getPost() {
        return post;
    }

    public void setPost(List<PostBean> post) {
        this.post = post;
    }

    public static class ThreadBean {
        /**
         * id : 179
         * title : I/O 2017 Recap
         * author_id : 21141
         * author_name : naiveuser
         * author_nickname : asdafff
         * c_post : 13
         * b_top : 0
         * b_elite : 0
         * visibility : 0
         * t_reply : 1495891466
         * t_create : 1495808968
         * t_modify : 1495852844
         * content : [img]https://bbs.twtstudio.com/api/img/63[/img]
         * Explore the grounds, check out this year&amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;rsquo;s hands-on learning experiences, listen to featured technical talks, and see what people had to say in the social feed.
         * <p>
         * friend : 0
         * in_collection : 0
         */

        private int id;
        private String title;
        private int author_id;
        private String author_name;
        private String author_nickname;
        private int c_post;
        private int b_top;
        private int b_elite;
        private int visibility;
        private int t_reply;
        private int t_create;
        private int t_modify;
        private String content;
        private int friend;
        private int in_collection;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFriend() {
            return friend;
        }

        public void setFriend(int friend) {
            this.friend = friend;
        }

        public int getIn_collection() {
            return in_collection;
        }

        public void setIn_collection(int in_collection) {
            this.in_collection = in_collection;
        }
    }

    public static class BoardBean {
        /**
         * id : 24
         * forum_id : 14
         * forum_name : 学术文艺
         * name : 互联网技术
         */

        private int id;
        private int forum_id;
        private String forum_name;
        private String name;

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
    }

    public static class PostBean {
        /**
         * id : 118
         * author_id : 21146
         * author_name : EasonK
         * author_nickname : 等待AMS只有骨折
         * content : 哇塞，好大的图片诶
         * floor : 2
         * t_create : 1495811823
         * t_modify : 0
         * friend : 0
         */

        private int id;
        private int author_id;
        private String author_name;
        private String author_nickname;
        private String content;
        private int floor;
        private int t_create;
        private int t_modify;
        private int friend;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(int author_id) {
            this.author_id = author_id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
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

        public int getFriend() {
            return friend;
        }

        public void setFriend(int friend) {
            this.friend = friend;
        }
    }
}
