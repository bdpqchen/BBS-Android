package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import java.util.HashMap;

/**
 * Created by bdpqchen on 17-6-9.
 */

public class ImageFormatUtil {

    private int imageIndex = 1;
    private HashMap<String, Integer> images = new HashMap<>();

    public String replaceImageFormat(String content) {
        if (images != null) {
            int size = images.size();
            for (int i = 1; i <= size; i++) {
                String key = getReplaceKey(i);
                if (content.contains(key))
                    content = content.replace(key,
                            "\n![](attach:" + images.get(getReplaceKey(i)) + ")\n");
            }
        }
        if (images != null) {
            images.clear();
            imageIndex = 1;
        }
        return content;
    }
    private String getReplaceKey(int index) {
        return "[photo" + index + "]";
    }
    public String getShowImageCode(int id) {
        return addImage(id);
    }
    private String addImage(int id) {
        String key = getReplaceKey(imageIndex);
        images.put(key, id);
        imageIndex++;
        return key;
    }

}
