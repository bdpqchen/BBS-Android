package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import java.util.Random;

/**
 * Created by bdpqchen on 17-9-19.
 */

public final class RandomUtil {

    public static int getForumIdRandom() {
        int max = 35;
        int min = 28;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static String getInduceCreateText() {
/*
        String texts[] = {"你在想什么? 什么都没说?",
                "你在说什么? 只听一次也会记得, 听两次就火热",
                "一言一语, 是指定旋律",
                "我在唱什么, 什么都觉得, 原来原来你是我的主打歌",
                "主的可是你, 打得我好神不守舍, 然后\n" + "不断想起你的",
                "不用戴起耳机, 也有好情绪, 散不去",
                "假如有心, 句句都是单曲",
                "一言一语愈来愈有趣, 在我的排行榜升来升去",
                "百听你不厌, 才是好证据"
        };
*/
        String texts0[] = {"你在想什么?", "你想说什么?", "写点什么..."};
        int r = getRandom(3, 0);
        return texts0[r];
    }

    private static int getRandom(int max, int min) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

}
