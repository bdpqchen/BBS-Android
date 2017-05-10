package com.twtstudio.bbs.bdpqchen.bbs.main.latestPost;

import java.util.List;

/**
 * Created by bdpqchen on 17-5-11.
 */

class LatestPostModel {

        private List<?> latest;
        private List<AnnounceBean> announce;

        public List<?> getLatest() {
            return latest;
        }

        public void setLatest(List<?> latest) {
            this.latest = latest;
        }

        public List<AnnounceBean> getAnnounce() {
            return announce;
        }

        public void setAnnounce(List<AnnounceBean> announce) {
            this.announce = announce;
        }

        public static class AnnounceBean {
            /**
             * id : 1
             * title : 这是一片公告
             * content : 这是公告内容
             * t_create : 1494382828
             */

            private int id;
            private String title;
            private String content;
            private int t_create;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getT_create() {
                return t_create;
            }

            public void setT_create(int t_create) {
                this.t_create = t_create;
            }
        }
}
