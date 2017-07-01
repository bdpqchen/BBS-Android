package com.twtstudio.bbs.bdpqchen.bbs.individual.message.model;

/**
 * Created by Ricky on 2017/5/16.
 */

public class MessageModel {

    /**
     * id : 176
     * content : {"id":223,"thread_id":194,"thread_title":"正经测试帖","content":"引用 #6 RETROX的评论：\n[quote]引用 #5 bdpqchen的评论：\n引用 #4 RETROX的评论：\n最正经的测试回复回复回复回复，还要测试长","floor":7,"t_create":1495964635,"t_modify":0}
     * author_id : 39534
     * author_name : RETROX
     * author_nickname : RETROX
     * tag : 2
     * read : 0
     * t_create : 1495964635
     */

    private int id;
    private ContentModel content_model;
    private String content;
    private int author_id;
    private String author_name;
    private String author_nickname;
    private int tag;
    private int read;
    private int t_create;

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

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getT_create() {
        return t_create;
    }

    public void setT_create(int t_create) {
        this.t_create = t_create;
    }

    public ContentModel getContent_model() {
        return content_model;
    }

    public void setContent_model(ContentModel content_model) {
        this.content_model = content_model;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class ContentModel {
        /**
         * id : 223
         * thread_id : 194
         * thread_title : 正经测试帖
         * content : 引用 #6 RETROX的评论：
         * [quote]引用 #5 bdpqchen的评论：
         * 引用 #4 RETROX的评论：
         * 最正经的测试回复回复回复回复，还要测试长
         * floor : 7
         * t_create : 1495964635
         * t_modify : 0
         */

        private int id;
        private int thread_id;
        private String thread_title;
        private String content;
        private int floor;
        private int t_create;
        private int t_modify;
        private String message;
        private int reply_id;
        private String reply_content;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getThread_id() {
            return thread_id;
        }

        public void setThread_id(int thread_id) {
            this.thread_id = thread_id;
        }

        public String getThread_title() {
            return thread_title;
        }

        public void setThread_title(String thread_title) {
            this.thread_title = thread_title;
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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }
    }
}
