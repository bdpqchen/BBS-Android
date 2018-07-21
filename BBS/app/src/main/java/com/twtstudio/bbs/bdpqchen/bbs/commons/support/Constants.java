package com.twtstudio.bbs.bdpqchen.bbs.commons.support;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;

/**
 * Created by bdpqchen on 17-5-3.
 */

public final class Constants {

    public static final String BASE_HOST = "bbs.tju.edu.cn";
    public static final String BASE_HOST_TWT = "bbs.twtstudio.com";
    public static final String BASE = "https://" + BASE_HOST;
    public static final String BASE_URL = BASE + "/api/";

    public static final int FRAGMENT_MAIN = 0;
    public static final int FRAGMENT_FORUM = 1;
    public static final int FRAGMENT_INDIVIDUAL = 2;

    public static final int REQUEST_CODE_AVATAR = 10;
    public static final int REQUEST_CODE_IMAGE_SELECTED = 11;
    public static final int REQUEST_CODE_RETRIEVE = 12;
    public static final int REQUEST_CODE_LOGIN = 13;
    public static final int REQUEST_CODE_EDITOR = 14;
    public static final int REQUEST_CODE_UPDATE_INFO = 15;
    public static final int REQUEST_CODE_AT_USER = 16;

    public static final int MAX_LENGTH_QUOTE = 60;
    public static final int MAX_LENGTH_POST = 50;
    public static final int MAX_LENGTH_Letter = 20;
    public static final int MIN_LENGTH_LETTER_CONTENT = 3;
    public static final int MAX_SEARCH_RESULT_USER = 3;
    public static final int ITEM_HEADER = -1;
    public static final int ITEM_NORMAL = 0;
    public static final int ITEM_FOOTER = 1;
    public static final int ITEM_END = 2;
    public static final int ITEM_JUST_HEADER = 3;
    public static final int ITEM_SIMPLE = 4;
    public static final int ITEM_MSG_LETTER = 5;
    public static final int ITEM_MSG_COMMENT = 6;
    public static final int ITEM_MSG_REPLY = 7;
    public static final int ITEM_MSG_APPEAL = 8;
    public static final int ITEM_LEFT = 9;
    public static final int ITEM_RIGHT = 10;
    public static final int ITEM_EMPTY = 11;
    public static final int ITEM_SEARCH_USER = 12;
    public static final int ITEM_SEARCH_THREAD = 13;
    public static final int ITEM_SEARCH_THREAD_HEADER = 14;
    public static final int ITEM_SEARCH_USER_HEADER = 15;
    public static final int ITEM_SEARCH_USER_HIDING = 16;
    public static final int ITEM_SEARCH_DIVIDER = 17;
    public static final int ITEM_MSG_AT_USER = 18;
    public static final int ITEM_CREATE_THREAD = 19;
    public static final int ITEM_UPDATE_AVAILABLE = 20;
    public static final int ITEM_BOARD = 21;
    public static final int ITEM_FORUM = 22;



    public static final int TAG_MSG_SYSTEM = 0;
    public static final int TAG_MSG_LETTER = 1;
    public static final int TAG_MSG_COMMENT = 2;
    public static final int TAG_MSG_REPLY = 3;
    public static final int TAG_MSG_APPEAL = 4;
    public static final int TAG_MSG_AT_USER = 11;


    public static final String BUNDLE_REGISTER_CID = "cid";
    public static final String BUNDLE_REGISTER_REAL_NAME = "real_name";
    public static final String BUNDLE_REGISTER_STU_NUM = "stunum";
    public static final String BUNDLE_REGISTER_PASSWORD = "password";
    public static final String BUNDLE_REGISTER_USERNAME = "username";
    public static final String BUNDLE_NICKNAME = "nickname";
    public static final String BUNDLE_SIGNATURE = "signature";
    public static final String BUNDLE_UID = "uid";
    public static final String BUNDLE_EMAIL = "email";
    public static final String BUNDLE_MESSAGE = "message";

    public static final int MODE_SEARCH_THREAD = 0;
    public static final int MODE_SEARCH_USER = 1;
    public static final int MODE_SEARCH_BOTH = 2;

    public static final int STATUS_USER_NORMAL = -1;
    public static final int STATUS_USER_ANONYMOUS = 0;
    public static final int STATUS_USER_NO_AVATAR = 1;

    //    public static final String NET_RETROFIT_POST_HEADER = "ContentActivity-Type:application/x-www-form-urlencoded; charset=utf-8";
    public static final String NET_RETROFIT_HEADER_TITLE = "authentication";
    public static final String NET_RETROFIT_HEADER_REQUEST = "X-Requested_With";
    public static final String BUNDLE_TOKEN = "token";
    public static final String ANONYMOUS_NAME = "匿名用户";

    /*intent*/
    public static final String INTENT_THREAD_ID = "intent_thread_id";
    public static final String INTENT_THREAD_TITLE = "intent_thread_title";
    public static final String INTENT_THREAD_CONTENT = "intent_thread_content";
    public static final String INTENT_BOARD_ID = "intent_board_id";
    public static final String INTENT_BOARD_TITLE = "intent_board_title";
    public static final String INTENT_BOARD_CAN_ANON = "intent_board_can_anons";
    public static final String INTENT_FORUM_ID = "intent_forum_id";
    public static final String INTENT_FORUM_TITLE = "intent_forum_title";
    public static final String INTENT_BOARD_NAMES = "intent_board_names";
    public static final String INTENT_BOARD_IDS = "intent_board_ids";
    public static final String INTENT_BOARD_CAN_ANONS = "intent_board_can_anons";
    public static final String INTENT_IS_SPECIFY_BOARD = "intent_is_specify_board";
    public static final String INTENT_BOARD_MODEL_LIST = "intent_board_model_list";
    public static final String INTENT_UNREAD = "intent_unread_count";
    public static final String INTENT_EDITOR_TITLE = "intent_editor_title";
    public static final String INTENT_EDITOR_TOOLBAR_TITLE = "intent_editor_toolbar_title";
    public static final String INTENT_EDITOR_CONTENT = "intent_editor_content";
    public static final String INTENT_SEARCH_MODE = "intent_search_mode";
    public static final String INTENT_SEARCH_THREAD = "intent_search_thread";
    public static final String INTENT_SEARCH_USER = "intent_search_user";
    public static final String INTENT_LOGIN = "intent_login";


    public static final String INTENT_RESULT_IMAGE_PATH = "intent_result_image_path";
    public static final String INTENT_RESULT_UPDATE_INFO = "intent_result_update_info";
    public static final String INTENT_RESULT_AT_USER_UID = "intent_result_at_user_uid";
    public static final String INTENT_RESULT_AT_USER_NAME = "intent_result_at_user_name";

    public static final String TOKEN = "token";
    public static final String ID = "id";
    public static final String UID = "uid";
    public static final String TO_UID = "to_uid";
    public static final String CID = "cid";
    public static final String FID = "fid";
    public static final String BID = "bid";
    public static final String TID = "tid";
    public static final String PID = "pid";
    public static final String USER = "user";
    public static final String AVATAR = "avatar";
    public static final String COVER = "cover";
    public static final String CONTENT = "content";
    public static final String TITLE = "title";
    public static final String REPLY_ID = "reply";
    public static final String IS_ANONYMOUS = "is_anonymous";
    public static final String ANONYMOUS = "anonymous";
    public static final String USERNAME = "username";
    public static final String REAL_NAME = "real_name";
    public static final String STUNUM = "stunum";
    public static final String EMAIL = "email";
    public static final String MESSAGE = "message";
    public static final String CAPTCHA_ID = "captcha_id";
    public static final String CAPTCHA_VALUE = "captcha_value";
    public static final String PASSWORD = "password";
    public static final String OLD_PASSWORD = "old_password";
    public static final String NAME = "name";
    public static final String FILE = "file";
    public static final String CONFIRM = "confirm";
    public static final String FRIEND_ID = "friend_id";
    public static final String IMG_URL = "img_url";
    public static final String THREAD = "thread";
    public static final String BOARD = "board";
    public static final String FORUM = "forum";
    public static final String KEYWORD = "keyword";

    public static final String OBJECT_NAME_DATA = "data";
    public static final String OBJECT_NAME_ERR = "err";
    public static final String OBJECT_NAME_MESSAGE = "message";

    //PIWIK相关
    public static final String PK_THREAD = "/forum/thread/";
    public static final String PK_HOME = BASE_HOST;
    public static final String PK_USER_ID = "[" + PrefUtil.getAuthUid() + "]" + " \"" + PrefUtil.getAuthUsername() + "\"";
    public static final String PK_CATEGORY_AJAX = "AjaxSender";
    public static final String PK_CATEGORY_SIGN = "Signing";
    public static final String PK_LOGIN = "/sign/in";
    public static final String PK_OLD_LOGIN = "/sign/old/login";
    public static final String PK_SIGN_UP = "/sign/up";
    public static final String PK_OLD_REGISTER = "/sign/old/register";
    public static final String PK_APPEAL = "/sign/appeal";
    public static final String PK_UPDATE_INFO = "/user/me/edit";
    public static final String PK_THREAD_LIST_OF_ONE_BOARD = "/forum/board/";
    public static final String PK_RETRIEVE = "/sign/auth";


    //所有前缀字符
    public static final String PRE_ATTACH = "attach:";
    public static final String PRE_MD_IMG = "![](" + PRE_ATTACH;

}
