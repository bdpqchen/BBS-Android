package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.content.Intent;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity.INTENT_THREAD_FLOOR;

/**
 * Created by bdpqchen on 17-7-3.
 */

public final class IntentUtil {

    public static Intent toThread(Context context, Class cls, int tid, String title, int floor){
        return toThread(context, cls, tid, title, floor, 0, "");
    }
    public static Intent toThread(Context context, Class cls, int tid, String title, int floor, int bid, String boardTitle){
        Intent intent = new Intent(context, cls);
        intent.putExtra(INTENT_THREAD_ID, tid);
        intent.putExtra(INTENT_THREAD_TITLE, title);
        intent.putExtra(INTENT_THREAD_FLOOR, floor);
        intent.putExtra(INTENT_BOARD_ID, bid);
        intent.putExtra(INTENT_BOARD_TITLE, boardTitle);
        return intent;
    }

}
