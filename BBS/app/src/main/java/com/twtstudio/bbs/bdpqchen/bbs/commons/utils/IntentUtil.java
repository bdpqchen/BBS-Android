package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.content.Context;
import android.content.Intent;

import com.twtstudio.bbs.bdpqchen.bbs.auth.retrieve.RetrieveActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread_list.ThreadListActivity;
import com.twtstudio.bbs.bdpqchen.bbs.individual.letter.LetterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.mdeditor.EditorActivity;
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleActivity;
import com.twtstudio.bbs.bdpqchen.bbs.picture.BigPhotoActivity;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.IMG_URL;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_BOARD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_CONTENT;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_EDITOR_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_ID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_THREAD_TITLE;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.UID;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.USERNAME;
import static com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.ThreadActivity.INTENT_THREAD_FLOOR;

/**
 * Created by bdpqchen on 17-7-3.
 */

public final class IntentUtil {

    public static Intent toThread(Context context, int tid){
        return toThread(context, tid, "", 0);
    }
    public static Intent toThread(Context context, int tid, String title, int floor){
        return toThread(context, tid, title, floor, 0, "");
    }
    public static Intent toThread(Context context, int tid, String title, int floor, int bid, String boardTitle){
        Intent intent = new Intent(context, ThreadActivity.class);
        intent.putExtra(INTENT_THREAD_ID, tid);
        intent.putExtra(INTENT_THREAD_TITLE, title);
        intent.putExtra(INTENT_THREAD_FLOOR, floor);
        intent.putExtra(INTENT_BOARD_ID, bid);
        intent.putExtra(INTENT_BOARD_TITLE, boardTitle);
        return intent;
    }

    public static Intent toThreadList(Context context, int bid, String name){
        Intent intent = new Intent(context, ThreadListActivity.class);
        intent.putExtra(INTENT_BOARD_ID, bid);
        intent.putExtra(INTENT_BOARD_TITLE, name);
        return intent;
    }

    public static Intent toPeople(Context context, int uid) {
        Intent intent = new Intent(context, PeopleActivity.class);
        intent.putExtra(UID, uid);
        return intent;
    }
    public static Intent toPeople(Context context, int uid, String username) {
        return toPeople(context, uid).putExtra(USERNAME, username);
    }

    public static Intent toLetter(Context context, int uid, String username) {
        Intent intent = new Intent(context, LetterActivity.class);
        intent.putExtra(UID, uid);
        intent.putExtra(USERNAME, username);
        return intent;
    }

    public static Intent toBigPhoto(Context context, String imageUrl) {
        Intent intent = new Intent(context, BigPhotoActivity.class);
        intent.putExtra(IMG_URL, imageUrl);
        return intent;
    }

    public static Intent toRetrieve(Context context) {
        Intent intent = new Intent(context, RetrieveActivity.class);
        return intent;
    }

    public static Intent toEditor(Context context, String title, String content){
        Intent intent = new Intent(context, EditorActivity.class);
        intent.putExtra(INTENT_EDITOR_TITLE, title);
        intent.putExtra(INTENT_EDITOR_CONTENT, content);
        return intent;
    }
}
