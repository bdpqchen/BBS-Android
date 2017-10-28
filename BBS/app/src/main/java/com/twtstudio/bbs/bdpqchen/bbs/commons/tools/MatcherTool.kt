package com.twtstudio.bbs.bdpqchen.bbs.commons.tools

/**
 * Created by bdpqchen on 17-10-29.
 */
object MatcherTool {

    /*
    * match the uid from @ username.
    *  @bdpqchen  --> 17004
    * */
    private var atName2uid: HashMap<String, Int> = HashMap()

    @JvmStatic
    fun addAtName(name: String, uid: Int) {
        atName2uid.put(name, uid)
    }

    @JvmStatic
    fun matchAtUid(atName: String): Int? {
        if (!atName2uid.containsKey(atName)) return 0
        return atName2uid[atName]
    }



}